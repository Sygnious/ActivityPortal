/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ProblemDomainModel;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

/**
 *
 * @author Sygnious
 * 
 * Class for activity. Contains information, interest coverage and participating
 * users.
 */
public class Activity implements java.io.Serializable {
    private int activityId; // TODO: Concider usefulness, maybe controller alone should cooperate with database
    private String name;
    private String description;
    private GregorianCalendar date; //Concider usefulness, maybe database alone should filter
    private int townId;
    private String imageName;
    private ArrayList<String> interests; // This activity covers selected interests
    private ArrayList<User> participants; 
    
    //1 Constructors:
    public Activity(int activityId, String name, String description, int townId){
        this.activityId=activityId;
        this.name = name;
        this.description = description;
        this.townId = townId;
        this.date = new GregorianCalendar();
        this.interests = new ArrayList();
        this.participants = new ArrayList();
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
    
    //Input must be in format YYYY-MM-DD. Should only be used when reading date from database
    public GregorianCalendar convertToGregorian(String input){
        StringTokenizer st = new StringTokenizer(input, "-");
        int year = Integer.parseInt(st.nextToken());
        int month = Integer.parseInt(st.nextToken());
        int date = Integer.parseInt(st.nextToken());
        return new GregorianCalendar(year, month, date);
    }
    
    //Returns in format YYYY-MM-DD
    public String convertFromGregorian(GregorianCalendar input){
        int month = input.get(2);
        String zero = "";
        if (input.get(2)<10){
            zero="0";
        }
        return input.get(1)+"-"+zero+input.get(2)+"-"+input.get(5);
    }
    
    // Needed for web presentation
    public String getDateAsString(){
        return convertFromGregorian(date);
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

    /**
     * @return the imageName
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * @param imageName the imageName to set
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
    
    
}
