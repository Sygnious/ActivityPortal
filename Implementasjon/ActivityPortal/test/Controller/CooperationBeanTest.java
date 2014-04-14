/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Database.*;
import ProblemDomainModel.*;

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
        Activity testAct2 = new Activity(99, "TestAct1", null, 1);
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
    
}
