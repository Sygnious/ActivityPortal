/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;
import ProblemDomainModel.*;
import Database.*;
import java.util.ArrayList;

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
public class ActivityBeanTest {
    
    private ActivityBean instance;
    private Activity instanceAct;
    private ArrayList<Activity> instanceActList;
    private String instanceString;
    
    public ActivityBeanTest() {
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

    @Test
    public void testShowAllActivities() throws Exception {
        System.out.println("showAllActivities");
        //Execute
        instance = new ActivityBean();
        instanceString = instance.showAllActivities();
        instanceActList = instance.getActList();
        instanceAct = instanceActList.get(0);
        
        // Checking return string
        assertEquals("Incorrect page navigation string", "activityList", instanceString);
        
        // Checking size of Activity List, checking name of first
        // testLoadAllActivities already tested in LoaderTest, therefore only a few details should suffice
        assertEquals("Loaded actityList first intance name is wrong", "Teater: \"Kristin Lavransdatter\"", instanceAct.getName());
        assertEquals("Loaded actityList size is wrong", 13, instanceActList.size());
    }
    
}
