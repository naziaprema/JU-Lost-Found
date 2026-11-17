package lost.found;
import javax.swing.*;
import java.util.*;
public class matchingReports extends JFrame {
public matchingReports(){initComponents();loadReports();}
private void go(JFrame page){page.setVisible(true);dispose();}
private void homeActionPerformed(java.awt.event.ActionEvent e){//GEN-FIRST:event_homeActionPerformed
go(new dashboard());}//GEN-LAST:event_homeActionPerformed
private void browseActionPerformed(java.awt.event.ActionEvent e){//GEN-FIRST:event_browseActionPerformed
go(new browseReports());}//GEN-LAST:event_browseActionPerformed
private void matchingActionPerformed(java.awt.event.ActionEvent e){//GEN-FIRST:event_matchingActionPerformed
go(new matchingReports());}//GEN-LAST:event_matchingActionPerformed
private void accountActionPerformed(java.awt.event.ActionEvent e){//GEN-FIRST:event_accountActionPerformed
go(new accountData());}//GEN-LAST:event_accountActionPerformed

private java.util.List<ReportStore.Report> reports=java.util.List.of();
private java.util.List<MatchingEngine.Match> matches=java.util.List.of();
private boolean loading;
private void refreshActionPerformed(java.awt.event.ActionEvent e){//GEN-FIRST:event_refreshActionPerformed
loadReports();}//GEN-LAST:event_refreshActionPerformed
private void findActionPerformed(java.awt.event.ActionEvent e){//GEN-FIRST:event_findActionPerformed
findMatches();}//GEN-LAST:event_findActionPerformed
private void sourceActionPerformed(java.awt.event.ActionEvent e){//GEN-FIRST:event_sourceActionPerformed
if(!loading)findMatches();}//GEN-LAST:event_sourceActionPerformed
private void resultActionPerformed(java.awt.event.ActionEvent e){//GEN-FIRST:event_resultActionPerformed

int i=results.getSelectedIndex();
if(i<0||i>=matches.size()){details.setText("");return;}
MatchingEngine.Match match=matches.get(i);ReportStore.Report r=match.report();
details.setText("Item: "+r.itemName()+"\nType: "+r.type()+"\nCategory: "+r.category()+"\nLocation: "+r.location()+"\nDate: "+r.date()+"\nDescription: "+r.description()+"\n\nSimilarity: "+match.score()+"/100\n"+match.explanation()+"\n\nReport ID: "+r.id());
details.setCaretPosition(0);
}//GEN-LAST:event_resultActionPerformed
private void findMatches(){
int i=source.getSelectedIndex();matches=java.util.List.of();results.removeAllItems();details.setText("");
if(i<0||i>=reports.size()){status.setText("No saved reports to match.");return;}
matches=new MatchingEngine().findMatches(reports.get(i),reports);
for(MatchingEngine.Match m:matches)results.addItem(m.score()+"/100 | "+m.report().itemName()+" | "+m.report().location());
status.setText(matches.isEmpty()?"No suitable opposite-type reports found.":matches.size()+" possible match(es). Select one below.");
resultActionPerformed(null);
}
private void loadReports(){
refresh.setEnabled(false);find.setEnabled(false);source.setEnabled(false);status.setText("Loading reports...");
new SwingWorker<ReportStore.LoadResult,Void>(){
protected ReportStore.LoadResult doInBackground() throws Exception{return new ReportStore().loadAll();}
protected void done(){
refresh.setEnabled(true);find.setEnabled(true);source.setEnabled(true);
try{ReportStore.LoadResult loaded=get();reports=loaded.reports().stream().filter(r->"Active".equalsIgnoreCase(r.status())).toList();
loading=true;source.removeAllItems();for(ReportStore.Report r:reports)source.addItem(r.type()+" | "+r.itemName()+" | "+r.date()+" | "+r.id());loading=false;findMatches();
if(loaded.skippedFiles()>0)status.setText(status.getText()+" Skipped "+loaded.skippedFiles()+" unreadable file(s).");
}catch(Exception ex){loading=false;status.setText("Could not load reports.");JOptionPane.showMessageDialog(matchingReports.this,"Unable to read report files: "+ex.getMessage());}
}
}.execute();
}

