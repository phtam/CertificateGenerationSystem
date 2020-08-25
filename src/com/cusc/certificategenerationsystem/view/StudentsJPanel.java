package com.cusc.certificategenerationsystem.view;

import com.cusc.certificategenerationsystem.db.DatabaseConnection;
import static com.cusc.certificategenerationsystem.db.DatabaseConnection.conn;
import com.cusc.certificategenerationsystem.db.DatabaseProcess;
import com.cusc.certificategenerationsystem.report.Report;
import com.cusc.certificategenerationsystem.security.MD5Encryptor;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PC_HP
 */
public class StudentsJPanel extends javax.swing.JPanel {
    int func=0;
    /**
     * Creates new form StudentsJPanel
     */
    public StudentsJPanel()  {
        initComponents();
        loadCourseInfo();
        loadClassInfo();
        HomeJPanel.setColorCombobox(new JComboBox[]{cbbClassID,cbbCourseID,cbbPayments});
        grantAccess();
        pnlModify.setVisible(false);
        pnlCancel.setVisible(false);
        lbNotification.setVisible(false);
        DatabaseProcess.tableModel = new DefaultTableModel();
        DatabaseProcess.loadData("SELECT S.[StudentID],[Name],[DOB],[Gender],"+
            "[Address],[Phone],[Username],[Status],R.[CourseID],[RegisDate],[Payments],C.[ClassID]  FROM Students S\n" +
            "JOIN ClassList cl ON cl.StudentID=S.StudentID\n" +
            "JOIN Class c ON c.ClassID=cl.ClassID\n" +
            "JOIN Registry r ON S.StudentID=r.StudentID and c.CourseID=r.CourseID ORDER BY S.[StudentID]", tblStudents);
        long millis = System.currentTimeMillis();
        java.sql.Date currentDate = new java.sql.Date(millis);
        dcDOB.setDate(currentDate);
        dcRegisDate.setDate(currentDate);
        txtStudentID.setText(DatabaseProcess.getID("execute sp_Student_identityID"));
    }

