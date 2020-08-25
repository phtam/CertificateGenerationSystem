package com.cusc.certificategenerationsystem.view;

import com.cusc.certificategenerationsystem.db.DatabaseConnection;
import static com.cusc.certificategenerationsystem.db.DatabaseConnection.conn;
import com.cusc.certificategenerationsystem.db.DatabaseProcess;
import com.cusc.certificategenerationsystem.report.Report;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ItemEvent;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author PC_HP
 */
public class CertificatesJPanel extends javax.swing.JPanel {
    int func = 0;
    public static String cetID=null;
    /**
     * Creates new form CertificateJPanel
     */
    public CertificatesJPanel() {
        initComponents();
        HomeJPanel.setColorCombobox(new JComboBox[]{cbbCourseID});
        loadCourseInfo();
        pnlIssued.setVisible(false);
        lbNotification.setVisible(false);
        DatabaseProcess.tableModel = new DefaultTableModel();
        DatabaseProcess.loadData("SELECT [CertificateID],[CertificateName], cer.[StudentID],[Name],\n" +
            "cer.[CourseID],CourseName,cer.[GradeID],GradeName,Payments,AverageMark,[Status] FROM [Certificate] cer  \n" +
            "JOIN Registry R ON R.CourseID=cer.CourseID AND R.StudentID=cer.StudentID\n" +
            "JOIN Students S ON S.StudentID=cer.StudentID\n" +
            "JOIN Course C ON C.CourseID=cer.CourseID\n" +
            "JOIN Grades G ON cer.GradeID=G.GradeID", tblCertificates);
        txtCertificateID.setText(DatabaseProcess.getID("execute sp_Certificate_identityID"));
    }
    private void disableForm(){
        txtStudentID.setEditable(false);
        cbbCourseID.setEnabled(false);
    }
    private void enableForm(){
        txtStudentID.setEditable(true);
        cbbCourseID.setEnabled(true);
    }
    private void updateCertificate() throws SQLException{
        String studentID = txtStudentID.getText().toUpperCase();
        String courseID = cbbCourseID.getSelectedItem().toString();
        String gradeID= txtGradeID.getText();
        String courseName = txtCourseName.getText();
        String gradeName = txtGradeName.getText();
        String certificateID = txtCertificateID.getText();
        String payments = txtPayments.getText();
        int averageMark = Integer.parseInt(txtAverageMark.getText());
        String certificateName = courseName + " " + gradeName;
        if(gradeID.equals("GRD004")==true){
            JOptionPane.showMessageDialog(null, "Grade must be greater than C for a certificate to be issued!", "Message",1);
        }else if(payments.equals("Unpaid")==true){
            JOptionPane.showMessageDialog(null, "You must pay the course fee to be issued a certificate!", "Message",1);
        }else{
            int row ;
            switch(func){
                case 1:
                    String sql1 ="INSERT INTO Certificate VALUES ('"+certificateID+"','"+certificateName+"','"
                    +studentID+"','"+gradeID+"','"+courseID+"')";
                    String sql2="UPDATE Registry SET AverageMark="+averageMark+",Status='Graduated' WHERE StudentID='"+studentID+"' "
                        + "AND CourseID='"+courseID+"'";
                    try {
                        DatabaseConnection.conn.setAutoCommit(false);
                        DatabaseConnection.connectSQL();
                        Statement state = conn.createStatement();
                        int row1 = state.executeUpdate(sql1);
                        int row2 = state.executeUpdate(sql2);
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
                        DatabaseConnection.conn.rollback();
                    }                    
                    break;
                case 2:
                    String sql3 ="DELETE FROM Certificate WHERE CertificateID='"+certificateID+"'";
                    String sql4="UPDATE Registry SET Status='Studying' WHERE StudentID='"+studentID+"' "
                        + "AND CourseID='"+courseID+"'";
                    try {
                        DatabaseConnection.conn.setAutoCommit(false);
                        DatabaseConnection.connectSQL();
                        Statement state = conn.createStatement();
                        int row1 = state.executeUpdate(sql3);
                        int row2 = state.executeUpdate(sql4);
                        DatabaseConnection.conn.commit();
                        DatabaseConnection.conn.setAutoCommit(true);
                        row =row1*row2;
                        if(row!=0){
                            notifySuccessful();
                        }
                        state.close();
                        DatabaseConnection.conn.close();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null,"Delete failed. No certificate found!" , "Message", 1);
                        DatabaseConnection.conn.rollback();
                    }
                    break; 
            }
        }  
    }
    private boolean checkCertificateExists(){
        boolean validate=true;
        String studentID = txtStudentID.getText().toUpperCase();
        String courseID = cbbCourseID.getSelectedItem().toString();
        String sql="SELECT * FROM [Certificate] WHERE StudentID='"+studentID+"' AND CourseID='"+courseID+"'";
        ResultSet rs = DatabaseProcess.displayInformation(sql);
        try {
            validate = !rs.next();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Check certificate exists failed!");
        }
        return validate;
    }
    private void notifySuccessful(){
        lbNotification.setVisible(true);
        DatabaseProcess.tableModel = (DefaultTableModel) tblCertificates.getModel();        
        DatabaseProcess.tableModel.setRowCount(0);        
        DatabaseProcess.loadData("SELECT [CertificateID],[CertificateName], cer.[StudentID],[Name],\n" +
            "cer.[CourseID],CourseName,cer.[GradeID],GradeName,Payments,AverageMark,[Status] FROM [Certificate] cer  \n" +
            "JOIN Registry R ON R.CourseID=cer.CourseID AND R.StudentID=cer.StudentID\n" +
            "JOIN Students S ON S.StudentID=cer.StudentID\n" +
            "JOIN Course C ON C.CourseID=cer.CourseID\n" +
            "JOIN Grades G ON cer.GradeID=G.GradeID", tblCertificates);
        resetForm();
        enableForm();
    }
    private void loadStudentInfo(){
        try {
            String studentID = txtStudentID.getText();
            String sql = "SELECT * FROM Registry R JOIN Students S ON R.StudentID=S.StudentID "
                    + "WHERE R.StudentID='"+studentID+"'";
            ResultSet rs = DatabaseProcess.displayInformation(sql);
            if(rs.next()){
                txtStudentName.setText(rs.getString("Name"));
            }else{
                txtStudentName.setText("");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "ID not found", "Message", 1);
        }
    }
    private void loadCourseInfo(){
        cbbCourseID.removeAllItems();
        DatabaseProcess.loadDataCombobox("SELECT * FROM Course C JOIN Registry R ON R.CourseID=C.CourseID "
                + "WHERE StudentID='"+txtStudentID.getText()+"'", cbbCourseID, "CourseID");
        DatabaseProcess.loadDataTextfield("SELECT * FROM Course WHERE CourseID=?", cbbCourseID, txtCourseName, "CourseName");
    }      
    private void loadCertificateInfo(){
        String courseName = txtCourseName.getText();
        String gradeName = txtGradeName.getText();
        String certificateName = courseName + " " + gradeName;
        txtCertificateName.setText(certificateName);
    }
    private void resetForm(){
        txtStudentID.setText(null);
        txtStudentName.setText(null);
        txtCourseName.setText(null);
        txtAverageMark.setText(null);
        txtGradeID.setText(null);
        txtGradeName.setText(null);
        txtCertificateName.setText(null);
        txtStatus.setText(null);
        txtPayments.setText(null);
        loadStudentInfo();
        loadCourseInfo();
        txtCertificateID.setText(DatabaseProcess.getID("execute sp_Certificate_identityID"));
        enableForm();
        pnlIssued.setVisible(false);
    }
    private void displayAverageMark(){
        try {
            String studentID = txtStudentID.getText().toUpperCase();
            String courseID = cbbCourseID.getSelectedItem().toString();
            String sql="SELECT (SUM(Mark))/(SELECT COUNT(SubjectID) FROM Subjects WHERE CourseID='"+courseID+"') \n" +
                "AS 'Mark' FROM Exam E \n" +
                "JOIN Subjects S ON E.SubjectID=S.SubjectID\n" +
                "JOIN Course C ON C.CourseID=S.CourseID WHERE StudentID='"+studentID+"'\n" +
                "AND S.CourseID='"+courseID+"'";
            ResultSet rs = DatabaseProcess.displayInformation(sql);
            if(rs.next()){
                txtAverageMark.setText(rs.getString("Mark"));
            }
        } catch (SQLException | NullPointerException ex) {
            txtAverageMark.setText("");
        }
    }
    private void checkPayments(){
        try {
            String studentID = txtStudentID.getText();
            String sql = "SELECT * FROM Registry "
                    + "WHERE StudentID='"+studentID+"'";
            ResultSet rs = DatabaseProcess.displayInformation(sql);
            if(rs.next()){
                txtPayments.setText(rs.getString("Payments"));
                txtStatus.setText(rs.getString("Status"));
            }else{
                txtPayments.setText(null);
                txtStatus.setText(null);
            }
        } catch (NullPointerException ex) {
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    private void displayGrade(){
        try {
            int avgMark = Integer.parseInt(txtAverageMark.getText());
            String sql = "SELECT * FROM Grades WHERE [Max]>="+avgMark+" AND [Min]<="+avgMark;
            ResultSet rs = DatabaseProcess.displayInformation(sql);
            if(rs.next()){
                txtGradeID.setText(rs.getString("GradeID"));
                txtGradeName.setText(rs.getString("GradeName"));
            }else{
                txtGradeID.setText(null);
                txtGradeName.setText(null);
            }
        } catch (NumberFormatException | SQLException | NullPointerException ex) {
            txtGradeID.setText(null);
            txtGradeName.setText(null);
            txtCertificateName.setText(null);
        }
    }
    private void PrintFrameToPDF() {
        JPanel panel = new CertificateOfStudent();
        JFrame frame = new JFrame();
        frame.add(new JScrollPane(panel));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setTitle("Certificate");
        frame.setVisible(true);

        final java.awt.Image image = getImageFromPanel(panel);

        /* This was just a text panel to make sure the full panel was
         * drawn to the panel.
         */
        JPanel newPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(300, 300);
            }
        };

        /* Print Image to PDF */
        String fileName = "D://Certificate"+txtStudentID.getText()+".pdf";
        printToPDF(image, fileName);

        JOptionPane.showMessageDialog(null, "Create certificate successful!");
    }
    private void printToPDF(java.awt.Image awtImage, String fileName) {
        try {
            Document d = new Document();
            PdfWriter writer = PdfWriter.getInstance(d, new FileOutputStream(fileName));
            d.open();


            Image iTextImage = Image.getInstance(writer, awtImage, 1);
            iTextImage.setAbsolutePosition(50, 50);
            iTextImage.scalePercent(25);
            d.add(iTextImage);

            d.close();

        } catch (Exception e) {
            e.printStackTrace();
        }   
    }

    public static java.awt.Image getImageFromPanel(Component component) {

        BufferedImage image = new BufferedImage(component.getWidth(),component.getHeight(), BufferedImage.TYPE_INT_RGB);
        component.paint(image.getGraphics());
        return image;
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
        tblCertificates = new javax.swing.JTable();
        pnlInfo = new javax.swing.JPanel();
        lbCourseName = new javax.swing.JLabel();
        cbbCourseID = new javax.swing.JComboBox();
        txtGradeName = new javax.swing.JTextField();
        txtCourseName = new javax.swing.JTextField();
        txtStudentName = new javax.swing.JTextField();
        lbStudentID = new javax.swing.JLabel();
        lbGradeID = new javax.swing.JLabel();
        lbGradeName = new javax.swing.JLabel();
        lbStudentName = new javax.swing.JLabel();
        lbCourseID = new javax.swing.JLabel();
        lbCertificateName = new javax.swing.JLabel();
        txtCertificateName = new javax.swing.JTextField();
        lbCertificateID = new javax.swing.JLabel();
        txtCertificateID = new javax.swing.JTextField();
        txtStudentID = new javax.swing.JTextField();
        lbAverageMark = new javax.swing.JLabel();
        txtAverageMark = new javax.swing.JTextField();
        txtGradeID = new javax.swing.JTextField();
        lbPayments = new javax.swing.JLabel();
        txtPayments = new javax.swing.JTextField();
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
        pnlDelete = new javax.swing.JPanel();
        lbDelete = new javax.swing.JLabel();
        pnlReset = new javax.swing.JPanel();
        lbReset = new javax.swing.JLabel();
        pnlReport = new javax.swing.JPanel();
        lbReport = new javax.swing.JLabel();
        pnlIssued = new javax.swing.JPanel();
        lbIssued = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        pnlDetails.setBackground(new java.awt.Color(255, 255, 255));
        pnlDetails.setForeground(new java.awt.Color(204, 204, 204));
        pnlDetails.setLayout(new java.awt.BorderLayout());

        tblCertificates.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        tblCertificates.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblCertificates.setSelectionBackground(new java.awt.Color(71, 120, 197));
        tblCertificates.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCertificatesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCertificates);

        pnlDetails.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pnlInfo.setBackground(new java.awt.Color(255, 255, 255));
        pnlInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Certificate Information", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 15))); // NOI18N

        lbCourseName.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbCourseName.setText("CourseName:");

        cbbCourseID.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cbbCourseID.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbCourseIDItemStateChanged(evt);
            }
        });
        cbbCourseID.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbbCourseIDPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        txtGradeName.setEditable(false);
        txtGradeName.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtGradeName.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        txtCourseName.setEditable(false);
        txtCourseName.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtCourseName.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        txtStudentName.setEditable(false);
        txtStudentName.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtStudentName.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        lbStudentID.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbStudentID.setText("StudentID:");

        lbGradeID.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbGradeID.setText("GradeID:");

        lbGradeName.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbGradeName.setText("Grade Name:");

        lbStudentName.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbStudentName.setText("Student Name:");

        lbCourseID.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbCourseID.setText("CourseID:");

        lbCertificateName.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbCertificateName.setText("Certificate Name:");

        txtCertificateName.setEditable(false);
        txtCertificateName.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtCertificateName.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        lbCertificateID.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbCertificateID.setText("CertificateID:");

        txtCertificateID.setEditable(false);
        txtCertificateID.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtCertificateID.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        txtStudentID.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtStudentID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtStudentIDKeyReleased(evt);
            }
        });

        lbAverageMark.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbAverageMark.setText("Average mark:");

        txtAverageMark.setEditable(false);
        txtAverageMark.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        txtGradeID.setEditable(false);
        txtGradeID.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        lbPayments.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbPayments.setText("Payments:");

        txtPayments.setEditable(false);
        txtPayments.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        lbStatus.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbStatus.setText("Status:");

        txtStatus.setEditable(false);
        txtStatus.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        javax.swing.GroupLayout pnlInfoLayout = new javax.swing.GroupLayout(pnlInfo);
        pnlInfo.setLayout(pnlInfoLayout);
        pnlInfoLayout.setHorizontalGroup(
            pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbStudentID)
                    .addComponent(lbCourseID)
                    .addComponent(lbGradeID)
                    .addComponent(lbCertificateID)
                    .addComponent(lbPayments))
                .addGap(40, 40, 40)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPayments)
                    .addComponent(txtCertificateID, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                    .addComponent(txtGradeID)
                    .addComponent(cbbCourseID, 0, 284, Short.MAX_VALUE)
                    .addComponent(txtStudentID))
                .addGap(57, 57, 57)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbAverageMark, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbStatus, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbCertificateName, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbGradeName, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbCourseName, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbStudentName, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(38, 38, 38)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtAverageMark, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                    .addComponent(txtCertificateName)
                    .addComponent(txtGradeName)
                    .addComponent(txtCourseName)
                    .addComponent(txtStudentName)
                    .addComponent(txtStatus))
                .addGap(60, 60, 60))
        );
        pnlInfoLayout.setVerticalGroup(
            pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoLayout.createSequentialGroup()
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlInfoLayout.createSequentialGroup()
                                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbStudentName, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtStudentName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(20, 20, 20)
                                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbCourseName, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtCourseName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlInfoLayout.createSequentialGroup()
                                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtStudentID, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbStudentID, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(20, 20, 20)
                                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(cbbCourseID, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbCourseID))
                                .addGap(20, 20, 20)
                                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lbGradeName, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txtGradeName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lbGradeID, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txtGradeID, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(20, 20, 20)
                                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lbCertificateName, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(txtCertificateName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(lbCertificateID)
                                        .addComponent(txtCertificateID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(20, 20, 20)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbAverageMark, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtAverageMark, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addGap(248, 248, 248)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbPayments, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtPayments, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(20, 20, 20)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbStatus, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        pnlInfoLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtAverageMark, txtCertificateName, txtCourseName, txtGradeName, txtStudentName});

        pnlInfoLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtCertificateID, txtGradeID, txtPayments, txtStudentID});

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
                .addGap(8, 8, 8))
        );
        pnlReportLayout.setVerticalGroup(
            pnlReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlReportLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(lbReport, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        pnlIssued.setBackground(new java.awt.Color(120, 168, 252));
        pnlIssued.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlIssued.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlIssuedMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlIssuedMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlIssuedMousePressed(evt);
            }
        });

        lbIssued.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lbIssued.setForeground(new java.awt.Color(255, 255, 255));
        lbIssued.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cusc/certificategenerationsystem/images/icon_issued.png"))); // NOI18N
        lbIssued.setText("Issued");

        javax.swing.GroupLayout pnlIssuedLayout = new javax.swing.GroupLayout(pnlIssued);
        pnlIssued.setLayout(pnlIssuedLayout);
        pnlIssuedLayout.setHorizontalGroup(
            pnlIssuedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlIssuedLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lbIssued)
                .addGap(8, 8, 8))
        );
        pnlIssuedLayout.setVerticalGroup(
            pnlIssuedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlIssuedLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(lbIssued, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addComponent(pnlIssued, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlManageLayout.setVerticalGroup(
            pnlManageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlManageLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(pnlAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(pnlDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(pnlReset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(pnlReport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(pnlIssued, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 1222, Short.MAX_VALUE)
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
                        .addComponent(pnlDetails, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
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

    private void cbbCourseIDPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbbCourseIDPopupMenuWillBecomeInvisible
        DatabaseProcess.loadDataTextfield("SELECT * FROM Course WHERE CourseID=?", cbbCourseID, txtCourseName, "CourseName");
        displayGrade();
        loadCertificateInfo();
        lbNotification.setVisible(false);
    }//GEN-LAST:event_cbbCourseIDPopupMenuWillBecomeInvisible

    private void pnlAddMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlAddMousePressed
        String mark=txtAverageMark.getText();
        try{
            if(checkCertificateExists()==false){
                JOptionPane.showMessageDialog(null, "Certificate has been issued!", "Message",1);
                return;
            }if(mark.matches("[0-9]{1,3}")==false){
                JOptionPane.showMessageDialog(null, "Students must complete all exams to be issued a certificate!", "Message",1);
                return;
            }else{
                func = 1;
                updateCertificate();
            }
        }catch(NullPointerException e){
            JOptionPane.showMessageDialog(null, "Please complete all information!", "Message", 1);
            txtCertificateID.requestFocus();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Certificate creation failed. Please check again!", "Message", 1);
        }
    }//GEN-LAST:event_pnlAddMousePressed

    private void tblCertificatesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCertificatesMouseClicked
        try {
            int row = tblCertificates.getSelectedRow();
            String idRow = (tblCertificates.getModel().getValueAt(row, 0)).toString();
            String sql = "SELECT * FROM [Certificate] C\n" +
                "JOIN Grades G ON C.GradeID=G.GradeID\n" +
                "JOIN Students S ON S.StudentID=C.StudentID "+
                "JOIN Registry R ON R.StudentID=C.StudentID AND R.CourseID=C.CourseID "+
                " WHERE [CertificateID]='"+idRow+"'";
            ResultSet rs = DatabaseProcess.displayInformation(sql);
            if(rs.next()){
                txtStudentID.setText(rs.getString("StudentID"));
                txtStudentName.setText(rs.getString("Name"));
                txtGradeID.setText(rs.getString("GradeID"));
                txtGradeName.setText(rs.getString("GradeName"));
                txtCertificateID.setText(rs.getString("CertificateID"));
                txtCertificateName.setText(rs.getString("CertificateName"));
                txtStatus.setText(rs.getString("Status"));
                txtAverageMark.setText(rs.getString("AverageMark"));
                cbbCourseID.removeAllItems();
                cbbCourseID.addItem(rs.getString("CourseID"));
            }      
            DatabaseProcess.loadDataTextfield("SELECT * FROM Course WHERE CourseID=?", cbbCourseID, txtCourseName, "CourseName");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "INFORMATION DISPLAY FAILED", "Message", 1);
        }
        cetID = txtCertificateID.getText();
        checkPayments();
        lbNotification.setVisible(false);
        disableForm();
        pnlIssued.setVisible(true);
    }//GEN-LAST:event_tblCertificatesMouseClicked

    private void pnlResetMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlResetMousePressed
        resetForm();
    }//GEN-LAST:event_pnlResetMousePressed

    private void txtStudentIDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStudentIDKeyReleased
        loadStudentInfo();
        loadCourseInfo();
        displayAverageMark();
        displayGrade();
        loadCertificateInfo();
        checkPayments();
    }//GEN-LAST:event_txtStudentIDKeyReleased

    private void pnlDeleteMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDeleteMousePressed
        try{
            int click = JOptionPane.showConfirmDialog(null, "You sure you want to delete! Check the information you"
                + " want to delete to make sure it doesn't affect the system!","Warning!",JOptionPane.YES_NO_OPTION);
            if(click == JOptionPane.YES_OPTION){
                func = 2;
                updateCertificate();
            }     
        }catch(NullPointerException e){
            JOptionPane.showMessageDialog(null, "Please complete all information!", "Message", 1);
            txtCertificateID.requestFocus();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Please check again!", "Message", 1);
        }
    }//GEN-LAST:event_pnlDeleteMousePressed

    private void txtSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchMouseClicked
        txtSearch.setText(null);
        txtSearch.setForeground(Color.BLACK);
    }//GEN-LAST:event_txtSearchMouseClicked

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        DatabaseProcess.tableModel = (DefaultTableModel) tblCertificates.getModel();        
        DatabaseProcess.tableModel.setRowCount(0); 
        String search = txtSearch.getText();
        DatabaseProcess.loadData("SELECT [CertificateID],[CertificateName], cer.[StudentID],[Name],\n" +
            "cer.[CourseID],CourseName,cer.[GradeID],GradeName,Payments,AverageMark,[Status] FROM [Certificate] cer  \n" +
            "JOIN Registry R ON R.CourseID=cer.CourseID AND R.StudentID=cer.StudentID\n" +
            "JOIN Students S ON S.StudentID=cer.StudentID\n" +
            "JOIN Course C ON C.CourseID=cer.CourseID\n" +
            "JOIN Grades G ON cer.GradeID=G.GradeID WHERE CertificateID LIKE '%"+search+"%' "
            + "or s.[StudentID] LIKE '%"+search+"%' or Name LIKE '%"+search+"%'", tblCertificates);
    }//GEN-LAST:event_txtSearchKeyReleased

    private void cbbCourseIDItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbCourseIDItemStateChanged
        if(evt.getStateChange()==ItemEvent.SELECTED){
            displayAverageMark();
        }
    }//GEN-LAST:event_cbbCourseIDItemStateChanged

    private void pnlReportMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlReportMouseEntered
        MainJFrame.setColorButton(pnlReport);
    }//GEN-LAST:event_pnlReportMouseEntered

    private void pnlReportMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlReportMouseExited
        MainJFrame.resetColorButton(pnlReport);
    }//GEN-LAST:event_pnlReportMouseExited

    private void pnlReportMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlReportMousePressed
        Report.ExportExcel(tblCertificates);
    }//GEN-LAST:event_pnlReportMousePressed

    private void pnlIssuedMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlIssuedMouseEntered
        MainJFrame.setColorButton(pnlIssued);
    }//GEN-LAST:event_pnlIssuedMouseEntered

    private void pnlIssuedMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlIssuedMouseExited
        MainJFrame.resetColorButton(pnlIssued);
    }//GEN-LAST:event_pnlIssuedMouseExited

    private void pnlIssuedMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlIssuedMousePressed
        PrintFrameToPDF();
        
    
    }//GEN-LAST:event_pnlIssuedMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cbbCourseID;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbAdd;
    private javax.swing.JLabel lbAverageMark;
    private javax.swing.JLabel lbCertificateID;
    private javax.swing.JLabel lbCertificateName;
    private javax.swing.JLabel lbCourseID;
    private javax.swing.JLabel lbCourseName;
    private javax.swing.JLabel lbDelete;
    private javax.swing.JLabel lbGradeID;
    private javax.swing.JLabel lbGradeName;
    private javax.swing.JLabel lbIssued;
    private javax.swing.JLabel lbNotification;
    private javax.swing.JLabel lbPayments;
    private javax.swing.JLabel lbReport;
    private javax.swing.JLabel lbReset;
    private javax.swing.JLabel lbSearch;
    private javax.swing.JLabel lbStatus;
    private javax.swing.JLabel lbStudentID;
    private javax.swing.JLabel lbStudentName;
    private javax.swing.JPanel pnlAdd;
    private javax.swing.JPanel pnlDelete;
    private javax.swing.JPanel pnlDetails;
    private javax.swing.JPanel pnlInfo;
    private javax.swing.JPanel pnlIssued;
    private javax.swing.JPanel pnlManage;
    private javax.swing.JPanel pnlReport;
    private javax.swing.JPanel pnlReset;
    private javax.swing.JPanel pnlSearch;
    private javax.swing.JPanel pnlTitle;
    private javax.swing.JTable tblCertificates;
    private javax.swing.JTextField txtAverageMark;
    private javax.swing.JTextField txtCertificateID;
    private javax.swing.JTextField txtCertificateName;
    private javax.swing.JTextField txtCourseName;
    private javax.swing.JTextField txtGradeID;
    private javax.swing.JTextField txtGradeName;
    private javax.swing.JTextField txtPayments;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtStatus;
    private javax.swing.JTextField txtStudentID;
    private javax.swing.JTextField txtStudentName;
    // End of variables declaration//GEN-END:variables
}
