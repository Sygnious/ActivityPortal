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
import java.io.Serializable;
import javax.inject.Inject;

/**
 *
 * @author Sygnious
 * 
 * Purpose is to offer methods that requires data from both ActivityBean 
 * and UserBean
 */
@Named(value = "coopB")
@SessionScoped
public class CooperationBean implements Serializable {

    @Inject private ActivityBean actBean;
    @Inject private UserBean userBean;
    boolean isRendered;
    
    /**
     * Creates a new instance of CooperationBean
     */
    public CooperationBean() {
    }
    
    // Used for rendering between "Sign up" and "Cancel Sign up" - buttons.
    public boolean findParticipationOnUserID(){
        return (userBean.getSingleUser().participatesInActivity(actBean.getSingleAct().getActivityId()));
    }
    
    // The get-method used for the rendering
    public boolean getIsRendered(){
        return isRendered = findParticipationOnUserID();
    }
    
    // Methods for signing User to or from activities.
    // The incorrect method should never be in display on the webpages,
    // by rendering with the findParticipationOnUserID-method.
    // Still, they will also be used internal in method for double control.
    // Methods should update both database and both user's list of participated
    // activites, sorted by date.
    
    // Note: User's list of activities is refreshed by reloading from database
    // Should ideally be implemented by adding seperate activity to own list,
    // and sort by using date comparison.
    // Use of database reloading is currently selected in order to save
    // implementation time.
    public String signUserOnActivity() throws Exception{
        // Control: Make sure user does NOT already participate
        if (findParticipationOnUserID()){
            return "error";
        }
        // Store user to activity, in database table activity_person:
        Storer.storeUserToActivity(userBean.getSingleUser(), actBean.getSingleAct());
        // Update user's activity list in class:
        userBean.setSingleUser(Loader.loadSingleUserOnID(userBean.getSingleUser().getUserId()));
        
        return "activitySignedOn";
    }
    
    // Using word "cancel" instead of "sign" in order to avoid confusion.
    public String cancelUserFromActivity() throws Exception{ 
        // Control: Make sure user DOES participate
        if (!findParticipationOnUserID()){
            return "error";
        }
        // Store user to activity, in database table activity_person:
        Storer.deleteUserFromActivity(userBean.getSingleUser(), actBean.getSingleAct());
        // Update user's activity list in class:
        userBean.setSingleUser(Loader.loadSingleUserOnID(userBean.getSingleUser().getUserId()));
        
        return "activityCancelled";
    }
    
    

    // Get and set-methods.
    // Likely useless for the application itself, if beans are injected
    // as the system starts up. But they are needed for the testclasses.
    
    /**
     * @return the actBean
     */
    public ActivityBean getActBean() {
        return actBean;
    }

    /**
     * @param actBean the actBean to set
     */
    public void setActBean(ActivityBean actBean) {
        this.actBean = actBean;
    }

    /**
     * @return the userBean
     */
    public UserBean getUserBean() {
        return userBean;
    }

    /**
     * @param userBean the userBean to set
     */
    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }
    
    
    
}
