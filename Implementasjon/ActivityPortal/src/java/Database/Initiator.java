/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Database;

/**
 *
 * @author Sygnious
 * 
 * Database Class which sole purpose is to reset the entire database.
 * This will be essential for running tests of the loader and storer,
 * as well as running demonstration of the finished prototype
 * 
 * The SQL-codes are stored in the file SQLCommandsRaw.sql.
 * This is essentially the same as the first file SQLCommands.sql,
 * with the difference being that all comments and empty lines are removed
 * in order to avoid errors caused when reading file.
 */


public class Initiator {
    
    // Prime method for Initiator, the entire database will be removed and
    // recreated using SQL-commands from SQLCommandsRaw.sql, one at a time.
    public static void resetDatabase(){
        
    }
    
    /*
    // Method for removing without rebuilding database.
    public void wipeOut(){
        
    }*/
    
}