    private void claimButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_claimButtonActionPerformed
int index=results.getSelectedIndex(); ClaimActions.open(this,index>=0 && index<matches.size()?matches.get(index).report():null,claimButton);
}//GEN-LAST:event_claimButtonActionPerformed
private void messagesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_messagesButtonActionPerformed
int sourceIndex=source.getSelectedIndex(),matchIndex=results.getSelectedIndex();
ClaimActions.openMatch(this,
    sourceIndex>=0 && sourceIndex<reports.size()?reports.get(sourceIndex):null,
    matchIndex>=0 && matchIndex<matches.size()?matches.get(matchIndex).report():null,
    messagesButton);
}//GEN-LAST:event_messagesButtonActionPerformed
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        brand = new javax.swing.JLabel();
        home = new javax.swing.JButton();
        browse = new javax.swing.JButton();
        matching = new javax.swing.JButton();
        account = new javax.swing.JButton();
        heading = new javax.swing.JLabel();
        sourceLabel = new javax.swing.JLabel();
        source = new javax.swing.JComboBox();
        find = new javax.swing.JButton();
        refresh = new javax.swing.JButton();
        status = new javax.swing.JLabel();
        results = new javax.swing.JComboBox();
        detailsScrollPane = new javax.swing.JScrollPane();
        details = new javax.swing.JTextArea();
        leftPanel = new javax.swing.JPanel();
        claimButton = new javax.swing.JButton();
        messagesButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CampusFind - Matching Reports");
        setResizable(false);

        brand.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        brand.setText("CampusFind");

        home.setText("Dashboard");
        home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homeActionPerformed(evt);
            }
        });

        browse.setText("Browse Reports");
        browse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseActionPerformed(evt);
            }
        });

        matching.setText("Matching Reports");
        matching.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                matchingActionPerformed(evt);
            }
        });

        account.setText("Account Data");
        account.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accountActionPerformed(evt);
            }
        });

        heading.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        heading.setText("Find Matching Reports");

        sourceLabel.setText("Choose a lost or found report");

        source.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sourceActionPerformed(evt);
            }
        });

        find.setText("Find Matches");
        find.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findActionPerformed(evt);
            }
        });

        refresh.setText("Refresh");
        refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshActionPerformed(evt);
            }
        });

        status.setText("Loading reports...");

        results.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resultActionPerformed(evt);
            }
        });

        details.setText("Select a match to view details.");
        details.setEditable(false);
        details.setLineWrap(true);
        details.setWrapStyleWord(true);
        detailsScrollPane.setViewportView(details);

        

        javax.swing.GroupLayout leftPanelLayout = new javax.swing.GroupLayout(leftPanel);
        leftPanel.setLayout(leftPanelLayout);
        leftPanelLayout.setHorizontalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 230, Short.MAX_VALUE)
        );
        leftPanelLayout.setVerticalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 620, Short.MAX_VALUE)
        );

        claimButton.setText("Claim / Contact Finder");
        claimButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                claimButtonActionPerformed(evt);
            }
        });

        messagesButton.setText("Message Selected Match");
        messagesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                messagesButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(260,260,260).addComponent(messagesButton,220,220,220).addGap(280,280,Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(brand, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(home, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(browse, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(matching, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(account, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(260, 260, 260)
                        .addComponent(heading, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(260, 260, 260)
                        .addComponent(sourceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(260, 260, 260)
                        .addComponent(source, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(260, 260, 260)
                        .addComponent(find, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(510, 510, 510)
                        .addComponent(refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(260, 260, 260)
                        .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(260, 260, 260)
                        .addComponent(results, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(260, 260, 260)
                        .addComponent(detailsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(leftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(claimButton, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(575,575,575).addComponent(messagesButton,30,30,30).addGap(15,15,Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(brand, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(180, 180, 180)
                .addComponent(home, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(240, 240, 240)
                .addComponent(browse, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(300, 300, 300)
                .addComponent(matching, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(360, 360, 360)
                .addComponent(account, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(heading, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(sourceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addComponent(source, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(160, 160, 160)
                .addComponent(find, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(160, 160, 160)
                .addComponent(refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(210, 210, 210)
                .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(250, 250, 250)
                .addComponent(results, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(305, 305, 305)
                .addComponent(detailsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(leftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(435, 435, 435)
                .addComponent(claimButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
            
        );

        leftPanel.setBackground(new java.awt.Color(15,35,61));
leftPanel.setForeground(new java.awt.Color(255,255,255));
home.setBackground(new java.awt.Color(27,53,85));
home.setForeground(new java.awt.Color(255,255,255));
browse.setBackground(new java.awt.Color(27,53,85));
browse.setForeground(new java.awt.Color(255,255,255));
matching.setBackground(new java.awt.Color(27,53,85));
matching.setForeground(new java.awt.Color(255,255,255));
account.setBackground(new java.awt.Color(27,53,85));
account.setForeground(new java.awt.Color(255,255,255));
claimButton.setBackground(new java.awt.Color(27,53,85));
claimButton.setForeground(new java.awt.Color(255,255,255));
brand.setForeground(new java.awt.Color(255,255,255));
pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton account;
    private javax.swing.JLabel brand;
    private javax.swing.JButton browse;
    private javax.swing.JButton claimButton;
    private javax.swing.JTextArea details;
    private javax.swing.JScrollPane detailsScrollPane;
    private javax.swing.JButton find;
    private javax.swing.JLabel heading;
    private javax.swing.JButton home;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JButton matching;
    private javax.swing.JButton messagesButton;
    private javax.swing.JButton refresh;
    private javax.swing.JComboBox results;
    private javax.swing.JComboBox source;
    private javax.swing.JLabel sourceLabel;
    private javax.swing.JLabel status;
    // End of variables declaration//GEN-END:variables
public static void main(String[] args){java.awt.EventQueue.invokeLater(()->new matchingReports().setVisible(true));}
}
