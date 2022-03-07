/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tn.nebulagaming.models.Event;
import tn.nebulagaming.models.Post;
import tn.nebulagaming.utils.GlobalConfig;

/**
 *
 * @author SuperNova
 */
public class ServiceEvent implements IService<Event> {

    Connection cnx = GlobalConfig.getInstance().getCnx() ;  
    
    @Override
    public void add(Event event) {
      try {
        String query = "INSERT INTO tbl_post (titlePost,descPost,statusPost,typePost,startDTM,endDTM,idUser,addressEvent,nbTicketAvailable,latitude,longitude,photoPost) VALUES ('" +event.getTitlePost()+"','"+event.getDescPost()+"','"+event.getStatusPost()+"','"+event.getTypePost()+"','"+event.getStartDTM()+"','"+event.getEndDTM()+"','"+event.getIdOwnerUser()+"','"+event.getAddressEvent()+"','"+event.getNbTicketAvailable()+"','"+event.getLatitude()+"','"+event.getLongitude()+"','"+event.getPhotoPost()+"')"; 
        Statement st = cnx.createStatement();
        st.executeUpdate(query) ;
        System.out.println("Event added with success !");

        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }  
    }

    @Override
    public void delete(int idEvent) {
        try {
        String query = "DELETE FROM tbl_post WHERE idPost =" +idEvent; 
        Statement st = cnx.createStatement();
        st.executeUpdate(query) ;
        System.out.println("Event deleted with success !");

        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }  
    }

    @Override
    public void update(Event event) {
        PreparedStatement st ;
       try {
          String query = "UPDATE tbl_post SET titlePost = ? , descPost =? , statusPost =? , startDTM=? , endDTM=? , addressEvent =? , nbTicketAvailable =? , latitude =? , longitude=? , photoPost=?  WHERE idPost =?"; 
          st = cnx.prepareStatement(query);
          st.setString (1,event.getTitlePost()) ;
          st.setString (2,event.getDescPost()) ;
          st.setInt(3,event.getStatusPost()) ;
          st.setDate(4,event.getStartDTM()) ;
          st.setDate(5,event.getEndDTM()) ;
          st.setString(6,event.getAddressEvent()) ;
          st.setInt(7, event.getNbTicketAvailable());
          st.setDouble(8, event.getLatitude());
          st.setDouble(9, event.getLongitude());
          st.setString(10, event.getPhotoPost());
          st.setInt(11,event.getIdPost());
          st.executeUpdate() ;
          System.out.println("Event updated with success !");

      }catch (SQLException e) {
          Logger.getLogger(ServicePost.class.getName()).log(Level.SEVERE , null , e);
      }  
    }

    @Override
    public List<Event> display() {
       List<Event> listEvent = new ArrayList<>(); 

        try {
           String query = "SELECT idPost , postedDTM , titlePost , descPost  , statusPost , photoPost ,startDTM , endDTM , addressEvent , nbTicketAvailable , idUser , latitude , longitude FROM tbl_post WHERE typePost = 'Event'"; 
           Statement st = cnx.createStatement();
           ResultSet rs = st.executeQuery(query) ;
           while (rs.next()) {
               listEvent.add(new Event(
                                   rs.getInt(1) ,
                                   rs.getDate(2),
                                   rs.getString(3),
                                   rs.getString(4),     
                                   rs.getInt(5),
                                   rs.getString(6) ,
                                   rs.getDate(7) ,
                                   rs.getDate(8) ,
                                   rs.getString(9) ,
                                   rs.getInt(10) ,
                                   rs.getInt(11) , 
                                   rs.getDouble(12) ,
                                   rs.getDouble(13)
               )) ;
           }
       }catch (SQLException e) {
           System.err.println(e.getMessage());
       }
       return listEvent ; 
    }
    
    public Event getEventById (int idEvent) {
        
        Event event = null;
    try {
           String query = "SELECT idPost , postedDTM , titlePost , descPost  , statusPost , photoPost ,startDTM , endDTM , addressEvent , nbTicketAvailable , idUser , latitude , longitude FROM tbl_post WHERE typePost = 'Event' AND idPost ="+idEvent; 
           Statement st = cnx.createStatement();
           ResultSet rs = st.executeQuery(query) ;
           while (rs.next()) {
               event = new Event(
                                   rs.getInt(1) ,
                                   rs.getDate(2),
                                   rs.getString(3),
                                   rs.getString(4),     
                                   rs.getInt(5),
                                   rs.getString(6) ,
                                   rs.getDate(7) ,
                                   rs.getDate(8) ,
                                   rs.getString(9) ,
                                   rs.getInt(10) ,
                                   rs.getInt(11) , 
                                   rs.getDouble(12) ,
                                   rs.getDouble(13));
           }
       }catch (SQLException e) {
           System.err.println(e.getMessage());
       }
    
    return event ;
    }
    
}
