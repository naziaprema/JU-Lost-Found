package lost.found;

import java.awt.*;
import java.nio.file.*;
import javax.swing.*;

/** Confirms that no demo page contains a messaging/contact entry point. */
public final class DemoChecks {
    private static void check(boolean condition,String message) {
        if(!condition)throw new AssertionError(message);
    }
    private static void inspect(Container parent) {
        for(Component component:parent.getComponents()) {
            if(component instanceof AbstractButton button) {
                String text=String.valueOf(button.getText()).toLowerCase(java.util.Locale.ROOT);
                check(!text.contains("message")&&!text.contains("contact")&&!text.contains("claim"),
                        "Messaging entry point remains: "+text);
            }
            if(component instanceof Container child)inspect(child);
        }
    }
    public static void main(String[] args)throws Exception {
        check(!Features.MESSAGING_ENABLED,"demo flag must disable messaging");
        check(!Files.exists(Path.of("src/lost/found/messages.form")),"message form should not be previewable");
        check(Files.exists(Path.of("deferred-messaging/ui-backup/messages.form.disabled"))
                || Files.exists(Path.of("deferred-messaging.zip")),"original form retained");
        Path data=Files.createTempDirectory(Path.of("build"),"demo-check-");
        System.setProperty("campusfind.data.dir",data.toString());
        SwingUtilities.invokeAndWait(()->{
            for(JFrame page:new JFrame[]{new login(),new createAccount(),new dashboard(),
                    new reportForm("Lost"),new reportForm("Found"),new browseReports(),
                    new matchingReports(),new accountData()}) {
                inspect(page.getContentPane());
                check(page.getContentPane().getWidth()==760&&page.getContentPane().getHeight()==620,
                        "Page size changed: "+page.getClass().getSimpleName());
                page.dispose();
            }
            messages hidden=new messages();
            hidden.setVisible(true);
            check(!hidden.isVisible(),"message window must remain hidden");
            hidden.dispose();
            JFrame parent=new dashboard();
            ClaimActions.open(parent,null,new JButton());
            ClaimActions.openMatch(parent,null,null,new JButton());
            check(parent.isDisplayable(),"disabled actions must not navigate");
            parent.dispose();
        });
        check(!Files.exists(data.resolve("claims")),"demo previews must not write conversations");
        System.out.println("PASS: all eight demo pages omit messaging/contact controls; message preview and actions are blocked; sizes and backups preserved.");
        System.exit(0);
    }
}
