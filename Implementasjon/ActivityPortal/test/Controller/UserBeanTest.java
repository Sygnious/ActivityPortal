/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;
import ProblemDomainModel.*;
import Database.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

/**
 *
 * @author Sygnious
 */
public class UserBeanTest {
    
    private UserBean instance;
    private User instanceUser;
    private ArrayList<User> instanceUserList;
    private String instanceString; // Many methods will include navigation
    
    public UserBeanTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        Initiator.resetDatabase(); // Since there are single methods that makes changes to database
        instance = new UserBean();
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of logIn method, of class UserBean.
     */
    @Test
    public void testLogIn() throws Exception{
        System.out.println("logIn");
        // Execute
        instanceString = instance.logIn();
        instanceUser = instance.getSingleUser();
        
        // Checking return string
        assertEquals("Incorrect page navigation string", "mainPage", instanceString);
        
        // Checking user name and size of activity table.
        // loadSingleUserOnID already tested in LoaderTest, therefore only a few details should suffice
        assertEquals("Loaded user firstName is wrong", "Torbjørn", instanceUser.getFirstName());
        assertEquals("Loaded user lastName is wrong", "Torbjørn", instanceUser.getFirstName());
        assertEquals("Loaded user partActs size is wrong", 4, instanceUser.getPartActs().size());
        assertEquals("Loaded user friends size is wrong", 6, instanceUser.getFriends().size());
    }
    
    @Test
    public void testLogOut(){
        System.out.println("logOut");
        // Execute
        instanceString = instance.logOut();
        
        // Checking return string
        assertEquals("Incorrect page navigation string", "index", instanceString);
    }
    
    @Test
    public void testReset() throws Exception{
        // Pretty much nothing extra to test in contrast to logOut. 
        // RestartDatabase is activated, but is tested in InitiatorTest
        System.out.println("reset");
        // Execute
        
        instanceString = instance.reset();
        
        // Checking return string
        assertEquals("Incorrect page navigation string", "index", instanceString);
    }
    
    @Test
    public void testShowAllOtherUsers() throws Exception{
        System.out.println("showAllOtherUsers");
        // Set up
        // User ID 1, only required data for interacting with Loader
        // and database.
        instance.setSingleUser(new User(1, null, null, 100, null, null));
        // Execute
        instanceString = instance.showAllOtherUsers();
        instanceUserList = instance.getUserList();
        assertEquals("Incorrect page navigation string", "userList", instanceString); //With test files, number of users are 16
        assertEquals("The expected list size is not correct", 15, instanceUserList.size()); //With test files, number of users are 16
        assertEquals("First instance userID is not correct", 10, instanceUserList.get(0).getUserId());
        assertEquals("First instance firstName is not correct", "U-Known", instanceUserList.get(0).getFirstName());
        assertEquals("First instance surName is not correct", "Anonymious", instanceUserList.get(0).getSurName());
    }
    
    @Test
    public void testSelectUser() throws Exception{
        System.out.println("selectUser");
        // Set up
        
        // Only user_ID is critical for this to work.
        instanceUser = new User(2, null, null, -99, null, null); 
        
        // Execute 1: Should not do anything if parameter ojbect is not of type User
        instanceString = instance.selectUser("This String is a hoax and will not work");
        
        assertNull("instanceString was not null when using wrong object type", instanceString);
        assertNull("User details changed even if object type was wrong for method", instanceUser.getFirstName());//TODO: Dobbelsjekk
        
        // Execute 2: Changes when parameter object is of type User:
        instanceString = instance.selectUser(instanceUser);
        
        // Checking return string
        assertEquals("Incorrect page navigation string", "userOne", instanceString);
        // testLoadSingleActivityOnID already tested in LoaderTest, therefore only a few details should suffice
        assertEquals("Loaded user firstName is wrong", "Tove", instance.getOtherUser().getFirstName());
        assertEquals("Loaded user surName is wrong", "Johansen", instance.getOtherUser().getSurName());
    }
    
