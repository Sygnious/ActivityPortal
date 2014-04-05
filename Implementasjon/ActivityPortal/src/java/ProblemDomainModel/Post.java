/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ProblemDomainModel;

/**
 *
 * @author Sygnious
 */
public class Post implements java.io.Serializable{
    private int postId;
    private String postAddress;
    
    public Post(int postId, String postAddress){
        this.postId = postId;
        this.postAddress = postAddress;
    }

    /**
     * @return the postId
     */
    public int getPostId() {
        return postId;
    }

    /**
     * @param postId the postId to set
     */
    public void setPostId(int postId) {
        this.postId = postId;
    }

    /**
     * @return the postAddress
     */
    public String getPostAddress() {
        return postAddress;
    }

    /**
     * @param postAddress the postAddress to set
     */
    public void setPostAddress(String postAddress) {
        this.postAddress = postAddress;
    }
}
