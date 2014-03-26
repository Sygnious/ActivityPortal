/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Database;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.sql.*;
import java.io.*;
import java.net.URL;

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
    
    //private static String relativeFilePath = "Database/SQLFiles/SQLCommandsRaw.sql";
    private static String absoluteFilePath = "/Users/Sygnious/Documents/Skole/4DAT/EksperterITeam/Systemutvikling/Implementasjon/ActivityPortal/src/java/Database/SQLFiles/SQLCommandsRaw.sql";
    
    // Prime method for Initiator, the entire database will be removed and
    // recreated using SQL-commands from SQLCommandsRaw.sql, one at a time.
    public static void resetDatabase(){// throws IOException{ //TODO: Replace with try and catch ??
        //First reading SQL file
        ArrayList<String> commands;
        try {
            commands = readSQLFile(absoluteFilePath);
            //commands = readSQLFile(relativeFilePath);
            ExecuteReset(commands);
        } catch (Exception e){
            ConnectionHandler.printError(e, "resetDatabase");
        }
    }
    
    /*
    // Method for removing without rebuilding database.
    public void wipeOut(){
        
    }*/
    
    // Sub-methods, in order to make try-catch block short in top-method
    private static void ExecuteReset(ArrayList<String> commands) throws Exception{
        Connection con = null;
        PreparedStatement stm = null;
        boolean drop = true;
        StringTokenizer st = null;
        try{
            con = ConnectionHandler.openConnection();
            
            for (String command : commands){
                
                if (drop){
                    // In dropping section, must be executed safely.
                    // Control first if is still in dropping section, and execute thereby
                    st = new StringTokenizer(command);
                    if (st.nextToken().equals("DROP")){
                        try{
                           stm = con.prepareStatement(command);
                           stm.execute(); 
                        }catch (SQLSyntaxErrorException e){
                            System.out.println("DROP NOT EXECUTED");
                        }
                    } else {
                        drop = false;
                        stm = con.prepareStatement(command);
                        stm.execute();
                    }
                } else {
                    // Not in dropping section, regular execute should work
                    stm = con.prepareStatement(command);
                    stm.execute();
                }
            }
        } catch (Exception e){
            throw e;
        } finally {
            ConnectionHandler.closeAll(null, stm, con);
        }
    }
    
    
    private static ArrayList<String> readSQLFile(String path) throws IOException{
        ArrayList<String> result = new ArrayList();
        
        FileReader fr = new FileReader(path);
        BufferedReader textReader = new BufferedReader(fr);
        String line;
        int i = 0;
        while ((line = textReader.readLine()) != null){
            result.add(line);
        }
        textReader.close();
        
        return result;
    }
    
    public static void main(String... args) throws Exception {
        URL location = Initiator.class.getProtectionDomain().getCodeSource().getLocation();
        String path = location.getFile();
        path += "Database/SQLFiles/SQLCommandsRaw.sql";
        System.out.println(path);
        
    }
    
}
