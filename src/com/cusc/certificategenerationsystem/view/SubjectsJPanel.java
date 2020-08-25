package com.cusc.certificategenerationsystem.view;

import com.cusc.certificategenerationsystem.db.DatabaseConnection;
import com.cusc.certificategenerationsystem.db.DatabaseProcess;
import com.cusc.certificategenerationsystem.report.Report;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PC_HP
 */
public class SubjectsJPanel extends javax.swing.JPanel {
    int func=0;
    /**
     * Creates new form SubjectsJPanel
     */
    public SubjectsJPanel() {
        initComponents();
        HomeJPanel.setColorCombobox(new JComboBox[]{cbbCourseID});
        grantAccess();
        pnlModify.setVisible(false);
        pnlCancel.setVisible(false);
        lbNotification.setVisible(false);
        DatabaseConnection.connectSQL();
        DatabaseProcess.tableModel = new DefaultTableModel();
        DatabaseProcess.loadData("SELECT [SubjectID],[SubjectName],[Fee],[NumOfLesson],S.[CourseID],[CourseName] "
                + "FROM Subjects S JOIN Course C ON S.CourseID=C.CourseID ORDER BY [SubjectID]", tblSubject);
        txtSubjectID.setText(DatabaseProcess.getID("execute sp_Subject_identityID"));
        loadInfo();
    }
    private void grantAccess(){
        if("Certificate Cell".equals(DatabaseProcess.checkRole())){
            disableForm();
            pnlManage.setVisible(false);
        } 
    }
    private void disableForm(){
        cbbCourseID.setEnabled(false);
        txtSubjectName.setEditable(false);
        txtFee.setEditable(false);
        txtNumOfLesson.setEditable(false);
    }
    private void enableForm(){
        cbbCourseID.setEnabled(true);
        txtSubjectName.setEditable(true);
        txtFee.setEditable(true);
        txtNumOfLesson.setEditable(true);
    }
    private void updateSubject(){
        String courseID = cbbCourseID.getSelectedItem().toString();
        String subjectID = txtSubjectID.getText();
        String subjectName = txtSubjectName.getText();
        int  numOfLesson = Integer.parseInt(txtNumOfLesson.getText()) ;
        int fee = Integer.parseInt(txtFee.getText());
        int row;
        if(courseID.length()==0){
            JOptionPane.showMessageDialog(null, "You must enter COURSE information first!", "Message", 1);
        }else if(subjectName.matches(".{2,50}")==false){
            JOptionPane.showMessageDialog(null, "Subject name must be between 2 and 50 characters!", "Message", 1);
        }else if(numOfLesson<0){
            JOptionPane.showMessageDialog(null, "Number of lesson must be an integer and greater than 0", "Message", 1);
        }else if(fee<0){
            JOptionPane.showMessageDialog(null, "Fee must be an integer and greater than 0 ", "Message", 1);
        }else{
            switch(func){
                case 1:
                    String sql1 = "INSERT INTO Subjects VALUES ('"+subjectID+"','"+courseID+"','"
                    +subjectName+"',"+fee+","+numOfLesson+")";
                    row = DatabaseProcess.updateData(sql1);
                    if(row != 0){
                        notifySuccessful();
                    }
                    break;
                case 2:
                    String sql2 = "UPDATE Subjects SET "+
                            "CourseID='"+courseID+"', "+
                            "SubjectName='"+subjectName+"', "+
                            "Fee="+fee+", "+
                            "NumOfLesson="+numOfLesson+" WHERE SubjectID='"+subjectID+"'";
                    row = DatabaseProcess.updateData(sql2);
                    if(row!=0){
                        notifySuccessful();
                    }
                    break;
                case 3:
                    String sql3= "DELETE FROM Subjects WHERE SubjectID='"+subjectID+"'";
                    row =DatabaseProcess.updateData(sql3);
                    if(row!=0){
                        notifySuccessful();
                    }
                    break;
            } 
        }
    }
    private void notifySuccessful(){
        lbNotification.setVisible(true);
        DatabaseProcess.tableModel= (DefaultTableModel) tblSubject.getModel();
        DatabaseProcess.tableModel.setRowCount(0);
        DatabaseProcess.loadData("SELECT [SubjectID],[SubjectName],[Fee],[NumOfLesson],S.[CourseID],[CourseName] "
        + "FROM Subjects S JOIN Course C ON S.CourseID=C.CourseID ORDER BY [SubjectID]", tblSubject);
        resetForm();
        enableForm();
    }
    private void loadInfo(){
        cbbCourseID.removeAllItems();
        DatabaseProcess.loadDataCombobox("SELECT * FROM Course", this.cbbCourseID, "CourseID");
        String query = "SELECT * FROM Course WHERE CourseID = ?";
        DatabaseProcess.loadDataTextfield(query, this.cbbCourseID, this.txtCourseName, "CourseName");
    }
    private void resetForm(){
        cbbCourseID.setSelectedIndex(0);
        txtSubjectName.setText(null);
        txtFee.setText(null);
        txtNumOfLesson.setText(null);
        txtSubjectID.setText(DatabaseProcess.getID("execute sp_Subject_identityID"));
        enableForm();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlTitle = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        pnlSearch = new javax.swing.JPanel();
        lbSearch = new javax.swing.JLabel();
        lbNotification = new javax.swing.JLabel();
        pnlDetails = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSubject = new javax.swing.JTable();
        pnlInfo = new javax.swing.JPanel();
        lbFee = new javax.swing.JLabel();
        txtFee = new javax.swing.JTextField();
        lbSubjectID = new javax.swing.JLabel();
        txtCourseName = new javax.swing.JTextField();
        lbNumOfLesson = new javax.swing.JLabel();
        txtNumOfLesson = new javax.swing.JTextField();
        lbCourseID = new javax.swing.JLabel();
        txtSubjectID = new javax.swing.JTextField();
        lbCourseName = new javax.swing.JLabel();
        cbbCourseID = new javax.swing.JComboBox();
        lbSubjectName = new javax.swing.JLabel();
        txtSubjectName = new javax.swing.JTextField();
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
        pnlSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlSearchMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlSearchMouseExited(evt);
            }
        });

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

        tblSubject.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        tblSubject.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblSubject.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSubjectMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSubject);

        pnlDetails.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pnlInfo.setBackground(new java.awt.Color(255, 255, 255));
        pnlInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Subject Information", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 15))); // NOI18N

        lbFee.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbFee.setText("Fee:");

        txtFee.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtFee.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFeeKeyPressed(evt);
            }
        });

        lbSubjectID.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbSubjectID.setText("SubjectID:");

        txtCourseName.setEditable(false);
        txtCourseName.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtCourseName.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        lbNumOfLesson.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbNumOfLesson.setText("Number of lesson:");

        txtNumOfLesson.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        lbCourseID.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbCourseID.setText("CourseID:");

        txtSubjectID.setEditable(false);
        txtSubjectID.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtSubjectID.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        lbCourseName.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbCourseName.setText("Course Name:");

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

        lbSubjectName.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbSubjectName.setText("Subject Name:");

        txtSubjectName.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtSubjectName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSubjectNameKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout pnlInfoLayout = new javax.swing.GroupLayout(pnlInfo);
        pnlInfo.setLayout(pnlInfoLayout);
        pnlInfoLayout.setHorizontalGroup(
            pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfoLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbSubjectID)
                    .addComponent(lbCourseID)
                    .addComponent(lbSubjectName))
                .addGap(40, 40, 40)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSubjectID, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                    .addComponent(cbbCourseID, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtSubjectName, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE))
                .addGap(70, 70, 70)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbFee, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbCourseName, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbNumOfLesson, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(46, 46, 46)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCourseName, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                    .addComponent(txtFee)
                    .addComponent(txtNumOfLesson))
                .addGap(60, 60, 60))
        );
        pnlInfoLayout.setVerticalGroup(
            pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbCourseName, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtCourseName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbFee, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtFee, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbNumOfLesson, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNumOfLesson, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlInfoLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(lbCourseID))
                            .addComponent(cbbCourseID, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbSubjectID, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtSubjectID, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbSubjectName, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtSubjectName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(22, 22, 22))
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
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 1282, Short.MAX_VALUE)
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
                        .addComponent(pnlDetails, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlManage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void pnlSearchMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlSearchMouseEntered
        pnlSearch.setBackground(new Color(120,168,252));
    }//GEN-LAST:event_pnlSearchMouseEntered

    private void pnlSearchMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlSearchMouseExited
        pnlSearch.setBackground(new Color(71,120,197));
    }//GEN-LAST:event_pnlSearchMouseExited

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

    private void cbbCourseIDPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbbCourseIDPopupMenuWillBecomeInvisible
        String query = "SELECT * FROM Course WHERE CourseID = ?";
        DatabaseProcess.loadDataTextfield(query, this.cbbCourseID, this.txtCourseName, "CourseName");
        lbNotification.setVisible(false);
    }//GEN-LAST:event_cbbCourseIDPopupMenuWillBecomeInvisible

    private void txtSubjectNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSubjectNameKeyPressed
        lbNotification.setVisible(false);
    }//GEN-LAST:event_txtSubjectNameKeyPressed

    private void txtFeeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFeeKeyPressed
        lbNotification.setVisible(false);
    }//GEN-LAST:event_txtFeeKeyPressed

    private void pnlAddMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlAddMousePressed
        try {
           func=1;
           updateSubject();
        } catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, "The data entered is in the wrong format! Please try again!", "Message", 1);
        }catch(NullPointerException e){
            JOptionPane.showMessageDialog(null, "Please complete all information!", "Message", 1);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Update failed. Please check again!", "Message", 1);
        }
    }//GEN-LAST:event_pnlAddMousePressed

    private void tblSubjectMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSubjectMouseClicked
        disableForm();
        pnlModify.setVisible(false);
        pnlCancel.setVisible(false);
        try {
            int row = tblSubject.getSelectedRow();
            String idRow = (tblSubject.getModel().getValueAt(row, 0)).toString();
            String sql = "SELECT * FROM [dbo].[Subjects] WHERE [SubjectID]='"+idRow+"'";
            ResultSet rs = DatabaseProcess.displayInformation(sql);
            if(rs.next()){
                txtSubjectID.setText(rs.getString("SubjectID"));
                txtSubjectName.setText(rs.getString("SubjectName"));
                cbbCourseID.setSelectedItem(rs.getString("CourseID"));
                txtNumOfLesson.setText(rs.getString("NumOfLesson"));
                txtFee.setText(rs.getString("Fee"));
            }
            DatabaseProcess.loadDataTextfield("SELECT * FROM Course WHERE CourseID = ?", this.cbbCourseID, this.txtCourseName, "CourseName");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "INFORMATION DISPLAY FAILED", "Message", 1);
        }
    }//GEN-LAST:event_tblSubjectMouseClicked

    private void pnlResetMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlResetMousePressed
        resetForm();
    }//GEN-LAST:event_pnlResetMousePressed

    private void pnlModifyMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlModifyMousePressed
        try {
           func=2;
           updateSubject();
        } catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, "The data entered is in the wrong format! Please try again!", "Message", 1);
        }catch(NullPointerException e){
            JOptionPane.showMessageDialog(null, "Please complete all information!", "Message", 1);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Update failed. Please check again!", "Message", 1);
        }
        pnlCancel.setVisible(false);
        pnlModify.setVisible(false);
    }//GEN-LAST:event_pnlModifyMousePressed

    private void pnlDeleteMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDeleteMousePressed
        try {
            int click = JOptionPane.showConfirmDialog(null, "You sure you want to delete! Check the information you"
                + " want to delete to make sure it doesn't affect the system!","Warning!",JOptionPane.YES_NO_OPTION);
            if(click == JOptionPane.YES_OPTION){
                func = 3;
                updateSubject();
            }
        } catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, "The data entered is in the wrong format! Please try again!", "Message", 1);
        }catch(NullPointerException e){
            JOptionPane.showMessageDialog(null, "Please complete all information!", "Message", 1);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Update failed. Please check again!", "Message", 1);
        }
    }//GEN-LAST:event_pnlDeleteMousePressed

    private void txtSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchMouseClicked
        txtSearch.setText(null);
        txtSearch.setForeground(Color.BLACK);
    }//GEN-LAST:event_txtSearchMouseClicked

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        DatabaseProcess.tableModel= (DefaultTableModel) tblSubject.getModel();
        DatabaseProcess.tableModel.setRowCount(0);
        String search = txtSearch.getText();
        DatabaseProcess.loadData("SELECT [SubjectID],[SubjectName],[Fee],[NumOfLesson],S.[CourseID],[CourseName] "
                + "FROM Subjects S JOIN Course C ON S.CourseID=C.CourseID WHERE SubjectID like '%"+search+"%' "
                + "OR SubjectName like '%"+search+"%' OR S.CourseID like '%"+search+"%' "
                + "OR CourseName like '%"+search+"%'", tblSubject);
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

    private void pnlReportMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlReportMouseEntered
        MainJFrame.setColorButton(pnlReport);
    }//GEN-LAST:event_pnlReportMouseEntered

    private void pnlReportMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlReportMouseExited
        MainJFrame.resetColorButton(pnlReport);
    }//GEN-LAST:event_pnlReportMouseExited

    private void pnlReportMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlReportMousePressed
        Report.ExportExcel(tblSubject);
    }//GEN-LAST:event_pnlReportMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbbCourseID;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbAdd;
    private javax.swing.JLabel lbCancel;
    private javax.swing.JLabel lbCourseID;
    private javax.swing.JLabel lbCourseName;
    private javax.swing.JLabel lbDelete;
    private javax.swing.JLabel lbEdit;
    private javax.swing.JLabel lbFee;
    private javax.swing.JLabel lbModify;
    private javax.swing.JLabel lbNotification;
    private javax.swing.JLabel lbNumOfLesson;
    private javax.swing.JLabel lbReport;
    private javax.swing.JLabel lbReset;
    private javax.swing.JLabel lbSearch;
    private javax.swing.JLabel lbSubjectID;
    private javax.swing.JLabel lbSubjectName;
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
    private javax.swing.JTable tblSubject;
    private javax.swing.JTextField txtCourseName;
    private javax.swing.JTextField txtFee;
    private javax.swing.JTextField txtNumOfLesson;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSubjectID;
    private javax.swing.JTextField txtSubjectName;
    // End of variables declaration//GEN-END:variables
}
