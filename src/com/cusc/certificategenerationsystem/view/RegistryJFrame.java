package com.cusc.certificategenerationsystem.view;

import com.cusc.certificategenerationsystem.db.DatabaseConnection;
import com.cusc.certificategenerationsystem.db.DatabaseProcess;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author PC_HP
 */
public class RegistryJFrame extends javax.swing.JFrame {

    /**
     * Creates new form RegistryJFrame
     */
    public RegistryJFrame() {
        initComponents();
        loadCourseInfo();
        loadClassInfo();
        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width/2 - getWidth()/2,size.height/2 - getHeight()/2);
        long millis = System.currentTimeMillis();
        java.sql.Date currentDate = new java.sql.Date(millis);
        dcRegisDate.setDate(currentDate);
        this.setIconImage(new ImageIcon(getClass().getResource("/com/cusc/certificategenerationsystem/images/icon_logo.png")).getImage());
    }
    private void insertAdditionalRegistration(){
        DatabaseConnection.connectSQL();
        String studentID = txtStudentID.getText().toUpperCase();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String classID = cbbClassID.getSelectedItem().toString();
        String courseID = cbbCourseID.getSelectedItem().toString();
        Date regisDate = dcRegisDate.getDate();
        String registryDate = sdf.format(regisDate);
        String payments = cbbPayments.getSelectedItem().toString();
        int row;
        if(studentID.length()==0){
            JOptionPane.showMessageDialog(null, "You must enter STUDENTID information first!", "Message", 1);
        }else if(classID.length()==0){
            JOptionPane.showMessageDialog(null, "You must enter CLASS information first!", "Message", 1);
        }else if(courseID.length()==0){
            JOptionPane.showMessageDialog(null, "You must enter COURSE information first!", "Message", 1);
        }else{
            String sql1 = "INSERT INTO ClassList VALUES('"+studentID+"','"+classID+"')";
            String sql2 = "INSERT INTO Registry VALUES('"+courseID+"','"+studentID+"','"+registryDate+"','"+
                                payments+"',null,'Studying')";
            row = DatabaseProcess.updateStudent2(sql1, sql2);
            if(row!=0){
                resetForm();
                JOptionPane.showMessageDialog(null, "Sign Up Success!", "Congratulations",1);
            }            

        } 
    }
