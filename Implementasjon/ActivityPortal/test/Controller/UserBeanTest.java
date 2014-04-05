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
        instance = new UserBean();
        instanceString = instance.logIn();
        instanceUser = instance.getSingleUser();
        
        // Checking return string
        assertEquals("Incorrect page navigation string", "mainPage", instanceString);
        
        // Checking user name and size of activity table.
        // loadSingleUserOnID already tested in LoaderTest, therefore only a few details should suffice
        assertEquals("Loaded user firstName is wrong", "Torbjørn", instanceUser.getFirstName());
        assertEquals("Loaded user lastName is wrong", "Torbjørn", instanceUser.getFirstName());
        assertEquals("Loaded user partActs size is wrong", 4, instanceUser.getPartActs().size());
    }
    
    @Test
    public void testLogOut(){
        System.out.println("logOut");
        // Execute
        instance = new UserBean();
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
        instance = new UserBean();
        instanceString = instance.reset();
        
        // Checking return string
        assertEquals("Incorrect page navigation string", "index", instanceString);
    }
    
    
}
