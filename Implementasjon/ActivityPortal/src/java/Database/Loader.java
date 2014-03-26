/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Database;
import ProblemDomainModel.*;

import java.util.ArrayList;
import java.sql.*;



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
    private static final String demoDate =  "2014-03-01"; // For a full implementation, should be replaced by today's date
    
    // Single load methods.
    // Main purpose is currently for testing the Initiator class, but could
    // otherwise be useful for current or later implementation
    
    // getTableSize gets the Count(*) from specified table.
    public static int getTableSize(String tableName) throws Exception {
        // TODO: Decide how to handle Exceptions
        int result;
        con = null;
        stm = null;
        res = null;
        try{
            con = ConnectionHandler.openConnection();
            String stmString = "SELECT COUNT(*) FROM "+tableName;
            stm = con.prepareStatement(stmString);
            res = stm.executeQuery();
            res.next(); 
            result = res.getInt(1);
        } catch (Exception e){
            ConnectionHandler.printError(e, "Loader.getTableSize()");
            throw e;
        } finally {
            ConnectionHandler.closeAll(res, stm, con);
        }
        return result;
    }
    
    // User loads
    public static User loadSingleUserOnID(int userID){
        
        return null;
    }
    
    // Activity loads
    
    public static ArrayList<Activity> loadAllActivities() throws Exception{
        ArrayList<Activity> result = new ArrayList();
        Activity instance;
        
        try{
            con = ConnectionHandler.openConnection();
            String stmString = "SELECT FROM activities WHERE date > ?";
            
            /*
            * Code section for implementing today's date.
            */
            stm = con.prepareStatement(stmString);
            stm.setString(1, demoDate);
            res = stm.executeQuery();
            
            while (res.next()){
                instance = new Activity(res.getInt(1), res.getString(2), res.getString(3), res.getInt(4));
                
            }
            
        } catch (Exception e){
            ConnectionHandler.printError(e, "Loader.loadAllActivities()");
            throw e;
        } finally {
            ConnectionHandler.closeAll(res, stm, con);
        }
        
        
        return null;
    }
    
    public static Activity loadSingleActivityOnID(int userID){
        
        return null;
    }
    
    
}
