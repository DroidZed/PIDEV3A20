/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tn.nebulagaming.models.Participation;
import tn.nebulagaming.models.Post;
import tn.nebulagaming.utils.GlobalConfig;

/**
 *
 * @author SuperNova
 */
public class ServiceParticipation implements IService<Participation> {

    Connection cnx = GlobalConfig.getInstance().getCnx() ;
    
    @Override
    public void add(Participation participation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(int idParticipation ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Participation participation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Participation> display() {
      return null ;  
    }
    
    public List<Participation> displayParticipationByEvent(int idEvent) {
         List<Participation> listParticipation = new ArrayList<>(); 

        try {
           String query = "SELECT idParticipation , bookedDTM , idUser , idPost , idPayType FROM `tbl_participation` WHERE idPost ="+idEvent; 
           Statement st = cnx.createStatement();
           ResultSet rs = st.executeQuery(query) ;
           while (rs.next()) {
               listParticipation.add(new Participation (
                                   rs.getInt(1),
                                   rs.getDate(2) ,
                                   rs.getInt(3),
                                   rs.getInt(4),
                                   rs.getInt(5))) ;
           }
       }catch (SQLException e) {
           System.err.println(e.getMessage());
       }
       return listParticipation ; 
    }
    
}
