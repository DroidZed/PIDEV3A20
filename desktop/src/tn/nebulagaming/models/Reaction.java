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
public class Reaction {
    @Getter private int idReaction ;
    @Getter @Setter private int idTypeReact ; 
    @Getter @Setter private Date reactedDTM ; 
    @Getter @Setter private int idUser ; 
    @Getter @Setter private int idPost ; 
    @Getter @Setter private int idComment ; 
    
    //Constructor for Reaction on Post or comment 
    public Reaction (int idTypeReact,Date reactedDTM,int idUser,int idPost,int idComment) {
    this.idTypeReact = idTypeReact ;
    this.reactedDTM = reactedDTM ;
    this.idUser = idUser ;
    this.idPost = idPost ; 
    this.idComment = idComment ;  
    }
    
    public Reaction (int idReaction ,int idTypeReact , Date reactedDTM,int idPost,int idUser) {
    this.idReaction = idReaction ;
    this.idTypeReact = idTypeReact ;
    this.reactedDTM = reactedDTM ;
    this.idUser = idUser ;
    this.idPost = idPost ; 
    }
}
