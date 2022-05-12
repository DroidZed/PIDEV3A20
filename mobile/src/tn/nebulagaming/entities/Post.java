/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.entities;

import java.util.Date;


/**
 *
 * @author SuperNova
 */
public class Post {
     private int idPost ; 
    private Date postedDTM ; 
    private String titlePost ; 
    private String descPost ; 
    private int statusPost ;
    private String photoPost ;  
    private String typePost ; 
    private Date startDTM ;
    private Date endDTM ;
    private int idOwnerUser ;  
    
      public Post (Date postedDTM ,String titlePost , String descPost , int statusPost, String photoPost , String typePost,Date startDTM,Date endDTM, int idOwnerUser) {
        this.postedDTM = postedDTM ;    
        this.titlePost = titlePost ;
        this.descPost = descPost ;
        this.statusPost = statusPost ;
        this.photoPost = photoPost ;
        this.typePost = typePost ;
        this.startDTM = startDTM ;
        this.endDTM = endDTM ;
        this.idOwnerUser = idOwnerUser ; 
    }
      
      public Post () {
      
      }

    public int getIdPost() {
        return idPost;
    }

    public void setIdPost(int idPost) {
        this.idPost = idPost;
    }

    public Date getPostedDTM() {
        return postedDTM;
    }

    public void setPostedDTM(Date postedDTM) {
        this.postedDTM = postedDTM;
    }

    public String getTitlePost() {
        return titlePost;
    }

    public void setTitlePost(String titlePost) {
        this.titlePost = titlePost;
    }

    public String getDescPost() {
        return descPost;
    }

    public void setDescPost(String descPost) {
        this.descPost = descPost;
    }

    public int getStatusPost() {
        return statusPost;
    }

    public void setStatusPost(int statusPost) {
        this.statusPost = statusPost;
    }

    public String getPhotoPost() {
        return photoPost;
    }

    public void setPhotoPost(String photoPost) {
        this.photoPost = photoPost;
    }

    public String getTypePost() {
        return typePost;
    }

    public void setTypePost(String typePost) {
        this.typePost = typePost;
    }

    public Date getStartDTM() {
        return startDTM;
    }

    public void setStartDTM(Date startDTM) {
        this.startDTM = startDTM;
    }

    public Date getEndDTM() {
        return endDTM;
    }

    public void setEndDTM(Date endDTM) {
        this.endDTM = endDTM;
    }

    public int getIdOwnerUser() {
        return idOwnerUser;
    }

    public void setIdOwnerUser(int idOwnerUser) {
        this.idOwnerUser = idOwnerUser;
    }
      
      
      
}
