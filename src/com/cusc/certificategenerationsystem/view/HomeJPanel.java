/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.certificategenerationsystem.view;

import com.cusc.certificategenerationsystem.db.DatabaseProcess;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JPanel;

/**
 *
 * @author PC_HP
 */
public class HomeJPanel extends javax.swing.JPanel {

    /**
     * Creates new form HomeJPanel
     */
    public HomeJPanel() {
        initComponents();
        showJPanel(new StudentsJPanel());
        pnlStudents.setBorder(BorderFactory.createBevelBorder(1));
        grantAccess();
        
    }
    private void activeColor(JPanel pane){
        pane.setBackground(new Color(71,120,197));
        pane.setBorder(BorderFactory.createBevelBorder(1));
    }    
    
    private void deactiveColor(JPanel [] pane){
        for(int i=0; i<pane.length;i++){
            pane[i].setBackground(new Color(120,168,252));
            pane[i].setBorder(null);
        }    
    }
    private void showJPanel(JPanel pane){
        JPanel pnlChild = pane;
        pnlView.removeAll();
        pnlView.add(pnlChild);
        pnlView.validate();
    }
    private void grantAccess(){
        if("Certificate Cell".equals(DatabaseProcess.checkRole())){
           pnlTeaching.setVisible(false);
           pnlAdmin.setVisible(false);
        } 
    }
    public static void setColorCombobox(JComboBox [] cbb){
        for(int i=0;i<cbb.length;i++){
            cbb[i].setRenderer(new DefaultListCellRenderer() {
                @Override
                public void paint(Graphics g) {
                    setForeground(Color.BLACK);
                    super.paint(g);
                }
            });
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

        pnlBlueMenu = new javax.swing.JPanel();
        pnlStudents = new javax.swing.JPanel();
        lbStudents = new javax.swing.JLabel();
        pnlTeachers = new javax.swing.JPanel();
        lbTeachers = new javax.swing.JLabel();
        pnlCourse = new javax.swing.JPanel();
        lbCourse = new javax.swing.JLabel();
        pnlClass = new javax.swing.JPanel();
        lbClass = new javax.swing.JLabel();
        pnlCertificate = new javax.swing.JPanel();
        lbCertificate = new javax.swing.JLabel();
        pnlSubjects = new javax.swing.JPanel();
        lbSubjects = new javax.swing.JLabel();
        pnlExam = new javax.swing.JPanel();
        lbExam = new javax.swing.JLabel();
        pnlGrade = new javax.swing.JPanel();
        lbGrade = new javax.swing.JLabel();
        pnlTeaching = new javax.swing.JPanel();
        lbTeaching = new javax.swing.JLabel();
        pnlAdmin = new javax.swing.JPanel();
        lbAdmin = new javax.swing.JLabel();
        pnlView = new javax.swing.JPanel();

        pnlBlueMenu.setBackground(new java.awt.Color(71, 120, 197));

        pnlStudents.setBackground(new java.awt.Color(71, 120, 197));
        pnlStudents.setPreferredSize(new java.awt.Dimension(270, 52));
        pnlStudents.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlStudentsMousePressed(evt);
            }
        });

        lbStudents.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        lbStudents.setForeground(new java.awt.Color(255, 255, 255));
        lbStudents.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cusc/certificategenerationsystem/images/image_manage_students.png"))); // NOI18N
        lbStudents.setText("Manage Students");

        javax.swing.GroupLayout pnlStudentsLayout = new javax.swing.GroupLayout(pnlStudents);
        pnlStudents.setLayout(pnlStudentsLayout);
        pnlStudentsLayout.setHorizontalGroup(
            pnlStudentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentsLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lbStudents)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlStudentsLayout.setVerticalGroup(
            pnlStudentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStudentsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbStudents)
                .addGap(18, 18, 18))
        );

        pnlTeachers.setBackground(new java.awt.Color(120, 168, 252));
        pnlTeachers.setPreferredSize(new java.awt.Dimension(270, 52));
        pnlTeachers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlTeachersMousePressed(evt);
            }
        });

        lbTeachers.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        lbTeachers.setForeground(new java.awt.Color(255, 255, 255));
        lbTeachers.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cusc/certificategenerationsystem/images/image_manage_teachers.png"))); // NOI18N
        lbTeachers.setText("Manage Teachers");

        javax.swing.GroupLayout pnlTeachersLayout = new javax.swing.GroupLayout(pnlTeachers);
        pnlTeachers.setLayout(pnlTeachersLayout);
        pnlTeachersLayout.setHorizontalGroup(
            pnlTeachersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTeachersLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lbTeachers)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlTeachersLayout.setVerticalGroup(
            pnlTeachersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTeachersLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbTeachers)
                .addGap(18, 18, 18))
        );

        pnlCourse.setBackground(new java.awt.Color(120, 168, 252));
        pnlCourse.setPreferredSize(new java.awt.Dimension(270, 52));
        pnlCourse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlCourseMousePressed(evt);
            }
        });

        lbCourse.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        lbCourse.setForeground(new java.awt.Color(255, 255, 255));
        lbCourse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cusc/certificategenerationsystem/images/image_manage_course.png"))); // NOI18N
        lbCourse.setText("Manage Course");

        javax.swing.GroupLayout pnlCourseLayout = new javax.swing.GroupLayout(pnlCourse);
        pnlCourse.setLayout(pnlCourseLayout);
        pnlCourseLayout.setHorizontalGroup(
            pnlCourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCourseLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lbCourse)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlCourseLayout.setVerticalGroup(
            pnlCourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCourseLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbCourse)
                .addGap(18, 18, 18))
        );

        pnlClass.setBackground(new java.awt.Color(120, 168, 252));
        pnlClass.setPreferredSize(new java.awt.Dimension(270, 52));
        pnlClass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlClassMousePressed(evt);
            }
        });

        lbClass.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        lbClass.setForeground(new java.awt.Color(255, 255, 255));
        lbClass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cusc/certificategenerationsystem/images/image_manage_class.png"))); // NOI18N
        lbClass.setText("Manage Class");

        javax.swing.GroupLayout pnlClassLayout = new javax.swing.GroupLayout(pnlClass);
        pnlClass.setLayout(pnlClassLayout);
        pnlClassLayout.setHorizontalGroup(
            pnlClassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlClassLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lbClass)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlClassLayout.setVerticalGroup(
            pnlClassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlClassLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbClass)
                .addGap(18, 18, 18))
        );

        pnlCertificate.setBackground(new java.awt.Color(120, 168, 252));
        pnlCertificate.setPreferredSize(new java.awt.Dimension(270, 52));
        pnlCertificate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlCertificateMousePressed(evt);
            }
        });

        lbCertificate.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        lbCertificate.setForeground(new java.awt.Color(255, 255, 255));
        lbCertificate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cusc/certificategenerationsystem/images/image_manage_certificate.png"))); // NOI18N
        lbCertificate.setText("Manage Certificates");

        javax.swing.GroupLayout pnlCertificateLayout = new javax.swing.GroupLayout(pnlCertificate);
        pnlCertificate.setLayout(pnlCertificateLayout);
        pnlCertificateLayout.setHorizontalGroup(
            pnlCertificateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCertificateLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lbCertificate)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlCertificateLayout.setVerticalGroup(
            pnlCertificateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCertificateLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbCertificate)
                .addGap(18, 18, 18))
        );

        pnlSubjects.setBackground(new java.awt.Color(120, 168, 252));
        pnlSubjects.setPreferredSize(new java.awt.Dimension(270, 52));
        pnlSubjects.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlSubjectsMousePressed(evt);
            }
        });

        lbSubjects.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        lbSubjects.setForeground(new java.awt.Color(255, 255, 255));
        lbSubjects.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cusc/certificategenerationsystem/images/image_manage_subject.png"))); // NOI18N
        lbSubjects.setText("Manage Subjects");

        javax.swing.GroupLayout pnlSubjectsLayout = new javax.swing.GroupLayout(pnlSubjects);
        pnlSubjects.setLayout(pnlSubjectsLayout);
        pnlSubjectsLayout.setHorizontalGroup(
            pnlSubjectsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSubjectsLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lbSubjects)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlSubjectsLayout.setVerticalGroup(
            pnlSubjectsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSubjectsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbSubjects)
                .addGap(18, 18, 18))
        );

        pnlExam.setBackground(new java.awt.Color(120, 168, 252));
        pnlExam.setPreferredSize(new java.awt.Dimension(270, 52));
        pnlExam.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlExamMousePressed(evt);
            }
        });

        lbExam.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        lbExam.setForeground(new java.awt.Color(255, 255, 255));
        lbExam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cusc/certificategenerationsystem/images/image_manage_exam.png"))); // NOI18N
        lbExam.setText("Manage Exam");

        javax.swing.GroupLayout pnlExamLayout = new javax.swing.GroupLayout(pnlExam);
        pnlExam.setLayout(pnlExamLayout);
        pnlExamLayout.setHorizontalGroup(
            pnlExamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlExamLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lbExam)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlExamLayout.setVerticalGroup(
            pnlExamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlExamLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbExam)
                .addGap(18, 18, 18))
        );

        pnlGrade.setBackground(new java.awt.Color(120, 168, 252));
        pnlGrade.setPreferredSize(new java.awt.Dimension(270, 52));
        pnlGrade.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlGradeMousePressed(evt);
            }
        });

        lbGrade.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        lbGrade.setForeground(new java.awt.Color(255, 255, 255));
        lbGrade.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cusc/certificategenerationsystem/images/image_manage_grade.png"))); // NOI18N
        lbGrade.setText("Manage Grade");

        javax.swing.GroupLayout pnlGradeLayout = new javax.swing.GroupLayout(pnlGrade);
        pnlGrade.setLayout(pnlGradeLayout);
        pnlGradeLayout.setHorizontalGroup(
            pnlGradeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGradeLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lbGrade)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlGradeLayout.setVerticalGroup(
            pnlGradeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlGradeLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbGrade)
                .addGap(18, 18, 18))
        );

        pnlTeaching.setBackground(new java.awt.Color(120, 168, 252));
        pnlTeaching.setPreferredSize(new java.awt.Dimension(270, 52));
        pnlTeaching.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlTeachingMousePressed(evt);
            }
        });

        lbTeaching.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        lbTeaching.setForeground(new java.awt.Color(255, 255, 255));
        lbTeaching.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cusc/certificategenerationsystem/images/image_manage_teaching.png"))); // NOI18N
        lbTeaching.setText("Manage Teaching");

        javax.swing.GroupLayout pnlTeachingLayout = new javax.swing.GroupLayout(pnlTeaching);
        pnlTeaching.setLayout(pnlTeachingLayout);
        pnlTeachingLayout.setHorizontalGroup(
            pnlTeachingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTeachingLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lbTeaching)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlTeachingLayout.setVerticalGroup(
            pnlTeachingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTeachingLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbTeaching)
                .addGap(18, 18, 18))
        );

        pnlAdmin.setBackground(new java.awt.Color(120, 168, 252));
        pnlAdmin.setPreferredSize(new java.awt.Dimension(270, 52));
        pnlAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlAdminMousePressed(evt);
            }
        });

        lbAdmin.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        lbAdmin.setForeground(new java.awt.Color(255, 255, 255));
        lbAdmin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/cusc/certificategenerationsystem/images/image_manage_admin.png"))); // NOI18N
        lbAdmin.setText("Manage Admin & CC");

        javax.swing.GroupLayout pnlAdminLayout = new javax.swing.GroupLayout(pnlAdmin);
        pnlAdmin.setLayout(pnlAdminLayout);
        pnlAdminLayout.setHorizontalGroup(
            pnlAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAdminLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lbAdmin)
                .addContainerGap(53, Short.MAX_VALUE))
        );
        pnlAdminLayout.setVerticalGroup(
            pnlAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAdminLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbAdmin)
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout pnlBlueMenuLayout = new javax.swing.GroupLayout(pnlBlueMenu);
        pnlBlueMenu.setLayout(pnlBlueMenuLayout);
        pnlBlueMenuLayout.setHorizontalGroup(
            pnlBlueMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTeachers, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
            .addComponent(pnlStudents, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
            .addComponent(pnlCourse, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
            .addComponent(pnlClass, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
            .addComponent(pnlSubjects, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
            .addComponent(pnlExam, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
            .addComponent(pnlGrade, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
            .addComponent(pnlAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
            .addComponent(pnlTeaching, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
            .addComponent(pnlCertificate, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
        );
        pnlBlueMenuLayout.setVerticalGroup(
            pnlBlueMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBlueMenuLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(pnlStudents, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(pnlTeachers, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(pnlCourse, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(pnlClass, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(pnlCertificate, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(pnlSubjects, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(pnlExam, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(pnlGrade, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(pnlTeaching, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(pnlAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlView.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(pnlBlueMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlView, javax.swing.GroupLayout.DEFAULT_SIZE, 887, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBlueMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void pnlStudentsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlStudentsMousePressed
        activeColor(pnlStudents);
        deactiveColor(new JPanel[]{pnlTeachers,pnlCourse,pnlClass,pnlCertificate,pnlSubjects,
            pnlExam,pnlGrade,pnlAdmin,pnlTeaching});
        showJPanel(new StudentsJPanel());
    }//GEN-LAST:event_pnlStudentsMousePressed

    private void pnlTeachersMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTeachersMousePressed
        activeColor(pnlTeachers);
        deactiveColor(new JPanel[]{pnlStudents,pnlCourse,pnlClass,pnlCertificate,pnlSubjects,
            pnlExam,pnlGrade,pnlAdmin,pnlTeaching});
        showJPanel(new TeachersJPanel());
    }//GEN-LAST:event_pnlTeachersMousePressed

    private void pnlCourseMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlCourseMousePressed
        activeColor(pnlCourse);
        deactiveColor(new JPanel[]{pnlStudents,pnlTeachers,pnlClass,pnlCertificate,pnlSubjects,
            pnlExam,pnlGrade,pnlAdmin,pnlTeaching});
        showJPanel(new CourseJPanel());
    }//GEN-LAST:event_pnlCourseMousePressed

    private void pnlClassMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlClassMousePressed
        activeColor(pnlClass);
        deactiveColor(new JPanel[]{pnlStudents,pnlTeachers,pnlCourse,pnlCertificate,pnlSubjects,
            pnlExam,pnlGrade,pnlAdmin,pnlTeaching});
        showJPanel(new ClassJPanel());
    }//GEN-LAST:event_pnlClassMousePressed

    private void pnlCertificateMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlCertificateMousePressed
        activeColor(pnlCertificate);
        deactiveColor(new JPanel[]{pnlStudents,pnlTeachers,pnlCourse,pnlClass,pnlSubjects,
            pnlExam,pnlGrade,pnlAdmin,pnlTeaching});
        showJPanel(new CertificatesJPanel());
    }//GEN-LAST:event_pnlCertificateMousePressed

    private void pnlSubjectsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlSubjectsMousePressed
        activeColor(pnlSubjects);
        deactiveColor(new JPanel[]{pnlStudents,pnlTeachers,pnlCourse,pnlCertificate,pnlClass,
            pnlExam,pnlGrade,pnlAdmin,pnlTeaching});
        showJPanel(new SubjectsJPanel());
    }//GEN-LAST:event_pnlSubjectsMousePressed

    private void pnlExamMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlExamMousePressed
        activeColor(pnlExam);
        deactiveColor(new JPanel[]{pnlStudents,pnlTeachers,pnlCourse,pnlCertificate,pnlClass,pnlSubjects
            ,pnlGrade,pnlAdmin,pnlTeaching});
        showJPanel(new ExamJPanel());
    }//GEN-LAST:event_pnlExamMousePressed

    private void pnlGradeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlGradeMousePressed
        activeColor(pnlGrade);
        deactiveColor(new JPanel[]{pnlStudents,pnlTeachers,pnlCourse,pnlCertificate,pnlClass,pnlSubjects,
            pnlExam,pnlAdmin,pnlTeaching});
        showJPanel(new GradeJPanel());
    }//GEN-LAST:event_pnlGradeMousePressed

    private void pnlTeachingMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTeachingMousePressed
        activeColor(pnlTeaching);
        deactiveColor(new JPanel[]{pnlStudents,pnlTeachers,pnlCourse,pnlCertificate,pnlClass,pnlSubjects,
            pnlExam,pnlGrade,pnlAdmin});
        showJPanel(new TeachingJPanel());
    }//GEN-LAST:event_pnlTeachingMousePressed

    private void pnlAdminMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlAdminMousePressed
        activeColor(pnlAdmin);
        deactiveColor(new JPanel[]{pnlStudents,pnlTeachers,pnlCourse,pnlCertificate,pnlClass,pnlSubjects,
            pnlExam,pnlGrade,pnlTeaching});
        showJPanel(new AdminJPanel());
    }//GEN-LAST:event_pnlAdminMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbAdmin;
    private javax.swing.JLabel lbCertificate;
    private javax.swing.JLabel lbClass;
    private javax.swing.JLabel lbCourse;
    private javax.swing.JLabel lbExam;
    private javax.swing.JLabel lbGrade;
    private javax.swing.JLabel lbStudents;
    private javax.swing.JLabel lbSubjects;
    private javax.swing.JLabel lbTeachers;
    private javax.swing.JLabel lbTeaching;
    private javax.swing.JPanel pnlAdmin;
    private javax.swing.JPanel pnlBlueMenu;
    private javax.swing.JPanel pnlCertificate;
    private javax.swing.JPanel pnlClass;
    private javax.swing.JPanel pnlCourse;
    private javax.swing.JPanel pnlExam;
    private javax.swing.JPanel pnlGrade;
    private javax.swing.JPanel pnlStudents;
    private javax.swing.JPanel pnlSubjects;
    private javax.swing.JPanel pnlTeachers;
    private javax.swing.JPanel pnlTeaching;
    private javax.swing.JPanel pnlView;
    // End of variables declaration//GEN-END:variables
}