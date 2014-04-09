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
        System.out.println("showAllActivitye");
        //Execute
        instance = new ActivityBean();
        instanceString = instance.showAllActivities();
        instanceActList = instance.getActList();
        instanceAct = instanceActList.get(0);
        
        // Checking return string
        assertEquals("Incorrect page navigation string", "activityList", instanceString);
        // Checking size of Activity List, checking name of first
        // testLoadAllActivities already tested in LoaderTest, therefore only a few details should suffice
        assertEquals("Loaded activityList first intance name is wrong", "Skitur på Gråkallen", instanceAct.getName());
        assertEquals("Loaded activityList size is wrong", 13, instanceActList.size());
    }
    
    @Test
    public void testShowActivityDetails() throws Exception {
        System.out.println("showActivityDetails");
        // Set up
        
        // Though not correct happening in the application, a chosen activity
        // from the web page still has activity id-number that is used.
        instanceAct = new Activity(1, null, null, -99); 
        instance = new ActivityBean();
        
        // Execute 1: Should not do anything if parameter ojbect is not of type Activity
        instanceString = instance.showActivityDetails("This String is a hoax and will not work");
        
        assertNull("instanceString was not null when using wrong object type", instanceString);
        assertNull("Activity details changed even if object type was wrong for method", instanceAct.getName());
        
        // Execute 2: Changes when parameter object is of type Activiy:
        instanceString = instance.showActivityDetails(instanceAct);
        
        // Checking return string
        assertEquals("Incorrect page navigation string", "activityOne", instanceString);
        // testLoadSingleActivityOnID already tested in LoaderTest, therefore only a few details should suffice
        assertEquals("Loaded actity name is wrong", "Teater: \"Kristin Lavransdatter\"", instance.getSingleAct().getName());
        assertEquals("Loaded activity description is wrong", "Forestilling med \"Kristing Lavransdatter\" på Trøndelag Teater. Oppmøte kl 18:00", instance.getSingleAct().getDescription());
    }
    
    @Test
    public void testShowActivitiesByInterest() throws Exception{
        System.out.println("showActivitiesByInterest");
        instance = new ActivityBean();
        // First test by null
        instanceString = instance.showActivitiesByInterest(null);
        assertNull("return was not null when input was null", instanceString);

        //Execute
        instanceString = instance.showActivitiesByInterest("Teater og kultur");
        instanceActList = instance.getActList();
        instanceAct = instanceActList.get(0);
        
          // Checking return string
        assertEquals("Incorrect page navigation string", "activityList", instanceString);
        // testloadActivitiesOnInterest already tested in LoaderTest, therefore only a few details should suffice
        assertEquals("Loaded actity name is wrong", "Teater: \"Kristin Lavransdatter\"", instance.getActList().get(0).getName());
        assertEquals("Loaded activityList size is wrong", 4, instanceActList.size());
    }
    
    @Test
    public void testShowActivitiesBySearch() throws Exception{
        System.out.println("showActivitiesBySearch");
        instance = new ActivityBean();
        instance.setCurrentPage("activitySearch"); //The supposed page when performing this
        // First test by null
        instance.setKeyWord(null);
        instanceString = instance.showActivitiesBySearch();
        assertEquals("return was not currentPage when input was null", "activitySearch", instanceString);

        // Second test by empty string
        instance.setKeyWord("");
        instanceString = instance.showActivitiesBySearch();
        assertEquals("return was not currentPage when input was empty", "activitySearch", instanceString);
        
        // This test by "Teater and Kultur". Note that no other keywords are tested,
        // testLoadActivitiesOnSearch1-3 in LoaderTest cover these;
        //Execute
        instance.setKeyWord("Teater");
        instanceString = instance.showActivitiesBySearch();
        instanceActList = instance.getActList();
        instanceAct = instanceActList.get(0);
        
          // Checking return string
        assertEquals("Incorrect page navigation string", "activityList", instanceString);
        // testloadActivitiesOnInterest already tested in LoaderTest, therefore only a few details should suffice
        assertEquals("Loaded actity name is wrong", "Teater: \"Kristin Lavransdatter\"", instance.getActList().get(0).getName());
        assertEquals("Loaded activityList size is wrong", 2, instanceActList.size());
    }
    
}