    @Test
    public void testAddFriend() throws Exception{
        System.out.println("addFriend");
        // Set up
        // User with ID 2 that has friends with IDs 1, 3, 4 in order 3, 1, 4
        // Friends are sorted by last name. Preparation are done alphabetically.
        // Data may be overwritten from database (except ID), depending on implementation.
        // lastNames must therefore be correct with IDs compared to database, 
        // in order for tests to work.
        instanceUser = new User(2, null, null, 100, null, null);
        instanceUser.insertFriend(new User(3, "Jens", "Johansen", 66, null, null));
        instanceUser.insertFriend(new User(1, "Torbjørn", "Langland", 61, null, null));
        instanceUser.insertFriend(new User(4, "Trine", "Larsen", 72, null, null));
        instance.setSingleUser(instanceUser);
        
        // Test 1: Adding new friend:
        // Execute
        instanceUser = new User(5, "Ludvig", "Hansen", 56, null, null);
        instance.setOtherUser(instanceUser);
        instanceString = instance.addFriend();
        int newSize = instance.getSingleUser().getFriends().size();
        assertEquals("Incorrect page navigation string", "userFriendAdded", instanceString);
        // Testing difference between old and new size, the static number may vary depending on implementation.
        assertEquals("Resulting size in friends list is not increased", 4, newSize);
        instanceUser = instance.getSingleUser().getFriends().get(0); //Should be first activty by given date
        assertEquals("Added friend found in list has wrong ID", 5, instanceUser.getUserId());
        
        // Test 2 Adding already added friend, should cause error and no update
        // Execute
        instanceString = instance.addFriend(); //otherFriend still set to Ludvig Hansen with ID 5. Should cause error
        newSize = instance.getSingleUser().getFriends().size();
        assertEquals("Incorrect page navigation string", "error", instanceString);
        assertEquals("Resulting size in friends activity list has not remained unchanged", 4, newSize);
    }
    
    @Test
    public void testDeleteFriend() throws Exception{
        System.out.println("deleteFriend");
        // Set up
        // User with ID 2 that has friends with IDs 1, 3, 4 in order 1, 4, 3
        // Friends are sorted by last name. Preparation are done alphabetically.
        // Data may be overwritten from database (except ID), depending on implementation.
        // lastNames must therefore be correct with IDs compared to database, 
        // in order for tests to work.
        instanceUser = new User(2, null, null, 100, null, null);
        instanceUser.insertFriend(new User(3, "Jens", "Johansen", 66, null, null));
        instanceUser.insertFriend(new User(1, "Torbjørn", "Langland", 61, null, null));
        instanceUser.insertFriend(new User(4, "Trine", "Larsen", 72, null, null));
        instance.setSingleUser(instanceUser);
        
        // Test 1: Deleting existing friend:
        // Execute
        instanceUser = new User(3, "Jens", "Johansen", 66, null, null);
        instance.setOtherUser(instanceUser);
        instanceString = instance.deleteFriend();
        int newSize = instance.getSingleUser().getFriends().size();
        assertEquals("Incorrect page navigation string", "userFriendDeleted", instanceString);
        // Testing difference between old and new size, the static number may vary depending on implementation.
        assertEquals("Resulting size in friends list is not decreased", 2, newSize);
        instanceUser = instance.getSingleUser().getFriends().get(0); //Should be first activty by given date
        assertEquals("First remaning friend found in list has wrong ID", 1, instanceUser.getUserId());
        
        // Test 2 Deleting non-existing friend, should cause error and no update
        // Execute
        instanceString = instance.deleteFriend(); //otherFriend still set to Tove Johansen with ID 2. Should cause error
        newSize = instance.getSingleUser().getFriends().size();
        assertEquals("Incorrect page navigation string", "error", instanceString);
        assertEquals("Resulting size in friends activity list has not remained unchanged", 2, newSize);
    }
    
    @Test
    public void testGetIsRendered() throws Exception{
        System.out.println("UserBean.getIsRendered");
        // Set up
        instanceUser = new User(1, null, null, 100, null, null);
        ArrayList<User> friends = new ArrayList();
        friends.add(new User(2, null, null, 100, null, null));
        friends.add(new User(3, null, null, 100, null, null));
        friends.add(new User(4, null, null, 100, null, null));
        instanceUser.setFriends(friends);
        instance.setSingleUser(instanceUser);
        boolean result;
        
        // Test 1: Getting true when otherUser is in friends list of singleUser
        instance.setOtherUser(new User(2, null, null, 100, null, null));
        // Execute
        result = instance.getIsRendered();
        assertEquals("isRendered was false when otherUser is friend", true, result);
        
        // Test 2: Getting false when otherUser is not in friends list of signleUser
        instance.setOtherUser(new User(7, null, null, 100, null, null));
        // Execute
        result = instance.getIsRendered();
        assertEquals("isRendered was true when otherUser is not friend", false, result);
    }
}
