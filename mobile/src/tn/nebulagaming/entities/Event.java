/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.entities;

import java.util.Date;



/**
 *
 * @author SuperNova
 */
public class Event extends Post {
    
    public Event () {
    }
    
    
    public Event(Date postedDTM, String titlePost, String descPost, int statusPost, String photoPost, String typePost, Date startDTM, Date endDTM, int idOwnerUser) {
        super(postedDTM, titlePost, descPost, statusPost, photoPost, typePost, startDTM, endDTM, idOwnerUser);
    }
    
    private String addressEvent ; 
    private int nbTicketAvailable ; 
    private float latitude ; 

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
    private float longitude ; 
   
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
