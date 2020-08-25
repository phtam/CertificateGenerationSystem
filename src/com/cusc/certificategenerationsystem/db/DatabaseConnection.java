package com.cusc.certificategenerationsystem.db;

import static com.cusc.certificategenerationsystem.db.DatabaseConnection.conn;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;


/**
 *
 * @author PC_HP
 */
public class DatabaseConnection {
    public static Connection conn = null;

    public static void connectSQL(){
        try {
            String username = "sa";
            String password = "sa";
            String url = "jdbc:sqlserver://localhost:1433;databaseName=DBCertificate_Generation_System;";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            try {
                conn = java.sql.DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Database connection failed, the port may not match!", "Message", 1);
            } catch(NullPointerException ex){
                JOptionPane.showMessageDialog(null, "Database connection failed, the port may not match!", "Message",1);
            } catch(Exception ex){
                JOptionPane.showMessageDialog(null, "Database connection failed, the port may not match!", "Message",1);
            }
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Database connection failed, the port may not match!", "Message",1);
        } 
    }
}


    
