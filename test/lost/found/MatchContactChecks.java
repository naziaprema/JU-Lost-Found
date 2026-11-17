package lost.found;

import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;
import javax.swing.*;

/** Matching contact opens the correct existing conversation in either direction. */
public final class MatchContactChecks {
    private static void check(boolean ok,String message){if(!ok)throw new AssertionError(message);}
    private static String id(Path file){return file.getFileName().toString().replace(".txt","");}
    private static Object field(Object target,String name)throws Exception{
        var f=target.getClass().getDeclaredField(name);f.setAccessible(true);return f.get(target);
    }
    private interface Operation{void run()throws Exception;}
    private static void reject(Operation action)throws Exception{
        try{action.run();throw new AssertionError("Expected rejection");}catch(IllegalArgumentException expected){}
    }
    public static void main(String[] args)throws Exception{
        Path data=Files.createTempDirectory(Path.of("build"),"match-contact-check-");
        System.setProperty("campusfind.data.dir",data.toString());
        AccountStore accounts=new AccountStore(data);
        var finder=accounts.register("Test Finder","MC-F","mc-f@example.invalid","Contact-Test-927!".toCharArray());
        var owner=accounts.register("Test Owner","MC-O","mc-o@example.invalid","Contact-Test-927!".toCharArray());
        var other=accounts.register("Test Other","MC-X","mc-x@example.invalid","Contact-Test-927!".toCharArray());
        ReportStore reports=new ReportStore(data);ClaimStore claims=new ClaimStore(data);
        String found=id(reports.save("Found","Blue Wallet","Personal","Blue leather wallet","Library",LocalDate.now(),null,finder));
        String lost=id(reports.save("Lost","Blue Wallet","Personal","Blue leather wallet","Library",LocalDate.now(),null,owner));
        var c=claims.contactMatch(lost,found,owner);
        check(c.finderId().equals(finder.id())&&c.claimantId().equals(owner.id()),"correct participants");
        check(claims.contactMatch(found,lost,finder).id().equals(c.id()),"finder contacts matched owner in same thread");
        check(claims.contactMatch(lost,found,finder).id().equals(c.id()),"finder can use either source direction");
        check(claims.contactMatch(found,lost,owner).id().equals(c.id()),"owner can use either source direction");
        reject(()->claims.contactMatch(found,lost,other));
        reject(()->claims.contactMatch(lost,found,null));
        reject(()->claims.contactMatch(lost,"missing",owner));
        reject(()->claims.contactMatch(found,found,finder));
        Session.signIn(owner);
        final matchingReports[] page=new matchingReports[1];
        SwingUtilities.invokeAndWait(()->page[0]=new matchingReports());
        long deadline=System.currentTimeMillis()+10000;boolean ready=false;
        while(!ready&&System.currentTimeMillis()<deadline){
            final boolean[] done={false};
            SwingUtilities.invokeAndWait(()->{try{
                @SuppressWarnings("unchecked") var list=(List<ReportStore.Report>)field(page[0],"reports");
                for(int i=0;i<list.size();i++)if(list.get(i).id().equals(lost)){
                    ((JComboBox<?>)field(page[0],"source")).setSelectedIndex(i);
                    done[0]=((JComboBox<?>)field(page[0],"results")).getItemCount()==1;
                }
            }catch(Exception ex){throw new RuntimeException(ex);}});
            ready=done[0];if(!ready)Thread.sleep(30);
        }
        check(ready,"matching results loaded");
        SwingUtilities.invokeAndWait(()->{try{
            JButton button=(JButton)field(page[0],"messagesButton");
            check(button.getText().equals("Message Selected Match"),"direct contact button label");
            check(page[0].getContentPane().getWidth()==760&&page[0].getContentPane().getHeight()==620,"page size");
            java.awt.Point point=SwingUtilities.convertPoint(button,button.getWidth()/2,button.getHeight()/2,page[0].getContentPane());
            check(SwingUtilities.getDeepestComponentAt(page[0].getContentPane(),point.x,point.y)==button,"contact button is visible and not covered by a panel");
            button.doClick();
        }catch(Exception ex){throw new RuntimeException(ex);}});
        deadline=System.currentTimeMillis()+10000;boolean opened=false;
        while(!opened&&System.currentTimeMillis()<deadline){
            final boolean[] done={false};
            SwingUtilities.invokeAndWait(()->{
                for(var window:java.awt.Window.getWindows())if(window instanceof messages chat&&chat.isDisplayable()){
                    try{done[0]=c.id().equals(field(chat,"preferredId"));chat.dispose();}catch(Exception ex){throw new RuntimeException(ex);}
                }
            });opened=done[0];if(!opened)Thread.sleep(30);
        }
        check(opened,"button opens selected match conversation, not generic inbox");
        check(!page[0].isDisplayable(),"matching page closes after successful navigation");
        reports.markReturned(found,finder);
        reject(()->claims.contactMatch(lost,found,owner));
        Session.signOut();
        System.out.println("PASS: matching contact directions, existing thread reuse, access checks, stale matches and direct GUI navigation.");
        System.exit(0);
    }
}
