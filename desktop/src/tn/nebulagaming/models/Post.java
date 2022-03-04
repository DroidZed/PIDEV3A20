/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.models;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
      

    public Post (int idPost ,String titlePost ,String descPost ,int statusPost,String photoPost , Date startDTM, Date endDTM) {
    this.idPost = idPost ;
    this.titlePost = titlePost ;
    this.descPost = descPost ;
    this.statusPost = statusPost ;
    this.photoPost = photoPost ;
    this.startDTM = startDTM ;
    this.endDTM  = endDTM ;
    }
  
    
    public Post (int idPost ,Date postedDTM , String titlePost , String descPost , int statusPost, String photoPost , int idOwnerUser) {  
        this.idPost = idPost ;
        this.postedDTM = postedDTM ;
        this.titlePost = titlePost ;
        this.descPost = descPost ;  
        this.statusPost = statusPost ;
        this.photoPost = photoPost ;
        this.idOwnerUser = idOwnerUser ; 
    }
    
    //Add Event or Quiz
    public Post (int idPost , Date postedDTM ,String titlePost ,String descPost ,int statusPost, String photoPost ,Date startDTM, Date endDTM, int idOwnerUser) {
        this.idPost = idPost ;
        this.postedDTM = postedDTM ;
        this.titlePost = titlePost ;
        this.descPost = descPost ;  
        this.statusPost = statusPost ;
        this.startDTM = startDTM ;
        this.endDTM = endDTM ;
        this.photoPost = photoPost ;
        this.idOwnerUser = idOwnerUser ; 
    }

    //Add Post 
    public Post (Date postedDTM, String titlePost, String descPost, int statusPost,String typePost ,String photoPost, int idUser) {
      this.postedDTM = postedDTM ;
      this.titlePost = titlePost ;
      this.descPost = descPost ;
      this.statusPost = statusPost ;
      this.typePost = typePost;
      this.photoPost = photoPost ;
      this.idOwnerUser = idUser ;
    }
    
    public Post (int idPost, String titlePost, String descPost,String photoPost, int statusPost) {
      this.idPost = idPost ;
      this.titlePost = titlePost ;
      this.descPost = descPost ;
      this.statusPost = statusPost ;
      this.photoPost = photoPost ;
    }
    
    public Post (Date postedDTM, String titlePost, String descPost, int statusPost,String photoPost, int idUser) {
      this.postedDTM = postedDTM ;
      this.titlePost = titlePost ;
      this.descPost = descPost ;
      this.statusPost = statusPost ;
      this.photoPost = photoPost ;
      this.idOwnerUser = idUser ;
    }
    
    public int getIdPost() { return this.idPost ;}
    public void setIdPost(int idPost) {this.idPost = idPost ;}

    public Date getPostedDTM() { return this.postedDTM ;}
    public void setPostedDTM(Date postedDTM) { this.postedDTM = postedDTM ;}

    public String getTitlePost() {return this.titlePost ;}
    public void setTitlePost(String titlePost) {this.titlePost = titlePost ;}


    public String getDescPost() { return this.descPost ;}
    public void setDescPost(String descPost) { this.descPost = descPost ;}


    public int getStatusPost () { return this.statusPost ;}
    public void setStatusPost (int statusPost) {this.statusPost = statusPost ;}


    public String getPhotoPost () { return this.photoPost ;}
    public void setPhotoPost (String photoPost) { this.photoPost = photoPost ;}

    public String getTypePost () { return this.typePost ;}
    public void setTypePost (String typePost) { this.typePost = typePost ;}

    
    public Date getStartDTM() {return this.startDTM ;}
    public void setStartDTM(Date startDTM) {this.startDTM = startDTM;}


    public Date getEndDTM () {return this.endDTM ;}
    public void setEndDTM(Date endDTM) {this.endDTM = endDTM;}
  

    public int getIdOwnerUser () {return this.idOwnerUser ;}
    public void setOwnerUser (int idOwnerUser) {this.idOwnerUser = idOwnerUser ;}

    
    
    @Override
    public String toString() {
        return 
       
        " ,Post Title = "+titlePost+ 
        " ,Post Description = "+descPost+
        " , Date posted = "+postedDTM+ 
        " ,Post Status = "+statusPost+
        " ,Post cover = "+photoPost+
        " ,Owner Id = "+idOwnerUser ;
    }
    

  

}
