/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;
import ProblemDomainModel.*;
import Database.*;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.util.ArrayList;

/**
 *
 * @author Sygnious
 * 
 * Purpose of this class is all control related to handling user data.
 */
@Named("userB")
@SessionScoped
public class UserBean implements java.io.Serializable{

    private User singleUser = new User();
    private User otherUser = new User();
    private ArrayList<User> userList = new ArrayList();
    private String currentPage;
    private String keyWord;
    
    public UserBean(){
        // Compulsory Constructor
    }
    
    // Used for login from first page.
    // Static choice of user for this prototype project.
    // Currently as Torbjørn Langland. May change. 
    public String logIn() throws Exception{
        singleUser = Loader.loadSingleUserOnID(1);
        return currentPage = "mainPage";
    }
    
    public String logOut(){
        return currentPage = "index";
    }
    
    // For restarting the entire prototype demostration.
    // Closely related to logout, plus extra restart functionality.
    // Therefore added to UserBean.
    public String reset() throws Exception{
        Initiator.resetDatabase();
        singleUser = new User();
        return currentPage = "index";
    }
    
    public String showAllOtherUsers() throws Exception{
        userList = Loader.loadAllUsersExceptOwn(singleUser.getUserId());
        return currentPage = "userList";
    }    
    
    public String showUsersBySearch() throws Exception{
        if (keyWord==null || keyWord.trim().equals("")){
            return currentPage = "userSearch";
        }
        userList = Loader.loadUsersOnSearchExceptOwn(singleUser.getUserId(), keyWord);
        return "userList";
    }
    
    public String selectUser(Object obj) throws Exception{
        if (obj instanceof User){
            User instance = (User) obj;
            otherUser = Loader.loadSingleUserOnID(instance.getUserId());
            return currentPage = "userOne";
        }
        
        return null;
    }
    
    public String addFriend() throws Exception{
        if (singleUser.hasFriend(otherUser.getUserId())){
            return "error";
        }
        Storer.storeFriendOfUser(singleUser, otherUser);
        singleUser = Loader.loadSingleUserOnID(singleUser.getUserId());
        return "userFriendAdded";
    }
    
    public String deleteFriend() throws Exception{
        if (!singleUser.hasFriend(otherUser.getUserId())){
            return "error";
        }
        Storer.deleteFriendOfUser(singleUser, otherUser);
        singleUser = Loader.loadSingleUserOnID(singleUser.getUserId());
        return "userFriendDeleted";
    }
    
    public String updateUser() throws Exception{
        Storer.storeNewUserDetails(singleUser);
        return "userUpdate";
    }
    
    // Get-method used for rendering
    public boolean getIsRendered(){
        return singleUser.hasFriend(otherUser.getUserId());
    }
    // Get- and set-methods
    
    /**
     * @return the singleUser
     */
    public User getSingleUser() {
        return singleUser;
    }

    /**
     * @param singleUser the singleUser to set
     */
    public void setSingleUser(User singleUser) {
        this.singleUser = singleUser;
    }

    /**
     * @return the userList
     */
    public ArrayList<User> getUserList() {
        return userList;
    }

    /**
     * @param userList the userList to set
     */
    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
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
     * @return the otherUser
     */
    public User getOtherUser() {
        return otherUser;
    }

    /**
     * @param otherUser the otherUser to set
     */
    public void setOtherUser(User otherUser) {
        this.otherUser = otherUser;
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
