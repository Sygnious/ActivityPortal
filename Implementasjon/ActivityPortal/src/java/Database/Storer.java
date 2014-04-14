/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Database;
import ProblemDomainModel.*;

import java.sql.*;

/**
 *
 * @author Sygnious
 * 
 * Class that handles stores and changes on database.
 * For the prototype, only storing of activity participation, deleting of
 * activity participation, and changes in user details are necessary
 * Adding or deleting friends may also be implemented
 */
public class Storer {
    
    private static Connection con;
    private static PreparedStatement stm; 
    
    // Stores new user details in database, overwrites old.
    public static void storeNewUserDetails(User user) throws Exception{
        try{
            con = ConnectionHandler.openConnection();
            String statementString = "UPDATE person SET first_name = ?, last_name = ?, age = ?, address = ?, post_id = ? WHERE person_id = ?";
            stm = con.prepareStatement(statementString);
            stm.setString(1, user.getFirstName());
            stm.setString(2, user.getSurName());
            stm.setInt(3, user.getAge());
            stm.setString(4, user.getAddress());
            stm.setInt(5, user.getPostAddress().getPostId());
            stm.setInt(6, user.getUserId());
            stm.executeUpdate();
        } catch (Exception e){
            ConnectionHandler.printError(e, "Storer.storeNewUserDetails()");
            throw e;
        } finally {
            ConnectionHandler.closeStatement(stm);
            ConnectionHandler.closeConnection(con);
        }
    }
    
    // Connect and disconnect methods, for the connections tables.
    // WARNING: Methods will not request data from database to check
    // if what they try to store already exists, or try to delete doesn't exist
    // This control is to be handled by the webpages and the Classes in the 
    // Controller-package, by rendering store/delete buttons based on existance.
    
    // Connect user to activity
    public static void storeUserToActivity(User user, Activity activity) throws Exception{
        try{
            con = ConnectionHandler.openConnection();
            String statementString ="INSERT INTO activity_person VALUES(?, ?)";
            stm = con.prepareStatement(statementString);
            stm.setInt(1, activity.getActivityId());
            stm.setInt(2, user.getUserId());
            stm.execute();
        } catch (Exception e){
            ConnectionHandler.printError(e, "Storer.storeUserToActivity");
            throw e;
        } finally {
            ConnectionHandler.closeStatement(stm);
            ConnectionHandler.closeConnection(con);
        }
    }
    
    // Disconnect user to activity
    public static void deleteUserFromActivity(User user, Activity activity) throws Exception{
        try{
            con = ConnectionHandler.openConnection();
            String statementString ="DELETE FROM activity_person WHERE activity_id = ? AND person_id = ?";
            stm = con.prepareStatement(statementString);
            stm.setInt(1, activity.getActivityId());
            stm.setInt(2, user.getUserId());
            stm.execute();
        } catch (Exception e){
            ConnectionHandler.printError(e, "Storer.deleteUserFromActivity");
            throw e;
        } finally {
            ConnectionHandler.closeStatement(stm);
            ConnectionHandler.closeConnection(con);
        }
    }
    
    public static void storeFriendOfUser(User user, User friend) throws Exception{
        
    }
    
    public static void deleteFriendOfUser(User user, User friend) throws Exception{
        
    }
}
