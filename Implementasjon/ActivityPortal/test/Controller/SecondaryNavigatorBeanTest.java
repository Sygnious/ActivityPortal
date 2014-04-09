/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

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
public class SecondaryNavigatorBeanTest {
    
    private String instanceString; // Many methods will include navigation
    private SecondaryNavigatorBean instance;
    
    public SecondaryNavigatorBeanTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
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
    public void testActivityIndex() {
        System.out.println("activityIndex");
        instanceString = new SecondaryNavigatorBean().activityIndex();
        assertEquals("Incorrect page navigation string", "activityIndex", instanceString);
    }
    
    @Test
    public void testActivitySearch() {
        System.out.println("activitySearch");
        instanceString = new SecondaryNavigatorBean().activitySearch();
        assertEquals("Incorrect page navigation string", "activitySearch", instanceString);
    }
    
    @Test
    public void testInterestList() throws Exception{
        System.out.println("interestList");
        instance = new SecondaryNavigatorBean();
        instanceString = instance.interestList();
        assertEquals("Incorrect page navigation string", "interestList", instanceString);
        assertEquals("array size is wrong", 9, instance.getInterests().size());
        assertEquals("first instance in interestlist is wrong", "Teater og kultur", instance.getInterests().get(0));
    }
    
}
