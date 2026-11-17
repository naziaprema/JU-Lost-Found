package lost.found;

import javax.swing.*;

/** Shared claim action for Browse Reports and Matching Reports. */
public final class ClaimActions {
    private ClaimActions() {}
    public static void open(JFrame parent, ReportStore.Report report, JButton button) {
        AccountStore.Account user=Session.current();
        if(user==null){JOptionPane.showMessageDialog(parent,"Please log in to claim an item.");return;}
        if(report==null){JOptionPane.showMessageDialog(parent,"Select a found report first.");return;}
        if(!"Found".equals(report.type())){JOptionPane.showMessageDialog(parent,"Only found items can be claimed.");return;}
        button.setEnabled(false);
        new SwingWorker<ClaimStore.Conversation,Void>(){
            protected ClaimStore.Conversation doInBackground() throws Exception {
                return new ClaimStore().claim(report.id(),user);
            }
            protected void done(){
                button.setEnabled(true);
                try{
                    ClaimStore.Conversation conversation=get();
                    if(!user.equals(Session.current()) || !parent.isDisplayable())return;
                    new messages(conversation.id()).setVisible(true);parent.dispose();
                }catch(Exception ex){
                    Throwable cause=ex.getCause()==null?ex:ex.getCause();
                    JOptionPane.showMessageDialog(parent,cause.getMessage(),"Cannot open claim",JOptionPane.WARNING_MESSAGE);
                }
            }
        }.execute();
    }
}
