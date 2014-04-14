/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;
import ProblemDomainModel.*;


import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.inject.Inject;

/**
 *
 * @author Sygnious
 * 
 * Purpose is to offer methods for cooperation between the User-objects and
 * Activity-objects since UserBean and ActivityBean are independent. 
 */
@Named(value = "coopB")
@SessionScoped
public class CooperationBean implements Serializable {

    @Inject private ActivityBean actBean;
    @Inject private UserBean userBean;
    
    /**
     * Creates a new instance of CooperationBean
     */
    public CooperationBean() {
    }
    
    // Used for rendering between "Sign up" and "Cancel Sign up" - buttons
    public boolean findParticipationOnUserID(){
        return (userBean.getSingleUser().participatesInActivity(actBean.getSingleAct().getActivityId()));
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
