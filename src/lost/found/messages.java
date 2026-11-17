package lost.found;
/** A file-based claim inbox. Visible controls are defined in messages.form. */
public class messages extends javax.swing.JFrame {

    private final AccountStore.Account user;
    private final ClaimStore store = new ClaimStore();
    private final javax.swing.Timer pollTimer;
    private java.util.List<ClaimStore.Conversation> conversations = java.util.List.of();
    private boolean busy, filling;
    private String preferredId;
    private record Inbox(java.util.List<ClaimStore.Conversation> conversations,
            String selectedId, java.util.List<ClaimStore.Message> messages) {}

    public messages() { this(null); }
    public messages(String conversationId) {
        initComponents();
        user=Session.current();
        preferredId=conversationId;
        pollTimer=new javax.swing.Timer(3000, e -> refreshInbox());
        if (!Features.MESSAGING_ENABLED) return;
        if(user==null) { historyArea.setText("Log in to view your claims and messages.");sendButton.setEnabled(false); }
        else { refreshInbox();pollTimer.start(); }
    }
    @Override public void dispose() { if(pollTimer!=null)pollTimer.stop();super.dispose(); }
    @Override public void setVisible(boolean visible) {
        super.setVisible(visible && Features.MESSAGING_ENABLED);
    }
    private void go(javax.swing.JFrame page) { page.setVisible(true);dispose(); }
    private boolean sameUser() { return user!=null && user.equals(Session.current()); }
    private String selectedId() {
        int i=threadsComboBox.getSelectedIndex();
        return i>=0 && i<conversations.size()?conversations.get(i).id():null;
    }
    private void setBusy(boolean value) {
        busy=value;
        threadsComboBox.setEnabled(!value);
        refreshButton.setEnabled(!value && sameUser());
        sendButton.setEnabled(!value && sameUser() && selectedId()!=null);
    }
    private void refreshInbox() {
        if(busy || !sameUser())return;
        final String desired=preferredId!=null?preferredId:selectedId();
        setBusy(true);
        new javax.swing.SwingWorker<Inbox,Void>() {
            protected Inbox doInBackground() throws Exception {
                java.util.List<ClaimStore.Conversation> list=store.list(user);
                String id=list.stream().filter(c->c.id().equals(desired)).map(ClaimStore.Conversation::id)
                        .findFirst().orElse(list.isEmpty()?null:list.get(0).id());
                return new Inbox(list,id,id==null?java.util.List.of():store.messages(id,user));
            }
            protected void done() {
                if(!isDisplayable()) { busy=false;return; }
                try {
                    Inbox inbox=get();
                    if(!sameUser()){dispose();return;}
                    filling=true;
                    conversations=inbox.conversations();
                    threadsComboBox.removeAllItems();
                    int selected=-1;
                    for(int i=0;i<conversations.size();i++) {
                        ClaimStore.Conversation c=conversations.get(i);
                        threadsComboBox.addItem(c.itemName()+" | "+c.otherName(user.id())
                                +(c.finderId().equals(user.id())?" (claimant)":" (finder)"));
                        if(c.id().equals(inbox.selectedId()))selected=i;
                    }
                    threadsComboBox.setSelectedIndex(selected);
                    preferredId=inbox.selectedId();
                    filling=false;
                    StringBuilder text=new StringBuilder();
                    for(ClaimStore.Message m:inbox.messages()) {
                        String when=java.time.Instant.parse(m.sentAt()).atZone(java.time.ZoneId.systemDefault())
                                .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                        text.append(m.senderId().equals(user.id())?"You":m.senderName())
                                .append(" [").append(when).append("]\n").append(m.text()).append("\n\n");
                    }
                    String rendered=text.length()==0?(selected<0?"No conversations yet. Claim a found report to start.":"No messages yet. Introduce yourself to discuss the item."):text.toString();
                    if(!historyArea.getText().equals(rendered)) { historyArea.setText(rendered);historyArea.setCaretPosition(historyArea.getDocument().getLength()); }
                    historyScrollPane.setToolTipText(conversations.isEmpty()?"No claims yet.":conversations.size()+" conversation(s). Refreshes every 3 seconds.");
                    if(selected>=0) { ClaimStore.Conversation c=conversations.get(selected);historyArea.setToolTipText("Report: "+c.reportId()); }
                } catch(Exception ex) {
                    historyScrollPane.setToolTipText("Could not read messages. Use Refresh to retry.");
                    historyArea.setToolTipText(errorMessage(ex));
                } finally { filling=false;setBusy(false); }
            }
        }.execute();
    }
    private static String errorMessage(Exception ex) {
        Throwable cause=ex.getCause()==null?ex:ex.getCause();return cause.getMessage();
    }
    private void threadsComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_threadsComboBoxActionPerformed
        if(filling || busy)return;
        preferredId=selectedId();
        composeArea.setText("");
        refreshInbox();
    }//GEN-LAST:event_threadsComboBoxActionPerformed
    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        refreshInbox();
    }//GEN-LAST:event_refreshButtonActionPerformed
    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        String id=selectedId(),text=composeArea.getText();
        if(busy || !sameUser() || id==null)return;
        if(text.isBlank() || text.length()>2000){javax.swing.JOptionPane.showMessageDialog(this,"Enter a message of 1–2,000 characters.");return;}
        setBusy(true);composeArea.setEnabled(false);
        new javax.swing.SwingWorker<Void,Void>() {
            protected Void doInBackground() throws Exception { store.send(id,user,text);return null; }
            protected void done() {
                composeArea.setEnabled(true);setBusy(false);
                try{get();composeArea.setText("");preferredId=id;refreshInbox();}
                catch(Exception ex){javax.swing.JOptionPane.showMessageDialog(messages.this,errorMessage(ex),"Message not sent",javax.swing.JOptionPane.ERROR_MESSAGE);}
            }
        }.execute();
    }//GEN-LAST:event_sendButtonActionPerformed
    private void homeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeActionPerformed
        go(new dashboard());
    }//GEN-LAST:event_homeActionPerformed
    private void browseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseActionPerformed
        go(new browseReports());
    }//GEN-LAST:event_browseActionPerformed
    private void matchingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_matchingActionPerformed
        go(new matchingReports());
    }//GEN-LAST:event_matchingActionPerformed
    private void accountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accountActionPerformed
        go(new accountData());
    }//GEN-LAST:event_accountActionPerformed

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        leftPanel = new javax.swing.JPanel();
        brandLabel = new javax.swing.JLabel();
        home = new javax.swing.JButton();
        browse = new javax.swing.JButton();
        matching = new javax.swing.JButton();
        account = new javax.swing.JButton();
        contentPanel = new javax.swing.JPanel();
        headingLabel = new javax.swing.JLabel();
        threadLabel = new javax.swing.JLabel();
        threadsComboBox = new javax.swing.JComboBox();
        historyScrollPane = new javax.swing.JScrollPane();
        historyArea = new javax.swing.JTextArea();
        composeLabel = new javax.swing.JLabel();
        composeScrollPane = new javax.swing.JScrollPane();
        composeArea = new javax.swing.JTextArea();
        sendButton = new javax.swing.JButton();
        refreshButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CampusFind - Messages");
        setResizable(false);

        
        leftPanel.setPreferredSize(new java.awt.Dimension(230, 620));

        brandLabel.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        brandLabel.setText("CampusFind");

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

        javax.swing.GroupLayout leftPanelLayout = new javax.swing.GroupLayout(leftPanel);
        leftPanel.setLayout(leftPanelLayout);
        leftPanelLayout.setHorizontalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(brandLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(home, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(browse, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(matching, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(account, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        leftPanelLayout.setVerticalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftPanelLayout.createSequentialGroup()
                .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(leftPanelLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(brandLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(leftPanelLayout.createSequentialGroup()
                        .addGap(180, 180, 180)
                        .addComponent(home, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(leftPanelLayout.createSequentialGroup()
                        .addGap(240, 240, 240)
                        .addComponent(browse, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(leftPanelLayout.createSequentialGroup()
                        .addGap(300, 300, 300)
                        .addComponent(matching, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(leftPanelLayout.createSequentialGroup()
                        .addGap(360, 360, 360)
                        .addComponent(account, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(215, Short.MAX_VALUE))
        );

        contentPanel.setPreferredSize(new java.awt.Dimension(530, 620));

        headingLabel.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        headingLabel.setText("My Messages / Claims");

        threadLabel.setText("Conversation");

        threadsComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                threadsComboBoxActionPerformed(evt);
            }
        });

        historyArea.setEditable(false);
        historyArea.setLineWrap(true);
        historyArea.setWrapStyleWord(true);
        historyScrollPane.setViewportView(historyArea);

        composeLabel.setText("Your message (up to 2,000 characters)");

        composeArea.setLineWrap(true);
        composeArea.setWrapStyleWord(true);
        composeScrollPane.setViewportView(composeArea);

        sendButton.setText("Send");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        refreshButton.setText("Refresh");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout contentPanelLayout = new javax.swing.GroupLayout(contentPanel);
        contentPanel.setLayout(contentPanelLayout);
        contentPanelLayout.setHorizontalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentPanelLayout.createSequentialGroup()
                .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contentPanelLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(headingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(contentPanelLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(threadLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(contentPanelLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(threadsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(contentPanelLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(historyScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(contentPanelLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(composeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(contentPanelLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(composeScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(contentPanelLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(contentPanelLayout.createSequentialGroup()
                        .addGap(280, 280, 280)
                        .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        contentPanelLayout.setVerticalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentPanelLayout.createSequentialGroup()
                .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contentPanelLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(headingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(contentPanelLayout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(threadLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(contentPanelLayout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(threadsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(contentPanelLayout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addComponent(historyScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(contentPanelLayout.createSequentialGroup()
                        .addGap(405, 405, 405)
                        .addComponent(composeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(contentPanelLayout.createSequentialGroup()
                        .addGap(435, 435, 435)
                        .addComponent(composeScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(contentPanelLayout.createSequentialGroup()
                        .addGap(530, 530, 530)
                        .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(contentPanelLayout.createSequentialGroup()
                        .addGap(530, 530, 530)
                        .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(55, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(leftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(230, 230, 230)
                .addComponent(contentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(leftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(contentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
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
brandLabel.setForeground(new java.awt.Color(255,255,255));
pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton account;
    private javax.swing.JLabel brandLabel;
    private javax.swing.JButton browse;
    private javax.swing.JTextArea composeArea;
    private javax.swing.JLabel composeLabel;
    private javax.swing.JScrollPane composeScrollPane;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JLabel headingLabel;
    private javax.swing.JTextArea historyArea;
    private javax.swing.JScrollPane historyScrollPane;
    private javax.swing.JButton home;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JButton matching;
    private javax.swing.JButton refreshButton;
    private javax.swing.JButton sendButton;
    private javax.swing.JLabel threadLabel;
    private javax.swing.JComboBox threadsComboBox;
    // End of variables declaration//GEN-END:variables
public static void main(String[] args){java.awt.EventQueue.invokeLater(()->(Features.MESSAGING_ENABLED ? new messages() : new dashboard()).setVisible(true));}
}
