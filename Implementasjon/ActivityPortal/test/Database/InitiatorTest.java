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
            
            // Selecting first instance and checking details
            stmString = "SELECT * FROM town";
            stm = con.prepareStatement(stmString);
            res = stm.executeQuery();
            res.next(); 
            resultInt = res.getInt(1);
            resultString = res.getString(2);
            assertEquals("Row from \"Town\" has wrong town_id", 1, resultInt);
            assertEquals("Row from \"Town\" has wrong town_name", "Trondheim", resultString);
            
        } catch (Exception e){
            ConnectionHandler.printError(e, "testTownTable()");
            fail("Exception caused test to fail");
        } finally {
            ConnectionHandler.closeAll(res, stm, con);
        }
    }
    
    @Test
    public void testPostTable() {
        System.out.println("Testing table post");
        
        try{
            // Making connection:
            con = ConnectionHandler.openConnection();
            
            // Size:
            stmString = "SELECT COUNT(*) FROM post";
            stm = con.prepareStatement(stmString);
            res = stm.executeQuery();
            res.next(); 
            resultInt = res.getInt(1);
            assertEquals("Table \"Post\" has wrong size", 13, resultInt);
            
            // Selecting first instance and checking details
            stmString = "SELECT * FROM post";
            stm = con.prepareStatement(stmString);
            res = stm.executeQuery();
            res.next(); 
            resultInt = res.getInt(1);
            resultString = res.getString(2);
            assertEquals("Row from \"Post\" has wrong post_id", 7227, resultInt);
            assertEquals("Row from \"Post\" has wrong post_address", "Gimse", resultString);
            resultInt = res.getInt(3);
            assertEquals("Row from \"Post\" has wrong town_id", 2, resultInt);
        } catch (Exception e){
            ConnectionHandler.printError(e, "testPostTable()");
            fail("Exception caused test to fail");
        } finally {
            ConnectionHandler.closeAll(res, stm, con);
        }
    }
    
    @Test
    public void testPersonTable() {
        System.out.println("Testing table person");
        
        try{
            // Making connection:
            con = ConnectionHandler.openConnection();
            
            // Size:
            stmString = "SELECT COUNT(*) FROM person";
            stm = con.prepareStatement(stmString);
            res = stm.executeQuery();
            res.next(); 
            resultInt = res.getInt(1);
            assertEquals("Table \"Person\" has wrong size", 16, resultInt);
            
            // Selecting first instance and checking details
            stmString = "SELECT * FROM person";
            stm = con.prepareStatement(stmString);
            res = stm.executeQuery();
            res.next(); 
            resultInt = res.getInt(1);
            resultString = res.getString(2);
            assertEquals("Row from \"Person\" has wrong person_id", 1, resultInt);
            assertEquals("Row from \"Person\" has wrong first_name", "Torbjørn", resultString);
            resultString = res.getString(3);
            assertEquals("Row from \"Person\" has wrong last_name", "Langland", resultString);
            resultInt = res.getInt(4);
            assertEquals("Row from \"Person\" has wrong age", 26, resultInt);
            resultString = res.getString(5);
            assertEquals("Row from \"Person\" has wrong address", "Moholt Allé 03-32 H0302", resultString);
            resultInt = res.getInt(6);
            assertEquals("Row from \"Person\" has wrong post_id", 7050, resultInt);
            
        } catch (Exception e){
            ConnectionHandler.printError(e, "testPersonTable()");
            fail("Exception caused test to fail");
        } finally {
            ConnectionHandler.closeAll(res, stm, con);
        }
    }
    
    @Test
    public void testInterestTable() {
        System.out.println("Testing table interest");
        
        try{
            // Making connection:
            con = ConnectionHandler.openConnection();
            
            // Size:
            stmString = "SELECT COUNT(*) FROM interest";
            stm = con.prepareStatement(stmString);
            res = stm.executeQuery();
            res.next(); 
            resultInt = res.getInt(1);
            assertEquals("Table \"Interest\" has wrong size", 9, resultInt);
            
            // Selecting first instance and checking details
            stmString = "SELECT * FROM interest";
            stm = con.prepareStatement(stmString);
            res = stm.executeQuery();
            res.next(); 
            resultInt = res.getInt(1);
            resultString = res.getString(2);
            assertEquals("Row from \"Interest\" has wrong interest_id", 1, resultInt);
            assertEquals("Row from \"Interest\" has wrong interest_name", "Teater og kultur", resultString);
            resultString = res.getString(3);
            assertEquals("Row from \"Interest\" has wrong interest_description", "Forestillinger på teater, besøk på museum, informasjon og ordninger til kulturarrangement.", resultString);
        } catch (Exception e){
            ConnectionHandler.printError(e, "testInterestTable()");
            fail("Exception caused test to fail");
        } finally {
            ConnectionHandler.closeAll(res, stm, con);
        }
    }
    
    @Test
    public void testPersonInterestTable() {
        System.out.println("Testing table person_interest");
        
        try{
            // Making connection:
            con = ConnectionHandler.openConnection();
            
            // Size:
            stmString = "SELECT COUNT(*) FROM person_interest";
            stm = con.prepareStatement(stmString);
            res = stm.executeQuery();
            res.next(); 
            resultInt = res.getInt(1);
            assertEquals("Table \"Person_Interest\" has wrong size", 55, resultInt);
            
            // Selecting first instance and checking details
            stmString = "SELECT * FROM person_interest";
            stm = con.prepareStatement(stmString);
            res = stm.executeQuery();
            res.next(); 
            resultInt = res.getInt(1);
            assertEquals("Row from \"Person_Interest\" has wrong person_id", 1, resultInt);
            resultInt = res.getInt(1);
            assertEquals("Row from \"Person_Interest\" has wrong interest_id", 1, resultInt);
        } catch (Exception e){
            ConnectionHandler.printError(e, "testPersonInterestTable()");
            fail("Exception caused test to fail");
        } finally {
            ConnectionHandler.closeAll(res, stm, con);
        }
    }
    
    @Test
    public void testActivityTable() {
        System.out.println("Testing table activity");
        
        try{
            // Making connection:
            con = ConnectionHandler.openConnection();
            
            // Size:
            stmString = "SELECT COUNT(*) FROM activity";
            stm = con.prepareStatement(stmString);
            res = stm.executeQuery();
            res.next(); 
            resultInt = res.getInt(1);
            assertEquals("Table \"Activity\" has wrong size", 14, resultInt);
            
            // Selecting first instance and checking details
            stmString = "SELECT * FROM activity";
            stm = con.prepareStatement(stmString);
            res = stm.executeQuery();
            res.next(); 
            resultInt = res.getInt(1);
            resultString = res.getString(2);
            assertEquals("Row from \"activity\" has wrong activity_id", 1, resultInt);
            assertEquals("Row from \"activity\" has wrong activity_name", "Teater: \"Kristin Lavransdatter\"", resultString);
            resultString = res.getString(3);
            resultInt = res.getInt(4);
            assertEquals("Row from \"activity\" has wrong activity_description", "Forestilling med \"Kristing Lavransdatter\" på Trøndelag Teater. Oppmøte kl 18:00", resultString);
            assertEquals("Row from \"activity\" has wrong town_id", 1, resultInt);
            resultString = res.getString(5);
            assertEquals("Row from \"activity\" has wrong activity_date", "2014-04-09", resultString);
        } catch (Exception e){
            ConnectionHandler.printError(e, "testActivityTable()");
            fail("Exception caused test to fail");
        } finally {
            ConnectionHandler.closeAll(res, stm, con);
        }
    }
    
    @Test
    public void testActivityInterestTable() {
        System.out.println("Testing table activity_interest");
        
        try{
            // Making connection:
            con = ConnectionHandler.openConnection();
            
            // Size:
            stmString = "SELECT COUNT(*) FROM activity_interest";
            stm = con.prepareStatement(stmString);
            res = stm.executeQuery();
            res.next(); 
            resultInt = res.getInt(1);
            assertEquals("Table \"Activity_Interest\" has wrong size", 15, resultInt);
            
            // Selecting first instance and checking details
            stmString = "SELECT * FROM activity_interest";
            stm = con.prepareStatement(stmString);
            res = stm.executeQuery();
            res.next(); 
            resultInt = res.getInt(1);
            assertEquals("Row from \"activity_interest\" has wrong activity_id", 1, resultInt);
            resultInt = res.getInt(1);
            assertEquals("Row from \"activity_interest\" has wrong interest_id", 1, resultInt);
        } catch (Exception e){
            ConnectionHandler.printError(e, "testActivityInterestTable()");
            fail("Exception caused test to fail");
        } finally {
            ConnectionHandler.closeAll(res, stm, con);
        }
    }
    
    @Test
    public void testActivityPersonTable() {
        System.out.println("Testing table activity_person");
        
        try{
            // Making connection:
            con = ConnectionHandler.openConnection();
            
            // Size:
            stmString = "SELECT COUNT(*) FROM activity_person";
            stm = con.prepareStatement(stmString);
            res = stm.executeQuery();
            res.next(); 
            resultInt = res.getInt(1);
            assertEquals("Table \"Activity_Person\" has wrong size", 40, resultInt);
            
            // Selecting first instance and checking details
            stmString = "SELECT * FROM activity_person";
            stm = con.prepareStatement(stmString);
            res = stm.executeQuery();
            res.next(); 
            resultInt = res.getInt(1);
            assertEquals("Row from \"activity_interest\" has wrong activity_id", 1, resultInt);
            resultInt = res.getInt(1);
            assertEquals("Row from \"activity_interest\" has wrong person_id", 1, resultInt);
        } catch (Exception e){
            ConnectionHandler.printError(e, "testActivityPersonTable()");
            fail("Exception caused test to fail");
        } finally {
            ConnectionHandler.closeAll(res, stm, con);
        }
    }
}
