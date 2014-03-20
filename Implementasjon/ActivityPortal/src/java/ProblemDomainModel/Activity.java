/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ProblemDomainModel;

import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 *
 * @author Sygnious
 */
public class Activity {
    private int activityId; // TODO: Concider usefulness, maybe controller alone should cooperate with database
    private String name;
    private String description;
    private GregorianCalendar date; //Concider usefulness, maybe database alone should filter
    private int townId;
    private ArrayList<String> interests; // This activity covers selected interests
    private ArrayList<User> participants; 
    
    //1 Constructors:
    public Activity(int activityId, String name, String description, int townId){
        this.activityId=activityId;
        this.name = name;
        this.description = description;
        this.townId = townId;
        this.date = new GregorianCalendar(); //TODO: Find a way to handle this
        this.interests = new ArrayList<String>();
        this.participants = new ArrayList<User>();
    }
    //2 Class-specific methods:
    
    public void insertInterest(String newInterest){
        if (newInterest!=null && !newInterest.trim().equals("")){
            interests.add(newInterest);
        }
    }
    
    public void removeInterest(String deadInterest){
        if (deadInterest!=null && !deadInterest.trim().equals("")){
            interests.remove(deadInterest);
        }
    }
    
    public void insertUser(User newUser){
        if (newUser!=null){
            participants.add(newUser);
        }
    }
    
    public User findUserById(int userId){
        for (User selectedUser : participants){
            if (selectedUser.getUserId()==userId){
                return selectedUser;
            }
        }
        return null;
    }
    
    public void removeUser(User deadUser){
        if (deadUser!=null){
            participants.remove(deadUser);
        }
    }
    
    //3 Get- and set-methods:

    /**
     * @return the activityId
     */
    public int getActivityId() {
        return activityId;
    }

    /**
     * @param activityId the activityId to set
     */
    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the date
     */
    public GregorianCalendar getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    /**
     * @return the townId
     */
    public int getTownId() {
        return townId;
    }

    /**
     * @param townId the townId to set
     */
    public void setTownId(int townId) {
        this.townId = townId;
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

    /**
     * @return the participants
     */
    public ArrayList<User> getParticipants() {
        return participants;
    }

    /**
     * @param participants the participants to set
     */
    public void setParticipants(ArrayList<User> participants) {
        this.participants = participants;
    }
    
    
    
}
