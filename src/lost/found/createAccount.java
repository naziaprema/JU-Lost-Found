package lost.found;
import javax.swing.*;
import java.util.*;
public class createAccount extends JFrame {
public createAccount(){initComponents();}
private void go(JFrame page){page.setVisible(true);dispose();}
private void homeActionPerformed(java.awt.event.ActionEvent e){//GEN-FIRST:event_homeActionPerformed
go(new dashboard());}//GEN-LAST:event_homeActionPerformed
private void browseActionPerformed(java.awt.event.ActionEvent e){//GEN-FIRST:event_browseActionPerformed
go(new browseReports());}//GEN-LAST:event_browseActionPerformed
private void matchingActionPerformed(java.awt.event.ActionEvent e){//GEN-FIRST:event_matchingActionPerformed
go(new matchingReports());}//GEN-LAST:event_matchingActionPerformed
private void accountActionPerformed(java.awt.event.ActionEvent e){//GEN-FIRST:event_accountActionPerformed
go(new accountData());}//GEN-LAST:event_accountActionPerformed

private void backActionPerformed(java.awt.event.ActionEvent e){//GEN-FIRST:event_backActionPerformed
go(new login());}//GEN-LAST:event_backActionPerformed
private void createActionPerformed(java.awt.event.ActionEvent e){//GEN-FIRST:event_createActionPerformed

char[] first=secret.getPassword(),second=confirm.getPassword();
if(!Arrays.equals(first,second)){Arrays.fill(first,'\0');Arrays.fill(second,'\0');JOptionPane.showMessageDialog(this,"Passwords do not match.");return;}
Arrays.fill(second,'\0');
String name=fullName.getText(),student=studentId.getText(),mail=email.getText();
create.setEnabled(false);back.setEnabled(false);
new SwingWorker<AccountStore.Account,Void>(){
protected AccountStore.Account doInBackground() throws Exception{try{return new AccountStore().register(name,student,mail,first);}finally{Arrays.fill(first,'\0');}}
protected void done(){create.setEnabled(true);back.setEnabled(true);secret.setText("");confirm.setText("");try{AccountStore.Account result=get();JOptionPane.showMessageDialog(createAccount.this,"Account created. Please log in.");login page=new login();page.setIdentifier(result.email());go(page);}catch(Exception ex){Throwable cause=ex.getCause()==null?ex:ex.getCause();JOptionPane.showMessageDialog(createAccount.this,cause.getMessage(),"Cannot create account",JOptionPane.ERROR_MESSAGE);}}
}.execute();
}//GEN-LAST:event_createActionPerformed

// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
private void initComponents(){
setDefaultCloseOperation(3);setTitle("CampusFind - Create Account");setResizable(false);
brand=new javax.swing.JLabel();
brand.setText("CampusFind");
home=new javax.swing.JButton();
home.setText("Dashboard");
home.setEnabled(false);
browse=new javax.swing.JButton();
browse.setText("Browse Reports");
browse.setEnabled(false);
matching=new javax.swing.JButton();
matching.setText("Matching Reports");
matching.setEnabled(false);
account=new javax.swing.JButton();
account.setText("Account Data");
account.setEnabled(false);
heading=new javax.swing.JLabel();
heading.setText("Create Account");
nameLabel=new javax.swing.JLabel();
nameLabel.setText("Full name");
fullName=new javax.swing.JTextField();
fullName.setText("");
idLabel=new javax.swing.JLabel();
idLabel.setText("Student ID");
studentId=new javax.swing.JTextField();
studentId.setText("");
emailLabel=new javax.swing.JLabel();
emailLabel.setText("Email");
email=new javax.swing.JTextField();
email.setText("");
passwordLabel=new javax.swing.JLabel();
passwordLabel.setText("Password (8–128 characters)");
secret=new javax.swing.JPasswordField();
confirmLabel=new javax.swing.JLabel();
confirmLabel.setText("Confirm password");
confirm=new javax.swing.JPasswordField();
create=new javax.swing.JButton();
create.setText("Create Account");
create.addActionListener(this::createActionPerformed);
back=new javax.swing.JButton();
back.setText("Back to Login");
back.addActionListener(this::backActionPerformed);
heading.setFont(new java.awt.Font("Segoe UI",1,26));brand.setFont(new java.awt.Font("Segoe UI",1,24));leftPanel=new javax.swing.JPanel();
javax.swing.GroupLayout layout=new javax.swing.GroupLayout(getContentPane());getContentPane().setLayout(layout);
layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(layout.createSequentialGroup().addGap(15,15,15).addComponent(brand,200,200,200).addGap(545,545,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(15,15,15).addComponent(home,200,200,200).addGap(545,545,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(15,15,15).addComponent(browse,200,200,200).addGap(545,545,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(15,15,15).addComponent(matching,200,200,200).addGap(545,545,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(15,15,15).addComponent(account,200,200,200).addGap(545,545,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(260,260,260).addComponent(heading,460,460,460).addGap(40,40,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(260,260,260).addComponent(nameLabel,450,450,450).addGap(50,50,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(260,260,260).addComponent(fullName,450,450,450).addGap(50,50,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(260,260,260).addComponent(idLabel,450,450,450).addGap(50,50,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(260,260,260).addComponent(studentId,450,450,450).addGap(50,50,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(260,260,260).addComponent(emailLabel,450,450,450).addGap(50,50,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(260,260,260).addComponent(email,450,450,450).addGap(50,50,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(260,260,260).addComponent(passwordLabel,450,450,450).addGap(50,50,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(260,260,260).addComponent(secret,450,450,450).addGap(50,50,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(260,260,260).addComponent(confirmLabel,450,450,450).addGap(50,50,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(260,260,260).addComponent(confirm,450,450,450).addGap(50,50,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(260,260,260).addComponent(create,220,220,220).addGap(280,280,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(500,500,500).addComponent(back,210,210,210).addGap(50,50,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addComponent(leftPanel,230,230,230).addGap(530,530,Short.MAX_VALUE)));
layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(layout.createSequentialGroup().addGap(60,60,60).addComponent(brand,40,40,40).addGap(520,520,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(180,180,180).addComponent(home,45,45,45).addGap(395,395,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(240,240,240).addComponent(browse,45,45,45).addGap(335,335,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(300,300,300).addComponent(matching,45,45,45).addGap(275,275,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(360,360,360).addComponent(account,45,45,45).addGap(215,215,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(30,30,30).addComponent(heading,40,40,40).addGap(550,550,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(95,95,95).addComponent(nameLabel,25,25,25).addGap(500,500,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(125,125,125).addComponent(fullName,32,32,32).addGap(463,463,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(170,170,170).addComponent(idLabel,25,25,25).addGap(425,425,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(200,200,200).addComponent(studentId,32,32,32).addGap(388,388,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(245,245,245).addComponent(emailLabel,25,25,25).addGap(350,350,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(275,275,275).addComponent(email,32,32,32).addGap(313,313,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(320,320,320).addComponent(passwordLabel,25,25,25).addGap(275,275,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(350,350,350).addComponent(secret,32,32,32).addGap(238,238,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(395,395,395).addComponent(confirmLabel,25,25,25).addGap(200,200,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(425,425,425).addComponent(confirm,32,32,32).addGap(163,163,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(490,490,490).addComponent(create,40,40,40).addGap(90,90,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(490,490,490).addComponent(back,40,40,40).addGap(90,90,Short.MAX_VALUE))
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
private javax.swing.JPanel leftPanel;
private javax.swing.JLabel brand;
private javax.swing.JButton home;
private javax.swing.JButton browse;
private javax.swing.JButton matching;
private javax.swing.JButton account;
private javax.swing.JLabel heading;
private javax.swing.JLabel nameLabel;
private javax.swing.JTextField fullName;
private javax.swing.JLabel idLabel;
private javax.swing.JTextField studentId;
private javax.swing.JLabel emailLabel;
private javax.swing.JTextField email;
private javax.swing.JLabel passwordLabel;
private javax.swing.JPasswordField secret;
private javax.swing.JLabel confirmLabel;
private javax.swing.JPasswordField confirm;
private javax.swing.JButton create;
private javax.swing.JButton back;
// End of variables declaration//GEN-END:variables
public static void main(String[] args){java.awt.EventQueue.invokeLater(()->new createAccount().setVisible(true));}
}
