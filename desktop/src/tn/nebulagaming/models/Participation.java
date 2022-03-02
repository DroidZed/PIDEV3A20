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
public class Participation {
    @Getter private int idParticipation ;    
    @Getter @Setter private int rank ;
    @Getter @Setter private int result ; 
    @Getter @Setter private Date bookedDTM ;
    @Getter @Setter private int idUser ; 
    @Getter @Setter private int idPost ; 
    @Getter @Setter private int idPayType ;   
    
    public Participation (int rank,int result,Date bookedDTM,int idUser,int idPost) {
        this.rank = rank ;
        this.result = result ; 
        this.bookedDTM = bookedDTM ;
        this.idUser = idUser ;
        this.idPost = idPost ; 
    }
}
