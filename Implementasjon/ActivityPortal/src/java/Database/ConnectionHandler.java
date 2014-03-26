/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Database;

import java.sql.*;

/**
 *
 * @author Sygnious
 * 
 * Sole purpose is to open and close connection.
 * Will be used by all other classes.
 * Testing in JUnit is not done. Testing of Initiator, Loader and Storer will 
 * imply that ConnectionHandler is working
 */
public class ConnectionHandler {
    private static String databaseDriver = "org.apache.derby.jdbc.ClientDriver";
    private static String databaseName = "jdbc:derby://localhost:1527/ActivityPortal;user=vprg;password=vprg";
    // Databasename is currently static. Use of dynamic databasename is not 
    // expected, only one database is to be used for this prototype project
    
    // Open connection
    
    public static Connection openConnection() throws Exception{
        try{
            Class.forName(databaseDriver);
            Connection con = DriverManager.getConnection(databaseName);
            return con;
        } catch (Exception e){
            printError(e, "openConnection()");
            throw e; //TODO: Concider if this is necessary, since you base this on the book
        }
    }
    
    // Close connection
    
    // Should be used as main, except if sub-methods need to be seperated in 
    // other code, in which case they need to be called separately
    public static void closeAll(ResultSet res, Statement stm, Connection con){
        closeResultSet(res);
        closeStatement(stm);
        closeConnection(con);
    }
    
    // Sub-methods for closing.
    // Three first methods are for executing closing ResultSet, Statement
    // and Connection, but also includes catching of SQLException, allowing
    // the program to continue
    public static void closeResultSet(ResultSet res){
        try {
            if (res!= null &&!res.isClosed()){
                res.close();
            }
        } catch (SQLException e) {
            printError(e, "closeResultSet()");
        }
    }

    
    public static void closeStatement(Statement stm){
        try {
            if (stm != null){
                stm.close();
            }
        } catch (SQLException e){
            printError(e, "closeStatement()");
        }
    }
    
    public static void closeConnection(Connection con){
        try{
            if (con != null){
                con.close();
            }
        } catch (SQLException e){
            printError(e, "closeConnection()");
        }
    }
    
    
    // TODO: Need description of why use the next 3 methods
    public static void rollBack(Connection con){
        try {
            if (con != null && !con.getAutoCommit()){
                con.rollback();
            }
        } catch (SQLException e){
            printError(e, "rollBack()");
        }
    }
    
    public static void setAutoCommit(Connection con){
        try {
            if (con != null && !con.getAutoCommit()){
                con.setAutoCommit(true);
            }
        } catch (SQLException e){
            printError(e, "setAutoCommit()");
        }
    }
    
    public static void printError(Exception e, String message){
        System.err.println("*** Error detected: " + message + ". ***");
        e.printStackTrace(System.err);
    }
    
}
