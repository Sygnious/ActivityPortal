/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Database;

import java.sql.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Sygnious
 * 
 * Only one solely method is tested: Restarting the database.
 * The entire database will get all it's tables tested, by sizes and some
 * contents. The test names are based on tables.
 * 
 * No methods from Loader is used, to avoid interdependence between the classes
 * that are tested (should not depend on methods that are tested in LoaderTest,
 * which again depends on Initiator.resetDatabase, that is solely tested in this class).
 * 
 * Lenght of tables, and contents in first rows are tested
 */
public class InitiatorTest {
    int resultInt;
    String stmString, resultString;
    Connection con;
    PreparedStatement stm;
    ResultSet res;
    
    public InitiatorTest() {
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
        con = null;
        stm = null;
        res = null;
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of resetDatabase method, of class Initiator.
     */
    
    @Test
    public void testUserTable() {
        // Town
        // Size: 6
        assertEquals("Table \"Town\" has wrong size", 6, 44);
    }
    
    @Test
    public void testTownTable() {
        System.out.println("Testing table town");
        
        try{
            // Making connection:
            con = ConnectionHandler.openConnection();
            
            // Size:
            stmString = "SELECT COUNT(*) FROM town";
            stm = con.prepareStatement(stmString);
            res = stm.executeQuery();
            res.next(); 
            resultInt = res.getInt(1);
            assertEquals("Table \"Town\" has wrong size", 6, resultInt);
            
            // Selecting town with id = 1 and name = Trondheim:
            stmString = "SELECT FROM town WHERE town.town_id=1";
            stm = con.prepareStatement(stmString);
            res = stm.executeQuery();
            res.next(); 
            resultInt = res.getInt(1);
            resultString = res.getString(2);
            assertEquals("Row from \"Town\" has wrong town_id", 54, resultInt);
            assertEquals("Row from \"Town\" has wrong town_name", "Bergen", resultString);
            
        } catch (Exception e){
            ConnectionHandler.printSQLError(e, "testTownTable()");
            fail("Exception caused test to fail");
        } finally {
            ConnectionHandler.closeAll(res, stm, con);
        }
        
        // Size: 6
        
    }
    
    
    
}
