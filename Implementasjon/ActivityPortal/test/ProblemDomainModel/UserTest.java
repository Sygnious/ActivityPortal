/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ProblemDomainModel;

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
public class UserTest {
    
    private User instance;
    
    public UserTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new User(1, "Torbjørn", "Langland", 26, "Moholt Allé 03-02 H0302", 7050, "Trondheim");
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of insertInterest method, of class User.
     */
    @Test
    public void testInsertInterest() {
        System.out.println("insertInterest");
        instance.insertInterest("Content1");
        instance.insertInterest("Content2");
        instance.insertInterest(null);//Should be ignored
        instance.insertInterest("Content3");
        instance.insertInterest("   ");//Should be ignored
        assertEquals("ArrayList size wrong", 3, instance.getInterests().size());
        assertEquals("First input wrong", "Content1", instance.getInterests().get(0));
        assertEquals("Last input wrong", "Content3", instance.getInterests().get(instance.getInterests().size()-1));
    }

    /**
     * Test of removeInterest method, of class User.
     */
    @Test
    public void testRemoveInterest() {
        System.out.println("removeInterest");
        
        // Set up
        ArrayList<String> instanceList = new ArrayList();
        instanceList.add("Content1");
        instanceList.add("Content2");
        instanceList.add("Content3");
        instanceList.add("Content4");
        instanceList.add("Content5");
        instanceList.add("Content6");
        instance.setInterests(instanceList);
        
        // Execute
        instance.removeInterest("Content1");
        instance.removeInterest("Content1"); // Should be ignored
        instance.removeInterest("Content2");
        instance.removeInterest("  "); //Should be ignored
        instance.removeInterest("Content5");
        instance.removeInterest(null); //Should be ignored
        
        assertEquals("ArrayList size wrong", 3, instance.getInterests().size());
        assertEquals("First input wrong", "Content3", instance.getInterests().get(0));
        assertEquals("Last input wrong", "Content6", instance.getInterests().get(instance.getInterests().size()-1));
    }

    /**
     * Test of insertActivity method, of class User.
     */
    @Test
    public void testInsertActivity() {
        System.out.println("insertActivity");
        instance.insertActivity(new Activity(1, "Content1", null, 1));
        instance.insertActivity(new Activity(2, "Content2", null, 1));
        instance.insertActivity(null); //Should be ignored
        instance.insertActivity(new Activity(3, "Content3", null, 1));
        assertEquals("ArrayList size wrong", 3, instance.getPartActs().size());
        assertEquals("First input wrong", "Content1", instance.getPartActs().get(0).getName());
        assertEquals("Last input wrong", "Content3", instance.getPartActs().get(instance.getPartActs().size()-1).getName());
    }
    
    @Test
    public void testFindActivityById() {
        System.out.println("findActivityById");
        // Set up
        ArrayList<Activity> instanceList = new ArrayList();
        instanceList.add(new Activity(1, "Content1", null, 1));
        instanceList.add(new Activity(2, "Content2", null, 1));
        instanceList.add(new Activity(3, "Content3", null, 1));
        instanceList.add(new Activity(4, "Content4", null, 1));
        instance.setPartActs(instanceList);
        
        // Execute
        Activity result1 = instance.findActivityById(2);
        Activity result2 = instance.findActivityById(8); //Should return null
        assertEquals("Wrong activity found", "Content2", result1.getName());
        assertNull("Error when handling non-existing Id", result2);
    }
    
    @Test
    public void testParticipatesInActivity() {
        System.out.println("participatesInActivity");
        ArrayList<Activity> instanceList = new ArrayList();
        instanceList.add(new Activity(1, "Content1", null, 1));
        instanceList.add(new Activity(2, "Content2", null, 1));
        instanceList.add(new Activity(3, "Content3", null, 1));
        instanceList.add(new Activity(4, "Content4", null, 1));
        instance.setPartActs(instanceList);
        
        boolean result1= instance.participatesInActivity(3);
        boolean result2= instance.participatesInActivity(6);
        
        assertEquals("Wrong: claims not to participate in activity with id 3", true, result1);
        assertEquals("Wrong: claims to participate in activity with id 6", false, result2);
    }

    /**
     * Test of removeInterest method, of class User.
     */
    @Test
    public void testRemoveActivity() {
        System.out.println("removeActivity");
        // Set up
        ArrayList<Activity> instanceList = new ArrayList();
        instanceList.add(new Activity(1, "Content1", null, 1));
        instanceList.add(new Activity(2, "Content2", null, 1));
        instanceList.add(new Activity(3, "Content3", null, 1));
        instanceList.add(new Activity(4, "Content4", null, 1));
        instanceList.add(new Activity(5, "Content5", null, 1));
        instanceList.add(new Activity(6, "Content6", null, 1));
        instance.setPartActs(instanceList);
        
        // Execute
        Activity deletion;
        deletion = instance.findActivityById(1);
        instance.removeActivity(deletion);
        deletion = instance.findActivityById(3);
        instance.removeActivity(deletion);
        instance.removeActivity(null); //Should be ignored
        deletion = instance.findActivityById(3); //Should return null
        instance.removeActivity(deletion); //Should be ignored
        deletion = instance.findActivityById(6);
        instance.removeActivity(deletion);
        
        assertEquals("ArrayList size wrong", 3, instance.getPartActs().size());
        assertEquals("First input wrong", "Content2", instance.getPartActs().get(0).getName());
        assertEquals("Last input wrong", "Content5", instance.getPartActs().get(instance.getPartActs().size()-1).getName());
    }
    
