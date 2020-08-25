package com.cusc.certificategenerationsystem.db;

import static com.cusc.certificategenerationsystem.db.DatabaseConnection.conn;
import static com.cusc.certificategenerationsystem.view.LoginJFrame.username;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PC_HP
 */
public class DatabaseProcess {
    public static DefaultTableModel tableModel;
    public static void loadData(String sql, JTable tbl) {
        try {
            DatabaseConnection.connectSQL();
            Statement stt = conn.createStatement();
            ResultSet rs = stt.executeQuery(sql);
            
            ResultSetMetaData metadata = rs.getMetaData();
            int numberColumns = metadata.getColumnCount();
            ArrayList<String> arrColumns = new ArrayList<>();
            for (int i = 1; i <= numberColumns; i++) {
                arrColumns.add(metadata.getColumnName(i));
            }
            tableModel.setColumnIdentifiers(arrColumns.toArray());
            ArrayList<String> arrRow = new ArrayList<>();
            while(rs.next()){
                for (int i = 1; i <= numberColumns; i++) {
                    arrRow.add(rs.getString(i));
                }
                tableModel.addRow(arrRow.toArray());
                arrRow.clear();
            }
            
            tbl.setModel(tableModel);
            rs.close();
            stt.close();
            DatabaseConnection.conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Data loaded into table failed!", "Message", 1);
        }
    }

    public static int updateStudent(String sql1, String sql2, String sql3){
        int mess=0;    
        try {
                DatabaseConnection.connectSQL();
                DatabaseConnection.conn.setAutoCommit(false);
                Statement state = conn.createStatement();
                int row1 = state.executeUpdate(sql1);
                int row2 = state.executeUpdate(sql2);
                int row3 = state.executeUpdate(sql3);
                mess=row1*row2*row3;
                DatabaseConnection.conn.commit();
                DatabaseConnection.conn.setAutoCommit(true);
                state.close();
                conn.close();
            } catch (SQLException ex) {
                try {
                    JOptionPane.showMessageDialog(null,"Information student is being stored and managed!" , "Message", 1);
                    DatabaseConnection.conn.rollback();
                } catch (SQLException ex1) {
                    JOptionPane.showMessageDialog(null,"Information student is being stored and managed!");
                }
            }
            return mess;
        } 

    public static int updateStudent2(String sql1, String sql2) {
        Statement state=null;
        int row=0;
        try {
            DatabaseConnection.connectSQL();
            DatabaseConnection.conn.setAutoCommit(false);
            state = conn.createStatement();
            int row1 = state.executeUpdate(sql1);
            int row2 = state.executeUpdate(sql2);
            row = row1*row2;
            DatabaseConnection.conn.commit();
            DatabaseConnection.conn.setAutoCommit(true);
            state.close();
            conn.close();
        } catch (SQLException ex) {
            try {
                JOptionPane.showMessageDialog(null, "Student have already signed up for this course", "Message", 1);
                DatabaseConnection.conn.rollback();
                state.close();
                conn.close();
            } catch (SQLException ex1) {
                JOptionPane.showMessageDialog(null, ex1.getMessage());
            }
        }
        return row;
    }
    public static void loadDataCombobox(String query, JComboBox cbb, String field){
        try {
            DatabaseConnection.connectSQL();
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet result = pst.executeQuery();
            while(result.next()){
                cbb.addItem(result.getString(field));
            }
            result.close();
            pst.close();
            DatabaseConnection.conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Data loaded into combobox failed!", "Message", 1);
        }
    }

    public static void loadDataTextfield(String query, JComboBox cbb, JTextField txt, String field){
        try {
            DatabaseConnection.connectSQL();
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, (String) cbb.getSelectedItem());
            ResultSet result = pst.executeQuery();
            if(result.next()){
                txt.setText(result.getString(field));
            }else{
                txt.setText(null);
            }
            result.close();
            pst.close();
            DatabaseConnection.conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Data loaded into textfield failed!", "Message", 1);
        }
    }
    
    public static String getID(String procedure){
        String ID = null;
        try {
            DatabaseConnection.connectSQL();
            Statement stt = conn.createStatement();
            ResultSet result = stt.executeQuery(procedure);
            while(result.next()){
                ID=result.getString(1);
            }
            stt.close();
            result.close();
            DatabaseConnection.conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Get ID failed!", "Message", 1);
        }
        return ID;
    }
    
    public static ResultSet displayInformation(String sql){
        ResultSet rsDisplay=null;
        try {
            DatabaseConnection.connectSQL();
            Statement state = conn.createStatement();
            rsDisplay = state.executeQuery(sql);
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Detailed display failed!", "Message", 1);
        }
        return rsDisplay;
    }    
    
    public static int updateData(String sql){
        int mess=0;
        try {
            DatabaseConnection.connectSQL();
            Statement state = conn.createStatement();
            mess = state.executeUpdate(sql);
            state.close();
            DatabaseConnection.conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Data already exists in the system, please try again!", "Message", 1);
        }
        return mess;
    }
    
    public static String checkRole(){
        String role=null;
        try {
            DatabaseConnection.connectSQL();
            Statement stt = DatabaseConnection.conn.createStatement();
            String sql= "SELECT * FROM Admin_CertificateCell WHERE Username='"+username+"'";
            ResultSet rs = stt.executeQuery(sql);
            while(rs.next()){
                role=rs.getString("Role");
            }
            rs.close();
            stt.close();
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return role;
    }
    

}
