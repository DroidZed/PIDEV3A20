/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.models;

import java.sql.Date;

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
    
    public Comment (int idComment , Date postedDate , int idPost , int idUser , String comment) {
        this.idComment = idComment;
        this.postedDTM = postedDate ;
        this.idPost = idPost ;
        this.idUser = idUser ;
        this.comment = comment ;
    }
    
    public Comment ( Date postedDate , int idPost , int idUser , String comment) {
        this.postedDTM = postedDate ;
        this.idPost = idPost ;
        this.idUser = idUser ;
        this.comment = comment ;
    }
    
    public int getIdComment () { return this.idComment ;}
    
    public Date getPostedDTM () {return this.postedDTM ;}
    
    public int getIdUser () {return this.idUser ;}
    
    public int getIdPost () {return this.idPost ;}
    
    public String getComment () {return this.comment ;}
    
    @Override
    public String toString() {
        return 
       
        " ,id Comment = "+idComment+ 
        " ,Posted Date = "+postedDTM+
        " , id User = "+idUser+ 
        " ,id Post = "+idPost+
        " ,Comment = "+comment;
    }
    
    
}