    private void grantAccess(){
        if("Certificate Cell".equals(DatabaseProcess.checkRole())){
            disableForm();
            pnlManage.setVisible(false);
        } 
    }
    private void disableForm(){
        txtName.setEditable(false);
        txtPassword.setEditable(false);
        txtUsername.setEditable(false);
        txtPhone.setEditable(false);
        txaAddress.setEditable(false);
        txtStatus.setEditable(false);
        dcDOB.setEnabled(false);
        dcRegisDate.setEnabled(false);
        radMale.setEnabled(false);
        radFemale.setEnabled(false);
        cbbClassID.setEnabled(false);
        cbbCourseID.setEnabled(false);
        cbbPayments.setEnabled(false);
        cbDefault.setEnabled(false);
    }  
    private void enableForm(){
        txtName.setEditable(true);
        txtPhone.setEditable(true);
        txaAddress.setEditable(true);
        dcDOB.setEnabled(true);
        dcRegisDate.setEnabled(true);
        radMale.setEnabled(true);
        radFemale.setEnabled(true);
        cbbPayments.setEnabled(true);
    } 
    private void updateStudent(){
        DatabaseConnection.connectSQL();
        String studentID = txtStudentID.getText();
        String name = txtName.getText();
        Date dob = dcDOB.getDate();
        String gender = null;
        if(radMale.isSelected()){
            gender="Male";
        } else {
            gender="Female";
        }
        String username = txtUsername.getText();
        String pass = txtPassword.getText();
        String password = MD5Encryptor.encrypt(pass);
        String phone = txtPhone.getText();
        String address = txaAddress.getText();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateDOB = sdf.format(dob);
        Calendar calendarDOB = Calendar.getInstance();
        calendarDOB.setTime(dob);
        String classID = cbbClassID.getSelectedItem().toString();
        String courseID = cbbCourseID.getSelectedItem().toString();
        Date regisDate = dcRegisDate.getDate();
        String registryDate = sdf.format(regisDate);
        String payments = cbbPayments.getSelectedItem().toString();
        int row;
        if(name.matches("^[a-zA-Z_0-9\\s]{5,40}")==false){
            JOptionPane.showMessageDialog(null, "Name cannot empty. Name does not contain special characters!", "Message", 1);
            txtName.requestFocus();
        }else if(getAge(calendarDOB)<18){
            JOptionPane.showMessageDialog(null, "Student must be over 18 years old to register!", "Message", 1);
            dcDOB.requestFocus();
        }else if(username.matches("^[a-zA-Z_0-9]{5,15}")==false){
            JOptionPane.showMessageDialog(null, "Username must be between 5 and 15 characters. Name does not contain special characters!", "Message", 1);
            txtUsername.requestFocus();
        }else if(pass.matches(".{8,50}")==false){
            JOptionPane.showMessageDialog(null, "Password must be greater than 8 characters and less than 50 characters!", "Message", 1);
            txtPassword.requestFocus();
        }else if(address.matches(".{5,120}") == false){
            JOptionPane.showMessageDialog(null, "Address must be greater than 5 characters and less than 120 characters!", "Message",1);
            txaAddress.requestFocus();
        }else if(phone.matches("^[0-9]{8,15}")==false){
            JOptionPane.showMessageDialog(null, "Invalid phone number, please try again!", "Message", 1);
            txtPhone.requestFocus();
        }else if(getTime(registryDate)<0){
            JOptionPane.showMessageDialog(null, "Registration date is invalid!", "Message", 1);
            dcRegisDate.requestFocus();
        }else{
            switch(func){
                case 1:
                    if(checkValidateUsername(username)==false){
                        JOptionPane.showMessageDialog(null, "Username already exists. Please try again!", "Message", 1);
                        txtUsername.requestFocus();
                    }else{
                        String sql1 = "INSERT INTO Students VALUES('"+studentID+"','"+name+"','"+dateDOB+"','"+gender+"','"+address+"','"
                        +phone+"','"+username+"','"+password+"')";
                        String sql2 = "INSERT INTO ClassList VALUES('"+studentID+"','"+classID+"')";
                        String sql3 = "INSERT INTO Registry VALUES('"+courseID+"','"+studentID+"','"+registryDate+"','"+
                                payments+"',null,'Studying')";
                        row = DatabaseProcess.updateStudent(sql1, sql2, sql3);
                        if(row != 0){
                            notifySuccessful();
                        }
                    }
                    break;
                case 2:
                    String sql4 = "UPDATE Students SET "+
                            "Name='"+name+"',"+
                            "DOB='"+dateDOB+"',"+
                            "Gender='"+gender+"',"+
                            "Address='"+address+"',"+
                            "Phone='"+phone+"',"+
                            "Username='"+username+"' "+
                            "WHERE StudentID='"+studentID+"'";
                    String sql5= "UPDATE Registry SET "+
                            "RegisDate='"+registryDate+"',"+
                            "Payments='"+payments+"' "+
                            "WHERE CourseID='"+courseID+"' AND StudentID='"+studentID+"'";
                    try {
                        DatabaseConnection.conn.setAutoCommit(false);
                        DatabaseConnection.connectSQL();
                        Statement state = conn.createStatement();
                        int row1 = state.executeUpdate(sql4);
                        int row2 = state.executeUpdate(sql5);
                        DatabaseConnection.conn.commit();
                        DatabaseConnection.conn.setAutoCommit(true);
                        row =row1*row2;
                        if(row!=0){
                            notifySuccessful();
                        }
                        state.close();
                        DatabaseConnection.conn.close();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null,"Data already exists in the system!" , "Message", 1);
                    }
                    break;
                case 3:
                    String sql6 = "DELETE FROM Registry WHERE CourseID='"+courseID+"' AND StudentID='"+studentID+"' ";
                    String sql7 = "DELETE FROM ClassList WHERE ClassID='"+classID+"' AND StudentID='"+studentID+"' ";
                    row = DatabaseProcess.updateStudent2(sql6, sql7);
                    if(row!=0){
                        notifySuccessful();
                    }
                    break;
            }         
        }
    }
    public void notifySuccessful(){
        lbNotification.setVisible(true);
        DatabaseProcess.tableModel = (DefaultTableModel)tblStudents.getModel();
        DatabaseProcess.tableModel.setRowCount(0);
        DatabaseProcess.loadData("SELECT S.[StudentID],[Name],[DOB],[Gender],"+
            "[Address],[Phone],[Username],[Status],R.[CourseID],[RegisDate],[Payments],C.[ClassID]  FROM Students S\n" +
            "JOIN ClassList cl ON cl.StudentID=S.StudentID\n" +
            "JOIN Class c ON c.ClassID=cl.ClassID\n" +
            "JOIN Registry r ON S.StudentID=r.StudentID and c.CourseID=r.CourseID ORDER BY S.[StudentID]", tblStudents);
        txtStudentID.setText(DatabaseProcess.getID("execute sp_Student_identityID"));
        resetForm();
        enableForm();
    }
    private void resetForm(){
        txtName.setText(null);
        txtUsername.setText(null);
        txtPassword.setText(null);
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
        cbDefault.setSelected(false);
        cbbClassID.setEnabled(true);
        cbbCourseID.setEnabled(true);
        txtUsername.setEditable(true);
        txtPassword.setEditable(true);
        cbDefault.setEnabled(true);
        txtStatus.setText(null);
        txtStudentID.setText(DatabaseProcess.getID("execute sp_Student_identityID"));
        loadCourseInfo();
        loadClassInfo();
        enableForm();
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
    private void displayCourse(){
        try {
            int row = tblStudents.getSelectedRow();
            String idRow = (tblStudents.getModel().getValueAt(row, 0)).toString();
            String sql = "SELECT * FROM Registry WHERE StudentID='"+idRow+"'";
            ResultSet rs = DatabaseProcess.displayInformation(sql);
            if(rs.next()){
                cbbCourseID.setSelectedItem(rs.getString("CourseID"));
                dcRegisDate.setDate(rs.getDate("RegisDate"));
                String payments =rs.getString("Payments");
                txtStatus.setText(rs.getString("Status"));
                switch(payments){
                    case "Unpaid":
                        cbbPayments.setSelectedIndex(0);
                        break;
                    case "Paid":
                        cbbPayments.setSelectedIndex(1);
                        break;
                }
            }
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "INFORMATION DISPLAY FAILED", "Message", 1);
        }
        loadClassInfo();
    }
    private void displayStudent(){
        int row = tblStudents.getSelectedRow();
        String idRow = (tblStudents.getModel().getValueAt(row, 0)).toString();
        String sql = "SELECT * FROM Students WHERE StudentID='"+idRow+"'";
        ResultSet rs = DatabaseProcess.displayInformation(sql);
        try {
            if(rs.next()){
                txtStudentID.setText(rs.getString("StudentID"));
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
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "INFORMATION DISPLAY FAILED", "Message", 1);
        }
    }
    private void displayClass(){
        try {
            int row = tblStudents.getSelectedRow();
            String idRow = (tblStudents.getModel().getValueAt(row, 0)).toString();
            String sql = "SELECT * FROM ClassList WHERE StudentID='"+idRow+"'";
            ResultSet rs = DatabaseProcess.displayInformation(sql);
            if(rs.next()){
                cbbClassID.setSelectedItem(rs.getString("ClassID"));   
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "INFORMATION DISPLAY FAILED", "Message", 1);
        }
    }
    private boolean checkValidateUsername(String user){
        boolean validate = true;
        String sql = "SELECT Username FROM Students";
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
    private long getTime(String date) {
        long getDaysDiff = 0;
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        Date date1 = null;
        Date date2 = null;
        try {
        String startDate = date;
        String endDate = simpleDateFormat.format(currentDate);
        date1 = simpleDateFormat.parse(startDate);
        date2 = simpleDateFormat.parse(endDate);
        long getDiff = date2.getTime() - date1.getTime();
        getDaysDiff = getDiff / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
        }
        return getDaysDiff;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroupGender = new javax.swing.ButtonGroup();
        pnlDetails = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblStudents = new javax.swing.JTable();
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
        cbDefault = new javax.swing.JCheckBox();
        lbPayments = new javax.swing.JLabel();
        cbbPayments = new javax.swing.JComboBox();
        txtPassword = new javax.swing.JPasswordField();
        lbStatus = new javax.swing.JLabel();
        txtStatus = new javax.swing.JTextField();
        pnlTitle = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        pnlSearch = new javax.swing.JPanel();
        lbSearch = new javax.swing.JLabel();
        lbNotification = new javax.swing.JLabel();
        pnlManage = new javax.swing.JPanel();
        pnlAdd = new javax.swing.JPanel();
        lbAdd = new javax.swing.JLabel();
        pnlModify = new javax.swing.JPanel();
        lbModify = new javax.swing.JLabel();
        pnlDelete = new javax.swing.JPanel();
        lbDelete = new javax.swing.JLabel();
        pnlRegistry = new javax.swing.JPanel();
        lbRegistry = new javax.swing.JLabel();
        pnlReset = new javax.swing.JPanel();
        lbReset = new javax.swing.JLabel();
        pnlEdit = new javax.swing.JPanel();
        lbEdit = new javax.swing.JLabel();
        pnlCancel = new javax.swing.JPanel();
        lbCancel = new javax.swing.JLabel();
        pnlGraduate = new javax.swing.JPanel();
        lbGraduate = new javax.swing.JLabel();
        pnlStudying = new javax.swing.JPanel();
        lbStudying = new javax.swing.JLabel();
        pnlLostKey = new javax.swing.JPanel();
        lbLostKey = new javax.swing.JLabel();
        pnlReport = new javax.swing.JPanel();
        lbReport = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        pnlDetails.setBackground(new java.awt.Color(255, 255, 255));
        pnlDetails.setForeground(new java.awt.Color(204, 204, 204));
        pnlDetails.setLayout(new java.awt.BorderLayout());

        tblStudents.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        tblStudents.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblStudents.setRowHeight(20);
        tblStudents.setRowMargin(5);
        tblStudents.setSelectionBackground(new java.awt.Color(71, 120, 197));
        tblStudents.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblStudentsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblStudents);

        pnlDetails.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pnlInfo.setBackground(new java.awt.Color(255, 255, 255));
        pnlInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Student Information", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 15))); // NOI18N

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

        lbGender.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbGender.setText("Gender:");

        radMale.setBackground(new java.awt.Color(255, 255, 255));
        btnGroupGender.add(radMale);
        radMale.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        radMale.setSelected(true);
        radMale.setText("Male");

        radFemale.setBackground(new java.awt.Color(255, 255, 255));
        btnGroupGender.add(radFemale);
        radFemale.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        radFemale.setText("Female");

        lbDOB.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbDOB.setText("Date of Birth:");

        lbCourseID.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbCourseID.setText("CourseID:");

        txaAddress.setColumns(20);
        txaAddress.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txaAddress.setLineWrap(true);
        txaAddress.setRows(5);
        scpAddress.setViewportView(txaAddress);

        lbAddress.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbAddress.setText("Address:");

        cbbCourseID.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cbbCourseID.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbbCourseIDPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        dcDOB.setDateFormatString("yyyy-MM-dd");
        dcDOB.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        lbID.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbID.setText("StudentID:");

        txtStudentID.setEditable(false);
        txtStudentID.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtStudentID.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        lbClassID.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbClassID.setText("ClassID:");

        cbbClassID.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        lbRegisDate.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbRegisDate.setText("Registry Date:");

        dcRegisDate.setDateFormatString("yyyy-MM-dd");
        dcRegisDate.setFocusable(false);
        dcRegisDate.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        cbDefault.setBackground(new java.awt.Color(255, 255, 255));
        cbDefault.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cbDefault.setText("Default Password");
        cbDefault.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cbDefaultMousePressed(evt);
            }
        });

        lbPayments.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbPayments.setText("Payments:");

        cbbPayments.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cbbPayments.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Unpaid", "Paid" }));

        lbStatus.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbStatus.setText("Status:");

        txtStatus.setEditable(false);
        txtStatus.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtStatus.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout pnlInfoLayout = new javax.swing.GroupLayout(pnlInfo);
        pnlInfo.setLayout(pnlInfoLayout);
        pnlInfoLayout.setHorizontalGroup(
            pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfoLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbName, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbDOB, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbGender, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbUsername, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbPassword, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbID, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbPhone, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(38, 38, 38)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtStudentID)
                    .addComponent(txtName)
                    .addComponent(dcDOB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addComponent(radMale, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                        .addGap(49, 49, 49)
                        .addComponent(radFemale, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE))
                    .addComponent(txtUsername)
                    .addComponent(txtPassword)
                    .addComponent(cbDefault, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtPhone))
                .addGap(60, 60, 60)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbRegisDate)
                    .addComponent(lbPayments)
                    .addComponent(lbClassID)
                    .addComponent(lbCourseID)
                    .addComponent(lbAddress)
                    .addComponent(lbStatus))
                .addGap(40, 40, 40)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scpAddress, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                    .addComponent(cbbCourseID, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dcRegisDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtStatus)
                    .addComponent(cbbClassID, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbbPayments, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(60, 60, 60))
        );
        pnlInfoLayout.setVerticalGroup(
            pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbID)
                            .addComponent(txtStudentID, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbName, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(20, 20, 20)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dcDOB, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbDOB, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(20, 20, 20)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbGender)
                            .addComponent(radMale)
                            .addComponent(radFemale))
                        .addGap(20, 20, 20)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbUsername))
                        .addGap(20, 20, 20)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPassword, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbPassword, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(9, 9, 9)
                        .addComponent(cbDefault)
                        .addGap(10, 10, 10)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbPhone, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtPhone, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbAddress, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(scpAddress, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbbCourseID, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbCourseID, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(20, 20, 20)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbbClassID, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbClassID, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(20, 20, 20)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbbPayments, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbPayments, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(20, 20, 20)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbRegisDate, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dcRegisDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbStatus, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(10, 10, 10))
        );

        pnlInfoLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {dcDOB, txtName, txtPassword, txtStudentID, txtUsername});

        pnlInfoLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cbbClassID, cbbCourseID, cbbPayments, dcRegisDate, txtPhone, txtStatus});

        pnlTitle.setBackground(new java.awt.Color(71, 120, 197));
        pnlTitle.setPreferredSize(new java.awt.Dimension(100, 72));

        txtSearch.setFont(new java.awt.Font("Times New Roman", 2, 18)); // NOI18N
        txtSearch.setForeground(new java.awt.Color(204, 204, 204));
        txtSearch.setText("ID");
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
            .addComponent(lbSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        lbNotification.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        lbNotification.setForeground(new java.awt.Color(255, 255, 255));
        lbNotification.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cusc/certificategenerationsystem/images/icon_OK.png"))); // NOI18N
        lbNotification.setText("Notify: Updated successfully");

        javax.swing.GroupLayout pnlTitleLayout = new javax.swing.GroupLayout(pnlTitle);
        pnlTitle.setLayout(pnlTitleLayout);
        pnlTitleLayout.setHorizontalGroup(
            pnlTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTitleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbNotification, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 615, Short.MAX_VALUE)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63))
        );
        pnlTitleLayout.setVerticalGroup(
            pnlTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTitleLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(pnlSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(29, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTitleLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbNotification)
                .addContainerGap())
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
                .addGap(12, 12, 12)
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
            .addGroup(pnlModifyLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbModify, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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

        pnlRegistry.setBackground(new java.awt.Color(120, 168, 252));
        pnlRegistry.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlRegistry.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlRegistryMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlRegistryMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlRegistryMousePressed(evt);
            }
        });

        lbRegistry.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbRegistry.setForeground(new java.awt.Color(255, 255, 255));
        lbRegistry.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cusc/certificategenerationsystem/images/icon_registry_student.png"))); // NOI18N
        lbRegistry.setText("Registry");

        javax.swing.GroupLayout pnlRegistryLayout = new javax.swing.GroupLayout(pnlRegistry);
        pnlRegistry.setLayout(pnlRegistryLayout);
        pnlRegistryLayout.setHorizontalGroup(
            pnlRegistryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRegistryLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(lbRegistry, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlRegistryLayout.setVerticalGroup(
            pnlRegistryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRegistryLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(lbRegistry, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addGap(12, 12, 12)
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
                .addGap(12, 12, 12)
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

        pnlGraduate.setBackground(new java.awt.Color(120, 168, 252));
        pnlGraduate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlGraduate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlGraduateMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlGraduateMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlGraduateMousePressed(evt);
            }
        });

        lbGraduate.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbGraduate.setForeground(new java.awt.Color(255, 255, 255));
        lbGraduate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cusc/certificategenerationsystem/images/icon_graduate.png"))); // NOI18N
        lbGraduate.setText("Graduate");

        javax.swing.GroupLayout pnlGraduateLayout = new javax.swing.GroupLayout(pnlGraduate);
        pnlGraduate.setLayout(pnlGraduateLayout);
        pnlGraduateLayout.setHorizontalGroup(
            pnlGraduateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlGraduateLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(lbGraduate)
                .addGap(7, 7, 7))
        );
        pnlGraduateLayout.setVerticalGroup(
            pnlGraduateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlGraduateLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(lbGraduate, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        pnlStudying.setBackground(new java.awt.Color(120, 168, 252));
        pnlStudying.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlStudying.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlStudyingMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlStudyingMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlStudyingMousePressed(evt);
            }
        });

        lbStudying.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbStudying.setForeground(new java.awt.Color(255, 255, 255));
        lbStudying.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cusc/certificategenerationsystem/images/icon_people.png"))); // NOI18N
        lbStudying.setText("Studying");

        javax.swing.GroupLayout pnlStudyingLayout = new javax.swing.GroupLayout(pnlStudying);
        pnlStudying.setLayout(pnlStudyingLayout);
        pnlStudyingLayout.setHorizontalGroup(
            pnlStudyingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudyingLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(lbStudying, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlStudyingLayout.setVerticalGroup(
            pnlStudyingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStudyingLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(lbStudying, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addComponent(pnlRegistry, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlGraduate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlStudying, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlLostKey, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlManageLayout.setVerticalGroup(
            pnlManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlManageLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(pnlAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(pnlRegistry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(pnlReset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(pnlStudying, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(pnlGraduate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 1206, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addComponent(pnlManage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(pnlDetails, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                        .addGap(10, 10, 10))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlManage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void pnlAddMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlAddMouseEntered
        MainJFrame.setColorButton(pnlAdd);
    }//GEN-LAST:event_pnlAddMouseEntered

    private void pnlAddMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlAddMouseExited
        MainJFrame.resetColorButton(pnlAdd);
    }//GEN-LAST:event_pnlAddMouseExited

    private void pnlAddMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlAddMousePressed
        try{
            func=1;
        updateStudent();
        }catch(NullPointerException e){
            JOptionPane.showMessageDialog(null, "Please complete all information.You must enter course and class information!", "Message", 1);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Registry failed. Please check again!", "Message", 1);
        }
    }//GEN-LAST:event_pnlAddMousePressed

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

    private void pnlRegistryMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlRegistryMouseEntered
        MainJFrame.setColorButton(pnlRegistry);
    }//GEN-LAST:event_pnlRegistryMouseEntered

    private void pnlRegistryMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlRegistryMouseExited
        MainJFrame.resetColorButton(pnlRegistry);
    }//GEN-LAST:event_pnlRegistryMouseExited

    private void pnlResetMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlResetMouseEntered
        MainJFrame.setColorButton(pnlReset);
    }//GEN-LAST:event_pnlResetMouseEntered

    private void pnlResetMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlResetMouseExited
        MainJFrame.resetColorButton(pnlReset);
    }//GEN-LAST:event_pnlResetMouseExited

    private void cbDefaultMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbDefaultMousePressed
        if(!cbDefault.isSelected()){
            txtPassword.setText("123456789");
        }
        if(cbDefault.isSelected()){
            txtPassword.setText(null);
        }
    }//GEN-LAST:event_cbDefaultMousePressed

    private void txtNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameKeyPressed
        lbNotification.setVisible(false);
    }//GEN-LAST:event_txtNameKeyPressed

    private void txtUsernameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsernameKeyPressed
        lbNotification.setVisible(false);
    }//GEN-LAST:event_txtUsernameKeyPressed

    private void tblStudentsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblStudentsMouseClicked
        displayStudent();
        displayCourse();
        displayClass();
        disableForm();
        pnlModify.setVisible(false);
        pnlCancel.setVisible(false);
    }//GEN-LAST:event_tblStudentsMouseClicked

    private void pnlResetMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlResetMousePressed
        DatabaseProcess.tableModel = (DefaultTableModel)tblStudents.getModel();
        DatabaseProcess.tableModel.setRowCount(0);
        DatabaseProcess.loadData("SELECT S.[StudentID],[Name],[DOB],[Gender],"+
            "[Address],[Phone],[Username],[Status],R.[CourseID],[RegisDate],[Payments],C.[ClassID]  FROM Students S\n" +
            "JOIN ClassList cl ON cl.StudentID=S.StudentID\n" +
            "JOIN Class c ON c.ClassID=cl.ClassID\n" +
            "JOIN Registry r ON S.StudentID=r.StudentID and c.CourseID=r.CourseID ORDER BY S.[StudentID]", tblStudents);
        txtStudentID.setText(DatabaseProcess.getID("execute sp_Student_identityID"));
        resetForm();
        enableForm();
    }//GEN-LAST:event_pnlResetMousePressed

    private void pnlRegistryMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlRegistryMousePressed
        JFrame frame = new RegistryJFrame();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setTitle("Additional Registration");
    }//GEN-LAST:event_pnlRegistryMousePressed

    private void cbbCourseIDPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbbCourseIDPopupMenuWillBecomeInvisible
        loadClassInfo();
    }//GEN-LAST:event_cbbCourseIDPopupMenuWillBecomeInvisible

    private void pnlEditMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlEditMouseEntered
        MainJFrame.setColorButton(pnlEdit);
    }//GEN-LAST:event_pnlEditMouseEntered

    private void pnlEditMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlEditMouseExited
        MainJFrame.resetColorButton(pnlEdit);
    }//GEN-LAST:event_pnlEditMouseExited

    private void pnlCancelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlCancelMouseEntered
        pnlCancel.setBackground(new Color(255,51,51));
    }//GEN-LAST:event_pnlCancelMouseEntered

    private void pnlCancelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlCancelMouseExited
        pnlCancel.setBackground(new Color(255,102,102));
    }//GEN-LAST:event_pnlCancelMouseExited

    private void pnlEditMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlEditMousePressed
        pnlModify.setVisible(true);
        pnlCancel.setVisible(true);
        cbbClassID.setEnabled(false);
        cbbCourseID.setEnabled(false);
        enableForm();
    }//GEN-LAST:event_pnlEditMousePressed

    private void pnlDeleteMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDeleteMousePressed
        int click = JOptionPane.showConfirmDialog(null, "You sure you want to delete! Check the information you"
            + " want to delete to make sure it doesn't affect the system!","Warning!",JOptionPane.YES_NO_OPTION);
        if(click == JOptionPane.YES_OPTION){
            func = 3;
            updateStudent();
        }
    }//GEN-LAST:event_pnlDeleteMousePressed

    private void pnlCancelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlCancelMousePressed
        pnlCancel.setVisible(false);
        pnlModify.setVisible(false);
        disableForm();
    }//GEN-LAST:event_pnlCancelMousePressed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        DatabaseProcess.tableModel = (DefaultTableModel) tblStudents.getModel();
        DatabaseProcess.tableModel.setRowCount(0);
        String search = txtSearch.getText();
        DatabaseProcess.loadData("SELECT S.[StudentID],[Name],[DOB],[Gender],"+
            "[Address],[Phone],[Username],[Status],R.[CourseID],[RegisDate],[Payments],C.[ClassID]  FROM Students S\n" +
            "JOIN ClassList cl ON cl.StudentID=S.StudentID\n" +
            "JOIN Class c ON c.ClassID=cl.ClassID\n" +
            "JOIN Registry r ON S.StudentID=r.StudentID and c.CourseID=r.CourseID " +
            "WHERE S.StudentID LIKE'%"+search+"%' OR Name LIKE'%"+search+"%' OR Username LIKE'%"+search+"%'", tblStudents);
    }//GEN-LAST:event_txtSearchKeyReleased

    private void txtSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchMouseClicked
        txtSearch.setText(null);
        txtSearch.setForeground(Color.BLACK);
    }//GEN-LAST:event_txtSearchMouseClicked

    private void pnlModifyMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlModifyMousePressed
        try{
            func=2;
            updateStudent();
        }catch(NullPointerException e){
            JOptionPane.showMessageDialog(null, "Please complete all information.You must enter course and class information!", "Message", 1);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Registry failed. Please check again!", "Message", 1);
        }
        pnlCancel.setVisible(false);
        pnlModify.setVisible(false);
    }//GEN-LAST:event_pnlModifyMousePressed

    private void pnlGraduateMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlGraduateMouseEntered
        MainJFrame.setColorButton(pnlGraduate);
    }//GEN-LAST:event_pnlGraduateMouseEntered

    private void pnlGraduateMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlGraduateMouseExited
        MainJFrame.resetColorButton(pnlGraduate);
    }//GEN-LAST:event_pnlGraduateMouseExited

    private void pnlStudyingMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlStudyingMouseEntered
        MainJFrame.setColorButton(pnlStudying);
    }//GEN-LAST:event_pnlStudyingMouseEntered

    private void pnlStudyingMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlStudyingMouseExited
        MainJFrame.resetColorButton(pnlStudying);
    }//GEN-LAST:event_pnlStudyingMouseExited

    private void pnlStudyingMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlStudyingMousePressed
        DatabaseProcess.tableModel = (DefaultTableModel)tblStudents.getModel();
        DatabaseProcess.tableModel.setRowCount(0);
        DatabaseProcess.loadData("SELECT S.[StudentID],[Name],[DOB],[Gender],"+
            "[Address],[Phone],[Username],[Status],R.[CourseID],[RegisDate],[Payments],C.[ClassID]  FROM Students S\n" +
            "JOIN ClassList cl ON cl.StudentID=S.StudentID\n" +
            "JOIN Class c ON c.ClassID=cl.ClassID\n" +
            "JOIN Registry r ON S.StudentID=r.StudentID and c.CourseID=r.CourseID "+
            "WHERE Status='Studying'", tblStudents);
    }//GEN-LAST:event_pnlStudyingMousePressed

    private void pnlGraduateMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlGraduateMousePressed
        DatabaseProcess.tableModel = (DefaultTableModel)tblStudents.getModel();
        DatabaseProcess.tableModel.setRowCount(0);
        DatabaseProcess.loadData("SELECT S.[StudentID],[Name],[DOB],[Gender],"+
            "[Address],[Phone],[Username],[Status],R.[CourseID],[RegisDate],[Payments],C.[ClassID]  FROM Students S\n" +
            "JOIN ClassList cl ON cl.StudentID=S.StudentID\n" +
            "JOIN Class c ON c.ClassID=cl.ClassID\n" +
            "JOIN Registry r ON S.StudentID=r.StudentID and c.CourseID=r.CourseID "+
            "WHERE Status='Graduated'", tblStudents);
    }//GEN-LAST:event_pnlGraduateMousePressed

    private void pnlLostKeyMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLostKeyMouseEntered
        MainJFrame.setColorButton(pnlLostKey);
    }//GEN-LAST:event_pnlLostKeyMouseEntered

    private void pnlLostKeyMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLostKeyMouseExited
        MainJFrame.resetColorButton(pnlLostKey);
    }//GEN-LAST:event_pnlLostKeyMouseExited

    private void pnlLostKeyMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLostKeyMousePressed
        JFrame frame = new LostKeyStudentJFrame();
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
        Report.ExportExcel(tblStudents);
    }//GEN-LAST:event_pnlReportMousePressed


    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btnGroupGender;
    private javax.swing.JCheckBox cbDefault;
    private javax.swing.JComboBox cbbClassID;
    private javax.swing.JComboBox cbbCourseID;
    private javax.swing.JComboBox cbbPayments;
    private com.toedter.calendar.JDateChooser dcDOB;
    private com.toedter.calendar.JDateChooser dcRegisDate;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbAdd;
    private javax.swing.JLabel lbAddress;
    private javax.swing.JLabel lbCancel;
    private javax.swing.JLabel lbClassID;
    private javax.swing.JLabel lbCourseID;
    private javax.swing.JLabel lbDOB;
    private javax.swing.JLabel lbDelete;
    private javax.swing.JLabel lbEdit;
    private javax.swing.JLabel lbGender;
    private javax.swing.JLabel lbGraduate;
    private javax.swing.JLabel lbID;
    private javax.swing.JLabel lbLostKey;
    private javax.swing.JLabel lbModify;
    private javax.swing.JLabel lbName;
    private javax.swing.JLabel lbNotification;
    private javax.swing.JLabel lbPassword;
    private javax.swing.JLabel lbPayments;
    private javax.swing.JLabel lbPhone;
    private javax.swing.JLabel lbRegisDate;
    private javax.swing.JLabel lbRegistry;
    private javax.swing.JLabel lbReport;
    private javax.swing.JLabel lbReset;
    private javax.swing.JLabel lbSearch;
    private javax.swing.JLabel lbStatus;
    private javax.swing.JLabel lbStudying;
    private javax.swing.JLabel lbUsername;
    private javax.swing.JPanel pnlAdd;
    private javax.swing.JPanel pnlCancel;
    private javax.swing.JPanel pnlDelete;
    private javax.swing.JPanel pnlDetails;
    private javax.swing.JPanel pnlEdit;
    private javax.swing.JPanel pnlGraduate;
    private javax.swing.JPanel pnlInfo;
    private javax.swing.JPanel pnlLostKey;
    private javax.swing.JPanel pnlManage;
    private javax.swing.JPanel pnlModify;
    private javax.swing.JPanel pnlRegistry;
    private javax.swing.JPanel pnlReport;
    private javax.swing.JPanel pnlReset;
    private javax.swing.JPanel pnlSearch;
    private javax.swing.JPanel pnlStudying;
    private javax.swing.JPanel pnlTitle;
    private javax.swing.JRadioButton radFemale;
    private javax.swing.JRadioButton radMale;
    private javax.swing.JScrollPane scpAddress;
    private javax.swing.JTable tblStudents;
    private javax.swing.JTextArea txaAddress;
    private javax.swing.JTextField txtName;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtStatus;
    private javax.swing.JTextField txtStudentID;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
