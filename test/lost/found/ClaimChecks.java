package lost.found;

import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;
import javax.swing.*;

/** Isolated checks: real user accounts, reports and messages are never touched. */
public final class ClaimChecks {
    private static int checks;
    private static void check(boolean condition,String description) {
        if(!condition)throw new AssertionError(description);checks++;
    }
    private interface Operation { void run() throws Exception; }
    private static void reject(Operation action,String description) throws Exception {
        try { action.run();throw new AssertionError(description); }
        catch(IllegalArgumentException ex) { checks++; }
    }
    public static void main(String[] args) throws Exception {
        if (!Features.MESSAGING_ENABLED) { System.out.println("SKIP: messaging GUI deferred for presentation; run DemoChecks instead."); return; }
        Path data=Files.createTempDirectory(Path.of("build"),"claim-check-");
        System.setProperty("campusfind.data.dir",data.toString());
        AccountStore accounts=new AccountStore(data);
        char[] password="Synthetic-Claim-Test-927!".toCharArray();
        var finder=accounts.register("Test Finder","F-1","finder@example.invalid",password);
        var claimant=accounts.register("Test Claimant","C-1","claimant@example.invalid",password);
        var outsider=accounts.register("Test Other","O-1","other@example.invalid",password);
        Arrays.fill(password,'\0');
        ReportStore reports=new ReportStore(data);
        Path foundFile=reports.save("Found","Black Wallet","Personal","Black leather wallet",
                "Library",LocalDate.now(),null,finder);
        String reportId=foundFile.getFileName().toString().replace(".txt","");
        var report=reports.loadAll().reports().get(0);
        check(report.authorId().equals(finder.id()),"report author persists");
        check(report.authorName().equals(finder.fullName()),"report author name persists");
        ClaimStore claims=new ClaimStore(data);
        reject(()->claims.claim(reportId,null),"anonymous claim rejected");
        reject(()->claims.claim(reportId,finder),"self claim rejected");
        var c=claims.claim(reportId,claimant);
        check(c.finderId().equals(finder.id()) && c.claimantId().equals(claimant.id()),"correct participants");
        check(new ClaimStore(data).claim(reportId,claimant).id().equals(c.id()),"duplicate claim reopens conversation");
        check(claims.list(finder).size()==1 && claims.list(claimant).size()==1,"both inboxes contain claim");
        check(claims.list(outsider).isEmpty(),"unrelated inbox hides conversation");
        reject(()->claims.messages(c.id(),outsider),"unrelated read rejected");
        reject(()->claims.send(c.id(),outsider,"No access"),"unrelated send rejected");
        reject(()->claims.messages("../outside",claimant),"path traversal rejected");
        reject(()->claims.send(c.id(),claimant,"  "),"blank message rejected");
        reject(()->claims.send(c.id(),claimant,"x".repeat(2001)),"oversized message rejected");
        String first="This may be mine.\nআমার মানিব্যাগ 🙂 = : \\";
        claims.send(c.id(),claimant,first);
        claims.send(c.id(),finder,"Please describe what was inside.");
        var history=new ClaimStore(data).messages(c.id(),finder);
        check(history.size()==2,"messages persist after reopening");
        check(history.get(0).text().equals(first),"Unicode, newlines and punctuation round trip");
        check(history.get(0).senderId().equals(claimant.id()) && history.get(1).senderId().equals(finder.id()),"both directions retain authors");
        var second=claims.claim(reportId,outsider);
        check(!second.id().equals(c.id()),"separate claimant conversation");
        check(claims.messages(second.id(),outsider).isEmpty(),"claimant conversations stay separate");
        reject(()->claims.messages(second.id(),claimant),"claimant cannot read another claimant");
        ExecutorService workers=Executors.newFixedThreadPool(4);
        try {
            List<Future<?>> sends=new ArrayList<>();
            for(int i=0;i<12;i++) {
                final int number=i;
                sends.add(workers.submit(()->{try{new ClaimStore(data).send(c.id(),number%2==0?finder:claimant,"Concurrent "+number);}catch(Exception ex){throw new RuntimeException(ex);}}));
            }
            for(Future<?> send:sends)send.get();
        } finally { workers.shutdown(); }
        check(claims.messages(c.id(),claimant).size()==14,"simultaneous sends do not overwrite messages");
        Path legacy=reports.save("Found","Legacy Bottle","Other","Older report","Library",LocalDate.now(),null);
        String legacyId=legacy.getFileName().toString().replace(".txt","");
        reject(()->claims.claim(legacyId,claimant),"legacy report cannot invent finder");
        Path lost=reports.save("Lost","Phone","Electronics","Lost phone","Library",LocalDate.now(),null,finder);
        reject(()->claims.claim(lost.getFileName().toString().replace(".txt",""),claimant),"lost reports cannot be claimed");
        Properties closed=new Properties();
        try(var reader=Files.newBufferedReader(foundFile)){closed.load(reader);}
        closed.setProperty("status","Closed");
        try(var writer=Files.newBufferedWriter(foundFile)){closed.store(writer,"closed test report");}
        var fourth=accounts.register("Test Fourth","T-4","fourth@example.invalid","Fourth-Test-927!".toCharArray());
        reject(()->claims.claim(reportId,fourth),"closed reports reject new claims");
        check(claims.claim(reportId,claimant).id().equals(c.id()),"existing conversation remains accessible after closure");
        check(reports.loadAll().reports().stream().anyMatch(r->r.id().equals(legacyId)&&r.authorId().isBlank()),"legacy reports remain readable");
        Session.signIn(claimant);
        final messages[] window=new messages[1];
        SwingUtilities.invokeAndWait(()->{
            window[0]=new messages(c.id());
            check(window[0].getContentPane().getWidth()==760&&window[0].getContentPane().getHeight()==620,"messages page size");
        });
        // Await the inbox worker, rather than guessing its completion time.
        long deadline=System.currentTimeMillis()+10000;
        boolean loaded=false;
        while(System.currentTimeMillis()<deadline && !loaded){
            final boolean[] done={false};
            SwingUtilities.invokeAndWait(()->{
                try{
                    var field=messages.class.getDeclaredField("historyArea");field.setAccessible(true);
                    done[0]=((JTextArea)field.get(window[0])).getText().contains(first);
                }catch(Exception ex){throw new RuntimeException(ex);}
            });
            loaded=done[0];if(!loaded)Thread.sleep(30);
        }
        check(loaded,"inbox renders persisted conversation");
        SwingUtilities.invokeAndWait(()->{
            try{
                var draft=messages.class.getDeclaredField("composeArea");draft.setAccessible(true);
                ((JTextArea)draft.get(window[0])).setText("UI sender test");
                var send=messages.class.getDeclaredField("sendButton");send.setAccessible(true);
                ((JButton)send.get(window[0])).doClick();
            }catch(Exception ex){throw new RuntimeException(ex);}
        });
        deadline=System.currentTimeMillis()+10000;boolean sent=false;
        while(System.currentTimeMillis()<deadline && !sent){
            sent=claims.messages(c.id(),finder).stream().anyMatch(m->m.text().equals("UI sender test"));
            if(!sent)Thread.sleep(30);
        }
        check(sent,"Send button persists claimant message for finder");
        // Wait until send completion re-enables composition before testing incoming polling.
        final boolean[] ready={false};
        deadline=System.currentTimeMillis()+10000;
        while(System.currentTimeMillis()<deadline && !ready[0]){
            SwingUtilities.invokeAndWait(()->{try{var f=messages.class.getDeclaredField("composeArea");f.setAccessible(true);ready[0]=((JTextArea)f.get(window[0])).isEnabled();}catch(Exception ex){throw new RuntimeException(ex);}});
            if(!ready[0])Thread.sleep(30);
        }
        check(ready[0],"composer re-enabled after sending");
        SwingUtilities.invokeAndWait(()->{try{var f=messages.class.getDeclaredField("composeArea");f.setAccessible(true);((JTextArea)f.get(window[0])).setText("Unsent draft");}catch(Exception ex){throw new RuntimeException(ex);}});
        claims.send(c.id(),finder,"Incoming polling test");
        deadline=System.currentTimeMillis()+10000;loaded=false;
        while(System.currentTimeMillis()<deadline && !loaded){
            final boolean[] done={false};
            SwingUtilities.invokeAndWait(()->{try{var f=messages.class.getDeclaredField("historyArea");f.setAccessible(true);done[0]=((JTextArea)f.get(window[0])).getText().contains("Incoming polling test");}catch(Exception ex){throw new RuntimeException(ex);}});
            loaded=done[0];if(!loaded)Thread.sleep(30);
        }
        check(loaded,"incoming reply appears automatically");
        SwingUtilities.invokeAndWait(()->{try{var f=messages.class.getDeclaredField("composeArea");f.setAccessible(true);check(((JTextArea)f.get(window[0])).getText().equals("Unsent draft"),"polling preserves unsent draft");}catch(Exception ex){throw new RuntimeException(ex);}});
        SwingUtilities.invokeAndWait(()->window[0].dispose());
        Session.signOut();
        System.out.println("PASS: "+checks+" claims, authorization, persistence, concurrent message, compatibility and inbox checks.");
        System.exit(0);
    }
}
