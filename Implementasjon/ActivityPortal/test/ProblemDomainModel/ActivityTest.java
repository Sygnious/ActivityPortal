/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ProblemDomainModel;

import java.util.ArrayList;
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
public class ActivityTest {
    
    private Activity instance;
    
    public ActivityTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new Activity(1, "TestActivity", "This instance of Activity is used for testclass ActicityTest", 1);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of insertInterest method, of class Activity.
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
     * Test of removeInterest method, of class Activity.
     */
    @Test
    public void testRemoveInterest() {
        System.out.println("insertInterest");
        // Set up:
        ArrayList<String> instanceList = new ArrayList();
        instanceList.add("Content1");
        instanceList.add("Content2");
        instanceList.add("Content3");
        instanceList.add("Content4");
        instanceList.add("Content5");
        instanceList.add("Content6");
        instance.setInterests(instanceList);
        // Execute:
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
     * Test of insertUser method, of class Activity.
     */
    @Test
    public void testInsertUser() {
        System.out.println("insertUser");
    }

    @Test
    public void testFindUserById() {
        System.out.println("findUserById");
        // Set up:
        ArrayList<User> instanceList = new ArrayList();
        instanceList.add(new User(1, "Content1", null, 7050, null, null));
        instanceList.add(new User(2, "Content2", null, 7050, null, null));
        instanceList.add(new User(3, "Content3", null, 7050, null, null));
        instanceList.add(new User(4, "Content4", null, 7050, null, null));
        instance.setParticipants(instanceList);
        
        // Execute:
        User result1 = instance.findUserById(2);
        User result2 = instance.findUserById(8); //Should return null
        assertEquals("Wrong user found", "Content2", result1.getFirstName());
        assertNull("Error when handling non-existing Id", result2);
    }
    
    /**
     * Test of removeUser method, of class Activity.
     */
    @Test
    public void testRemoveUser() {
        System.out.println("removeUser");
        // Set up
        ArrayList<User> instanceList = new ArrayList();
        instanceList.add(new User(1, "Content1", null, 7050, null, null));
        instanceList.add(new User(2, "Content2", null, 7050, null, null));
        instanceList.add(new User(3, "Content3", null, 7050, null, null));
        instanceList.add(new User(4, "Content4", null, 7050, null, null));
        instanceList.add(new User(5, "Content5", null, 7050, null, null));
        instanceList.add(new User(6, "Content6", null, 7050, null, null));
        instance.setParticipants(instanceList);
        
        // Execute
        User deletion;
        deletion = instance.findUserById(1);
        instance.removeUser(deletion);
        deletion = instance.findUserById(3);
        instance.removeUser(deletion);
        instance.removeUser(null); //Should be ignored
        deletion = instance.findUserById(3); //Should return null
        instance.removeUser(deletion); //Should be ignored
        deletion = instance.findUserById(6);
        instance.removeUser(deletion);
        
        assertEquals("ArrayList size wrong", 3, instance.getParticipants().size());
        assertEquals("First input wrong", "Content2", instance.getParticipants().get(0).getFirstName());
        assertEquals("Last input wrong", "Content5", instance.getParticipants().get(instance.getParticipants().size()-1).getFirstName());
        
    }
    
    @Test
    public void testConvertToGregorian(){
        System.out.println("convertToGregorian");
        GregorianCalendar instanceCalendar = new Activity(0, null, null, 0).convertToGregorian("2013-05-28");
        assertEquals("Year is wrong", 2013, instanceCalendar.get(1));
        assertEquals("Month is wrong", 5, instanceCalendar.get(2));
        assertEquals("Day is wrong", 28, instanceCalendar.get(5));
    }
    
    @Test
    public void testConvertFromGregorian(){
        System.out.println("convertFromGregorian");
        String instanceString = new Activity(0, null, null, 0).convertFromGregorian(new GregorianCalendar(2013, 5, 28));
        assertEquals("Date is wrong", "2013-05-28", instanceString);
    }
    
    @Test
    public void testDateAsString(){
        System.out.println("dateAsString");
        instance = new Activity(0, null, null, 0);
        instance.setDate(new GregorianCalendar(2016, 8, 13));
        String instanceString = instance.getDateAsString();
        assertEquals("Date is wrong", "2016-08-13", instanceString);
    }
}
