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

public class Quiz extends Post{
    
    private int idCorrectAnswer ;
    
     public Quiz (Date postedDTM ,String titlePost , String descPost , int statusPost, String photoPost , String typePost,Date startDTM,Date endDTM, int idOwnerUser, int idCorrectAnswer) {
        super(postedDTM ,titlePost ,descPost ,statusPost, photoPost , typePost, startDTM, endDTM, idOwnerUser); 
        this.idCorrectAnswer = idCorrectAnswer ;
    }
     
    public int getIdCorrectAnswer () {return this.idCorrectAnswer ;}
    public void setIdCorrectAnswer (int idCorrectAnswer) {this.idCorrectAnswer = idCorrectAnswer;}  
}
