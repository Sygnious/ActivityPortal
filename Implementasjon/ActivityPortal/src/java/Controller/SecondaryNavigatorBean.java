/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

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
    
}
