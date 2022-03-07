/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.models;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author SuperNova
 */

public class Answer {
    
    private int idAnswer ;
    private int idPost ; 
    private int idUser ;
    private Date answeredDTM ; 
    private String answer ; 
    
      public Answer (Date answeredDTM ,String answer , int idPost , int idUser ) {
        this.answeredDTM = answeredDTM ;
        this.answer = answer ;
        this.idPost = idPost ;
        this.idUser = idUser ;
    }
      
      
      
      public int getIdUser () { return this.idUser ;}
      public int getIdPost () { return this.idPost ;}
      public String getAnswer () { return this.answer ;}
      public Date getAnsweredDTM () { return this.answeredDTM ;}

    
}
