package net.nexus.sql;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
/**
 *
 * @author sqlitetutorial.net
 */
public class SQL {
	private static Connection conn;
	
     /**
     * Connect to database
     */
    public static void connect() {
        conn = null;
        try {
            // db parameters
        	File dir = new File("plugins/Nexus/database");
        	if(!dir.exists()) {
        		dir.mkdirs();
        	}
        	
            String url = "jdbc:sqlite:plugins/Nexus/database/nexus.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            
            System.out.println("Connection to SQLite has been established.");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Disconnects the Database
     */
    public static void disconnect() {
    	 try {
             if (conn != null) {
                 conn.close();
             }
         } catch (SQLException ex) {
             System.out.println(ex.getMessage());
         }
    }
    
    /**
     * SQL Statement
     *
     */
    public static void onUpdate(String sql) {        
    	try {
           Statement stmt = conn.createStatement();
	       stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * SQL Query
     */
    public static ResultSet onQuery(String sql){
        try {
             Statement stmt  = conn.createStatement();
             return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
