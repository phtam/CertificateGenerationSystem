package com.cusc.certificategenerationsystem.view;

import com.cusc.certificategenerationsystem.db.DatabaseConnection;
import com.cusc.certificategenerationsystem.db.DatabaseProcess;
import com.cusc.certificategenerationsystem.report.Report;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PC_HP
 */
public class ClassJPanel extends javax.swing.JPanel {
    int func=0;
    /**
     * Creates new form ClassJPanel
     */
    public ClassJPanel()  {
        initComponents();
        loadCourseInfo();
        HomeJPanel.setColorCombobox(new JComboBox[]{cbbCourseID});
        grantAccess();
        pnlModify.setVisible(false);
        pnlCancel.setVisible(false);
        lbNotification.setVisible(false);
        DatabaseConnection.connectSQL();
        long millis = System.currentTimeMillis();
        java.sql.Date currentDate = new java.sql.Date(millis);
        dcStartDate.setDate(currentDate);
        dcEndDate.setDate(currentDate);
        DatabaseProcess.tableModel = new DefaultTableModel();
        DatabaseProcess.loadData("SELECT * FROM Class", tblClass);
        txtClassID.setText(DatabaseProcess.getID("execute sp_Class_identityID"));
    }
    private void grantAccess(){
        if("Certificate Cell".equals(DatabaseProcess.checkRole())){
           disableForm();
           pnlManage.setVisible(false);
        } 
    }
    private void disableForm(){
        txtClassName.setEditable(false);
        dcEndDate.setEnabled(false);
        dcStartDate.setEnabled(false);
        cbbCourseID.setEnabled(false);
    }
    private void enableForm(){
        txtClassName.setEditable(true);
        dcEndDate.setEnabled(true);
        dcStartDate.setEnabled(true);
        cbbCourseID.setEnabled(true);
    }
    private void updateClass(){
        String classID = txtClassID.getText();
        String className = txtClassName.getText();
        Date sDate = dcStartDate.getDate();
        Date eDate = dcEndDate.getDate();
        String courseID = cbbCourseID.getSelectedItem().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = sdf.format(sDate);
        String endDate = sdf.format(eDate);
        
        if(className.matches(".{2,15}") == false){
            JOptionPane.showMessageDialog(null, "Class Name must be greater than 2 characters and less than 15 characters!!", "Message", 1);
            txtClassName.requestFocus();
        }else if(getTime(startDate, endDate)<0){
            JOptionPane.showMessageDialog(null, "Start date must be before end date!", "Message", 1);
        }else{
            switch(func){
                case 1:
                    String sql1 = "INSERT INTO Class VALUES('"+classID+"','"+className+"','"+startDate+"','"+endDate+"','"+courseID+"')";
                    int row = DatabaseProcess.updateData(sql1);
                    if(row != 0){
                        notifySuccessful();
                    }
                    break;
                case 2:
                    String sql2 = "UPDATE Class SET "+
                            "ClassName='"+className+"',"+
                            "StartDate='"+startDate+"',"+
                            "EndDate='"+endDate+"', "+
                            "CourseID='"+courseID+"' WHERE ClassID='"+classID+"'";
                    row = DatabaseProcess.updateData(sql2);
                    if(row != 0){
                        notifySuccessful();
                    }
                    break;
                case 3: 
                    String sql3 = "DELETE FROM Class WHERE ClassID='"+classID+"'";
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
        DatabaseProcess.loadData("SELECT * FROM Class", tblClass);
        pnlModify.setVisible(false);
        pnlCancel.setVisible(false);
        resetForm();
        enableForm();
    }
    private void resetForm(){
        txtClassName.setText(null);
        long millis = System.currentTimeMillis();  
        java.sql.Date date = new java.sql.Date(millis);  
        dcStartDate.setDate(date);
        dcEndDate.setDate(date);
        txtClassID.setText(DatabaseProcess.getID("execute sp_Class_identityID"));
        enableForm();
    }
    private void loadCourseInfo(){
        cbbCourseID.removeAllItems();
        DatabaseProcess.loadDataCombobox("SELECT * FROM Course", this.cbbCourseID, "CourseID");
        String query = "SELECT * FROM Course WHERE CourseID = ?";
        DatabaseProcess.loadDataTextfield("SELECT * FROM Course WHERE CourseID = ?", this.cbbCourseID, this.txtCourseName, "CourseName");
    }
    private long getTime(String sDate,String eDate) {
        long getDaysDiff = 0;
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        Date date1 = null;
        Date date2 = null;
        try {
        String startDate = sDate;
        String endDate = eDate;
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

        pnlDetails = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClass = new javax.swing.JTable();
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
        pnlRp = new javax.swing.JPanel();
        lbReport1 = new javax.swing.JLabel();
        pnlInfo = new javax.swing.JPanel();
        lbClassID = new javax.swing.JLabel();
        txtClassID = new javax.swing.JTextField();
        txtClassName = new javax.swing.JTextField();
        lbClassName = new javax.swing.JLabel();
        dcStartDate = new com.toedter.calendar.JDateChooser();
        lbStartDate = new javax.swing.JLabel();
        lbEndDate = new javax.swing.JLabel();
        dcEndDate = new com.toedter.calendar.JDateChooser();
        lbCourseName = new javax.swing.JLabel();
        txtCourseName = new javax.swing.JTextField();
        lbCourseID = new javax.swing.JLabel();
        cbbCourseID = new javax.swing.JComboBox();
        pnlTitle = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        pnlSearch = new javax.swing.JPanel();
        lbSearch = new javax.swing.JLabel();
        lbNotification = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        pnlDetails.setBackground(new java.awt.Color(255, 255, 255));
        pnlDetails.setForeground(new java.awt.Color(204, 204, 204));
        pnlDetails.setLayout(new java.awt.BorderLayout());

        tblClass.setFont(new java.awt.Font("Times New Roman", 0, 15)); // NOI18N
        tblClass.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblClass.setRowHeight(20);
        tblClass.setRowMargin(5);
        tblClass.setSelectionBackground(new java.awt.Color(71, 120, 197));
        tblClass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClassMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClass);

        pnlDetails.add(jScrollPane1, java.awt.BorderLayout.CENTER);

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

        pnlRp.setBackground(new java.awt.Color(120, 168, 252));
        pnlRp.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlRp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlRpMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlRpMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlRpMousePressed(evt);
            }
        });

