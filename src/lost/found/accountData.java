package lost.found;
import javax.swing.*;
import java.util.*;
public class accountData extends JFrame {
public accountData(){initComponents();AccountStore.Account user=Session.current();if(user!=null){nameValue.setText("Name: "+user.fullName());studentValue.setText("Student ID: "+user.studentId());emailValue.setText("Email: "+user.email());}}
private void go(JFrame page){page.setVisible(true);dispose();}
private void homeActionPerformed(java.awt.event.ActionEvent e){//GEN-FIRST:event_homeActionPerformed
go(new dashboard());}//GEN-LAST:event_homeActionPerformed
private void browseActionPerformed(java.awt.event.ActionEvent e){//GEN-FIRST:event_browseActionPerformed
go(new browseReports());}//GEN-LAST:event_browseActionPerformed
private void matchingActionPerformed(java.awt.event.ActionEvent e){//GEN-FIRST:event_matchingActionPerformed
go(new matchingReports());}//GEN-LAST:event_matchingActionPerformed
private void accountActionPerformed(java.awt.event.ActionEvent e){//GEN-FIRST:event_accountActionPerformed
go(new accountData());}//GEN-LAST:event_accountActionPerformed
private void logoutActionPerformed(java.awt.event.ActionEvent e){//GEN-FIRST:event_logoutActionPerformed
Session.signOut();go(new login());}//GEN-LAST:event_logoutActionPerformed
private void messagesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_messagesButtonActionPerformed
new messages().setVisible(true); dispose();
}//GEN-LAST:event_messagesButtonActionPerformed
// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
private void initComponents(){
messagesButton = new javax.swing.JButton(); messagesButton.setText("Messages / Claims"); messagesButton.addActionListener(this::messagesButtonActionPerformed);
setDefaultCloseOperation(3);setTitle("CampusFind - Account Data");setResizable(false);
brand=new javax.swing.JLabel();
brand.setText("CampusFind");
home=new javax.swing.JButton();
home.setText("Dashboard");
home.addActionListener(this::homeActionPerformed);
browse=new javax.swing.JButton();
browse.setText("Browse Reports");
browse.addActionListener(this::browseActionPerformed);
matching=new javax.swing.JButton();
matching.setText("Matching Reports");
matching.addActionListener(this::matchingActionPerformed);
account=new javax.swing.JButton();
account.setText("Account Data");
account.addActionListener(this::accountActionPerformed);
heading=new javax.swing.JLabel();
heading.setText("Account Data");
nameValue=new javax.swing.JLabel();
nameValue.setText("Not signed in");
studentValue=new javax.swing.JLabel();
studentValue.setText("");
emailValue=new javax.swing.JLabel();
emailValue.setText("");
logout=new javax.swing.JButton();
logout.setText("Log Out / Log In");
logout.addActionListener(this::logoutActionPerformed);
heading.setFont(new java.awt.Font("Segoe UI",1,26));brand.setFont(new java.awt.Font("Segoe UI",1,24));leftPanel=new javax.swing.JPanel();
javax.swing.GroupLayout layout=new javax.swing.GroupLayout(getContentPane());getContentPane().setLayout(layout);
layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(260,260,260).addComponent(messagesButton,220,220,220).addGap(280,280,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(15,15,15).addComponent(brand,200,200,200).addGap(545,545,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(15,15,15).addComponent(home,200,200,200).addGap(545,545,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(15,15,15).addComponent(browse,200,200,200).addGap(545,545,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(15,15,15).addComponent(matching,200,200,200).addGap(545,545,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(15,15,15).addComponent(account,200,200,200).addGap(545,545,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(260,260,260).addComponent(heading,450,450,450).addGap(50,50,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(260,260,260).addComponent(nameValue,460,460,460).addGap(40,40,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(260,260,260).addComponent(studentValue,460,460,460).addGap(40,40,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(260,260,260).addComponent(emailValue,460,460,460).addGap(40,40,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(260,260,260).addComponent(logout,220,220,220).addGap(280,280,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addComponent(leftPanel,230,230,230).addGap(530,530,Short.MAX_VALUE)));
layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(395,395,395).addComponent(messagesButton,40,40,40).addGap(185,185,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(60,60,60).addComponent(brand,40,40,40).addGap(520,520,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(180,180,180).addComponent(home,45,45,45).addGap(395,395,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(240,240,240).addComponent(browse,45,45,45).addGap(335,335,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(300,300,300).addComponent(matching,45,45,45).addGap(275,275,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(360,360,360).addComponent(account,45,45,45).addGap(215,215,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(30,30,30).addComponent(heading,40,40,40).addGap(550,550,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(120,120,120).addComponent(nameValue,35,35,35).addGap(465,465,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(175,175,175).addComponent(studentValue,35,35,35).addGap(410,410,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(230,230,230).addComponent(emailValue,35,35,35).addGap(355,355,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(330,330,330).addComponent(logout,40,40,40).addGap(250,250,Short.MAX_VALUE))
.addComponent(leftPanel,620,620,620));
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
brand.setForeground(new java.awt.Color(255,255,255));
pack();setLocationRelativeTo(null);
}// </editor-fold>//GEN-END:initComponents
// Variables declaration - do not modify//GEN-BEGIN:variables
private javax.swing.JButton messagesButton;
private javax.swing.JPanel leftPanel;
private javax.swing.JLabel brand;
private javax.swing.JButton home;
private javax.swing.JButton browse;
private javax.swing.JButton matching;
private javax.swing.JButton account;
private javax.swing.JLabel heading;
private javax.swing.JLabel nameValue;
private javax.swing.JLabel studentValue;
private javax.swing.JLabel emailValue;
private javax.swing.JButton logout;
// End of variables declaration//GEN-END:variables
public static void main(String[] args){java.awt.EventQueue.invokeLater(()->new accountData().setVisible(true));}
}
