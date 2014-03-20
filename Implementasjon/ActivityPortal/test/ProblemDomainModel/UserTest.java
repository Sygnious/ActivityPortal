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
        //Preparing individual test:
        ArrayList<String> instanceList = new ArrayList();
        instanceList.add("Content1");
        instanceList.add("Content2");
        instanceList.add("Content3");
        instanceList.add("Content4");
        instanceList.add("Content5");
        instanceList.add("Content6");
        instance.setInterests(instanceList);
        //Performing test:
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
        //Preparing individual test:
        ArrayList<Activity> instanceList = new ArrayList();
        instanceList.add(new Activity(1, "Content1", null, 1));
        instanceList.add(new Activity(2, "Content2", null, 1));
        instanceList.add(new Activity(3, "Content3", null, 1));
        instanceList.add(new Activity(4, "Content4", null, 1));
        instance.setPartActs(instanceList);
        
        //Performing test:
        Activity result1 = instance.findActivityById(2);
        Activity result2 = instance.findActivityById(8); //Should return null
        assertEquals("Wrong activity found", "Content2", result1.getName());
        assertNull("Error when handling non-existing Id", result2);
    }

    /**
     * Test of removeInterest method, of class User.
     */
    @Test
    public void testRemoveActivity() {
        System.out.println("removeActivity");
        //Preparing individual test
        ArrayList<Activity> instanceList = new ArrayList();
        instanceList.add(new Activity(1, "Content1", null, 1));
        instanceList.add(new Activity(2, "Content2", null, 1));
        instanceList.add(new Activity(3, "Content3", null, 1));
        instanceList.add(new Activity(4, "Content4", null, 1));
        instanceList.add(new Activity(5, "Content5", null, 1));
        instanceList.add(new Activity(6, "Content6", null, 1));
        instance.setPartActs(instanceList);
        
        //Performing test
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
}
