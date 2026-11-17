package lost.found;

/** A simple dashboard editable with the NetBeans GUI Builder. */
public class dashboard extends javax.swing.JFrame {

    public dashboard() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        leftPanel = new javax.swing.JPanel();
        brandLabel = new javax.swing.JLabel();
        navDashboardButton = new javax.swing.JButton();
        navBrowseButton = new javax.swing.JButton();
        navMatchingButton = new javax.swing.JButton();
        navAccountButton = new javax.swing.JButton();
        contentPanel = new javax.swing.JPanel();
        headingLabel = new javax.swing.JLabel();
        welcomeLabel = new javax.swing.JLabel();
        reportLostButton = new javax.swing.JButton();
        reportFoundButton = new javax.swing.JButton();
        browseButton = new javax.swing.JButton();
        matchingButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CampusFind - Dashboard");
        setResizable(false);

        leftPanel.setBackground(new java.awt.Color(228, 228, 228));
        leftPanel.setForeground(new java.awt.Color(0, 0, 153));
        leftPanel.setPreferredSize(new java.awt.Dimension(230, 620));

        brandLabel.setBackground(new java.awt.Color(0, 0, 0));
        brandLabel.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        brandLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        brandLabel.setText("CampusFind");

        navDashboardButton.setBackground(new java.awt.Color(102, 204, 255));
        navDashboardButton.setText("Dashboard");
        navDashboardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                navDashboardButtonActionPerformed(evt);
            }
        });

        navBrowseButton.setBackground(new java.awt.Color(102, 204, 255));
        navBrowseButton.setText("Browse Reports");
        navBrowseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                navBrowseButtonActionPerformed(evt);
            }
        });

        navMatchingButton.setBackground(new java.awt.Color(102, 204, 255));
        navMatchingButton.setText("Matching Reports");
        navMatchingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                navMatchingButtonActionPerformed(evt);
            }
        });

        navAccountButton.setBackground(new java.awt.Color(102, 204, 255));
        navAccountButton.setText("Account Data");
        navAccountButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                navAccountButtonActionPerformed(evt);
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
                    .addComponent(navDashboardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(navBrowseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(navMatchingButton, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(navAccountButton, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 29, Short.MAX_VALUE))
        );
        leftPanelLayout.setVerticalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftPanelLayout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(brandLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addComponent(navDashboardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(navBrowseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(navMatchingButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(navAccountButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        contentPanel.setPreferredSize(new java.awt.Dimension(530, 620));

        headingLabel.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        headingLabel.setText("Dashboard");

        welcomeLabel.setText("Welcome! Choose an option below.");

        reportLostButton.setBackground(new java.awt.Color(0, 204, 255));
        reportLostButton.setText("Report Lost Item");
        reportLostButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportLostButtonActionPerformed(evt);
            }
        });

        reportFoundButton.setBackground(new java.awt.Color(0, 204, 255));
        reportFoundButton.setText("Report Found Item");
        reportFoundButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportFoundButtonActionPerformed(evt);
            }
        });

        browseButton.setBackground(new java.awt.Color(0, 204, 255));
        browseButton.setText("Browse Reports");
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseButtonActionPerformed(evt);
            }
        });

        matchingButton.setBackground(new java.awt.Color(0, 204, 255));
        matchingButton.setText("Matching");
        matchingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                matchingButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout contentPanelLayout = new javax.swing.GroupLayout(contentPanel);
        contentPanel.setLayout(contentPanelLayout);
        contentPanelLayout.setHorizontalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentPanelLayout.createSequentialGroup()
                .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contentPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(welcomeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(contentPanelLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(contentPanelLayout.createSequentialGroup()
                                .addComponent(reportLostButton, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(reportFoundButton, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(headingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(browseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contentPanelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(matchingButton, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(35, 35, 35))
        );
        contentPanelLayout.setVerticalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentPanelLayout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(headingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(welcomeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contentPanelLayout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(reportLostButton, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(reportFoundButton, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(contentPanelLayout.createSequentialGroup()
                        .addGap(167, 167, 167)
                        .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(browseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(matchingButton, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(231, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(230, 230, 230)
                        .addComponent(contentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(leftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void reportLostButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportLostButtonActionPerformed
        new reportForm("Lost").setVisible(true);
        dispose();
    }//GEN-LAST:event_reportLostButtonActionPerformed

    private void reportFoundButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportFoundButtonActionPerformed
        new reportForm("Found").setVisible(true);
        dispose();
    }//GEN-LAST:event_reportFoundButtonActionPerformed

    private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseButtonActionPerformed
        new browseReports().setVisible(true);
        dispose();
    }//GEN-LAST:event_browseButtonActionPerformed

    private void matchingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_matchingButtonActionPerformed
        javax.swing.JOptionPane.showMessageDialog(this, "Matching screen will be added next.");
    }//GEN-LAST:event_matchingButtonActionPerformed

    private void navDashboardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_navDashboardButtonActionPerformed
        reportLostButton.requestFocusInWindow();
    }//GEN-LAST:event_navDashboardButtonActionPerformed

    private void navBrowseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_navBrowseButtonActionPerformed
        browseButtonActionPerformed(evt);
    }//GEN-LAST:event_navBrowseButtonActionPerformed

    private void navMatchingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_navMatchingButtonActionPerformed
        matchingButtonActionPerformed(evt);
    }//GEN-LAST:event_navMatchingButtonActionPerformed

    private void navAccountButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_navAccountButtonActionPerformed
        javax.swing.JOptionPane.showMessageDialog(this, "Account data screen will be added next.");
    }//GEN-LAST:event_navAccountButtonActionPerformed

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new dashboard().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel brandLabel;
    private javax.swing.JButton browseButton;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JLabel headingLabel;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JButton matchingButton;
    private javax.swing.JButton navAccountButton;
    private javax.swing.JButton navBrowseButton;
    private javax.swing.JButton navDashboardButton;
    private javax.swing.JButton navMatchingButton;
    private javax.swing.JButton reportFoundButton;
    private javax.swing.JButton reportLostButton;
    private javax.swing.JLabel welcomeLabel;
    // End of variables declaration//GEN-END:variables
}
