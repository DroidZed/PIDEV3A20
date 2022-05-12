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
public class Comment {
 
    
    private int idComment ;
    private Date postedDTM ;
    private int idPost ;
    private int idUser ; 
    private String comment ;

    public int getIdComment() {
        return idComment;
    }

    public void setIdComment(int idComment) {
        this.idComment = idComment;
    }

    public Date getPostedDTM() {
        return postedDTM;
    }

    public void setPostedDTM(Date postedDTM) {
        this.postedDTM = postedDTM;
    }

    public int getIdPost() {
        return idPost;
    }

    public void setIdPost(int idPost) {
        this.idPost = idPost;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Comment(int idPost, int idUser, String comment) {
        this.idPost = idPost;
        this.idUser = idUser;
        this.comment = comment;
    }
    
    

    
    
}
