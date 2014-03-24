/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Database;
import java.sql.*;
import ProblemDomainModel.*;


/**
 *
 * @author Sygnious
 * 
 * Class that handles loads from database
 */
public class Loader {
    private static Connection con;
    private static PreparedStatement stm;
    private static ResultSet res;
    
    
    
    
    // Single load methods.
    // Main purpose is currently for testing the Initiator class, but could
    // otherwise be useful for current or later implementation
    
    // getTableSize gets the Count(*) from specified table.
    public static int getTableSize(String tableName) throws Exception {
        // TODO: Decide how to handle Exceptions
        int result;
        con = ConnectionHandler.openConnection();
        stm = null;
        res = null;
        try{
            String stmString = "SELECT COUNT(*) FROM "+tableName;
            stm = con.prepareStatement(stmString);
            res = stm.executeQuery();
            res.next(); 
            result = res.getInt(1);
        } catch (Exception e){
            ConnectionHandler.printSQLError(e, "Loader.getTableSize()");
            throw e;
        } finally {
            ConnectionHandler.closeAll(res, stm, con);
        }
        return result;
    }
    
    public static User loadSingleUserOnID(int userID){
        
        return null;
    }
    
    public static Activity loadSingleActivityOnID(int userID){
        
        return null;
    }
}