    @Test
    public void testInsertFriend(){
        System.out.println("insertFriend");
        instance.insertFriend(new User(2, "Clark", "Kent", 30, null, null));
        instance.insertFriend(new User(3, "Bruce", "Wayne", 35, null, null));
        instance.insertActivity(null); //Should be ignored
        instance.insertFriend(new User(4, "Lisa", "Harris", 90, null, null));
        assertEquals("ArrayList size wrong", 3, instance.getFriends().size());
        assertEquals("First input wrong", "Clark", instance.getFriends().get(0).getFirstName());
        assertEquals("Last input wrong", "Lisa", instance.getFriends().get(instance.getFriends().size()-1).getFirstName());
    }
    
    @Test
    public void testFindFriendByID(){
        System.out.println("findFriendByID");
        // Set up
        ArrayList<User> instanceList = new ArrayList();
        instanceList.add(new User(2, "Pjodor", "Larsen", 51, "Addresse 33", new Post(7050, "Trondheim")));
        instanceList.add(new User(3, "Jens", "Larsen", 52, "Addresse 33", new Post(7050, "Trondheim")));
        instanceList.add(new User(4, "Lise", "Larsen", 53, "Addresse 33", new Post(7050, "Trondheim")));
        instanceList.add(new User(5, "Nasse", "Larsen", 54, "Addresse 33", new Post(7050, "Trondheim")));
        instanceList.add(new User(6, "Gunhild", "Larsen", 55, "Addresse 33", new Post(7050, "Trondheim")));
        instance.setFriends(instanceList);
        
        // Execute
        
        User result1 = instance.findFriendById(2);
        User result2 = instance.findFriendById(8);
        assertEquals("Wrong user found", "Pjodor", result1.getFirstName());
        assertNull("Error when handling non-existing id", result2);
    }
    
    @Test
    public void testHasFriend(){
        System.out.println("hasFriend");
        // Set up
        ArrayList<User> instanceList = new ArrayList();
        instanceList.add(new User(2, "Pjodor", "Larsen", 51, "Addresse 33", new Post(7050, "Trondheim")));
        instanceList.add(new User(3, "Jens", "Larsen", 52, "Addresse 33", new Post(7050, "Trondheim")));
        instanceList.add(new User(4, "Lise", "Larsen", 53, "Addresse 33", new Post(7050, "Trondheim")));
        instanceList.add(new User(5, "Nasse", "Larsen", 54, "Addresse 33", new Post(7050, "Trondheim")));
        instanceList.add(new User(6, "Gunhild", "Larsen", 55, "Addresse 33", new Post(7050, "Trondheim")));
        instance.setFriends(instanceList);
        
        // Execute
        
        boolean result1 = instance.hasFriend(2);
        boolean result2 = instance.hasFriend(8);
        assertEquals("Wrong: Claims to not having existing friend", true, result1);
        assertEquals("Wrong: Claims to have non-existing friend", false, result2);
    }
    
    @Test
    public void testRemoveFriend(){
        System.out.println("removeFriend");
        // Set up
        ArrayList<User> instanceList = new ArrayList();
        instanceList.add(new User(2, "Friend1", null, 100, null, null));
        instanceList.add(new User(3, "Friend2", null, 100, null, null));
        instanceList.add(new User(4, "Friend3", null, 100, null, null));
        instanceList.add(new User(5, "Friend4", null, 100, null, null));
        instanceList.add(new User(6, "Friend5", null, 100, null, null));
        instanceList.add(new User(7, "Friend6", null, 100, null, null));
        instance.setFriends(instanceList);
        
        // Execute
        User deletion;
        deletion = instance.findFriendById(2);
        instance.removeFriend(deletion);
        deletion = instance.findFriendById(3);
        instance.removeFriend(deletion);
        instance.removeFriend(null); //Should be ignored
        deletion = instance.findFriendById(9); //Should return null
        instance.removeFriend(deletion); //Should be ignored
        deletion = instance.findFriendById(6);
        instance.removeFriend(deletion);
        
        assertEquals("ArrayList size wrong", 3, instance.getFriends().size());
        assertEquals("First input wrong", "Friend3", instance.getFriends().get(0).getFirstName());
        assertEquals("Last input wrong", "Friend6", instance.getFriends().get(instance.getFriends().size()-1).getFirstName());
    }
}
