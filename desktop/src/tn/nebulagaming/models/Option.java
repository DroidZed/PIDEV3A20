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

@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Option {
    @Getter private int idOption ; 
    @Getter @Setter Date createdDTM ; 
    @Getter @Setter int statusOption ; 
    @Getter @Setter String contentOption ; 
    @Getter @Setter int idPost ; 
    
    public Option (Date createdDTM , int statusOption , String contentOption , int idPost) {
        this.createdDTM = createdDTM ;
        this.statusOption = statusOption ;
        this.contentOption = contentOption ;
        this.idPost = idPost ;
    } 
}
