package com.cusc.certificategenerationsystem.view;

import com.cusc.certificategenerationsystem.db.DatabaseProcess;
import com.cusc.certificategenerationsystem.report.Report;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PC_HP
 */
public class TeachingJPanel extends javax.swing.JPanel {
    int func=0;
    /**
     * Creates new form TeachingJPanel
     */
    public TeachingJPanel() {
        initComponents();
        loadClassInfo();
        loadTeacherInfo();
        loadSubjectInfo();
        lbNotification.setVisible(false);
        DatabaseProcess.tableModel = new DefaultTableModel();
        DatabaseProcess.loadData("SELECT T.ClassID, ClassName, T.TeacherID, Name, T.SubjectID, SubjectName FROM Teaching T\n" +
            "JOIN Class C ON C.ClassID=T.ClassID\n" +
            "JOIN Teachers TCH ON TCH.TeacherID=T.TeacherID\n" +
            "JOIN Subjects S ON S.SubjectID=T.SubjectID", tblTeaching);
    }

    private void loadClassInfo(){
        cbbClassID.removeAllItems();
        DatabaseProcess.loadDataCombobox("SELECT * FROM Class", cbbClassID, "ClassID");
        DatabaseProcess.loadDataTextfield("SELECT * FROM Class WHERE ClassID=?", cbbClassID, txtClassName, "ClassName");
    }
    private void loadTeacherInfo(){
        cbbTeacherID.removeAllItems();
        DatabaseProcess.loadDataCombobox("SELECT * FROM Teachers", cbbTeacherID, "TeacherID");
        DatabaseProcess.loadDataTextfield("SELECT * FROM Teachers WHERE TeacherID=?", cbbTeacherID, txtTeacherName, "Name");
    }
    private void loadSubjectInfo(){
        cbbSubjectID.removeAllItems();
        DatabaseProcess.loadDataCombobox("SELECT * FROM [dbo].[Subjects]", cbbSubjectID, "SubjectID");
        DatabaseProcess.loadDataTextfield("SELECT * FROM Subjects WHERE SubjectID=?", cbbSubjectID, txtSubjectName, "SubjectName");
    }
    private void updateTeaching(){
        String classID = cbbClassID.getSelectedItem().toString();
        String teacherID = cbbTeacherID.getSelectedItem().toString();
        String subjectID = cbbSubjectID.getSelectedItem().toString();
        int row;
        if(classID.length()==0){
            JOptionPane.showMessageDialog(null, "You must enter CLASS information first!", "Message", 1);
        }else if(teacherID.length()==0){
            JOptionPane.showMessageDialog(null, "You must enter TEACHER information first!", "Message", 1);
        }else if(subjectID.length()==0){
            JOptionPane.showMessageDialog(null, "You must enter SUBJECT information first!", "Message", 1);
        }else{
            switch(func){
                case 1:
                    String sql1 = "INSERT INTO Teaching VALUES('"+classID+"','"+teacherID+"','"+subjectID+"')";
                    row = DatabaseProcess.updateData(sql1);
                    if(row != 0){
                        notifySuccessful();
                    }
                    break;
                case 2:
                    String sql2 = "DELETE FROM Teaching WHERE ClassID='"+classID+"' "
                            + "AND TeacherID='"+teacherID+"' AND SubjectID='"+subjectID+"'";
                    row = DatabaseProcess.updateData(sql2);
                    if(row != 0){
                        notifySuccessful();
                    }
                    break;
            }
            
        }
    }
    private void notifySuccessful(){
        lbNotification.setVisible(true);
        DatabaseProcess.tableModel = (DefaultTableModel) tblTeaching.getModel();
        DatabaseProcess.tableModel.setRowCount(0);
        DatabaseProcess.loadData("SELECT T.ClassID, ClassName, T.TeacherID, Name, T.SubjectID, SubjectName FROM Teaching T\n" +
            "JOIN Class C ON C.ClassID=T.ClassID\n" +
            "JOIN Teachers TCH ON TCH.TeacherID=T.TeacherID\n" +
            "JOIN Subjects S ON S.SubjectID=T.SubjectID", tblTeaching);
        resetForm();
    }
    private void resetForm(){
        cbbClassID.setSelectedIndex(0);
        cbbTeacherID.setSelectedIndex(0);
        cbbSubjectID.setSelectedIndex(0);
    }
    private void displayTeaching(){
        try {
            int row = tblTeaching.getSelectedRow();
            String idRow0 = (tblTeaching.getModel().getValueAt(row, 0)).toString();
            String idRow1 = (tblTeaching.getModel().getValueAt(row, 1)).toString();
            String idRow2 = (tblTeaching.getModel().getValueAt(row, 2)).toString();
            String sql = "SELECT * FROM Teaching WHERE ClassID='"+idRow0+"' AND TeacherID='"+idRow1+"' AND SubjectID='"+idRow2+"'";
            ResultSet rs = DatabaseProcess.displayInformation(sql);
            while(rs.next()){
                cbbClassID.setSelectedItem(rs.getString("ClassID"));
                cbbTeacherID.setSelectedItem(rs.getString("TeacherID"));
                cbbSubjectID.setSelectedItem(rs.getString("SubjectID"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "INFORMATION DISPLAY FAILED", "Message", 1);
        }
        DatabaseProcess.loadDataTextfield("SELECT * FROM Class WHERE ClassID=?", cbbClassID, txtClassName, "ClassName");
        DatabaseProcess.loadDataTextfield("SELECT * FROM Teachers WHERE TeacherID=?", cbbTeacherID, txtTeacherName, "Name");
        DatabaseProcess.loadDataTextfield("SELECT * FROM Subjects WHERE SubjectID=?", cbbSubjectID, txtSubjectName, "SubjectName");
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
        tblTeaching = new javax.swing.JTable();
        pnlInfo = new javax.swing.JPanel();
        lbSubjectName = new javax.swing.JLabel();
        cbbClassID = new javax.swing.JComboBox();
        txtSubjectName = new javax.swing.JTextField();
        txtClassName = new javax.swing.JTextField();
        lbClassID = new javax.swing.JLabel();
        lbClassName = new javax.swing.JLabel();
        lbTeacherName = new javax.swing.JLabel();
        txtTeacherName = new javax.swing.JTextField();
        cbbSubjectID = new javax.swing.JComboBox();
        lbSubjectID = new javax.swing.JLabel();
        lbTeacherID = new javax.swing.JLabel();
        cbbTeacherID = new javax.swing.JComboBox();
        pnlManage = new javax.swing.JPanel();
        pnlAdd = new javax.swing.JPanel();
        lbAdd = new javax.swing.JLabel();
        pnlDelete = new javax.swing.JPanel();
        lbDelete = new javax.swing.JLabel();
        pnlReset = new javax.swing.JPanel();
        lbReset = new javax.swing.JLabel();
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

        tblTeaching.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        tblTeaching.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblTeaching.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTeachingMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblTeaching);

        pnlDetails.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pnlInfo.setBackground(new java.awt.Color(255, 255, 255));
        pnlInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Teaching Information", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 15))); // NOI18N

        lbSubjectName.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbSubjectName.setText("Subject Name:");

        cbbClassID.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cbbClassID.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbbClassIDPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        txtSubjectName.setEditable(false);
        txtSubjectName.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtSubjectName.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        txtClassName.setEditable(false);
        txtClassName.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtClassName.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        lbClassID.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbClassID.setText("ClassID:");

        lbClassName.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbClassName.setText("ClassName:");

        lbTeacherName.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbTeacherName.setText("Teacher Name:");

        txtTeacherName.setEditable(false);
        txtTeacherName.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtTeacherName.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        cbbSubjectID.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cbbSubjectID.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbbSubjectIDPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        lbSubjectID.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbSubjectID.setText("SubjectID:");

        lbTeacherID.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbTeacherID.setText("TeacherID:");

        cbbTeacherID.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cbbTeacherID.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbbTeacherIDPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        javax.swing.GroupLayout pnlInfoLayout = new javax.swing.GroupLayout(pnlInfo);
        pnlInfo.setLayout(pnlInfoLayout);
        pnlInfoLayout.setHorizontalGroup(
            pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlInfoLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbClassID, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbSubjectID, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbTeacherID, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(24, 24, 24)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbbSubjectID, 0, 285, Short.MAX_VALUE)
                    .addComponent(cbbClassID, 0, 285, Short.MAX_VALUE)
                    .addComponent(cbbTeacherID, 0, 285, Short.MAX_VALUE))
                .addGap(70, 70, 70)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbSubjectName, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbClassName, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbTeacherName, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(46, 46, 46)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtClassName)
                    .addComponent(txtSubjectName)
                    .addComponent(txtTeacherName, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
                .addGap(60, 60, 60))
        );
        pnlInfoLayout.setVerticalGroup(
            pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbClassName, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtClassName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbSubjectName, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtSubjectName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbTeacherName, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtTeacherName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlInfoLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(lbClassID))
                            .addComponent(cbbClassID, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlInfoLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(lbSubjectID))
                            .addComponent(cbbSubjectID, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlInfoLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(lbTeacherID))
                            .addComponent(cbbTeacherID, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
                .addContainerGap())
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
            .addComponent(pnlReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 1209, Short.MAX_VALUE)
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
                        .addComponent(pnlDetails, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
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

    private void cbbClassIDPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbbClassIDPopupMenuWillBecomeInvisible
        DatabaseProcess.loadDataTextfield("SELECT * FROM Class WHERE ClassID=?", cbbClassID, txtClassName, "ClassName");
        lbNotification.setVisible(false);
    }//GEN-LAST:event_cbbClassIDPopupMenuWillBecomeInvisible

    private void cbbTeacherIDPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbbTeacherIDPopupMenuWillBecomeInvisible
        DatabaseProcess.loadDataTextfield("SELECT * FROM Teachers WHERE TeacherID=?", cbbTeacherID, txtTeacherName, "Name");
        lbNotification.setVisible(false);
    }//GEN-LAST:event_cbbTeacherIDPopupMenuWillBecomeInvisible

    private void cbbSubjectIDPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbbSubjectIDPopupMenuWillBecomeInvisible
        DatabaseProcess.loadDataTextfield("SELECT * FROM Subjects WHERE SubjectID=?", cbbSubjectID, txtSubjectName, "SubjectName");
        lbNotification.setVisible(false);
    }//GEN-LAST:event_cbbSubjectIDPopupMenuWillBecomeInvisible

    private void pnlAddMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlAddMousePressed
        try{
            func=1;
            updateTeaching();
        }catch(NullPointerException e){
            JOptionPane.showMessageDialog(null, "Please make sure the information entered is complete and does not exist in the system, please try again!", "Message", 1);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Please make sure the information entered is complete and does not exist in the system, please try again!", "Message", 1);
        }
        
    }//GEN-LAST:event_pnlAddMousePressed

    private void pnlResetMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlResetMousePressed
        resetForm();
    }//GEN-LAST:event_pnlResetMousePressed

    private void tblTeachingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTeachingMouseClicked
        displayTeaching();
    }//GEN-LAST:event_tblTeachingMouseClicked

    private void pnlDeleteMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDeleteMousePressed
        int click = JOptionPane.showConfirmDialog(null, "You sure you want to delete! Check the information you"
                + " want to delete to make sure it doesn't affect the system!","Warning!",JOptionPane.YES_NO_OPTION);
        if(click == JOptionPane.YES_OPTION){
           try{
            func=2;
            updateTeaching();
            }catch(NullPointerException e){
                JOptionPane.showMessageDialog(null, "Please make sure the information entered is complete, please try again!", "Message", 1);
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Please make sure the information entered is complete, please try again!", "Message", 1);
            }
        }
    }//GEN-LAST:event_pnlDeleteMousePressed

    private void txtSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchMouseClicked
        txtSearch.setText(null);
        txtSearch.setForeground(Color.BLACK);
    }//GEN-LAST:event_txtSearchMouseClicked

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        DatabaseProcess.tableModel = (DefaultTableModel) tblTeaching.getModel();
        DatabaseProcess.tableModel.setRowCount(0);
        String search = txtSearch.getText();
         DatabaseProcess.loadData("SELECT T.ClassID, ClassName, T.TeacherID, Name, T.SubjectID, SubjectName FROM Teaching T\n" +
            "JOIN Class C ON C.ClassID=T.ClassID\n" +
            "JOIN Teachers TCH ON TCH.TeacherID=T.TeacherID\n" +
            "JOIN Subjects S ON S.SubjectID=T.SubjectID WHERE T.ClassID like '%"+search+"%' "
            + "OR T.TeacherID like '%"+search+"%' "
            + "OR T.SubjectID like '%"+search+"%'", tblTeaching);
    }//GEN-LAST:event_txtSearchKeyReleased

    private void pnlReportMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlReportMouseEntered
        MainJFrame.setColorButton(pnlReport);
    }//GEN-LAST:event_pnlReportMouseEntered

    private void pnlReportMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlReportMouseExited
        MainJFrame.resetColorButton(pnlReport);
    }//GEN-LAST:event_pnlReportMouseExited

    private void pnlReportMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlReportMousePressed
        Report.ExportExcel(tblTeaching);
    }//GEN-LAST:event_pnlReportMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbbClassID;
    private javax.swing.JComboBox cbbSubjectID;
    private javax.swing.JComboBox cbbTeacherID;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbAdd;
    private javax.swing.JLabel lbClassID;
    private javax.swing.JLabel lbClassName;
    private javax.swing.JLabel lbDelete;
    private javax.swing.JLabel lbNotification;
    private javax.swing.JLabel lbReport;
    private javax.swing.JLabel lbReset;
    private javax.swing.JLabel lbSearch;
    private javax.swing.JLabel lbSubjectID;
    private javax.swing.JLabel lbSubjectName;
    private javax.swing.JLabel lbTeacherID;
    private javax.swing.JLabel lbTeacherName;
    private javax.swing.JPanel pnlAdd;
    private javax.swing.JPanel pnlDelete;
    private javax.swing.JPanel pnlDetails;
    private javax.swing.JPanel pnlInfo;
    private javax.swing.JPanel pnlManage;
    private javax.swing.JPanel pnlReport;
    private javax.swing.JPanel pnlReset;
    private javax.swing.JPanel pnlSearch;
    private javax.swing.JPanel pnlTitle;
    private javax.swing.JTable tblTeaching;
    private javax.swing.JTextField txtClassName;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSubjectName;
    private javax.swing.JTextField txtTeacherName;
    // End of variables declaration//GEN-END:variables
}