        lbReport1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbReport1.setForeground(new java.awt.Color(255, 255, 255));
        lbReport1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cusc/certificategenerationsystem/images/icon_report.png"))); // NOI18N
        lbReport1.setText("Report");

        javax.swing.GroupLayout pnlRpLayout = new javax.swing.GroupLayout(pnlRp);
        pnlRp.setLayout(pnlRpLayout);
        pnlRpLayout.setHorizontalGroup(
            pnlRpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRpLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lbReport1)
                .addGap(8, 8, 8))
        );
        pnlRpLayout.setVerticalGroup(
            pnlRpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRpLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(lbReport1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addComponent(pnlRp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(pnlRp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(pnlEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(pnlModify, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(pnlCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        pnlInfo.setBackground(new java.awt.Color(255, 255, 255));
        pnlInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Class Information", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 15))); // NOI18N

        lbClassID.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbClassID.setText("ClassID:");

        txtClassID.setEditable(false);
        txtClassID.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtClassID.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        txtClassName.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtClassName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtClassNameKeyPressed(evt);
            }
        });

        lbClassName.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbClassName.setText("Class Name:");

        dcStartDate.setDateFormatString("yyyy-MM-dd");
        dcStartDate.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        lbStartDate.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbStartDate.setText("Start Date:");

        lbEndDate.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbEndDate.setText("End Date:");

        dcEndDate.setDateFormatString("yyyy-MM-dd");
        dcEndDate.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        lbCourseName.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbCourseName.setText("Course Name:");

        txtCourseName.setEditable(false);
        txtCourseName.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        lbCourseID.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbCourseID.setText("CourseID:");

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

        javax.swing.GroupLayout pnlInfoLayout = new javax.swing.GroupLayout(pnlInfo);
        pnlInfo.setLayout(pnlInfoLayout);
        pnlInfoLayout.setHorizontalGroup(
            pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbStartDate, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbClassID, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbCourseID, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(40, 40, 40)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dcStartDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtClassID, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                    .addComponent(cbbCourseID, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(70, 70, 70)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbEndDate, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbClassName, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbCourseName, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(40, 40, 40)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtClassName, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                    .addComponent(dcEndDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtCourseName))
                .addGap(60, 60, 60))
        );
        pnlInfoLayout.setVerticalGroup(
            pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lbClassName, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtClassName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lbClassID, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtClassID, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dcEndDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbEndDate, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(20, 20, 20)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbCourseName, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtCourseName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbStartDate, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dcStartDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbbCourseID, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbCourseID, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(20, 20, 20))
        );

        pnlInfoLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cbbCourseID, dcStartDate, txtClassID});

        pnlInfoLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtClassName, txtCourseName});

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

        lbNotification.setBackground(new java.awt.Color(255, 255, 255));
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
                .addComponent(lbNotification, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addGroup(pnlTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbNotification)
                    .addGroup(pnlTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                        .addComponent(pnlSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1220, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void txtClassNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClassNameKeyPressed
        lbNotification.setVisible(false);
    }//GEN-LAST:event_txtClassNameKeyPressed

    private void pnlAddMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlAddMousePressed
        try{
            func=1;
            updateClass();
        }catch(NullPointerException e){
            JOptionPane.showMessageDialog(null, "Please complete all information.You must enter course and class information!", "Message", 1);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Registry failed. Please check again!", "Message", 1);
        }
    }//GEN-LAST:event_pnlAddMousePressed

    private void tblClassMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClassMouseClicked
        try {
            int row = tblClass.getSelectedRow();
            String idRow = (tblClass.getModel().getValueAt(row, 0).toString());
            String sql = "SELECT * FROM Class WHERE ClassID='"+idRow+"'";
            ResultSet rs = DatabaseProcess.displayInformation(sql);
            if(rs.next()){
                txtClassID.setText(rs.getString("ClassID"));
                txtClassName.setText(rs.getString("ClassName"));
                dcStartDate.setDate(rs.getDate("StartDate"));
                dcEndDate.setDate(rs.getDate("EndDate"));
                cbbCourseID.setSelectedItem(rs.getString("CourseID"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "INFORMATION DISPLAY FAILED", "Message", 1);
        }
        DatabaseProcess.loadDataTextfield("SELECT * FROM Course WHERE CourseID = ?", this.cbbCourseID, this.txtCourseName, "CourseName");
        lbNotification.setVisible(false);
        disableForm();
        pnlModify.setVisible(false);
        pnlCancel.setVisible(false);
    }//GEN-LAST:event_tblClassMouseClicked

    private void pnlResetMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlResetMousePressed
        resetForm();
        enableForm();
    }//GEN-LAST:event_pnlResetMousePressed

    private void pnlModifyMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlModifyMousePressed
        try{
            func=2;
            updateClass();
        }catch(NullPointerException e){
            JOptionPane.showMessageDialog(null, "Please complete all information.You must enter course and class information!", "Message", 1);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Registry failed. Please check again!", "Message", 1);
        }
        pnlCancel.setVisible(false);
        pnlModify.setVisible(false);
    }//GEN-LAST:event_pnlModifyMousePressed

    private void pnlDeleteMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDeleteMousePressed
        int click = JOptionPane.showConfirmDialog(null, "You sure you want to delete! Check the information you"
            + " want to delete to make sure it doesn't affect the system!","Warning!",JOptionPane.YES_NO_OPTION);
        if(click == JOptionPane.YES_OPTION){
            func = 3;
            updateClass();
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
        DatabaseProcess.loadData("SELECT * FROM Class WHERE ClassID like '%"+search+"%' OR ClassName like '%"+search+"%'", tblClass);
    }//GEN-LAST:event_txtSearchKeyReleased

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

    private void pnlRpMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlRpMouseEntered
        MainJFrame.setColorButton(pnlRp);
    }//GEN-LAST:event_pnlRpMouseEntered

    private void pnlRpMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlRpMouseExited
        MainJFrame.resetColorButton(pnlRp);
    }//GEN-LAST:event_pnlRpMouseExited

    private void pnlRpMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlRpMousePressed
        Report.ExportExcel(tblClass);
    }//GEN-LAST:event_pnlRpMousePressed

    private void cbbCourseIDPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbbCourseIDPopupMenuWillBecomeInvisible
        DatabaseProcess.loadDataTextfield("SELECT * FROM Course WHERE CourseID = ?", this.cbbCourseID, this.txtCourseName, "CourseName");
    }//GEN-LAST:event_cbbCourseIDPopupMenuWillBecomeInvisible


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbbCourseID;
    private com.toedter.calendar.JDateChooser dcEndDate;
    private com.toedter.calendar.JDateChooser dcStartDate;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbAdd;
    private javax.swing.JLabel lbCancel;
    private javax.swing.JLabel lbClassID;
    private javax.swing.JLabel lbClassName;
    private javax.swing.JLabel lbCourseID;
    private javax.swing.JLabel lbCourseName;
    private javax.swing.JLabel lbDelete;
    private javax.swing.JLabel lbEdit;
    private javax.swing.JLabel lbEndDate;
    private javax.swing.JLabel lbModify;
    private javax.swing.JLabel lbNotification;
    private javax.swing.JLabel lbReport1;
    private javax.swing.JLabel lbReset;
    private javax.swing.JLabel lbSearch;
    private javax.swing.JLabel lbStartDate;
    private javax.swing.JPanel pnlAdd;
    private javax.swing.JPanel pnlCancel;
    private javax.swing.JPanel pnlDelete;
    private javax.swing.JPanel pnlDetails;
    private javax.swing.JPanel pnlEdit;
    private javax.swing.JPanel pnlInfo;
    private javax.swing.JPanel pnlManage;
    private javax.swing.JPanel pnlModify;
    private javax.swing.JPanel pnlReset;
    private javax.swing.JPanel pnlRp;
    private javax.swing.JPanel pnlSearch;
    private javax.swing.JPanel pnlTitle;
    private javax.swing.JTable tblClass;
    private javax.swing.JTextField txtClassID;
    private javax.swing.JTextField txtClassName;
    private javax.swing.JTextField txtCourseName;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
