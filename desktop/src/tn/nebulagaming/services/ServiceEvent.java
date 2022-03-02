/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tn.nebulagaming.models.Event;
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
        String query = "INSERT INTO tbl_post (postedDTM,titlePost,descPost,statusPost,typePost,startDTM,endDTM,idUser,addressEvent,nbTicketAvailable) VALUES ('" +event.getPostedDTM()+"' , '" +event.getTitlePost()+"','"+event.getDescPost()+"','"+event.getStatusPost()+"','"+event.getTypePost()+"','"+event.getStartDTM()+"','"+event.getEndDTM()+"','"+event.getIdOwnerUser()+"','"+event.getAddressEvent()+"','"+event.getNbTicketAvailable()+"')"; 
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
        System.out.println("Post deleted with success !");

        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }  
    }

    @Override
    public void update(Event event) {
        PreparedStatement st ;
       try {
          String query = "UPDATE tbl_post SET postedDTM = ? , titlePost = ? , descPost =? , statusPost =? , startDTM=? , endDTM=? , idUser = ? , addressEvent =? , nbTicketAvailable =? WHERE idPost =?"; 
          st = cnx.prepareStatement(query);
          st.setDate(1,event.getPostedDTM()) ;
          st.setString (2,event.getTitlePost()) ;
          st.setString (3,event.getDescPost()) ;
          st.setInt(4,event.getStatusPost()) ;
          st.setDate(5,event.getStartDTM()) ;
          st.setDate (6,event.getEndDTM()) ;
          st.setInt (7,event.getIdOwnerUser()) ;
          st.setString (8,event.getAddressEvent()) ;
          st.setInt(9, event.getNbTicketAvailable());
          st.setInt(2,event.getIdPost());
          st.executeUpdate() ;
          System.out.println("Post updated with success !");

      }catch (SQLException e) {
          Logger.getLogger(ServicePost.class.getName()).log(Level.SEVERE , null , e);
      }  
    }

    @Override
    public List<Event> display() {
       return null ; 
    }
    
}
