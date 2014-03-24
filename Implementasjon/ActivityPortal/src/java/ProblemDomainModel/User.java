/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ProblemDomainModel;

import java.util.ArrayList;

/**
 *
 * @author Sygnious
 * 
 * Class of User. Contains information about User, interests and participates
 * activites.
 */
public class User {
    private int userId; // TODO: Concider usefulness, maybe controller alone should cooperate with database
    private String firstName;
    private String surName;
    private int age;
    private String address;
    private Post postAddress;
    private ArrayList<String> interests;
    private ArrayList<Activity> partActs; //partActs stands for participated activities
    //private ArrayList<User> friends;    //TODO: Implement at lager stage
    
    //1 Constructors:
    
    // Regular constructor
    public User(int userId, String firstName, String surName, int age, String address, Post postAddress){
        //userID needs to be unique. Should be handled outside, by a database operator
        this.userId = userId; 
        this.firstName = firstName;
        this.surName = surName;
        this.age = age;
        this.address = address;
        this.postAddress = postAddress;
        this.interests = new ArrayList<String>();
        this.partActs = new ArrayList<Activity>();
    }
    // Constructor using 2 String inputs for postAddress instead of object
    public User(int userId, String firstName, String surName, int age, String address, int postId, String postAddress){
        //userID needs to be unique. Should be handled outside, by a database operator
        this.userId = userId; 
        this.firstName = firstName;
        this.surName = surName;
        this.age = age;
        this.address = address;
        this.postAddress = new Post(postId, postAddress);
        this.interests = new ArrayList<String>();
        this.partActs = new ArrayList<Activity>();
    }
    
    public void insertInterest(String newInterest){
        if (newInterest!=null && !newInterest.trim().equals("")){
            interests.add(newInterest);
        }
    }
    
    //2 Class-specific methods:
    public void removeInterest(String deadInterest){
        if (deadInterest!=null && !deadInterest.trim().equals("")){
            interests.remove(deadInterest);
        }
    }
    
    public void insertActivity(Activity newActivity){
        if (newActivity!=null){
            partActs.add(newActivity);
        }
    }
    
    public Activity findActivityById(int activityId){
        for (Activity selectedActivity : partActs){
            if (selectedActivity.getActivityId()==activityId){
                return selectedActivity;
            } 
       }
        return null;
    }
    
    public void removeActivity(Activity deadActivity){
        if (deadActivity!=null){
            partActs.remove(deadActivity);
        }
    }

    
    //3 Get- and set-methods
    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the surName
     */
    public String getSurName() {
        return surName;
    }

    /**
     * @param surName the surName to set
     */
    public void setSurName(String surName) {
        this.surName = surName;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the postAddress
     */
    public Post getPostAddress() {
        return postAddress;
    }

    /**
     * @param postAddress the postAddress to set
     */
    public void setPostAddress(Post postAddress) {
        this.postAddress = postAddress;
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
     * @return the partActs
     */
    public ArrayList<Activity> getPartActs() {
        return partActs;
    }

    /**
     * @param partActs the partActs to set
     */
    public void setPartActs(ArrayList<Activity> partActs) {
        this.partActs = partActs;
    }
    
}
