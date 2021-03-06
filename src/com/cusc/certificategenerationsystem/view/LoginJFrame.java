package com.cusc.certificategenerationsystem.view;

import com.cusc.certificategenerationsystem.db.DatabaseConnection;
import com.cusc.certificategenerationsystem.security.MD5Encryptor;
import com.cusc.certificategenerationsystem.studentview.StudentJFrame;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author PC_HP
 */
public class LoginJFrame extends javax.swing.JFrame {
    public static String username;
    /**
     * 
     * 
     */
    public LoginJFrame() {
        initComponents();
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width/2 - getWidth()/2,size.height/2 - getHeight()/2);
        this.setIconImage(new ImageIcon(getClass().getResource("/com/cusc/certificategenerationsystem/images/icon_logo.png")).getImage());
        
    }
    private void logIn(){
        lbNotificationUsername.setText(null);
        lbNotificationPassword.setText(null);
        try {
            DatabaseConnection.connectSQL();
            username=txtUserName.getText();
            String pass=txtPassword.getText();
            String password = MD5Encryptor.encrypt(pass);
            if(username.matches(".{1,}")==false){
                lbNotificationUsername.setText("Username cannot empty");
                txtUserName.requestFocus();
            }else if(pass.matches(".{1,}")==false){
                lbNotificationPassword.setText("Password cannot empty");
                txtPassword.requestFocus();
            }else{
                Statement stt1 = DatabaseConnection.conn.createStatement();
                String sql1= "SELECT * FROM Admin_CertificateCell WHERE Username='"+username+"' AND Password='"+password+"'";
                ResultSet rs1 = stt1.executeQuery(sql1);
                if(rs1.next()){
                    JFrame frame = new MainJFrame();
                    frame.setVisible(true);
                    this.dispose();
                }else{
                    Statement stt2 = DatabaseConnection.conn.createStatement();
                    String sql2= "SELECT * FROM Students WHERE Username='"+username+"' AND Password='"+password+"'";
                    ResultSet rs2 = stt2.executeQuery(sql2);
                    if(rs2.next()){
                        JFrame frame = new StudentJFrame();
                        frame.setVisible(true);
                        this.dispose();
                    }else{
                        lbNotificationPassword.setText("Username or Password not correct");
                        return;
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database connection failed, the port may not match!", "Message", 1);
        }
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbCertificate = new javax.swing.JLabel();
        pnlLogIn = new javax.swing.JPanel();
        txtUserName = new javax.swing.JTextField();
        lbUserName = new javax.swing.JLabel();
        lbPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        btnSignIn = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        lbLogoLogIn = new javax.swing.JLabel();
        lbNotificationUsername = new javax.swing.JLabel();
        lbNotificationPassword = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Log In - Certificate Generation System");
        setResizable(false);

        lbCertificate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cusc/certificategenerationsystem/images/pic_login.png"))); // NOI18N

        pnlLogIn.setBackground(new java.awt.Color(255, 255, 255));

        txtUserName.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtUserName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUserNameKeyPressed(evt);
            }
        });

        lbUserName.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cusc/certificategenerationsystem/images/icon_user.png"))); // NOI18N

        lbPassword.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cusc/certificategenerationsystem/images/icon_pass.png"))); // NOI18N

        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPasswordKeyPressed(evt);
            }
        });

        btnSignIn.setBackground(new java.awt.Color(120, 168, 252));
        btnSignIn.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnSignIn.setForeground(new java.awt.Color(255, 255, 255));
        btnSignIn.setText("Sign in");
        btnSignIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignInActionPerformed(evt);
            }
        });

        btnCancel.setBackground(new java.awt.Color(255, 102, 102));
        btnCancel.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        lbLogoLogIn.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        lbLogoLogIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cusc/certificategenerationsystem/images/icon_login.jpg"))); // NOI18N

        lbNotificationUsername.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        lbNotificationUsername.setForeground(new java.awt.Color(255, 0, 0));

        lbNotificationPassword.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        lbNotificationPassword.setForeground(new java.awt.Color(255, 0, 0));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel1.setText("Username:");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setText("Password:");

        javax.swing.GroupLayout pnlLogInLayout = new javax.swing.GroupLayout(pnlLogIn);
        pnlLogIn.setLayout(pnlLogInLayout);
        pnlLogInLayout.setHorizontalGroup(
            pnlLogInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLogInLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(pnlLogInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlLogInLayout.createSequentialGroup()
                        .addComponent(btnSignIn, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(99, 99, 99)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlLogInLayout.createSequentialGroup()
                        .addComponent(lbLogoLogIn)
                        .addGap(12, 12, 12))
                    .addGroup(pnlLogInLayout.createSequentialGroup()
                        .addGroup(pnlLogInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbPassword, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbUserName, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(30, 30, 30)
                        .addGroup(pnlLogInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbNotificationUsername, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtUserName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                            .addComponent(txtPassword, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbNotificationPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))))
                .addGap(60, 60, 60))
        );

        pnlLogInLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtPassword, txtUserName});

        pnlLogInLayout.setVerticalGroup(
            pnlLogInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLogInLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbLogoLogIn, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(pnlLogInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbUserName)
                    .addGroup(pnlLogInLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbNotificationUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addGroup(pnlLogInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbPassword)
                    .addGroup(pnlLogInLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbNotificationPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlLogInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSignIn)
                    .addComponent(btnCancel))
                .addGap(105, 105, 105))
        );

        pnlLogInLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtPassword, txtUserName});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lbCertificate)
                .addGap(0, 0, 0)
                .addComponent(pnlLogIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbCertificate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlLogIn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSignInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignInActionPerformed
        logIn();
    }//GEN-LAST:event_btnSignInActionPerformed
    
    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnCancelActionPerformed

    private void txtPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswordKeyPressed
        if(evt.getKeyChar() == KeyEvent.VK_ENTER){
            logIn();
        }
    }//GEN-LAST:event_txtPasswordKeyPressed

    private void txtUserNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUserNameKeyPressed
        if(evt.getKeyChar() == KeyEvent.VK_ENTER){
            logIn();
        }
    }//GEN-LAST:event_txtUserNameKeyPressed

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSignIn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lbCertificate;
    private javax.swing.JLabel lbLogoLogIn;
    private javax.swing.JLabel lbNotificationPassword;
    private javax.swing.JLabel lbNotificationUsername;
    private javax.swing.JLabel lbPassword;
    private javax.swing.JLabel lbUserName;
    private javax.swing.JPanel pnlLogIn;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUserName;
    // End of variables declaration//GEN-END:variables
}
