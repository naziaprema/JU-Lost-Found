package lost.found;
public class login extends javax.swing.JFrame {
private char echo;
public login(){initComponents();echo=password.getEchoChar();getRootPane().setDefaultButton(submit);}
// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
private void initComponents(){
setDefaultCloseOperation(3);setTitle("CampusFind | Login");setResizable(false);
brand = new javax.swing.JLabel();
brand.setFont(new java.awt.Font("Segoe UI",1,36));
brand.setForeground(new java.awt.Color(0xffffff));
brand.setText("CampusFind");
brand.setHorizontalAlignment(0);
tagline = new javax.swing.JLabel();
tagline.setFont(new java.awt.Font("Segoe UI",0,16));
tagline.setForeground(new java.awt.Color(0xffffff));
tagline.setText("Lost & Found - Together");
tagline.setHorizontalAlignment(0);
welcome = new javax.swing.JLabel();
welcome.setFont(new java.awt.Font("Segoe UI",1,30));
welcome.setForeground(new java.awt.Color(0x182c48));
welcome.setText("Welcome Back!");
welcome.setHorizontalAlignment(0);
subtitle = new javax.swing.JLabel();
subtitle.setFont(new java.awt.Font("Segoe UI",0,12));
subtitle.setForeground(new java.awt.Color(0x182c48));
subtitle.setText("Login to find or report lost items on campus");
subtitle.setHorizontalAlignment(0);
emailLabel = new javax.swing.JLabel();
emailLabel.setFont(new java.awt.Font("Segoe UI",0,13));
emailLabel.setForeground(new java.awt.Color(0x182c48));
emailLabel.setText("Email or Student ID");
email = new javax.swing.JTextField();
email.setFont(new java.awt.Font("Segoe UI",0,13));
email.setForeground(new java.awt.Color(0x182c48));
passwordLabel = new javax.swing.JLabel();
passwordLabel.setFont(new java.awt.Font("Segoe UI",0,13));
passwordLabel.setForeground(new java.awt.Color(0x182c48));
passwordLabel.setText("Password");
password = new javax.swing.JPasswordField();
password.setFont(new java.awt.Font("Segoe UI",0,13));
password.setForeground(new java.awt.Color(0x182c48));
show = new javax.swing.JToggleButton();
show.setFont(new java.awt.Font("Segoe UI",0,13));
show.setForeground(new java.awt.Color(0x182c48));
show.setText("Show");
show.addActionListener(this::showActionPerformed);
submit = new javax.swing.JButton();
submit.setFont(new java.awt.Font("Segoe UI",1,13));
submit.setForeground(new java.awt.Color(0xffffff));
submit.setText("Login");
submit.setBackground(new java.awt.Color(0x2b74e0));
submit.setBorderPainted(false);
submit.addActionListener(this::submitActionPerformed);
divider = new javax.swing.JLabel();
divider.setFont(new java.awt.Font("Segoe UI",0,13));
divider.setForeground(new java.awt.Color(0x182c48));
divider.setText("-  or  -");
divider.setHorizontalAlignment(0);
register = new javax.swing.JButton();
register.setFont(new java.awt.Font("Segoe UI",0,13));
register.setForeground(new java.awt.Color(0x2b74e0));
register.setText("Create an Account");
register.setBackground(new java.awt.Color(0xffffff));
register.addActionListener(this::registerActionPerformed);
artwork = new javax.swing.JLabel();
artwork.setFont(new java.awt.Font("Segoe UI",0,13));
artwork.setForeground(new java.awt.Color(0x182c48));
artwork.setHorizontalAlignment(0);
artwork.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lost/found/ju.png.png")));
javax.swing.GroupLayout layout=new javax.swing.GroupLayout(getContentPane());
getContentPane().setLayout(layout);
layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(layout.createSequentialGroup().addGap(25,25,25).addComponent(brand,280,280,280).addGap(455,455,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(25,25,25).addComponent(tagline,280,280,280).addGap(455,455,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(380,380,380).addComponent(welcome,320,320,320).addGap(60,60,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(380,380,380).addComponent(subtitle,320,320,320).addGap(60,60,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(380,380,380).addComponent(emailLabel,320,320,320).addGap(60,60,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(380,380,380).addComponent(email,320,320,320).addGap(60,60,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(380,380,380).addComponent(passwordLabel,320,320,320).addGap(60,60,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(380,380,380).addComponent(password,240,240,240).addGap(140,140,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(625,625,625).addComponent(show,75,75,75).addGap(60,60,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(380,380,380).addComponent(submit,320,320,320).addGap(60,60,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(380,380,380).addComponent(divider,320,320,320).addGap(60,60,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(380,380,380).addComponent(register,320,320,320).addGap(60,60,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(0,0,0).addComponent(artwork,330,330,330).addGap(430,430,Short.MAX_VALUE)));
layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(layout.createSequentialGroup().addGap(150,150,150).addComponent(brand,60,60,60).addGap(410,410,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(220,220,220).addComponent(tagline,30,30,30).addGap(370,370,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(80,80,80).addComponent(welcome,50,50,50).addGap(490,490,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(135,135,135).addComponent(subtitle,40,40,40).addGap(445,445,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(205,205,205).addComponent(emailLabel,25,25,25).addGap(390,390,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(235,235,235).addComponent(email,42,42,42).addGap(343,343,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(290,290,290).addComponent(passwordLabel,25,25,25).addGap(305,305,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(320,320,320).addComponent(password,42,42,42).addGap(258,258,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(320,320,320).addComponent(show,42,42,42).addGap(258,258,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(430,430,430).addComponent(submit,44,44,44).addGap(146,146,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(480,480,480).addComponent(divider,30,30,30).addGap(110,110,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(525,525,525).addComponent(register,42,42,42).addGap(53,53,Short.MAX_VALUE))
.addGroup(layout.createSequentialGroup().addGap(0,0,0).addComponent(artwork,620,620,620).addGap(0,0,Short.MAX_VALUE)));

pack();setLocationRelativeTo(null);
}// </editor-fold>//GEN-END:initComponents
private void showActionPerformed(java.awt.event.ActionEvent evt){//GEN-FIRST:event_showActionPerformed
password.setEchoChar(show.isSelected()?(char)0:echo);show.setText(show.isSelected()?"Hide":"Show");}//GEN-LAST:event_showActionPerformed
private void submitActionPerformed(java.awt.event.ActionEvent evt){//GEN-FIRST:event_submitActionPerformed

char[] secret=password.getPassword();String identifier=email.getText().trim();
if(identifier.isEmpty()||secret.length==0){java.util.Arrays.fill(secret,'\0');javax.swing.JOptionPane.showMessageDialog(this,"Enter your email or student ID and password.");return;}
submit.setEnabled(false);register.setEnabled(false);
new javax.swing.SwingWorker<java.util.Optional<AccountStore.Account>,Void>(){
protected java.util.Optional<AccountStore.Account> doInBackground() throws Exception{
try{return new AccountStore().authenticate(identifier,secret);}finally{java.util.Arrays.fill(secret,'\0');}
}
protected void done(){submit.setEnabled(true);register.setEnabled(true);password.setText("");
try{java.util.Optional<AccountStore.Account> user=get();if(user.isEmpty()){javax.swing.JOptionPane.showMessageDialog(login.this,"Incorrect email/student ID or password.");return;}
Session.signIn(user.get());new dashboard().setVisible(true);dispose();
}catch(Exception ex){javax.swing.JOptionPane.showMessageDialog(login.this,"Could not read account files. "+(ex.getCause()==null?ex.getMessage():ex.getCause().getMessage()));}
}
}.execute();
}//GEN-LAST:event_submitActionPerformed
public void setIdentifier(String identifier){email.setText(identifier);}
private void registerActionPerformed(java.awt.event.ActionEvent evt){//GEN-FIRST:event_registerActionPerformed
new createAccount().setVisible(true);dispose();}//GEN-LAST:event_registerActionPerformed
public static void main(String[] args){java.awt.EventQueue.invokeLater(()->new login().setVisible(true));}
// Variables declaration - do not modify//GEN-BEGIN:variables
private javax.swing.JLabel brand;
private javax.swing.JLabel tagline;
private javax.swing.JLabel welcome;
private javax.swing.JLabel subtitle;
private javax.swing.JLabel emailLabel;
private javax.swing.JTextField email;
private javax.swing.JLabel passwordLabel;
private javax.swing.JPasswordField password;
private javax.swing.JToggleButton show;
private javax.swing.JButton submit;
private javax.swing.JLabel divider;
private javax.swing.JButton register;
private javax.swing.JLabel artwork;
// End of variables declaration//GEN-END:variables
}
