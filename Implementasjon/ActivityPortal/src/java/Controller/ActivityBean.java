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

/**
 *
 * @author Sygnious
 * 
 * Purpose of this class is all control related to handling activity data.
 */
@Named("actB")
@SessionScoped
public class ActivityBean implements java.io.Serializable {

    private Activity singleActivity;
    private String currentString;
    
    public ActivityBean(){
        
    }
    
}
