package lost.found;

import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;
import javax.swing.*;

/** Complete two-account report/match/claim/message/return journey using isolated files. */
public final class ReturnChecks {
    private static int checks;
    private static void check(boolean value,String description) {
        if(!value)throw new AssertionError(description);checks++;
    }
    private interface Operation { void run() throws Exception; }
    private static void reject(Operation action,String description) throws Exception {
        try{action.run();throw new AssertionError(description);}catch(IllegalArgumentException expected){checks++;}
    }
    private static Object field(Object target,String name) {
        try{var f=target.getClass().getDeclaredField(name);f.setAccessible(true);return f.get(target);}
        catch(Exception ex){throw new RuntimeException(ex);}
    }
    private interface Condition { boolean ready() throws Exception; }
    private static void await(Condition condition,String description) throws Exception {
        long deadline=System.currentTimeMillis()+10000;
        while(System.currentTimeMillis()<deadline){if(condition.ready()){checks++;return;}Thread.sleep(30);}
        throw new AssertionError(description);
    }
    private static Properties read(Path file) throws Exception {
        Properties p=new Properties();try(var r=Files.newBufferedReader(file)){p.load(r);}return p;
    }
    public static void main(String[] args) throws Exception {
        Path data=Files.createTempDirectory(Path.of("build"),"return-check-");
        System.setProperty("campusfind.data.dir",data.toString());
        AccountStore accounts=new AccountStore(data);
        var finder=accounts.register("Test Finder","RET-F","ret-f@example.invalid","Return-Test-927!".toCharArray());
        var owner=accounts.register("Test Owner","RET-O","ret-o@example.invalid","Return-Test-927!".toCharArray());
        var other=accounts.register("Test Other","RET-X","ret-x@example.invalid","Return-Test-927!".toCharArray());
        ReportStore reports=new ReportStore(data);ClaimStore claims=new ClaimStore(data);
        Path foundFile=reports.save("Found","Black Wallet","Personal","Black leather wallet","Library",LocalDate.now(),null,finder);
        String foundId=foundFile.getFileName().toString().replace(".txt","");
        Path lostFile=reports.save("Lost","Black Wallet","Personal","Black leather wallet","Library",LocalDate.now(),null,owner);
        String lostId=lostFile.getFileName().toString().replace(".txt","");
        var loaded=reports.loadAll().reports();
        var lost=loaded.stream().filter(r->r.id().equals(lostId)).findFirst().orElseThrow();
        check(new MatchingEngine().findMatches(lost,loaded).size()==1,"lost and found match before return");
        var conversation=claims.claim(foundId,owner);
        claims.send(conversation.id(),owner,"The wallet is mine.");
        claims.send(conversation.id(),finder,"We can arrange a handover.");
        reject(()->reports.markReturned(foundId,null),"anonymous return rejected");
        reject(()->reports.markReturned(foundId,owner),"claimant cannot mark return");
        reject(()->reports.markReturned(foundId,other),"unrelated account cannot mark return");
        reject(()->reports.markReturned(lostId,owner),"lost reports cannot be marked returned");
        reject(()->reports.markReturned("../outside",finder),"invalid path rejected");
        Properties before=read(foundFile);before.setProperty("customNote","Preserve this field");
        try(var writer=Files.newBufferedWriter(foundFile)){before.store(writer,"test");}
        Session.signIn(finder);
        final browseReports[] page=new browseReports[1];
        SwingUtilities.invokeAndWait(()->page[0]=new browseReports(reports));
        await(()->{
            final boolean[] ready={false};
            SwingUtilities.invokeAndWait(()->{
                @SuppressWarnings("unchecked") var list=(List<ReportStore.Report>)field(page[0],"loadedReports");
                JTable table=(JTable)field(page[0],"reportsTable");
                for(int i=0;i<list.size();i++)if(list.get(i).id().equals(foundId)){
                    int view=table.convertRowIndexToView(i);table.setRowSelectionInterval(view,view);
                    ready[0]=((JButton)field(page[0],"returnedButton")).isEnabled();
                }
            });return ready[0];
        },"finder can select and return own found report");
        SwingUtilities.invokeAndWait(()->{
            check(page[0].getContentPane().getWidth()==760&&page[0].getContentPane().getHeight()==620,"browse page keeps its size");
            ((JButton)field(page[0],"returnedButton")).doClick();
        });
        await(()->"Returned".equals(read(foundFile).getProperty("status")),"GUI return button saves status");
        await(()->{
            final boolean[] ready={false};
            SwingUtilities.invokeAndWait(()->ready[0]=((JTextArea)field(page[0],"detailsArea")).getText().contains("Status: Returned")
                    && !((JButton)field(page[0],"returnedButton")).isEnabled());
            return ready[0];
        },"returned status displayed and button disabled");
        SwingUtilities.invokeAndWait(()->page[0].dispose());
        Properties after=read(foundFile);
        check(after.getProperty("returnedBy").equals(finder.id()),"finder recorded");
        java.time.Instant.parse(after.getProperty("returnedAt"));checks++;
        for(String key:before.stringPropertyNames())if(!key.equals("status"))
            check(Objects.equals(before.getProperty(key),after.getProperty(key)),"report field preserved: "+key);
        reports.markReturned(foundId,finder);
        check(read(foundFile).getProperty("returnedAt").equals(after.getProperty("returnedAt")),"repeated return is idempotent");
        loaded=new ReportStore(data).loadAll().reports();
        check(loaded.stream().anyMatch(r->r.id().equals(foundId)&&r.status().equals("Returned")),"returned report persists and remains browseable");
        check(new MatchingEngine().findMatches(lost,loaded).isEmpty(),"returned candidate excluded from matching");
        var returned=loaded.stream().filter(r->r.id().equals(foundId)).findFirst().orElseThrow();
        check(new MatchingEngine().findMatches(returned,loaded).isEmpty(),"returned source excluded from matching");
        reject(()->claims.claim(foundId,other),"returned item rejects new claim");
        check(claims.claim(foundId,owner).id().equals(conversation.id()),"existing claim remains accessible");
        check(new ClaimStore(data).messages(conversation.id(),owner).size()==2,"earlier messages remain");
        claims.send(conversation.id(),finder,"Returned. Thank you.");
        check(claims.messages(conversation.id(),owner).size()==3,"existing conversation still supports replies");
        // Claim creation and return serialize across stores; no new claim may start after return.
        Path race=reports.save("Found","Keys","Personal","Two keys","Library",LocalDate.now(),null,finder);
        String raceId=race.getFileName().toString().replace(".txt","");
        ExecutorService pool=Executors.newFixedThreadPool(2);
        try{
            CountDownLatch start=new CountDownLatch(1);
            Future<?> a=pool.submit(()->{try{start.await();reports.markReturned(raceId,finder);}catch(Exception ex){throw new RuntimeException(ex);}});
            Future<?> b=pool.submit(()->{try{start.await();claims.claim(raceId,other);}catch(IllegalArgumentException expected){}catch(Exception ex){throw new RuntimeException(ex);}});
            start.countDown();a.get();b.get();
        }finally{pool.shutdown();}
        check(read(race).getProperty("status").equals("Returned"),"concurrent claim and return finish consistently");
        reject(()->claims.claim(raceId,owner),"new claim after concurrent return rejected");
        Session.signOut();
        System.out.println("PASS: "+checks+" return and complete journey checks.");
        System.exit(0);
    }
}
