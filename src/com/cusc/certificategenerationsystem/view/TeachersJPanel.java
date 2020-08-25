package com.cusc.certificategenerationsystem.view;

import com.cusc.certificategenerationsystem.db.DatabaseConnection;
import com.cusc.certificategenerationsystem.db.DatabaseProcess;
import com.cusc.certificategenerationsystem.report.Report;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PC_HP
 */
public class TeachersJPanel extends javax.swing.JPanel {
    int func=0;
    /**
     * Creates new form TeachersJPanel
     */
    public TeachersJPanel() {
        initComponents();
        pnlCancel.setVisible(false);
        pnlModify.setVisible(false);
        grantAccess();
        lbNotification.setVisible(false);
        long millis = System.currentTimeMillis();  
        java.sql.Date currentDate = new java.sql.Date(millis);
        dcDOB.setDate(currentDate);
        DatabaseConnection.connectSQL();
        DatabaseProcess.tableModel = new DefaultTableModel();
        DatabaseProcess.loadData("SELECT * FROM Teachers", tblTeachers);
        txtTeacherID.setText(DatabaseProcess.getID("execute sp_Teacher_identityID"));
    }
    
    private void grantAccess(){
        if("Certificate Cell".equals(DatabaseProcess.checkRole())){
            disableForm();
            pnlManage.setVisible(false);
        } 
    }
    private void disableForm(){
        txtName.setEditable(false);
        txtTechnique.setEditable(false);
        txtAcademicLevel.setEditable(false);
        txtPhone.setEditable(false);
        txaAddress.setEditable(false);
        dcDOB.setEnabled(false);
        radMale.setEnabled(false);
        radFemale.setEnabled(false);
    }
    private void enableForm(){
        txtName.setEditable(true);
        txtTechnique.setEditable(true);
        txtAcademicLevel.setEditable(true);
        txtPhone.setEditable(true);
        txaAddress.setEditable(true);
        dcDOB.setEnabled(true);
        radMale.setEnabled(true);
        radFemale.setEnabled(true);
    }
    private void updateTeacher(){
        String teacherID = txtTeacherID.getText();
        String name = txtName.getText();
        Date dob = dcDOB.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(dob);
        Calendar calendarDOB = Calendar.getInstance();
        calendarDOB.setTime(dob);
        String gender = null;
        if(radMale.isSelected()){
            gender = "Male";
        } else {
            gender = "Female";
        }
        String academicLevel = txtAcademicLevel.getText();
        String address = txaAddress.getText();
        String technique = txtTechnique.getText();
        String phone = txtPhone.getText();
        int row;
        if(name.matches("^[a-zA-Z_0-9\\s]{5,40}")==false){
            JOptionPane.showMessageDialog(null, "Name cannot empty. Name does not contain special characters!", "Message", 1);
            txtName.requestFocus();
        }else if(getAge(calendarDOB)<18){
            JOptionPane.showMessageDialog(null, "Teacher must be over 18 years old to register!", "Message", 1);
            dcDOB.requestFocus();
        }else if(academicLevel.matches("^.{5,100}")== false){
            JOptionPane.showMessageDialog(null, "Academic level must be between 5 and 100 characters!", "Message", 1);
            txtAcademicLevel.requestFocus();
        }else if(address.matches(".{5,120}") == false){
            JOptionPane.showMessageDialog(null, "Address must be greater than 5 characters and less than 120 characters!", "Meaasge", 1);
            txaAddress.requestFocus();
        }else if(technique.matches(".{5,100}")==false){
            JOptionPane.showMessageDialog(null, "Technique must be between 5 and 100 characters!", "Meaasge", 1);
            txtTechnique.requestFocus();
        }else if(phone.matches("^[0-9]{8,15}")== false){
            JOptionPane.showMessageDialog(null, "Invalid phone number, please try again!", "Message", 1);
            txtPhone.requestFocus();
        }else{
            switch(func){
                case 1:
                    String sql = "INSERT INTO Teachers VALUES('"+teacherID+"','"+name+"','"+date+"','"+gender+"','"
                    +address+"','"+phone+"','"+academicLevel+"','"+technique+"')";
                    row = DatabaseProcess.updateData(sql);;
                    if(row != 0){
                        notifySuccessful();
                    } 
                    break;
                case 2:
                    String sql2 = "UPDATE Teachers SET "+
                            "Name='"+name+"',"+
                            "DOB='"+date+"',"+
                            "Gender='"+gender+"',"+
                            "Address='"+address+"',"+
                            "Phone='"+phone+"',"+
                            "Technique='"+technique+"',"+
                            "AcademicLevel='"+academicLevel+"' "+
                            "WHERE TeacherID='"+teacherID+"'";
                    row = DatabaseProcess.updateData(sql2);
                    if(row != 0){
                        notifySuccessful();
                    }
                    break;
                case 3:
                    String sql3 ="DELETE FROM Teachers WHERE TeacherID='"+teacherID+"'";
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
        DatabaseProcess.tableModel = new DefaultTableModel();
        DatabaseProcess.tableModel.setRowCount(0);
        DatabaseProcess.loadData("SELECT * FROM Teachers", tblTeachers);
        resetForm();
        enableForm();
    }
    private void resetForm(){
        txtName.setText(null);
        txtPhone.setText(null);
        radMale.setSelected(true);
        long millis = System.currentTimeMillis();  
        java.sql.Date date = new java.sql.Date(millis);  
        dcDOB.setDate(date);
        txtAcademicLevel.setText(null);
        txtTechnique.setText(null);
        txaAddress.setText(null);
        txtTeacherID.setText(DatabaseProcess.getID("execute sp_Teacher_identityID"));
        enableForm();
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
        pnlTitle = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        pnlSearch = new javax.swing.JPanel();
        lbSearch = new javax.swing.JLabel();
        lbNotification = new javax.swing.JLabel();
        pnlDetails = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblTeachers = new javax.swing.JTable();
        pnlInfo = new javax.swing.JPanel();
        txtAcademicLevel = new javax.swing.JTextField();
        radFemale = new javax.swing.JRadioButton();
        txtName = new javax.swing.JTextField();
        lbName = new javax.swing.JLabel();
        lbDOB = new javax.swing.JLabel();
        lbPhone = new javax.swing.JLabel();
        lbTechnique = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        lbAcademicLevel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaAddress = new javax.swing.JTextArea();
        txtTechnique = new javax.swing.JTextField();
        dcDOB = new com.toedter.calendar.JDateChooser();
        lbGender = new javax.swing.JLabel();
        lbAddress = new javax.swing.JLabel();
        radMale = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        txtTeacherID = new javax.swing.JTextField();
        pnlManage = new javax.swing.JPanel();
        pnlAdd = new javax.swing.JPanel();
        lbAdd = new javax.swing.JLabel();
        pnlModify = new javax.swing.JPanel();
        lbModify = new javax.swing.JLabel();
        pnlDelete = new javax.swing.JPanel();
        lbDelete = new javax.swing.JLabel();
        pnlReset = new javax.swing.JPanel();
        lbReset = new javax.swing.JLabel();
        pnlCancel = new javax.swing.JPanel();
        lbCancel = new javax.swing.JLabel();
        pnlEdit = new javax.swing.JPanel();
        lbEdit = new javax.swing.JLabel();
        pnlReport = new javax.swing.JPanel();
        lbReport = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        pnlTitle.setBackground(new java.awt.Color(71, 120, 197));
        pnlTitle.setPreferredSize(new java.awt.Dimension(100, 72));

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        pnlDetails.setBackground(new java.awt.Color(255, 255, 255));
        pnlDetails.setForeground(new java.awt.Color(204, 204, 204));
        pnlDetails.setLayout(new java.awt.BorderLayout());

        tblTeachers.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        tblTeachers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblTeachers.setRowHeight(20);
        tblTeachers.setRowMargin(5);
        tblTeachers.setSelectionBackground(new java.awt.Color(71, 120, 197));
        tblTeachers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTeachersMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblTeachers);

        pnlDetails.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        pnlInfo.setBackground(new java.awt.Color(255, 255, 255));
        pnlInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Teacher Information", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 15))); // NOI18N

        txtAcademicLevel.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        radFemale.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(radFemale);
        radFemale.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        radFemale.setText("Female");

        txtName.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNameKeyPressed(evt);
            }
        });

        lbName.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbName.setText("Full Name:");

        lbDOB.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbDOB.setText("Date of Birth:");

        lbPhone.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbPhone.setText("Phone:");

        lbTechnique.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbTechnique.setText("Technique:");

        txtPhone.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        lbAcademicLevel.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbAcademicLevel.setText("Academic Level:");

        txaAddress.setColumns(20);
        txaAddress.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txaAddress.setLineWrap(true);
        txaAddress.setRows(5);
        txaAddress.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txaAddressKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(txaAddress);

        txtTechnique.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        dcDOB.setDateFormatString("yyyy-MM-dd");
        dcDOB.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        lbGender.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbGender.setText("Gender:");

        lbAddress.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbAddress.setText("Address:");

        radMale.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(radMale);
        radMale.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        radMale.setSelected(true);
        radMale.setText("Male");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel1.setText("TeacherID:");

        txtTeacherID.setEditable(false);
        txtTeacherID.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtTeacherID.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout pnlInfoLayout = new javax.swing.GroupLayout(pnlInfo);
        pnlInfo.setLayout(pnlInfoLayout);
        pnlInfoLayout.setHorizontalGroup(
            pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfoLayout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(lbName)
                    .addComponent(lbDOB)
                    .addComponent(lbGender)
                    .addComponent(lbAcademicLevel))
                .addGap(40, 40, 40)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTeacherID)
                    .addComponent(txtName)
                    .addComponent(dcDOB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtAcademicLevel)
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addComponent(radMale, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(43, 43, 43)
                        .addComponent(radFemale, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(139, 139, 139)))
                .addGap(70, 70, 70)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbAddress, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbTechnique, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbPhone, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(40, 40, 40)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                    .addComponent(txtTechnique, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                    .addComponent(txtPhone, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE))
                .addGap(60, 60, 60))
        );
        pnlInfoLayout.setVerticalGroup(
            pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbAddress))
                        .addGap(20, 20, 20)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtTechnique, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbTechnique))
                        .addGap(20, 20, 20)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbPhone)
                            .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(txtTeacherID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbName)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(dcDOB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbDOB))
                        .addGap(20, 20, 20)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbGender)
                            .addComponent(radMale)
                            .addComponent(radFemale))
                        .addGap(20, 20, 20)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtAcademicLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbAcademicLevel))))
                .addGap(30, 30, 30))
        );

        pnlInfoLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {dcDOB, txtName});

        pnlInfoLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtAcademicLevel, txtTeacherID});

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
            .addComponent(pnlReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlManageLayout.setVerticalGroup(
            pnlManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlManageLayout.createSequentialGroup()
                .addComponent(pnlAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(pnlDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(pnlReset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addComponent(pnlTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 1285, Short.MAX_VALUE)
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
                        .addComponent(pnlDetails, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
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
        func=1;
        updateTeacher();
        
    }//GEN-LAST:event_pnlAddMousePressed

    private void txtNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameKeyPressed
        lbNotification.setVisible(false);
    }//GEN-LAST:event_txtNameKeyPressed

    private void txaAddressKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txaAddressKeyPressed
        lbNotification.setVisible(false);
    }//GEN-LAST:event_txaAddressKeyPressed

    private void tblTeachersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTeachersMouseClicked
        try {
            int row = tblTeachers.getSelectedRow();
            String idRow = (tblTeachers.getModel().getValueAt(row, 0).toString());
            String sql = "SELECT * FROM Teachers WHERE TeacherID='"+idRow+"'";
            ResultSet rs = DatabaseProcess.displayInformation(sql);
            if(rs.next()){
                txtTeacherID.setText(rs.getString("TeacherID"));
                txtName.setText(rs.getString("Name"));
                dcDOB.setDate(rs.getDate("DOB"));
                txtAcademicLevel.setText(rs.getString("AcademicLevel"));
                txaAddress.setText(rs.getString("Address"));
                txtPhone.setText(rs.getString("Phone"));
                txtTechnique.setText(rs.getString("Technique"));
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
        pnlCancel.setVisible(false);
        pnlModify.setVisible(false);
        disableForm();
    }//GEN-LAST:event_tblTeachersMouseClicked

    private void pnlResetMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlResetMousePressed
        resetForm();
    }//GEN-LAST:event_pnlResetMousePressed

    private void pnlModifyMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlModifyMousePressed
        func=2;
        updateTeacher();
    }//GEN-LAST:event_pnlModifyMousePressed

    private void pnlDeleteMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDeleteMousePressed
        int click = JOptionPane.showConfirmDialog(null, "You sure you want to delete! Check the information you"
                + " want to delete to make sure it doesn't affect the system!","Warning!",JOptionPane.YES_NO_OPTION);
        if(click == JOptionPane.YES_OPTION){
            func = 3;
            updateTeacher();
        }
    }//GEN-LAST:event_pnlDeleteMousePressed

    private void txtSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchMouseClicked
        txtSearch.setText(null);
        txtSearch.setForeground(Color.BLACK);
    }//GEN-LAST:event_txtSearchMouseClicked

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        DatabaseProcess.tableModel = new DefaultTableModel();
        DatabaseProcess.tableModel.setRowCount(0);
        String search = txtSearch.getText();
        DatabaseProcess.loadData("SELECT * FROM Teachers WHERE TeacherID like '%"+search+"%'"
                + "OR Name like '%"+search+"%'", tblTeachers);
    }//GEN-LAST:event_txtSearchKeyReleased

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

    private void pnlReportMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlReportMouseEntered
        MainJFrame.setColorButton(pnlReport);
    }//GEN-LAST:event_pnlReportMouseEntered

    private void pnlReportMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlReportMouseExited
        MainJFrame.resetColorButton(pnlReport);
    }//GEN-LAST:event_pnlReportMouseExited

    private void pnlReportMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlReportMousePressed
        Report.ExportExcel(tblTeachers);
    }//GEN-LAST:event_pnlReportMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private com.toedter.calendar.JDateChooser dcDOB;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbAcademicLevel;
    private javax.swing.JLabel lbAdd;
    private javax.swing.JLabel lbAddress;
    private javax.swing.JLabel lbCancel;
    private javax.swing.JLabel lbDOB;
    private javax.swing.JLabel lbDelete;
    private javax.swing.JLabel lbEdit;
    private javax.swing.JLabel lbGender;
    private javax.swing.JLabel lbModify;
    private javax.swing.JLabel lbName;
    private javax.swing.JLabel lbNotification;
    private javax.swing.JLabel lbPhone;
    private javax.swing.JLabel lbReport;
    private javax.swing.JLabel lbReset;
    private javax.swing.JLabel lbSearch;
    private javax.swing.JLabel lbTechnique;
    private javax.swing.JPanel pnlAdd;
    private javax.swing.JPanel pnlCancel;
    private javax.swing.JPanel pnlDelete;
    private javax.swing.JPanel pnlDetails;
    private javax.swing.JPanel pnlEdit;
    private javax.swing.JPanel pnlInfo;
    private javax.swing.JPanel pnlManage;
    private javax.swing.JPanel pnlModify;
    private javax.swing.JPanel pnlReport;
    private javax.swing.JPanel pnlReset;
    private javax.swing.JPanel pnlSearch;
    private javax.swing.JPanel pnlTitle;
    private javax.swing.JRadioButton radFemale;
    private javax.swing.JRadioButton radMale;
    private javax.swing.JTable tblTeachers;
    private javax.swing.JTextArea txaAddress;
    private javax.swing.JTextField txtAcademicLevel;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTeacherID;
    private javax.swing.JTextField txtTechnique;
    // End of variables declaration//GEN-END:variables
}
