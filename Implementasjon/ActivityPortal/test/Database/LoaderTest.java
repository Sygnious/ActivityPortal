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
        assertEquals("The expected table size is not correct", 16, count);
    }
    
    // User load tests
    
    @Test
    public void testLoadAllUsersExceptOwn() throws Exception{
        System.out.println("loadAllUsersExceptOwn");
        ArrayList<User> instance = Loader.loadAllUsersExceptOwn(1);
        assertEquals("The expected list sie is not correct", 15, instance.size()); //With test files, number of users are 16
        assertEquals("First instance userID is not correct", 10, instance.get(0).getUserId());
        assertEquals("First instance firstName is not correct", "U-Known", instance.get(0).getFirstName());
        assertEquals("First instance surName is not correct", "Anonymious", instance.get(0).getSurName());
    }
    
    @Test
    public void testLoadAllUserFriends() throws Exception{
        System.out.println("loadAllUserFriends");
        ArrayList<User> instance = Loader.loadAllUserFriends(1);
        assertEquals("The expected list size is not correct", 6, instance.size()); //Number of friends connected to user with id 1 is 6
        assertEquals("First instance userID is not correct", 5, instance.get(0).getUserId());
        assertEquals("First instance firstName is not correct", "Ludvig", instance.get(0).getFirstName());
        assertEquals("First instance surName is not correct", "Hansen", instance.get(0).getSurName());
    }
    
    @Test
    public void testLoadSingleUserOnID() throws Exception{
        System.out.println("loadSingleUserOnID");
        User instance = Loader.loadSingleUserOnID(2);
        // Checking single variables
        assertEquals("userID is not correct", 2, instance.getUserId());
        assertEquals("firstName is not correct", "Tove", instance.getFirstName());
        assertEquals("surName is not correct", "Johansen", instance.getSurName());
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
        assertEquals("partActs instance ID is not correnct", 8, instance.getPartActs().get(0).getActivityId());
        assertEquals("partActs instance name is not correnct", "Skitur i Flåmarka", instance.getPartActs().get(0).getName());
        
        // Friends
        assertEquals("friends size is not correct", 3, instance.getFriends().size());
        assertEquals("friends instance ID is not correnct", 3, instance.getFriends().get(0).getUserId());
        assertEquals("friends instance firstName is not correnct", "Bjørn", instance.getFriends().get(0).getFirstName());
        assertEquals("friends instance surName is not correnct", "Johansen", instance.getFriends().get(0).getSurName());
        
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
        
        assertEquals("activityID is not correct", 7, instance.getActivityId());
        assertEquals("activityName is not correct", "Skitur på Gråkallen", instance.getName());
        assertEquals("activityDescription is not correct", "Ring 90 69 54 17 for påmelding. Elever fra Rimi Videregående Skole stiller opp", instance.getDescription());
        assertEquals("activityTownID is not correct", 1, instance.getTownId());
        assertEquals("activityDate is not correct", new GregorianCalendar(2014, 4, 2), instance.getDate());
        
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
    
    @Test
    public void testLoadActivitiesOnInterest() throws Exception{
        System.out.println("loadSingleActivitiesOnInterest");
        ArrayList<Activity> list = Loader.loadActivitiesOnInterest("Teater og kultur");
        // Checking size of array. 
        assertEquals("total array size is not correct", 4, list.size());
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
    
    // Will be split into 3: One search for name, one search for content, one search that misses.
    @Test
    public void testLoadActivitiesOnSearch1() throws Exception{
        System.out.println("loadActivitiesOnSearch1: Search finds name");
        
        ArrayList<Activity> list;
        Activity instance;
        list = Loader.loadActivitiesOnSearch("Teater");
        // Checking size of array. 
        assertEquals("total array size is not correct", 2, list.size());
        // Checking contents of first
        instance = list.get(0);
        
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
    public void testLoadActivitiesOnSearch2() throws Exception{
        System.out.println("loadActivitiesOnSearch2: Search finds description");
        
        ArrayList<Activity> list;
        Activity instance;
        list = Loader.loadActivitiesOnSearch("forestilling");
        // Checking size of array. 
        assertEquals("total array size is not correct", 1, list.size());
        // Checking contents of first
        instance = list.get(0);
        
        assertEquals("activityID is not correct", 11, instance.getActivityId());
        assertEquals("activityName is not correct", "Kino: \"Hobbiten 3\"", instance.getName());
        assertEquals("activityDescription is not correct", "Kinoforestilling på Nova med Hobbiten 3. Oppmøte 17:30.", instance.getDescription());
        assertEquals("activityTownID is not correct", 1, instance.getTownId());
        assertEquals("activityDate is not correct", new GregorianCalendar(2014, 5, 21), instance.getDate());
        
        // Lists should be empty since this is a total overview fuction.
        // Difference may occur if participants or interests are used for filter, this will be noted in other tests.
        // Covered Interest
        assertEquals("interest size is not correnct", 0, instance.getInterests().size());
        
        // Participants
        assertEquals("participants size is not correnct", 0, instance.getParticipants().size());
    }
    
    @Test
    public void testLoadActivitiesOnSearch3() throws Exception{
        System.out.println("loadActivitiesOnSearch2: Search finds nothing");
        
        ArrayList<Activity> list;
        list = Loader.loadActivitiesOnSearch("Carl I Hagen blir sint hvis du sniker inn i huset hans!");
        // Checking size of array. 
        assertEquals("total array size is not correct", 0, list.size());
    }
    
    @Test
    public void testLoadActivitiesOnAllFriends() throws Exception{
        System.out.println("loadActivitiesOnAllFriends");
        // Using user with ID 11, which has two friends with ID's 1 and 4.
        // Those two participate in 7 distinct activities, with ID 7 being first
        // One activity is unique for friend 1, and one for friend 4. 5 are shared.
        ArrayList<Activity> list = Loader.loadActivitiesOnAllFriends(11);
        assertEquals("total array size is not correct", 7, list.size());
        // Checking contents of first
        Activity instance = list.get(0);
        assertEquals("Instance activityID is not correct", 7, instance.getActivityId());
        assertEquals("Instance activityName is not correct", "Skitur på Gråkallen", instance.getName());
    }
    
    @Test
    public void testLoadActivitiesOnOneFriend() throws Exception{
        System.out.println("loadActivitiesOnAllFriends");
        
        // Assume user has friend with ID = 4.
        // Test will load all activities participated on by this friend
        
        ArrayList<Activity> list = Loader.loadActivitiesOnOneFriend(4);
        assertEquals("total array size is not correct", 6, list.size());
        // Checking contents of first
        Activity instance = list.get(0);
        assertEquals("Instance activityID is not correct", 7, instance.getActivityId());
        assertEquals("Instance activityName is not correct", "Skitur på Gråkallen", instance.getName());
    }
    
    // Other loads
    
    @Test
    public void testLoadAllInterestsNamesOnly() throws Exception{
        System.out.println("loadAllInterests");
        ArrayList<String> list = Loader.loadAllInterestsNamesOnly();
        
        assertEquals("interest size is not corrext is not correct", 9, list.size());
        assertEquals("insterest first instance is not correct", "Teater og kultur", list.get(0));
    }
}
