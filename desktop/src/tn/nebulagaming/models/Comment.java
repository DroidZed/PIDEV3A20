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
public class Comment {
    @Getter @Setter private int idComment ;
    @Getter @Setter private Date postedDTM ;
    @Getter @Setter private int idPost ;
    @Getter @Setter private int idUser ; 
    @Getter @Setter private String comment ;
    
    public Comment (Date postedDate , int idPost , int idUser , String comment) {
        this.postedDTM = postedDate ;
        this.idPost = idPost ;
        this.idUser = idUser ;
        this.comment = comment ;
    }
}
