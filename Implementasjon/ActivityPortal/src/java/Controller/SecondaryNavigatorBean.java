/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Database.*;
import java.util.ArrayList;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author Sygnious
 * 
 * Purpose is to handle what is strictly navigation when no action related to
 * user data or activity data occurs.
 */
@Named(value = "secNavB")
@SessionScoped
public class SecondaryNavigatorBean implements Serializable {

    private String currentPage;
    private ArrayList<String> interests;
    
    public SecondaryNavigatorBean() {
        
    }
    
    // A method for each possible navigation is not desirable, 
    // a record of current webpage would be of good help, other possible
    // parameters as well.
    
    // But due given the time constraint, this is chosen because it is easiest
    // implemented
    
    public String activityIndex(){
        return currentPage = "activityIndex";
    }
    
    public String activitySearch(){
        return currentPage = "activitySearch";
    }
    
    public String interestList() throws Exception{
        interests=Loader.loadAllInterestsNamesOnly();
        return "interestList";
    }

    /**
     * @return the currentPage
     */
    public String getCurrentPage() {
        return currentPage;
    }

    /**
     * @param currentPage the currentPage to set
     */
    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * @return the interests
     */
    public ArrayList<String> getInterests() {
        return interests;
    }

    /**
     * @param interests the interests to set
     */
    public void setInterests(ArrayList<String> interests) {
        this.interests = interests;
    }
    
}