//    private boolean checkRegistryExists(){
//        boolean validate=true;
//        String studentID = txtStudentID.getText();
//        String courseID = cbbCourseID.getSelectedItem().toString();
//        String sql="SELECT * FROM Registry WHERE StudentID='"+studentID+"' AND CourseID='"+courseID+"'";
//        ResultSet rs = DatabaseProcess.displayInformation(sql);
//        try {
//            validate = !rs.next();
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, "Check registry exists failed!");
//        }
//        return validate;
//    }
    private void resetForm(){
        txtStudentID.setText(null);
        txtName.setText(null);
        txtUsername.setText(null);
        txtPhone.setText(null);
        txaAddress.setText(null);
        cbbClassID.setSelectedIndex(0);
        cbbCourseID.setSelectedIndex(0);
        cbbPayments.setSelectedIndex(0);
        radMale.setSelected(true);
        long millis = System.currentTimeMillis();  
        java.sql.Date date = new java.sql.Date(millis);  
        dcDOB.setDate(date);
        dcRegisDate.setDate(date);
    }
    private void displayInfo(){
        String studentID = txtStudentID.getText();
        String sql = "SELECT * FROM Students WHERE StudentID='"+studentID+"'";
        ResultSet rs = DatabaseProcess.displayInformation(sql);
        try {
            if(rs.next()){
                dcDOB.setDate(rs.getDate("DOB"));
                txtName.setText(rs.getString("Name"));
                txtUsername.setText(rs.getString("Username"));
                txaAddress.setText(rs.getString("Address"));
                txtPhone.setText(rs.getString("Phone"));
                String gender=rs.getString("Gender");
                switch(gender){
                    case "Male":
                        radMale.setSelected(true);
                        break;
                    case "Female":
                        radFemale.setSelected(true);
                        break;
                }
            }else{
                dcDOB.setDate(null);
                txtName.setText(null);
                txtUsername.setText(null);
                txaAddress.setText(null);
                txtPhone.setText(null);
                radMale.setSelected(true);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "INFORMATION DISPLAY FAILED", "Message", 1);
        }
    }
    private void loadCourseInfo(){
        cbbCourseID.removeAllItems();
        DatabaseProcess.loadDataCombobox("SELECT * FROM Course", this.cbbCourseID, "CourseID");   
    }
    private void loadClassInfo(){
        cbbClassID.removeAllItems();
        DatabaseProcess.loadDataCombobox("SELECT * FROM Class WHERE CourseID='"+cbbCourseID.getSelectedItem().toString()+"'",
                this.cbbClassID, "ClassID");
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlInfo = new javax.swing.JPanel();
        txtName = new javax.swing.JTextField();
        lbName = new javax.swing.JLabel();
        lbUsername = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lbPhone = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        lbGender = new javax.swing.JLabel();
        radMale = new javax.swing.JRadioButton();
        radFemale = new javax.swing.JRadioButton();
        lbDOB = new javax.swing.JLabel();
        lbCourseID = new javax.swing.JLabel();
        scpAddress = new javax.swing.JScrollPane();
        txaAddress = new javax.swing.JTextArea();
        lbAddress = new javax.swing.JLabel();
        cbbCourseID = new javax.swing.JComboBox();
        dcDOB = new com.toedter.calendar.JDateChooser();
        lbID = new javax.swing.JLabel();
        txtStudentID = new javax.swing.JTextField();
        lbClassID = new javax.swing.JLabel();
        cbbClassID = new javax.swing.JComboBox();
        lbRegisDate = new javax.swing.JLabel();
        dcRegisDate = new com.toedter.calendar.JDateChooser();
        lbPayments = new javax.swing.JLabel();
        cbbPayments = new javax.swing.JComboBox();
        btnCancel = new javax.swing.JButton();
        btnRegistry = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Registration form");
        setResizable(false);

        pnlInfo.setBackground(new java.awt.Color(255, 255, 255));
        pnlInfo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlInfo.setToolTipText("");

        txtName.setEditable(false);
        txtName.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtName.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        lbName.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbName.setText("Full Name:");

        lbUsername.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbUsername.setText("Username:");

        txtUsername.setEditable(false);
        txtUsername.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtUsername.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        lbPhone.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbPhone.setText("Phone:");

        txtPhone.setEditable(false);
        txtPhone.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtPhone.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        lbGender.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbGender.setText("Gender:");

        radMale.setBackground(new java.awt.Color(255, 255, 255));
        radMale.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        radMale.setSelected(true);
        radMale.setText("Male");
        radMale.setEnabled(false);

        radFemale.setBackground(new java.awt.Color(255, 255, 255));
        radFemale.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        radFemale.setText("Female");
        radFemale.setEnabled(false);

        lbDOB.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbDOB.setText("Date of Birth:");

        lbCourseID.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbCourseID.setText("CourseID:");

        txaAddress.setEditable(false);
        txaAddress.setColumns(20);
        txaAddress.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txaAddress.setLineWrap(true);
        txaAddress.setRows(5);
        txaAddress.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        scpAddress.setViewportView(txaAddress);

        lbAddress.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbAddress.setText("Address:");

        cbbCourseID.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cbbCourseID.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbCourseIDItemStateChanged(evt);
            }
        });

        dcDOB.setDateFormatString("yyyy-MM-dd");
        dcDOB.setEnabled(false);
        dcDOB.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        lbID.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbID.setText("StudentID:");

        txtStudentID.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtStudentID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtStudentIDKeyReleased(evt);
            }
        });

        lbClassID.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbClassID.setText("ClassID:");

        cbbClassID.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        lbRegisDate.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbRegisDate.setText("Registry Date:");

        dcRegisDate.setDateFormatString("yyyy-MM-dd");
        dcRegisDate.setFocusable(false);
        dcRegisDate.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        lbPayments.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbPayments.setText("Payments:");

        cbbPayments.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cbbPayments.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Unpaid", "Paid" }));

        btnCancel.setBackground(new java.awt.Color(255, 102, 102));
        btnCancel.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnRegistry.setBackground(new java.awt.Color(71, 120, 197));
        btnRegistry.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        btnRegistry.setText("Registry");
        btnRegistry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlInfoLayout = new javax.swing.GroupLayout(pnlInfo);
        pnlInfo.setLayout(pnlInfoLayout);
        pnlInfoLayout.setHorizontalGroup(
            pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfoLayout.createSequentialGroup()
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addComponent(btnRegistry, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(btnCancel))
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbName, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbDOB, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbGender, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbUsername, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbID, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbPhone, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(40, 40, 40)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPhone)
                            .addGroup(pnlInfoLayout.createSequentialGroup()
                                .addComponent(radMale, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                                .addGap(49, 49, 49)
                                .addComponent(radFemale, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
                            .addComponent(txtStudentID)
                            .addComponent(dcDOB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtUsername)
                            .addComponent(txtName))
                        .addGap(60, 60, 60)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbClassID, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbRegisDate, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbCourseID, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbAddress, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbPayments, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(40, 40, 40)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dcRegisDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbbClassID, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbbCourseID, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(scpAddress, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
                            .addComponent(cbbPayments, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(60, 60, 60))
        );

        pnlInfoLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnCancel, btnRegistry});

        pnlInfoLayout.setVerticalGroup(
            pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbID)
                            .addComponent(txtStudentID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbName, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(20, 20, 20)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dcDOB, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbDOB, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(20, 20, 20)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbGender)
                            .addComponent(radMale)
                            .addComponent(radFemale))
                        .addGap(20, 20, 20)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbUsername))
                        .addGap(20, 20, 20)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbPhone)
                            .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbAddress, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(scpAddress, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbbCourseID, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbCourseID, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(20, 20, 20)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbbClassID, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbClassID, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(20, 20, 20)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbbPayments, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbPayments, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(20, 20, 20)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dcRegisDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbRegisDate, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(40, 40, 40)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegistry, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel))
                .addGap(20, 20, 20))
        );

        pnlInfoLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cbbClassID, cbbCourseID, cbbPayments, dcDOB, dcRegisDate, txtName, txtPhone, txtStudentID, txtUsername});

        pnlInfoLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnCancel, btnRegistry});

        getContentPane().add(pnlInfo, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtStudentIDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStudentIDKeyReleased
        displayInfo();
    }//GEN-LAST:event_txtStudentIDKeyReleased

    private void btnRegistryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistryActionPerformed
        try {
            insertAdditionalRegistration();
        }catch(NullPointerException e){
            JOptionPane.showMessageDialog(null, "You must enter complete information!", "Message", 1);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Registry failed. Please check again!", "Message", 1);
        }
        
    }//GEN-LAST:event_btnRegistryActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void cbbCourseIDItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbCourseIDItemStateChanged
        if(evt.getStateChange()==ItemEvent.SELECTED){
            loadClassInfo();
        }
    }//GEN-LAST:event_cbbCourseIDItemStateChanged

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
            java.util.logging.Logger.getLogger(RegistryJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistryJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistryJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistryJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistryJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnRegistry;
    private javax.swing.JComboBox cbbClassID;
    private javax.swing.JComboBox cbbCourseID;
    private javax.swing.JComboBox cbbPayments;
    private com.toedter.calendar.JDateChooser dcDOB;
    private com.toedter.calendar.JDateChooser dcRegisDate;
    private javax.swing.JLabel lbAddress;
    private javax.swing.JLabel lbClassID;
    private javax.swing.JLabel lbCourseID;
    private javax.swing.JLabel lbDOB;
    private javax.swing.JLabel lbGender;
    private javax.swing.JLabel lbID;
    private javax.swing.JLabel lbName;
    private javax.swing.JLabel lbPayments;
    private javax.swing.JLabel lbPhone;
    private javax.swing.JLabel lbRegisDate;
    private javax.swing.JLabel lbUsername;
    private javax.swing.JPanel pnlInfo;
    private javax.swing.JRadioButton radFemale;
    private javax.swing.JRadioButton radMale;
    private javax.swing.JScrollPane scpAddress;
    private javax.swing.JTextArea txaAddress;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtStudentID;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
