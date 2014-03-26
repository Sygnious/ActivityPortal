/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Database;
import ProblemDomainModel.*;

import java.util.GregorianCalendar;
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
public class LoaderTest {
    
    public LoaderTest() {
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
    public void testGetTableSize() throws Exception{
        System.out.println("getTableSize");
        // Using table "Person" from database for this instance
        int count = Loader.getTableSize("Person");
        assertEquals("The expected table size is not corrext", 16, count);
    }
    
    // User load tests
    @Test
    public void testLoadSingleUserOnID() throws Exception{
        System.out.println("loadSingleUserOnID");
        User instance = Loader.loadSingleUserOnID(2);
        // Checking single variables
        assertEquals("userID is not correct", 2, instance.getUserId());
        assertEquals("firstName is not correct", "Tove", instance.getFirstName());
        assertEquals("lastName is not correct", "Johansen", instance.getSurName());
        assertEquals("age is not correct", 61, instance.getAge());
        assertEquals("address is not correct", "Moholt Allé 555-555-555", instance.getAddress());
        assertEquals("postID is not correnct", 7050, instance.getPostAddress().getPostId());
        assertEquals("postAddress is not correnct", "Trondheim", instance.getPostAddress().getPostAddress());
        // Checcking Arraylists by size and ID of first contents, eventually last content (reason for why will be stated)
        // Interest
        assertEquals("interest size is not correnct", 5, instance.getInterests().size());
        assertEquals("interest first instance is not correnct", "Teater og kultur", instance.getInterests().get(0));
        // Checking extra content due to transform from Interest table to mere String arrayList
        assertEquals("interest last instance is not correnct", "Sang og musikk", instance.getInterests().get(instance.getInterests().size()-1)); 
        
        // Participated Activities
        assertEquals("partActs size is not correnct", 4, instance.getPartActs().size());
        assertEquals("partActs instance ID is not correnct", 1, instance.getPartActs().get(0).getActivityId());
        assertEquals("partActs instance name is not correnct", "Teater: \"Kristin Lavransdatter\"", instance.getPartActs().get(0).getName());
    }
    
    // Acitivty load tests
    @Test
    public void testLoadAllActivities() throws Exception{
        System.out.println("loadAllActivities");
        // Using table "Person" from database for this instance
        ArrayList<Activity> list = Loader.loadAllActivities();
        // Checking size of array. Totalt contants in database is 14, but one is post date
        assertEquals("total array size is not correct", 13, list.size());
        // Checking contents of first
        Activity instance = list.get(0);
        
        assertEquals("activityID is not correct", 1, instance.getActivityId());
        assertEquals("activityName is not correct", "Teater: \"Kristin Lavransdatter\"", instance.getName());
        assertEquals("activityDescription is not correct", "Forestilling med \"Kristing Lavransdatter\" på Trøndelag Teater. Oppmøte kl 18:00", instance.getDescription());
        assertEquals("activityTownID is not correct", 1, instance.getTownId());
        assertEquals("activityDate is not correct", new GregorianCalendar(2014, 4, 9), instance.getDate());
        
        // Lists should be empty since this is a total overview fuction.
        // Difference may occur if participants or interests are used for filter, this will be noted in other tests.
        // Covered Interest
        assertEquals("interest size is not correnct", 0, instance.getInterests().size());
        
        // Participants
        assertEquals("participants size is not correnct", 0, instance.getParticipants().size());
        
    }
    
    @Test
    public void testLoadSingleActivityOnID() throws Exception{
        System.out.println("loadSingleActivityOnID");
        Activity instance = Loader.loadSingleActivityOnID(5);
        // Checking single variables
        assertEquals("activityID is not correct", 5, instance.getActivityId());
        assertEquals("activityName is not correct", "Teater: \"De Miserable\"", instance.getName());
        assertEquals("activityDescription is not correct", "Forestilling med det franske stykket \"De Miserable\" på Trøndelag Teater. Oppmøte kl 19:00", instance.getDescription());
        assertEquals("activityTownID is not correct", 1, instance.getTownId());
        assertEquals("activityDate.Year is not correct", 2014, instance.getDate().get(1));
        assertEquals("activityDate.Month is not correct", 5, instance.getDate().get(2));
        assertEquals("activityDate.Day is not correct", 13, instance.getDate().get(5));
        
        // Checcking Arraylists by size and ID of first contents, eventually last content (reason for why will be stated)
        // Covered Interest
        assertEquals("interest size is not correnct", 1, instance.getInterests().size());
        assertEquals("interest first instance is not correnct", "Teater og kultur", instance.getInterests().get(0));
        
        // Participants
        assertEquals("participants size is not correnct", 4, instance.getParticipants().size());
        assertEquals("participatns first instance ID is not correnct", 1, instance.getParticipants().get(0).getUserId());
        assertEquals("participatns first instance name is not correnct", "Torbjørn", instance.getParticipants().get(0).getFirstName());
    }
}
