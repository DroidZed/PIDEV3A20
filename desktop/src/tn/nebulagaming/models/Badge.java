/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author SuperNova
 */


public class Badge {
    private int idBadge ; 
    private String nameBadge ;
    private String descBadge ;
    private String photoBadge ; 
    
    public Badge (int idBadge , String nameBadge,String descBadge,String photoBadge) {
        this.idBadge = idBadge ;
        this.nameBadge = nameBadge ;
        this.descBadge = descBadge ; 
        this.photoBadge = photoBadge ;  
    }
    
    public Badge (String nameBadge,String descBadge,String photoBadge) {
        this.nameBadge = nameBadge ;
        this.descBadge = descBadge ; 
        this.photoBadge = photoBadge ;  
    }
    
    public int getIdBadge() {return this.idBadge ;}

    public String getNameBadge () {return this.nameBadge ;}
    public void setNameBadge(String nameBadge) {this.nameBadge = nameBadge ;}

    public String getDescBadge () {return this.descBadge ;}
    public void setDescBadge(String descBadge) {this.descBadge = descBadge ;}

    public String getPhotoBadge () {return this.photoBadge ;}
    public void setPhotoBadge(String photoBadge) {this.photoBadge = photoBadge ;}
    
    public String toString () {
        return  
        " ,Badge Id = "+idBadge+ 
        " ,Badge name = "+nameBadge+
        " , Badge Description = "+descBadge+ 
        " ,Badge photo = "+photoBadge ;
    } 
    
}
