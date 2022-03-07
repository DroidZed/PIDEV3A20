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
public class Event extends Post {
    private String addressEvent ; 
    private int nbTicketAvailable ; 
    private double latitude ;
    private double longitude;
    
    
    //Constructor for add 
     public Event (Date postedDTM ,String titlePost , String descPost , int statusPost, String photoPost , String typePost,Date startDTM,Date endDTM, int idOwnerUser,String addressEvent,int nbTicketAvailable, double latitude , double longitude) {
        super(postedDTM ,titlePost ,descPost ,statusPost, photoPost , typePost, startDTM, endDTM, idOwnerUser); 
        this.addressEvent = addressEvent ;
        this.nbTicketAvailable = nbTicketAvailable ;
        this.latitude = latitude ;
        this.longitude = longitude ;
    }
    
    
    public Event (int idPost , Date postedDTM ,String titlePost , String descPost , int statusPost, String photoPost , Date startDTM,Date endDTM,String addressEvent ,int nbTicketAvailable, int idOwnerUser, double latitude , double longitude) {
        super(idPost ,postedDTM ,titlePost ,descPost ,statusPost, photoPost , startDTM, endDTM, idOwnerUser); 
        this.addressEvent = addressEvent ;
        this.nbTicketAvailable = nbTicketAvailable ;
        this.latitude = latitude ;
        this.longitude = longitude ;
        
    }
    
    //Constructor for update
    public Event (int idPost ,String titlePost , String descPost , int statusPost, String photoPost , Date startDTM,Date endDTM,String addressEvent ,int nbTicketAvailable, double latitude , double longitude ) {
        super(idPost ,titlePost ,descPost ,statusPost, photoPost , startDTM, endDTM); 
        this.addressEvent = addressEvent ;
        this.nbTicketAvailable = nbTicketAvailable ;
        this.latitude = latitude ;
        this.longitude = longitude ;
        
                
    }
     
    public String getAddressEvent() {return this.addressEvent ;}
    public void setAddressEvent(String addressEvent) {this.addressEvent = addressEvent ;}


    public int getNbTicketAvailable() {return this.nbTicketAvailable ;}
    public void setNbTicketAvailable(int nbTicketAvailable) {this.nbTicketAvailable = nbTicketAvailable ;}

    public double getLatitude () { return this.latitude ;}
    public double getLongitude () { return this.longitude ;}

    
    
}
