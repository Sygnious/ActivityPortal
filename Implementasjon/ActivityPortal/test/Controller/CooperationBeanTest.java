/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Database.*;
import ProblemDomainModel.*;
import java.util.GregorianCalendar;

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
public class CooperationBeanTest {
    
    private CooperationBean instance;
    private UserBean instanceUserB;      //Part of instance
    private ActivityBean instanceActB;   //Part of instance
    private String instanceString;
    
    public CooperationBeanTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        // Because stores may be handled at individual methods from this class,
        // it is important that database is reset after each method in this test calss
        Initiator.resetDatabase(); 
        /*instanceUserB.logIn();              // Loading User with ID 1
        instanceActB.showAllActivities();   // Loading All activities
        Activity one = instanceActB.getActList().get(0);
        instanceActB*/
        instance = new CooperationBean();
        instance.setActBean(instanceActB = new ActivityBean());
        instance.setUserBean(instanceUserB = new UserBean());
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of findParticipationOnUserID method, of class CooperationBean.
     */
    @Test
    public void testFindParticipationOnUserID() {
        System.out.println("findParticipationOnUserID");
        //Set up
        Activity testAct1 = new Activity(1, "TestAct1", null, 1);
        Activity testAct2 = new Activity(99, "TestAct2", null, 1);
        User user = new User(1, null, null, 1, null, null);
        user.insertActivity(new Activity(1, "MyAct1", null, 1));
        user.insertActivity(new Activity(4, "MyAct2", null, 1));
        user.insertActivity(new Activity(8, "MyAct3", null, 1));
        instanceUserB.setSingleUser(user);
        
        // Test one: Should return true when user participates in activity with
        // matching ID
        instanceActB.setSingleAct(testAct1);
        boolean result = instance.findParticipationOnUserID();
        
        assertEquals("result should be true when user participates in the current activity", true, result);
        
        // Test two: Should return false when user does not participate in activity.
        instanceActB.setSingleAct(testAct2);
        result = instance.findParticipationOnUserID();
        
        assertEquals("result should be false when user does not participate in the current activity", false, result);
        
    }
    
    // StorerTest tests the database methods themselves, the methods connected to
    // the CooperationBean which invoke storing will be tested for internal
    // lists size, contents and return strings.
    @Test
    public void testSignUserOnActivity() throws Exception{
        System.out.println("signUserOnActivity");
        // Set up
        prepareUserID2();
        Activity testAct = new Activity(7, "TestAct1", null, 1); //Activity 7 comes before 8 in database
        testAct.setDate(new GregorianCalendar(2014, 3, 2)); // First day after Loader demodate
        instanceActB.setSingleAct(testAct); 

        // Test 1: Adding new activity:
        // Execute
        String result = instance.signUserOnActivity();
        int newSize = instanceUserB.getSingleUser().getPartActs().size();
        assertEquals("resulting string was wrong", "activitySignedOn", result);
        // Testing difference between old and new size, the static number may vary depending on implementation.
        assertEquals("resulting size in participated activity list is not increased", 5, newSize);
        Activity firstAct = instanceUserB.getSingleUser().getPartActs().get(0); //Should be first activty by given date
        assertEquals("added activity found in list has wrong ID", 7, firstAct.getActivityId());
        
        // Test 2 Adding already added activity, should cause error and no update
        // Execute
        result = instance.signUserOnActivity();
        newSize = instanceUserB.getSingleUser().getPartActs().size();
        assertEquals("resulting string was wrong", "error", result);
        assertEquals("resulting size in participated activity list has not remained unchanged", 5, newSize);
    }
    
    @Test
    public void testCancelUserFromActivity() throws Exception{
        System.out.println("cancelUserFromActivity");
        
        // Set up
        prepareUserID2();
        // Removing activity with ID 8 from user (and thus database).
        
        // Test 1: Remove existing activity (ID 8),
        String result = instance.cancelUserFromActivity();
        int newSize = instanceUserB.getSingleUser().getPartActs().size();
        assertEquals("resulting string was wrong", "activityCancelled", result);
        // Testing difference between old and new size, the static number may vary depending on implementation.
        assertEquals("resulting size in participated activity list is not decreased", 3, newSize);
        Activity firstAct = instanceUserB.getSingleUser().getPartActs().get(0); //First activity removed, replaced by 2nd.
        assertEquals("second activity moved to first index in list has wrong id", 1, firstAct.getActivityId());
        
        // Test 2 Removing already removed activity with ID 8, should cause error and no uptdate
        // Execute
        result = instance.cancelUserFromActivity();
        newSize = instanceUserB.getSingleUser().getPartActs().size();
        assertEquals("resulting string was wrong", "error", result);
        assertEquals("resulting size in participated activity list has not remained unchanged", 3, newSize);
    }
    
    // Helping methods for tests that are using userID2
    private void prepareUserID2(){
        // Goal: Prepare user ID 2 with contents for activity list based on
        // contents from database. Only ID-numbers are essential and relevant.
        // The order in database, based on date, are 8, 1, 2, 5.
        // Warning: Contents may be overwritten with data from database
        // depending on how CooperationBean implements methods for 
        // refreshing and sorting user's list of participated activities
        // Test only ID's from contents.
        
        // Dates may be overwritten with data from database, but if final 
        // implementation only invokes store to database without reload,
        // testing dates must be added for tests to succeed.
        
        User testUser = new User(2, null, null, 1, null, null);
        Activity testAct = new Activity(8, "TestAct1", null, 1);
        testAct.setDate(new GregorianCalendar(2014, 3, 3));
        testUser.insertActivity(testAct); 
        instanceUserB.setSingleUser(testUser);
        instanceActB.setSingleAct(testAct); //Having the first activity as current activity
        testAct = new Activity(1, "TestAct2", null, 1);
        testAct.setDate(new GregorianCalendar(2014, 3, 4));
        testUser.insertActivity(testAct);
        testAct = new Activity(2, "TestAct3", null, 1);
        testAct.setDate(new GregorianCalendar(2014, 3, 5));
        testUser.insertActivity(testAct);
        testAct = new Activity(5, "TestAct4", null, 1);
        testAct.setDate(new GregorianCalendar(2014, 3, 6));
        testUser.insertActivity(testAct);
        
    }
    
}
