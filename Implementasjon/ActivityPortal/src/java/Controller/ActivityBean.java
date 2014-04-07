/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;
import ProblemDomainModel.*;
import Database.*;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.util.ArrayList;

/**
 *
 * @author Sygnious
 * 
 * Purpose of this class is all control related to handling activity data.
 */
@Named("actB")
@SessionScoped
public class ActivityBean implements java.io.Serializable {

    private Activity singleAct;
    private ArrayList<Activity> actList;
    private String currentPage;
    
    public ActivityBean(){
        // Compulsory Constructor
    }
    
    public String showAllActivities() throws Exception{
        setActList(Loader.loadAllActivities());
        return "activityList";
    }

    
    // Get- and set-methods
    
    /**
     * @return the singleAct
     */
    public Activity getSingleAct() {
        return singleAct;
    }

    /**
     * @param singleAct the singleAct to set
     */
    public void setSingleAct(Activity singleAct) {
        this.singleAct = singleAct;
    }

    /**
     * @return the actList
     */
    public ArrayList<Activity> getActList() {
        return actList;
    }

    /**
     * @param actList the actList to set
     */
    public void setActList(ArrayList<Activity> actList) {
        this.actList = actList;
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
    
    
}
