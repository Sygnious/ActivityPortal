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
        Initiator.resetDatabase();
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
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
    
    
}
