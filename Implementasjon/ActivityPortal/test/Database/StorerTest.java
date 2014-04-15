/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Database;
import ProblemDomainModel.*;

import java.sql.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Sygnious
 */
public class StorerTest {
    
    // Since Storer is independent of Loader, the tests will also act independent
    // of Loader.
    private Connection con;
    private PreparedStatement stm; 
    private ResultSet res;
    
    public StorerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
        Initiator.resetDatabase(); 
        // So that changes from storerTest does not affect any other uses, like
        // testing webpages
    }
    
    @Before
    public void setUp() {
        // Reset must be called every time, since changes in database may occur
        Initiator.resetDatabase();
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testStoreNewUserDetails() throws Exception{
        System.out.println("storeNewUserDetails");
        // Will overwrite User with ID=1, where name is data is Torbjørn Langland
        
        // Test 1: With all new details.
        User newUser = new User(1, "TorbjørnYo","LanglandYi", 90, "Hoftengata14", new Post(7051, "Trondheim"));
        // Execute
        Storer.storeNewUserDetails(newUser);
        // Load same from database in order to control the details:
        try {
            con = ConnectionHandler.openConnection();
            stm = con.prepareStatement("SELECT * FROM person WHERE person_id = 1");
            res = stm.executeQuery();
            res.next();
        
            // Note: Trondheim is written in manually. No changes in post table should ever occour, only the foreign key in person table.
            User instance = new User(res.getInt(1), res.getString(2), res.getString(3), res.getInt(4), res.getString(5), new Post(res.getInt(6), "Trondheim")); //TODO: Fix this issue in LOADER
            
            assertEquals("New userId is wrong", 1, instance.getUserId());
            assertEquals("New firstName is wrong", "TorbjørnYo", instance.getFirstName());
            assertEquals("New surName is wrong", "LanglandYi", instance.getSurName());
            assertEquals("New age is wrong", 90, instance.getAge());
            assertEquals("New Address is wrong", "Hoftengata14", instance.getAddress());
            assertEquals("New postId is wrong", 7051, instance.getPostAddress().getPostId());

            // Test 2: With null in post
            res.close();
            stm.close();
            
            fail("Must implement useful loading logic for NULL post in Loader");
        } catch (Exception e){
            ConnectionHandler.printError(e, "testStoreNewUserDetails()");
            fail("Exception caused test to fail");
        } finally {
            ConnectionHandler.closeAll(res, stm, con);
        }
        
    }
    
    @Test
    public void testStoreUserToActivity() throws Exception{
        System.out.println("storeUserToActivity");
        
        // Using existing user with id 2, and existing activity with id 9.
        // These two are not connected in activity_person.
        User user = new User(2, null, null, 100, null, null); //Only ID-details matters
        Activity activity = new Activity(9, null, null, 1);
        Storer.storeUserToActivity(user, activity);
        
        // Load instance from database in order to control the control that store was successful
        try {
            con = ConnectionHandler.openConnection();
            
            // Test divided into two: First testing length (expected 1 when existing, otherwise it will be 0).
            // If trying to test contents, and there are none, res.getInt(1) and res.getInt(2) will cause exception
            // To avoid this, the test will first control table length, and fail in assertEquals if storing has failed.
            stm = con.prepareStatement("SELECT COUNT(*) FROM activity_person WHERE activity_id = 9 AND person_id = 2");
            res = stm.executeQuery();
            res.next();
            int expectedNumberOfElements = res.getInt(1);
            
            assertEquals("Storage has gone wrong, count(*) is 0", 1, expectedNumberOfElements);
            
            res.close();
            stm.close();
            stm = con.prepareStatement("SELECT * FROM activity_person WHERE activity_id = 9 AND person_id = 2");
            res = stm.executeQuery();
            res.next();
            
            int expectedActivityID = res.getInt(1);
            int expectedPersonID = res.getInt(2);
            
            assertEquals("Stored activity_id is wrong", 9, expectedActivityID);
            assertEquals("Stored person_id is wrong", 2, expectedPersonID);
            
        } catch (Exception e){
            ConnectionHandler.printError(e, "testStoreUserToActivity()");
            fail("Exception caused test to fail");
        } finally {
            ConnectionHandler.closeAll(res, stm, con);
        }
    }
    
    @Test
    public void testDeleteUserFromActivity() throws Exception{
        System.out.println("deleteUserFromActivity");
        
        // Using existing user with id 2, and existing activity with id 9.
        // These two are will be connected in activity_person 
        // by using standard SQL INSERT command.
        // deleteUserFromActivity will then be called from Storer in order
        // to delete instance from 
        User user = new User(2, null, null, 100, null, null); //Only ID-details matters
        Activity activity = new Activity(9, null, null, 1);
        int count1; // Used for tracking
        int count2;
        int count3;
        
        // Load instance from database in order to control the control that store was successful
        try {
            con = ConnectionHandler.openConnection();
            
            // Set up:
            stm = con.prepareStatement("INSERT INTO activity_person VALUES(9, 2)");
            stm.execute();
            stm.close();
            
            stm = con.prepareStatement("SELECT COUNT(*) FROM activity_person WHERE activity_id = 9 AND person_id = 2");
            res = stm.executeQuery();
            res.next();
            count1 = res.getInt(1);
            res.close();
            stm.close();
            
            //Execute
            Storer.deleteUserFromActivity(user, activity);
            
            stm = con.prepareStatement("SELECT COUNT(*) FROM activity_person WHERE activity_id = 9 AND person_id = 2");
            res = stm.executeQuery();
            res.next();
            count2 = res.getInt(1);
            res.close();
            stm.close();
            
            //Additional execute, with non-existing data. No significant change should occur
            Storer.deleteUserFromActivity(user, activity);
            
            stm = con.prepareStatement("SELECT COUNT(*) FROM activity_person WHERE activity_id = 9 AND person_id = 2");
            res = stm.executeQuery();
            res.next();
            count3 = res.getInt(1);
            
            assertEquals("Deletion does not perform correctly, count2 is not 0", 0, count2);
            assertEquals("Deletion does not perform correctly (non-existing data), count3 is not 0", 0, count3);
            assertEquals("Deletion does not perform correclty, count2 is not 1 less than count1", count2, count1-1);
            
            //Deleting non-existing entry is not tested, since no exception or change happens in database.
        } catch (Exception e){
            ConnectionHandler.printError(e, "testDeleteUserFromActivity()");
            fail("Exception caused test to fail");
        } finally {
            ConnectionHandler.closeAll(res, stm, con);
        }
    }
    
    @Test
    public void testStoreFriendOfUser() throws Exception{
        System.out.println("storeFriendOfUser");
        
        // Using existing user with id 11, and existing friend with id 3.
        // These two are not connected in friends.
        User user = new User(11, null, null, 100, null, null); //Only ID-details matters
        User friend = new User(3, null, null, 100, null, null);
        Storer.storeFriendOfUser(user, friend);
        
        // Load instance from database in order to control that store was successful
        try {
            con = ConnectionHandler.openConnection();
            
            // Test divided into two: First testing length (expected 1 when existing, otherwise it will be 0).
            // If trying to test contents, and there are none, res.getInt(1) and res.getInt(2) will cause exception
            // To avoid this, the test will first control table length, and fail in assertEquals if storing has failed.
            stm = con.prepareStatement("SELECT COUNT(*) FROM friends WHERE person_id = 11 AND friend_id = 3");
            res = stm.executeQuery();
            res.next();
            int expectedNumberOfElements = res.getInt(1);
            
            assertEquals("Storage has gone wrong, count(*) is 0", 1, expectedNumberOfElements);
            
            res.close();
            stm.close();
            stm = con.prepareStatement("SELECT * FROM friends WHERE person_id = 11 AND friend_id = 3");
            res = stm.executeQuery();
            res.next();
            
            int expectedPersonID = res.getInt(1);
            int expectedFriendID = res.getInt(2);
            
            assertEquals("Stored person_id is wrong", 11, expectedPersonID);
            assertEquals("Stored friend_id is wrong", 3, expectedFriendID);
            
        } catch (Exception e){
            ConnectionHandler.printError(e, "testStoreFriendOfUser()");
            fail("Exception caused test to fail");
        } finally {
            ConnectionHandler.closeAll(res, stm, con);
        }
        
    }
    
    @Test
    public void testDeleteFriendOfUser() throws Exception{
        System.out.println("deleteFriendOfUser");
        
        // Using existing user with id 11, and existing friend with id 6.
        // These two will be connected in activity_person 
        // by using standard SQL INSERT command.
        // deleteUserFromActivity will then be called from Storer in order
        // to delete instance from 
        User user = new User(11, null, null, 100, null, null); //Only ID-details matters
        User friend = new User(6, null, null, 100, null, null);
        int count1; // Used for tracking
        int count2;
        int count3;
        
        // Load instance from database in order to control the control that store was successful
        try {
            con = ConnectionHandler.openConnection();
            
            // Set up:
            stm = con.prepareStatement("INSERT INTO friends VALUES(11, 6)");
            stm.execute();
            stm.close();
            
            stm = con.prepareStatement("SELECT COUNT(*) FROM friends WHERE person_id =11 AND friend_id = 6");
            res = stm.executeQuery();
            res.next();
            count1 = res.getInt(1);
            res.close();
            stm.close();
            
            //Execute
            Storer.deleteFriendOfUser(user, friend);
            
            stm = con.prepareStatement("SELECT COUNT(*) FROM friends WHERE person_id =11 AND friend_id = 6");
            res = stm.executeQuery();
            res.next();
            count2 = res.getInt(1);
            res.close();
            stm.close();
            
            //Additional execute, with non-existing data. No significant change should occur
            Storer.deleteFriendOfUser(user, friend);
            
            stm = con.prepareStatement("SELECT COUNT(*) FROM friends WHERE person_id =11 AND friend_id = 6");
            res = stm.executeQuery();
            res.next();
            count3 = res.getInt(1);
            
            assertEquals("Deletion does not perform correctly, count2 is not 0", 0, count2);
            assertEquals("Deletion does not perform correctly (non-existing data), count3 is not 0", 0, count3);
            assertEquals("Deletion does not perform correclty, count2 is not 1 less than count1", count2, count1-1);
            
            //Deleting non-existing entry is not tested, since no exception or change happens in database.
        } catch (Exception e){
            ConnectionHandler.printError(e, "testDeleteFriendOfUser()");
            fail("Exception caused test to fail");
        } finally {
            ConnectionHandler.closeAll(res, stm, con);
        }
        
    }
}
