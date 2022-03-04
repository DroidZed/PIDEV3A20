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


public class Participation {
    private int idParticipation ;    
    private int rank ;
    private int result ; 
    private Date bookedDTM ;
    private int idUser ; 
    private int idPost ; 
    private int idPayType ;   
    
    public Participation (int rank,int result,Date bookedDTM,int idUser,int idPost , int idPayType) {
        this.rank = rank ;
        this.result = result ; 
        this.bookedDTM = bookedDTM ;
        this.idUser = idUser ;
        this.idPost = idPost ; 
        this.idPayType = idPayType ;
    }
    
    public Participation (int idParticipation , Date bookedDTM,int idUser,int idPost,int idPayType) {
        this.idParticipation = idParticipation ;
        this.bookedDTM = bookedDTM ;
        this.idUser = idUser ;
        this.idPost = idPost ; 
        this.idPayType = idPayType ;
    }
    
    public int getIdParticipation () { return this.idParticipation ;}
    
    public Date getBookedDTM () { return this.bookedDTM ;}
    public void setBookedDTM (Date bookedDTM) {this.bookedDTM = bookedDTM ;}
    
    public int getIdUser () {return this.idUser ;}
    public int getIdPost () {return this.idPost ;}
    public int getPayType () {return this.idPayType ;}
    
    public int getRank () {return this.rank ;}
    public void setRank (int rank) {this.rank = rank ;}
    
    public int getResult () {return this.result ;}
    public void setResult (int result) {this.result = result;}
    
    
     @Override
    public String toString() {
        return 
       
        " ,id Participation = "+idParticipation+ 
        " ,Date Booked = "+bookedDTM+
        " , id User = "+idUser+ 
        " ,id Post = "+idPost+
        " ,id Pay Type = "+idPayType+
        " ,Rank = "+rank+ 
        " ,Result = "+result;
    }
    
    
    
}
