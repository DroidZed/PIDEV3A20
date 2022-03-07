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
import tn.nebulagaming.models.Comment;
import tn.nebulagaming.models.Reaction;
import tn.nebulagaming.utils.GlobalConfig;


/**
 *
 * @author SuperNova
 */
public class ServiceReaction  implements IServiceNewsfeed<Reaction> {

    Connection cnx = GlobalConfig.getInstance().getCnx() ;
    
    @Override
    public void add(Reaction t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Reaction t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Reaction> display() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<Reaction> displayReactionByEvent(int idEvent) {
         List<Reaction> listReaction = new ArrayList<>(); 

        try {
           String query = "SELECT idReact , idTypeReact , reactedDTM , idPost , idUser FROM `tbl_reaction` WHERE idPost ="+idEvent; 
           Statement st = cnx.createStatement();
           ResultSet rs = st.executeQuery(query) ;
           while (rs.next()) {
               listReaction.add(new Reaction (
                                   rs.getInt(1),  
                                   rs.getInt(2),
                                   rs.getDate(3) ,
                                   rs.getInt(4),
                                   rs.getInt(5))) ;
           }
       }catch (SQLException e) {
           System.err.println(e.getMessage());
       }
       return listReaction ; 
    }
    
    public int countLikesByPost (int idPost) {
         int countLikes = 0 ;

        try {
           String query = "SELECT count(idReact) as likes FROM tbl_reaction WHERE idTypeReact = 1 AND idPost="+idPost; 
           Statement st = cnx.createStatement();
           ResultSet rs = st.executeQuery(query) ;
           if (rs.next()) {
                countLikes = rs.getInt(1) ;
           }
       }catch (SQLException e) {
           System.err.println(e.getMessage());
       }
       return countLikes ; 
    }
    
    public int countDislikesByPost (int idPost) {
         int countDislikes = 0 ;

        try {
           String query = "SELECT count(idReact) as likes FROM tbl_reaction WHERE idTypeReact = 2 AND idPost="+idPost; 
           Statement st = cnx.createStatement();
           ResultSet rs = st.executeQuery(query) ;
           if (rs.next()) {
                countDislikes = rs.getInt(1) ;
           }
       }catch (SQLException e) {
           System.err.println(e.getMessage());
       }
       return countDislikes ; 
    }
    
    
}
