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
    private String keyWord;
    
    public ActivityBean(){
        // Compulsory Constructor
    }
    
    // Methods for loading activities
    
    public String showActivityDetails(Object obj) throws Exception{
        if (obj instanceof Activity){
            singleAct = (Activity) obj;
            singleAct = Loader.loadSingleActivityOnID(singleAct.getActivityId());
            return currentPage = "activityOne";
        }
        
        return null;
    }
    
    public String showAllActivities() throws Exception{
        actList = Loader.loadAllActivities();
        return currentPage = "activityList";
    }

    public String showActivitiesByInterest(Object obj) throws Exception{
        if (obj instanceof String){
            String input = (String) obj;
            actList = Loader.loadActivitiesOnInterest(input);
            return currentPage = "activityList";
        }
        return null;
    }
    
    public String showActivitiesBySearch() throws Exception{
        if (keyWord==null || keyWord.trim().equals("")){
            return currentPage = "activitySearch"; // SecondNavigatorBean operates independently
        }
        actList = Loader.loadActivitiesOnSearch(keyWord);
        return currentPage = "activityList";
    }
    
    // Methods for storing activities?
    
    /*
    
    Insert something here, if deemed necessary
    
    */
    
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

    /**
     * @return the keyWord
     */
    public String getKeyWord() {
        return keyWord;
    }

    /**
     * @param keyWord the keyWord to set
     */
    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
    
    
}
