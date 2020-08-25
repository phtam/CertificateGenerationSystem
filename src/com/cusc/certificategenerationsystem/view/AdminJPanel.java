package com.cusc.certificategenerationsystem.view;

import com.cusc.certificategenerationsystem.db.DatabaseProcess;
import com.cusc.certificategenerationsystem.report.Report;
import com.cusc.certificategenerationsystem.security.MD5Encryptor;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PC_HP
 */
public class AdminJPanel extends javax.swing.JPanel {
    int func = 0;
    /**
     * Creates new form AdminJPanel
     */
    public AdminJPanel() {
        initComponents();
        pnlModify.setVisible(false);
        pnlCancel.setVisible(false);
        lbNotification.setVisible(false);
        long millis = System.currentTimeMillis();
        java.sql.Date currentDate = new java.sql.Date(millis);
        dcDOB.setDate(currentDate);
        DatabaseProcess.tableModel = new DefaultTableModel();
        DatabaseProcess.loadData("SELECT [ID],[Name],[DOB],[Gender],[Address],[Phone],[Username],[Role] "
                + "FROM Admin_CertificateCell", tblAdmin);
        txtID.setText(DatabaseProcess.getID("execute sp_Admin_CertificateCell_identityID"));  
    }

    private void disableForm(){
        txtName.setEditable(false);
        txtPassword.setEditable(false);
        txtUsername.setEditable(false);
        txtPhone.setEditable(false);
        txaAddress.setEditable(false);
        dcDOB.setEnabled(false);
        radMale.setEnabled(false);
        radFemale.setEnabled(false);
        cbbRole.setEnabled(false);
    }  
    private void enableForm(){
        txtName.setEditable(true);
        txtPhone.setEditable(true);
        txaAddress.setEditable(true);
        dcDOB.setEnabled(true);
        radMale.setEnabled(true);
        radFemale.setEnabled(true);
        cbbRole.setEnabled(true);
    }  
    private void updateAdmin_CertificateCell(){
        String id = txtID.getText();
        String name = txtName.getText();
        Date dateOfBirth = dcDOB.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat();
        String date = sdf.format(dateOfBirth);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateOfBirth);
        String gender = null;
        if(radMale.isSelected()){
            gender = "Male";
        } else {
            gender = "Female";
        }
        String username = txtUsername.getText();
        String pass = txtPassword.getText();
        String password = MD5Encryptor.encrypt(pass);
        String address = txaAddress.getText();
        String phone = txtPhone.getText();
        String role = cbbRole.getSelectedItem().toString();
        int row;
        if(name.matches("^[a-zA-Z_0-9\\s]{5,40}") == false){
            JOptionPane.showMessageDialog(null, "Name cannot empty. Name does not contain special characters!", "Message", 1);
            txtName.requestFocus();
        }else if(username.matches("^[a-zA-Z_0-9]{5,15}") == false){
            JOptionPane.showMessageDialog(null, "Username must be between 5 and 15 characters. Name does not contain special characters!", "Message", 1);
            txtUsername.requestFocus();
        }else if(pass.matches(".{8,50}")== false){
            JOptionPane.showMessageDialog(null, "Password must be greater than 8 characters and less than 50 characters!", "Message", 1);
            txtPassword.requestFocus();
        }else if(address.matches(".{5,120}") == false){
            JOptionPane.showMessageDialog(null, "Address must be greater than 5 characters and less than 120 characters!", "Message", 1);
            txaAddress.requestFocus();
        }else if(phone.matches("^[0-9]{9,13}")== false){
            JOptionPane.showMessageDialog(null, "Invalid phone number, please try again!", "Message", 1);
            txtPhone.requestFocus();
        }else if(getAge(calendar)<18){
            JOptionPane.showMessageDialog(null, "Administrator must be over 18 years old to register!", "Message", 1);
            dcDOB.requestFocus();
        }else{
            switch(func){
                case 1:
                    if(this.checkValidateUsername(username)==false){
                        JOptionPane.showMessageDialog(null, "Username already exists. Please try again!", "Message", 1);
                        txtUsername.requestFocus();
                    }else{
                        String sql1 = "INSERT INTO Admin_CertificateCell VALUES ('"+id+"','"+name+"','"+date+"','"
                        +gender+"','"+address+"','"+phone+"','"+username+"','"+password+"','"+role+"')";
                        row = DatabaseProcess.updateData(sql1);
                        if(row != 0){
                            notifySuccessful();
                        }
                    }
                    break;
                case 2:
                    String sql2 = "UPDATE Admin_CertificateCell SET "+
                            "Name='"+name+"',"+
                            "DOB='"+date+"',"+
                            "Gender='"+gender+"',"+
                            "Address='"+address+"',"+
                            "Phone='"+phone+"',"+
                            "Username='"+username+"',"+
                            "Role='"+role+"'"+
                            "WHERE ID='"+id+"'";
                    row = DatabaseProcess.updateData(sql2);
                    if(row != 0){
                        notifySuccessful();
                    }
                    break;
                case 3:
                    String sql3 ="DELETE FROM Admin_CertificateCell WHERE ID='"+id+"'";
                    row = DatabaseProcess.updateData(sql3);
                    if(row != 0){
                        notifySuccessful();
                    }
                    break;    
            }
        }
        
    }
    private void notifySuccessful(){
        lbNotification.setVisible(true);
        DatabaseProcess.tableModel = (DefaultTableModel) tblAdmin.getModel();
        DatabaseProcess.tableModel.setRowCount(0);
        DatabaseProcess.loadData("SELECT [ID],[Name],[DOB],[Gender],[Address],[Phone],[Username],[Role] "
                + "FROM Admin_CertificateCell", tblAdmin);
        enableForm();
        resetForm();
    }
    private void resetForm(){
        txtName.setText(null);
        txtUsername.setText(null);
        txtPassword.setText(null);
        txtPhone.setText(null);
        txaAddress.setText(null);
        radMale.setSelected(true);
        long millis = System.currentTimeMillis();  
        java.sql.Date date = new java.sql.Date(millis);  
        dcDOB.setDate(date);
        cbbRole.setSelectedIndex(0);
        txtID.setText(DatabaseProcess.getID("execute sp_Admin_CertificateCell_identityID"));
        enableForm();
        txtUsername.setEditable(true);
        txtPassword.setEditable(true);
    }   
    private boolean checkValidateUsername(String user){
        boolean validate = true;
        String sql = "SELECT Username FROM Admin_CertificateCell ";
        ResultSet rs = DatabaseProcess.displayInformation(sql);
        try {
            if(rs.next()){
                if(rs.getString("Username").equals(user)){
                    validate = false;
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Check Validate Username Failed!", "Message", 1);
            validate = false;
        }
        return validate;
    }
    private static int getAge(Calendar bd) {
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - bd.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) <= bd.get(Calendar.DAY_OF_YEAR))
               return age - 1;
        return age;
    }    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        pnlDetails = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAdmin = new javax.swing.JTable();
        pnlInfo = new javax.swing.JPanel();
        txtName = new javax.swing.JTextField();
        lbName = new javax.swing.JLabel();
        lbUsername = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lbPassword = new javax.swing.JLabel();
        lbPhone = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        lbGender = new javax.swing.JLabel();
        radMale = new javax.swing.JRadioButton();
        radFemale = new javax.swing.JRadioButton();
        lbDOB = new javax.swing.JLabel();
        lbRole = new javax.swing.JLabel();
        scpAddress = new javax.swing.JScrollPane();
        txaAddress = new javax.swing.JTextArea();
        lbAddress = new javax.swing.JLabel();
        cbbRole = new javax.swing.JComboBox();
        dcDOB = new com.toedter.calendar.JDateChooser();
        lbID = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        pnlTitle = new javax.swing.JPanel();
        pnlSearch = new javax.swing.JPanel();
        lbSearch = new javax.swing.JLabel();
        lbNotification = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        pnlManage = new javax.swing.JPanel();
        pnlAdd = new javax.swing.JPanel();
        lbAdd = new javax.swing.JLabel();
        pnlModify = new javax.swing.JPanel();
        lbModify = new javax.swing.JLabel();
        pnlDelete = new javax.swing.JPanel();
        lbDelete = new javax.swing.JLabel();
        pnlReset = new javax.swing.JPanel();
        lbReset = new javax.swing.JLabel();
        pnlEdit = new javax.swing.JPanel();
        lbEdit = new javax.swing.JLabel();
        pnlCancel = new javax.swing.JPanel();
        lbCancel = new javax.swing.JLabel();
        pnlLostKey = new javax.swing.JPanel();
        lbLostKey = new javax.swing.JLabel();
        pnlReport = new javax.swing.JPanel();
        lbReport = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        pnlDetails.setBackground(new java.awt.Color(255, 255, 255));
        pnlDetails.setForeground(new java.awt.Color(204, 204, 204));
        pnlDetails.setLayout(new java.awt.BorderLayout());

        tblAdmin.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        tblAdmin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblAdmin.setRowHeight(20);
        tblAdmin.setRowMargin(5);
        tblAdmin.setSelectionBackground(new java.awt.Color(71, 120, 197));
        tblAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAdminMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblAdmin);

        pnlDetails.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pnlInfo.setBackground(new java.awt.Color(255, 255, 255));
        pnlInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Admin & Certificate Cell Information", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 15))); // NOI18N

        txtName.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNameKeyPressed(evt);
            }
        });

        lbName.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbName.setText("Full Name:");

        lbUsername.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbUsername.setText("Username:");

        txtUsername.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtUsername.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUsernameKeyPressed(evt);
            }
        });

        lbPassword.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbPassword.setText("Password:");

        lbPhone.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbPhone.setText("Phone:");

        txtPhone.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtPhone.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtPhoneMousePressed(evt);
            }
        });

        lbGender.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbGender.setText("Gender:");

        radMale.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(radMale);
        radMale.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        radMale.setSelected(true);
        radMale.setText("Male");

        radFemale.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(radFemale);
        radFemale.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        radFemale.setText("Female");

        lbDOB.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbDOB.setText("Date of Birth:");

        lbRole.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbRole.setText("Role:");

        txaAddress.setColumns(20);
        txaAddress.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txaAddress.setLineWrap(true);
        txaAddress.setRows(5);
        txaAddress.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txaAddressKeyPressed(evt);
            }
        });
        scpAddress.setViewportView(txaAddress);

        lbAddress.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbAddress.setText("Address:");

        cbbRole.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cbbRole.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Administrator", "Certificate Cell" }));

        dcDOB.setDateFormatString("yyyy-MM-dd");
        dcDOB.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        lbID.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbID.setText("ID:");

        txtID.setEditable(false);
        txtID.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtID.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout pnlInfoLayout = new javax.swing.GroupLayout(pnlInfo);
        pnlInfo.setLayout(pnlInfoLayout);
        pnlInfoLayout.setHorizontalGroup(
            pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfoLayout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addGap(139, 139, 139)
                        .addComponent(radMale, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(49, 49, 49)
                        .addComponent(radFemale, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(58, 58, 58))
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbName, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbDOB, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbGender, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbUsername, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbPassword, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbID, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(40, 40, 40)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtName)
                            .addComponent(dcDOB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtID)
                            .addComponent(txtUsername, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                            .addComponent(txtPassword))))
                .addGap(60, 60, 60)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbPhone, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbRole, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbAddress, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(40, 40, 40)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPhone, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scpAddress, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                    .addComponent(cbbRole, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(70, 70, 70))
        );
        pnlInfoLayout.setVerticalGroup(
            pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfoLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbID)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbPassword, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtPassword, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
            .addGroup(pnlInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbAddress, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scpAddress, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbPhone, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtPhone, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbbRole, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbRole, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        pnlInfoLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {dcDOB, txtID, txtName});

        pnlInfoLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtPassword, txtUsername});

        pnlTitle.setBackground(new java.awt.Color(71, 120, 197));
        pnlTitle.setPreferredSize(new java.awt.Dimension(100, 72));

        pnlSearch.setBackground(new java.awt.Color(71, 120, 197));

        lbSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cusc/certificategenerationsystem/images/image_search.png"))); // NOI18N

        javax.swing.GroupLayout pnlSearchLayout = new javax.swing.GroupLayout(pnlSearch);
        pnlSearch.setLayout(pnlSearchLayout);
        pnlSearchLayout.setHorizontalGroup(
            pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlSearchLayout.setVerticalGroup(
            pnlSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
        );

        lbNotification.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        lbNotification.setForeground(new java.awt.Color(255, 255, 255));
        lbNotification.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cusc/certificategenerationsystem/images/icon_OK.png"))); // NOI18N
        lbNotification.setText("Notify: Updated successfully");

        txtSearch.setFont(new java.awt.Font("Times New Roman", 2, 18)); // NOI18N
        txtSearch.setForeground(new java.awt.Color(204, 204, 204));
        txtSearch.setText("Search");
        txtSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSearchMouseClicked(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout pnlTitleLayout = new javax.swing.GroupLayout(pnlTitle);
        pnlTitle.setLayout(pnlTitleLayout);
        pnlTitleLayout.setHorizontalGroup(
            pnlTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTitleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbNotification, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63))
        );
        pnlTitleLayout.setVerticalGroup(
            pnlTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTitleLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbNotification)
                .addContainerGap())
            .addGroup(pnlTitleLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtSearch)
                    .addComponent(pnlSearch, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pnlManage.setBackground(new java.awt.Color(255, 255, 255));
        pnlManage.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Manage", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 15))); // NOI18N

        pnlAdd.setBackground(new java.awt.Color(120, 168, 252));
        pnlAdd.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlAddMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlAddMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlAddMousePressed(evt);
            }
        });

        lbAdd.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbAdd.setForeground(new java.awt.Color(255, 255, 255));
        lbAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cusc/certificategenerationsystem/images/icon_add.png"))); // NOI18N
        lbAdd.setText("Add");

        javax.swing.GroupLayout pnlAddLayout = new javax.swing.GroupLayout(pnlAdd);
        pnlAdd.setLayout(pnlAddLayout);
        pnlAddLayout.setHorizontalGroup(
            pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAddLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        pnlAddLayout.setVerticalGroup(
            pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAddLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(lbAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        pnlModify.setBackground(new java.awt.Color(120, 168, 252));
        pnlModify.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlModify.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlModifyMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlModifyMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlModifyMousePressed(evt);
            }
        });

        lbModify.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbModify.setForeground(new java.awt.Color(255, 255, 255));
        lbModify.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cusc/certificategenerationsystem/images/icon_update.png"))); // NOI18N
        lbModify.setText("Update");

        javax.swing.GroupLayout pnlModifyLayout = new javax.swing.GroupLayout(pnlModify);
        pnlModify.setLayout(pnlModifyLayout);
        pnlModifyLayout.setHorizontalGroup(
            pnlModifyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlModifyLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbModify)
                .addGap(25, 25, 25))
        );
        pnlModifyLayout.setVerticalGroup(
            pnlModifyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlModifyLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(lbModify, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        pnlDelete.setBackground(new java.awt.Color(120, 168, 252));
        pnlDelete.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlDeleteMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlDeleteMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlDeleteMousePressed(evt);
            }
        });

        lbDelete.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbDelete.setForeground(new java.awt.Color(255, 255, 255));
        lbDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cusc/certificategenerationsystem/images/icon_delete.png"))); // NOI18N
        lbDelete.setText("Delete");

        javax.swing.GroupLayout pnlDeleteLayout = new javax.swing.GroupLayout(pnlDelete);
        pnlDelete.setLayout(pnlDeleteLayout);
        pnlDeleteLayout.setHorizontalGroup(
            pnlDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDeleteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        pnlDeleteLayout.setVerticalGroup(
            pnlDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDeleteLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(lbDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        pnlReset.setBackground(new java.awt.Color(120, 168, 252));
        pnlReset.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlReset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlResetMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlResetMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlResetMousePressed(evt);
            }
        });

        lbReset.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbReset.setForeground(new java.awt.Color(255, 255, 255));
        lbReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cusc/certificategenerationsystem/images/icon_reset.png"))); // NOI18N
        lbReset.setText("Reset");

        javax.swing.GroupLayout pnlResetLayout = new javax.swing.GroupLayout(pnlReset);
        pnlReset.setLayout(pnlResetLayout);
        pnlResetLayout.setHorizontalGroup(
            pnlResetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlResetLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbReset, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        pnlResetLayout.setVerticalGroup(
            pnlResetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlResetLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(lbReset, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        pnlEdit.setBackground(new java.awt.Color(120, 168, 252));
        pnlEdit.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlEditMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlEditMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlEditMousePressed(evt);
            }
        });

        lbEdit.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbEdit.setForeground(new java.awt.Color(255, 255, 255));
        lbEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cusc/certificategenerationsystem/images/icon_edit.png"))); // NOI18N
        lbEdit.setText("Edit");

        javax.swing.GroupLayout pnlEditLayout = new javax.swing.GroupLayout(pnlEdit);
        pnlEdit.setLayout(pnlEditLayout);
        pnlEditLayout.setHorizontalGroup(
            pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEditLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlEditLayout.setVerticalGroup(
            pnlEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEditLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(lbEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        pnlCancel.setBackground(new java.awt.Color(255, 102, 102));
        pnlCancel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlCancelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlCancelMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlCancelMousePressed(evt);
            }
        });

        lbCancel.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbCancel.setForeground(new java.awt.Color(255, 255, 255));
        lbCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cusc/certificategenerationsystem/images/icon_cancel.png"))); // NOI18N
        lbCancel.setText("Cancel");

        javax.swing.GroupLayout pnlCancelLayout = new javax.swing.GroupLayout(pnlCancel);
        pnlCancel.setLayout(pnlCancelLayout);
        pnlCancelLayout.setHorizontalGroup(
            pnlCancelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCancelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlCancelLayout.setVerticalGroup(
            pnlCancelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCancelLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(lbCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        pnlLostKey.setBackground(new java.awt.Color(120, 168, 252));
        pnlLostKey.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlLostKey.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlLostKeyMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlLostKeyMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlLostKeyMousePressed(evt);
            }
        });

        lbLostKey.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbLostKey.setForeground(new java.awt.Color(255, 255, 255));
        lbLostKey.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cusc/certificategenerationsystem/images/icon_change_password.png"))); // NOI18N
        lbLostKey.setText("Password");

        javax.swing.GroupLayout pnlLostKeyLayout = new javax.swing.GroupLayout(pnlLostKey);
        pnlLostKey.setLayout(pnlLostKeyLayout);
        pnlLostKeyLayout.setHorizontalGroup(
            pnlLostKeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLostKeyLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(lbLostKey)
                .addGap(8, 8, 8))
        );
        pnlLostKeyLayout.setVerticalGroup(
            pnlLostKeyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLostKeyLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(lbLostKey, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        pnlReport.setBackground(new java.awt.Color(120, 168, 252));
        pnlReport.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlReportMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlReportMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlReportMousePressed(evt);
            }
        });

        lbReport.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbReport.setForeground(new java.awt.Color(255, 255, 255));
        lbReport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cusc/certificategenerationsystem/images/icon_report.png"))); // NOI18N
        lbReport.setText("Report");

        javax.swing.GroupLayout pnlReportLayout = new javax.swing.GroupLayout(pnlReport);
        pnlReport.setLayout(pnlReportLayout);
        pnlReportLayout.setHorizontalGroup(
            pnlReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlReportLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lbReport)
                .addGap(8, 8, 8))
        );
        pnlReportLayout.setVerticalGroup(
            pnlReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlReportLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(lbReport, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        javax.swing.GroupLayout pnlManageLayout = new javax.swing.GroupLayout(pnlManage);
        pnlManage.setLayout(pnlManageLayout);
        pnlManageLayout.setHorizontalGroup(
            pnlManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlModify, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlLostKey, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlManageLayout.setVerticalGroup(
            pnlManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlManageLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(pnlAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(pnlReset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(pnlDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(pnlLostKey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(pnlReport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(pnlEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(pnlModify, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(pnlCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 1342, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addComponent(pnlManage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(pnlDetails, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                        .addGap(10, 10, 10))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlManage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void pnlAddMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlAddMouseEntered
        MainJFrame.setColorButton(pnlAdd);
    }//GEN-LAST:event_pnlAddMouseEntered

    private void pnlAddMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlAddMouseExited
        MainJFrame.resetColorButton(pnlAdd);
    }//GEN-LAST:event_pnlAddMouseExited

    private void pnlModifyMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlModifyMouseEntered
        MainJFrame.setColorButton(pnlModify);
    }//GEN-LAST:event_pnlModifyMouseEntered

    private void pnlModifyMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlModifyMouseExited
        MainJFrame.resetColorButton(pnlModify);
    }//GEN-LAST:event_pnlModifyMouseExited

    private void pnlDeleteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDeleteMouseEntered
        MainJFrame.setColorButton(pnlDelete);
    }//GEN-LAST:event_pnlDeleteMouseEntered

    private void pnlDeleteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDeleteMouseExited
        MainJFrame.resetColorButton(pnlDelete);
    }//GEN-LAST:event_pnlDeleteMouseExited

    private void pnlResetMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlResetMouseEntered
        MainJFrame.setColorButton(pnlReset);
    }//GEN-LAST:event_pnlResetMouseEntered

    private void pnlResetMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlResetMouseExited
        MainJFrame.resetColorButton(pnlReset);
    }//GEN-LAST:event_pnlResetMouseExited

    private void pnlAddMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlAddMousePressed
        func = 1;
        updateAdmin_CertificateCell();
    }//GEN-LAST:event_pnlAddMousePressed

    private void tblAdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAdminMouseClicked
        disableForm();
        int row = tblAdmin.getSelectedRow();
        String idRow = (tblAdmin.getModel().getValueAt(row, 0)).toString();
        String sql = "SELECT * FROM Admin_CertificateCell WHERE ID='"+idRow+"'";
        ResultSet rs = DatabaseProcess.displayInformation(sql);
        try {
            if(rs.next()){
                txtID.setText(rs.getString("ID"));
                dcDOB.setDate(rs.getDate("DOB"));
                txtName.setText(rs.getString("Name"));
                txtUsername.setText(rs.getString("Username"));
                txtPassword.setText(rs.getString("Password"));
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
                String role =rs.getString("Role");
                switch(role){
                    case "Administrator":
                        cbbRole.setSelectedIndex(0);
                        break;
                    case "Certificate Cell":
                        cbbRole.setSelectedIndex(1);
                        break;
                }
                
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "INFORMATION DISPLAY FAILED", "Message", 1);
        }
        lbNotification.setVisible(false);
    }//GEN-LAST:event_tblAdminMouseClicked

    private void pnlResetMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlResetMousePressed
        resetForm();
        enableForm();
        txtUsername.setEditable(true);
    }//GEN-LAST:event_pnlResetMousePressed

    private void pnlModifyMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlModifyMousePressed
        func = 2;
        updateAdmin_CertificateCell();
        pnlCancel.setVisible(false);
        pnlModify.setVisible(false);
    }//GEN-LAST:event_pnlModifyMousePressed

    private void txtNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameKeyPressed
        lbNotification.setVisible(false);
    }//GEN-LAST:event_txtNameKeyPressed

    private void txtUsernameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsernameKeyPressed
        lbNotification.setVisible(false);
    }//GEN-LAST:event_txtUsernameKeyPressed

    private void txaAddressKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txaAddressKeyPressed
        lbNotification.setVisible(false);
    }//GEN-LAST:event_txaAddressKeyPressed

    private void txtPhoneMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPhoneMousePressed
        lbNotification.setVisible(false);
    }//GEN-LAST:event_txtPhoneMousePressed

    private void pnlDeleteMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDeleteMousePressed
        int click = JOptionPane.showConfirmDialog(null, "You sure you want to delete! Check the information you"
                + " want to delete to make sure it doesn't affect the system!","Warning!",JOptionPane.YES_NO_OPTION);
        if(click == JOptionPane.YES_OPTION){
            func = 3;
            updateAdmin_CertificateCell();
        } 
    }//GEN-LAST:event_pnlDeleteMousePressed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        DatabaseProcess.tableModel = (DefaultTableModel) tblAdmin.getModel();
        DatabaseProcess.tableModel.setRowCount(0);
        String search = txtSearch.getText();
        String sql = "SELECT [ID],[Name],[DOB],[Gender],[Address],[Phone],[Username],[Role] FROM Admin_CertificateCell WHERE ID like '%"+search+"%' OR Name like '%"+search+"%'";
        DatabaseProcess.loadData(sql, tblAdmin);
    }//GEN-LAST:event_txtSearchKeyReleased

    private void txtSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchMouseClicked
        txtSearch.setText(null);
        txtSearch.setForeground(Color.BLACK);
    }//GEN-LAST:event_txtSearchMouseClicked

    private void pnlEditMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlEditMouseEntered
        MainJFrame.setColorButton(pnlEdit);
    }//GEN-LAST:event_pnlEditMouseEntered

    private void pnlEditMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlEditMouseExited
        MainJFrame.resetColorButton(pnlEdit);
    }//GEN-LAST:event_pnlEditMouseExited

    private void pnlEditMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlEditMousePressed
        pnlModify.setVisible(true);
        pnlCancel.setVisible(true);
        enableForm();
    }//GEN-LAST:event_pnlEditMousePressed

    private void pnlCancelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlCancelMouseEntered
        pnlCancel.setBackground(new Color(255,51,51));
    }//GEN-LAST:event_pnlCancelMouseEntered

    private void pnlCancelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlCancelMouseExited
        pnlCancel.setBackground(new Color(255,102,102));
    }//GEN-LAST:event_pnlCancelMouseExited

    private void pnlCancelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlCancelMousePressed
        pnlCancel.setVisible(false);
        pnlModify.setVisible(false);
        disableForm();
    }//GEN-LAST:event_pnlCancelMousePressed

    private void pnlLostKeyMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLostKeyMouseEntered
        MainJFrame.setColorButton(pnlLostKey);
    }//GEN-LAST:event_pnlLostKeyMouseEntered

    private void pnlLostKeyMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLostKeyMouseExited
        MainJFrame.resetColorButton(pnlLostKey);
    }//GEN-LAST:event_pnlLostKeyMouseExited

    private void pnlLostKeyMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLostKeyMousePressed
        JFrame frame = new LostKeyAdminJFrame();
        frame.setVisible(true);
        frame.setTitle("Password retrieval");
    }//GEN-LAST:event_pnlLostKeyMousePressed

    private void pnlReportMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlReportMouseEntered
        MainJFrame.setColorButton(pnlReport);
    }//GEN-LAST:event_pnlReportMouseEntered

    private void pnlReportMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlReportMouseExited
        MainJFrame.resetColorButton(pnlReport);
    }//GEN-LAST:event_pnlReportMouseExited

    private void pnlReportMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlReportMousePressed
        Report.ExportExcel(tblAdmin);
    }//GEN-LAST:event_pnlReportMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbbRole;
    private com.toedter.calendar.JDateChooser dcDOB;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbAdd;
    private javax.swing.JLabel lbAddress;
    private javax.swing.JLabel lbCancel;
    private javax.swing.JLabel lbDOB;
    private javax.swing.JLabel lbDelete;
    private javax.swing.JLabel lbEdit;
    private javax.swing.JLabel lbGender;
    private javax.swing.JLabel lbID;
    private javax.swing.JLabel lbLostKey;
    private javax.swing.JLabel lbModify;
    private javax.swing.JLabel lbName;
    private javax.swing.JLabel lbNotification;
    private javax.swing.JLabel lbPassword;
    private javax.swing.JLabel lbPhone;
    private javax.swing.JLabel lbReport;
    private javax.swing.JLabel lbReset;
    private javax.swing.JLabel lbRole;
    private javax.swing.JLabel lbSearch;
    private javax.swing.JLabel lbUsername;
    private javax.swing.JPanel pnlAdd;
    private javax.swing.JPanel pnlCancel;
    private javax.swing.JPanel pnlDelete;
    private javax.swing.JPanel pnlDetails;
    private javax.swing.JPanel pnlEdit;
    private javax.swing.JPanel pnlInfo;
    private javax.swing.JPanel pnlLostKey;
    private javax.swing.JPanel pnlManage;
    private javax.swing.JPanel pnlModify;
    private javax.swing.JPanel pnlReport;
    private javax.swing.JPanel pnlReset;
    private javax.swing.JPanel pnlSearch;
    private javax.swing.JPanel pnlTitle;
    private javax.swing.JRadioButton radFemale;
    private javax.swing.JRadioButton radMale;
    private javax.swing.JScrollPane scpAddress;
    private javax.swing.JTable tblAdmin;
    private javax.swing.JTextArea txaAddress;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtName;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
