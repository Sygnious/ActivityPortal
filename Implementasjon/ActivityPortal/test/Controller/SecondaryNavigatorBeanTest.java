/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

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
    
}
