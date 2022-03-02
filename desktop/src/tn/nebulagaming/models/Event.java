/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.models;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author SuperNova
 */
public class Event extends Post  {
    private String addressEvent ; 
    private int nbTicketAvailable ; 
    
     public Event (Date postedDTM ,String titlePost , String descPost , int statusPost, String photoPost , String typePost,Date startDTM,Date endDTM, int idOwnerUser,String addressEvent,int nbTicketAvailable) {
        super(postedDTM ,titlePost ,descPost ,statusPost, photoPost , typePost, startDTM, endDTM, idOwnerUser); 
        this.addressEvent = addressEvent ;
        this.nbTicketAvailable = nbTicketAvailable ;
    }
     
    public String getAddressEvent() {return this.addressEvent ;}
    public void setAddressEvent(String addressEvent) {this.addressEvent = addressEvent ;}


    public int getNbTicketAvailable() {return this.nbTicketAvailable ;}
    public void setNbTicketAvailable(int nbTicketAvailable) {this.nbTicketAvailable = nbTicketAvailable ;}

    
}
