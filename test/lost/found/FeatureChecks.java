package lost.found;

import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;
import javax.swing.*;

/** Run with assertions enabled; uses isolated temporary data, never real accounts. */
public class FeatureChecks {
    private static void check(boolean ok, String message) {
        if (!ok) throw new AssertionError(message);
    }
    private static ReportStore.Report report(String id, String type, String name, int days, String status) {
        return new ReportStore.Report(id,type,name,"Electronics","white charging case",
                "Library",LocalDate.of(2026,1,10).plusDays(days),"",status,"");
    }
    public static void main(String[] args) throws Exception {
        Path directory=Files.createTempDirectory(Path.of("build"),"account-check-");
        System.setProperty("campusfind.data.dir",directory.toString());
        AccountStore store=new AccountStore(directory);
        char[] password="Synthetic-Test-927!".toCharArray();
        AccountStore.Account first=store.register("Test Student","TEST-1","test@example.invalid",password);
        check(new AccountStore(directory).authenticate("TEST@example.invalid",password).orElseThrow().equals(first),"email login/reload");
        check(store.authenticate("test-1",password).isPresent(),"student ID login");
        check(store.authenticate("TEST-1","wrong-password".toCharArray()).isEmpty(),"wrong password rejected");
        check(store.authenticate("missing",password).isEmpty(),"unknown user rejected");
        boolean duplicate=false;
        try{store.register("Other","TEST-2","TEST@example.invalid",password);}catch(IllegalArgumentException ex){duplicate=true;}
        check(duplicate,"duplicate email rejected");
        duplicate=false;
        try{store.register("Other","test-1","other@example.invalid",password);}catch(IllegalArgumentException ex){duplicate=true;}
        check(duplicate,"duplicate student ID rejected");
        boolean weak=false;
        try{store.register("Other","TEST-2","other@example.invalid","short".toCharArray());}catch(IllegalArgumentException ex){weak=true;}
        check(weak,"short password rejected");
        store.register("Other","TEST-2","other@example.invalid",password);
        List<Properties> saved=new ArrayList<>();
        try(var files=Files.newDirectoryStream(directory.resolve("users"),"*.txt")){
            for(Path file:files){String text=Files.readString(file);check(!text.contains(new String(password)),"no plaintext password");
                Properties p=new Properties();try(var reader=Files.newBufferedReader(file)){p.load(reader);}saved.add(p);}
        }
        check(!saved.get(0).getProperty("salt").equals(saved.get(1).getProperty("salt")),"unique salts");
        check(!saved.get(0).getProperty("passwordHash").equals(saved.get(1).getProperty("passwordHash")),"salted hashes differ");
        Session.signIn(first);check(Session.current().equals(first),"session login");Session.signOut();check(Session.current()==null,"logout");
        Arrays.fill(password,'\0');
        MatchingEngine engine=new MatchingEngine();
        var source=report("lost","Lost","White AirPods",0,"Active");
        var exact=report("exact","Found","white earbuds",0,"Active");
        var later=report("later","Found","white earbud",5,"Active");
        var results=engine.findMatches(source,List.of(source,exact,later,
                report("same","Lost","White AirPods",0,"Active"),
                report("closed","Found","white earbuds",0,"Closed"),
                report("early","Found","white earbuds",-1,"Active"),
                report("unrelated","Found","white bottle",0,"Active")));
        check(results.size()==2,"synonyms and exclusion rules");
        check(results.get(0).report().id().equals("exact")&&results.get(0).score()==100,"ranking and exact normalized score");
        check(results.get(1).score()<100,"date affects score");
        check(results.get(0).explanation().contains("Name:"),"explanation");
        check(engine.findMatches(exact,List.of(source)).size()==1,"found-to-lost matching");
        check(engine.findMatches(null,List.of()).isEmpty(),"empty source");
        check(engine.findMatches(report("closed","Lost","earbud",0,"Closed"),List.of(exact)).isEmpty(),"closed source excluded");
        SwingUtilities.invokeAndWait(()->{
            for(JFrame page:List.of(new createAccount(),new matchingReports(),new accountData(),new login())){
                check(page.getContentPane().getWidth()==760&&page.getContentPane().getHeight()==620,"consistent page size: "+page.getClass());
                page.dispose();
            }
        });
        System.out.println("PASS: registration, reload, login, duplicates, hashing, logout, matching, exclusions, ranking and GUI sizes.");
        System.exit(0);
    }
}
