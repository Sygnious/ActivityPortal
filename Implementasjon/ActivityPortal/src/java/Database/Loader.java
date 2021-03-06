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
    private static final String demoDate =  "2014-03-01"; // For a full implementation, should be replaced by today's date;
    
    // Single load methods.
    // Main purpose is currently for testing the Initiator class, but could
    // otherwise be useful for current or later implementation
    
    // getTableSize gets the Count(*) from specified table.
    public static int getTableSize(String tableName) throws Exception {
        // TODO: Decide how to handle Exceptions
        int result;
        try{
            con = ConnectionHandler.openConnection();
            // Executing query
            String stmString = "SELECT COUNT(*) FROM "+tableName;
            stm = con.prepareStatement(stmString);
            res = stm.executeQuery();
            res.next(); 
            // Getting results
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
    
    public static ArrayList<User> loadAllUsersExceptOwn(int userID) throws Exception{ // TODO: Implement
        ArrayList<User> result = new ArrayList();
        try{
            con = ConnectionHandler.openConnection();
            // Executing query
            String stmString = "SELECT * FROM person WHERE person_id <> ? ORDER BY last_name";
            stm = con.prepareStatement(stmString);
            stm.setInt(1, userID);
            res = stm.executeQuery();
            while(res.next()){
                result.add(new User(res.getInt(1), res.getString(2), res.getString(3), res.getInt(4), null, null)); // Skipping address and postAdress
            }
        } catch (Exception e){
            ConnectionHandler.printError(e, "Loader.loadAllUsersExceptOwn");
            throw e;
        } finally {
            ConnectionHandler.closeAll(res, stm, con);
        }
        return result;
    }
    
    public static ArrayList<User> loadUsersOnSearchExceptOwn(int userID, String keyWord) throws Exception{
        ArrayList<User> resultList = new ArrayList();
        try{
            con = ConnectionHandler.openConnection();
            //Executing query
            String stmString = "SELECT * FROM person WHERE person_id <> ? AND (first_name LIKE ? OR last_name LIKE ?)"; //TODO: Improve SQL sentence so that full name will work out
            stm = con.prepareStatement(stmString);
            stm.setInt(1, userID);
            stm.setString(2, "%"+keyWord+"%");
            stm.setString(3, "%"+keyWord+"%");
            
            res = stm.executeQuery();
            while(res.next()){
                resultList.add(new User(res.getInt(1), res.getString(2), res.getString(3), res.getInt(4), res.getString(5), null));
            }
        } catch (Exception e){
            ConnectionHandler.printError(e, "Loader.loadUsersOnSearchExceptOwn");
            throw e;
        } finally {
            ConnectionHandler.closeAll(res, stm, con);
        }
        return resultList;
    }
    
    
    
    public static ArrayList<User> loadAllUserFriends(int userID) throws Exception{ // TODO: Implement
        ArrayList<User> result = new ArrayList();
        try{
            con = ConnectionHandler.openConnection();
            // Executing query
            String stmString = "SELECT a.* FROM person a JOIN friends b ON (a.person_id = b.friend_id AND b.person_id = ?) ORDER BY a.last_name";
            stm = con.prepareStatement(stmString);
            stm.setInt(1, userID);
            res = stm.executeQuery();
            while(res.next()){
                result.add(new User(res.getInt(1), res.getString(2), res.getString(3), res.getInt(4), null, null)); // Skipping address and postAdress
            }
        } catch (Exception e){
            ConnectionHandler.printError(e, "Loader.loadAllUserFriends");
            throw e;
        } finally {
            ConnectionHandler.closeAll(res, stm, con);
        }
        return result;
    }
    
    public static User loadSingleUserOnID(int userID) throws Exception{
        User result = null;
        
        try{
            con = ConnectionHandler.openConnection();
            // Executing query for single user
            String stmString = "SELECT a.*, b.post_address FROM person a JOIN post b ON (a.post_id = b.post_id AND a.person_id =?)"; //TODO: Users with NULL post will suffer
            stm = con.prepareStatement(stmString);
            stm.setInt(1, userID);
            res = stm.executeQuery();
            
            // 1
            // Loading details of single user
            Post post;
            res.next();
            if (res.getString(7)==null){ //If mail is not attached, column 7 will have null-value. Using column 7 which is a string
                    post = null;
                } else {
                    post = new Post(res.getInt(6), res.getString(7));
                }
            result = new User(res.getInt(1), res.getString(2), res.getString(3), res.getInt(4), res.getString(5), post);
            res.close();
            stm.close();
            
            // 2
            // Executing query for interests the user has
            stmString = "SELECT a.interest_name FROM interest a JOIN person_interest b ON (a.interest_id = b.interest_id AND b.person_id = ?)";
            stm = con.prepareStatement(stmString);
            stm.setInt(1, userID);
            res = stm.executeQuery();
            // Loading details into Interest List
            while (res.next()){
                result.insertInterest(res.getString(1));
            }
            res.close();
            stm.close();
            
            // 3
            // Executing query for activities user participates in
            stmString = "SELECT a.* FROM activity a JOIN activity_person b ON (a.activity_id = b.activity_id AND b.person_id = ?) ORDER BY activity_date";
            stm = con.prepareStatement(stmString);
            stm.setInt(1, userID);
            res = stm.executeQuery();
            // Loading details into User PartActsList
            Activity instance;
            while (res.next()){//TODO: Handle NULL-values
                instance = new Activity(res.getInt(1), res.getString(2), res.getString(3), res.getInt(4));
                instance.setDate(instance.convertToGregorian(res.getString(5)));
                result.insertActivity(instance);
            }
            res.close();
            stm.close();
            
            // 4
            // Executing query for friends connected to user
            stmString = "SELECT a.* FROM person a JOIN friends b ON (a.person_id = b.friend_id AND b.person_id = ?) ORDER BY a.last_name";
            stm = con.prepareStatement(stmString);
            stm.setInt(1, userID);
            res = stm.executeQuery();
            // Loading details into User friends list 
            while(res.next()){
                //Skipping address and postAdress, those details may very well be loaded for a person when closely inspecting
                result.insertFriend(new User(res.getInt(1), res.getString(2), res.getString(3), res.getInt(4), null, null));
            }
        } catch (Exception e){
            ConnectionHandler.printError(e, "Loader.loadSingleUserOnID()");
            throw e;
        }  finally {
            ConnectionHandler.closeAll(res, stm, con);
        }
        
        
        return result;
    }
    
    // Activity loads
    
    public static ArrayList<Activity> loadAllActivities() throws Exception{
        ArrayList<Activity> resultList = new ArrayList();
        Activity instance;
        
        try{
            con = ConnectionHandler.openConnection();
            // Executing query for all activities
            String stmString = "SELECT * FROM activity WHERE activity_date > ? ORDER BY activity_date";
            
            /*
            * Code section for implementing today's date.
            */
            stm = con.prepareStatement(stmString);
            stm.setString(1, demoDate);
            res = stm.executeQuery();
            
            // Loading Activity List
            while (res.next()){ //TODO: Handle NULL-values
                instance = new Activity(res.getInt(1), res.getString(2), res.getString(3), res.getInt(4));
                instance.setDate(instance.convertToGregorian(res.getString(5)));
                resultList.add(instance);
            }
            
        } catch (Exception e){
            ConnectionHandler.printError(e, "Loader.loadAllActivities()");
            throw e;
        } finally {
            ConnectionHandler.closeAll(res, stm, con);
        }
        return resultList;
    }
    
    public static Activity loadSingleActivityOnID(int activityID) throws Exception{
        Activity result = null;
        
        try{
            con = ConnectionHandler.openConnection();
            // Executing query for single activity
            String stmString = "SELECT * FROM activity WHERE activity_id = ?";
            stm = con.prepareStatement(stmString);
            stm.setInt(1, activityID);
            res = stm.executeQuery();
            
            // Loading details of single activity
            res.next();
            result = new Activity(res.getInt(1), res.getString(2), res.getString(3), res.getInt(4));//TODO: Handle NULL-values
            result.setDate(result.convertToGregorian(res.getString(5)));
            result.setImageName(res.getString(6));
            res.close();
            stm.close();
            // Execuring query for interests covered by activity
            stmString = "SELECT a.interest_name FROM interest a JOIN activity_interest b ON (a.interest_id = b.interest_id AND b.activity_id = ?)";
            stm = con.prepareStatement(stmString);
            stm.setInt(1, activityID);
            res = stm.executeQuery();
            // Loading details into Interest List
            while (res.next()){
                result.insertInterest(res.getString(1));
            }
            res.close();
            stm.close();
            // Execuring query for participating Users
            stmString = "SELECT a.*, b.post_address FROM person a LEFT OUTER JOIN post b ON (a.post_id = b.post_id) JOIN activity_person c ON (a.person_id = c.person_id AND c.activity_id = ?)";
            stm = con.prepareStatement(stmString);
            stm.setInt(1, activityID);
            res = stm.executeQuery();
            // Loading details into User List
            Post post;
            while (res.next()){//TODO: Handle NULL-values
                if (res.getString(7)==null){ //If mail is not attached, column 7 will have null-value. Using column 7 which is a string
                    post = null;
                } else {
                    post = new Post(res.getInt(6), res.getString(7));
                }
                result.insertUser(new User(res.getInt(1), res.getString(2), res.getString(3), res.getInt(4), res.getString(5), post));
            }
        } catch (Exception e){
            ConnectionHandler.printError(e, "Loader.loadSingleActivityOnID()");
            throw e;
        }  finally {
            ConnectionHandler.closeAll(res, stm, con);
        }
        
        return result;
    }
    
    public static ArrayList<Activity> loadActivitiesOnInterest(String interest) throws Exception{
        ArrayList<Activity> resultList = new ArrayList();
        Activity instance;
        
        try{
            con = ConnectionHandler.openConnection();
            // Executing query for activities connected to one interest
            String stmString = "SELECT a.* FROM activity a JOIN activity_interest b ON (a.activity_id=b.activity_id AND a.activity_date> ?) JOIN interest c ON (b.interest_id = c.interest_id AND c.interest_name = ?) ORDER BY a.activity_date";
            
            /*
            * Code section for implementing today's date.
            */
            stm = con.prepareStatement(stmString);
            stm.setString(1, demoDate);
            stm.setString(2, interest);
            res = stm.executeQuery();
            
            // Loading Activity List
            while (res.next()){ //TODO: Handle NULL-values
                instance = new Activity(res.getInt(1), res.getString(2), res.getString(3), res.getInt(4));
                instance.setDate(instance.convertToGregorian(res.getString(5)));
                resultList.add(instance);
            }
            
        } catch (Exception e){
            ConnectionHandler.printError(e, "Loader.loadActivitiesOnInterest()");
            throw e;
        } finally {
            ConnectionHandler.closeAll(res, stm, con);
        }
        return resultList;
    }
    
    public static ArrayList<Activity> loadActivitiesOnSearch(String keyWord) throws Exception{
        ArrayList<Activity> resultList = new ArrayList();
        Activity instance;
        
        try{
            con = ConnectionHandler.openConnection();
            // Executing query for activities connected to one interest
            String stmString = "SELECT activity.* FROM activity WHERE activity_date > ? AND (activity_name LIKE ? OR activity_description LIKE ?) ORDER BY activity_date";
            
            /*
            * Code section for implementing today's date.
            */
            stm = con.prepareStatement(stmString);
            stm.setString(1, demoDate);
            stm.setString(2, "%"+keyWord+"%");
            stm.setString(3, "%"+keyWord+"%");
            res = stm.executeQuery();
            
            
            // Loading Activity List
            while (res.next()){ //TODO: Handle NULL-values
                instance = new Activity(res.getInt(1), res.getString(2), res.getString(3), res.getInt(4));
                instance.setDate(instance.convertToGregorian(res.getString(5)));
                resultList.add(instance);
            }
            
        } catch (Exception e){
            ConnectionHandler.printError(e, "Loader.loadActivitiesOnSearch()");
            throw e;
        } finally {
            ConnectionHandler.closeAll(res, stm, con);
        }
        return resultList;
    }
    
    public static ArrayList<Activity> loadActivitiesOnOneFriend(int friendId) throws Exception{
        ArrayList<Activity> resultList = new ArrayList();
        Activity instance;
        
        try {
            con = ConnectionHandler.openConnection();
            // Executing query for activities connected to friends
            String stmString = "SELECT DISTINCT a.* FROM activity a JOIN activity_person b ON (a.activity_id = b.activity_id AND b.person_id = ?) ORDER BY a.activity_date";
            stm = con.prepareStatement(stmString);
            stm.setInt(1, friendId);
            res = stm.executeQuery();
            
            // Loading Activity List
            while(res.next()){
                instance = new Activity(res.getInt(1), res.getString(2), res.getString(3), res.getInt(4));
                instance.setDate(instance.convertToGregorian(res.getString(5)));
                resultList.add(instance);
            }
        } catch (Exception e){
            ConnectionHandler.printError(e, "Loader.loadActivitiesOnOneFriend");
            throw e;
        } finally {
            ConnectionHandler.closeAll(res, stm, con);
        }
        return resultList;
    }
    
    public static ArrayList<Activity> loadActivitiesOnAllFriends(int userId) throws Exception{
        ArrayList<Activity> resultList = new ArrayList();
        Activity instance;
        
        try {
            con = ConnectionHandler.openConnection();
            // Executing query for activities connected to friends
            String stmString = "SELECT DISTINCT a.* FROM activity a JOIN activity_person b ON (a.activity_id = b.activity_id) JOIN friends c ON (b.person_id=c.friend_id AND c.person_id = ?) ORDER BY a.activity_date";
            stm = con.prepareStatement(stmString);
            stm.setInt(1, userId);
            res = stm.executeQuery();
            
            // Loading Activity List
            while(res.next()){
                instance = new Activity(res.getInt(1), res.getString(2), res.getString(3), res.getInt(4));
                instance.setDate(instance.convertToGregorian(res.getString(5)));
                resultList.add(instance);
            }
        } catch (Exception e){
            ConnectionHandler.printError(e, "Loader.loadActivitiesOnAllFriends");
            throw e;
        } finally {
            ConnectionHandler.closeAll(res, stm, con);
        }
        return resultList;
    }
    
    // Other loads
    
    public static ArrayList<String> loadAllInterestsNamesOnly() throws Exception{
        ArrayList<String> resultList = new ArrayList();
        
        try{
            con = ConnectionHandler.openConnection();
            // Executing query for all interests
            String stmString = "SELECT * FROM interest";
            
            /*
            * Code section for implementing today's date.
            */
            stm = con.prepareStatement(stmString);
            res = stm.executeQuery();
            
            while (res.next()){ //TODO: Handle NULL-values
                resultList.add(res.getString(2));
            }
            
        } catch (Exception e){
            ConnectionHandler.printError(e, "Loader.loadAllInterestsNamesOnly()");
            throw e;
        } finally {
            ConnectionHandler.closeAll(res, stm, con);
        }
        return resultList;
    }
    
    /*
    // Used to determine if a specified User participates in an Activity, 
    // by loading from Activity_Person table with specified IDs
    // Expected return values: 0 if not in table, 1 if in table.
    // Will be used for rendering between "Sign up" or "Cancel sign up" buttons
    // on the web pages for activity details.
    public static int loadIfParticipating() throws Exception{
        
        return -1;
    }*/
    
}
