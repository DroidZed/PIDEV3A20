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

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Answer {
    
    @Getter private int idAnswer ;
    @Getter @Setter private int idPost ; 
    @Getter @Setter private int idUser ;
    @Getter @Setter private Date answeredDTM ; 
    @Getter @Setter private int idOptionAnswer ; 
    
      public Answer (Date answeredDTM ,int idOptionAnswer , int idPost , int idUser ) {
        this.answeredDTM = answeredDTM ;
        this.idOptionAnswer = idOptionAnswer ;
        this.idPost = idPost ;
        this.idUser = idUser ;
    }
    
}
