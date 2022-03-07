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
import tn.nebulagaming.models.Participation;
import tn.nebulagaming.utils.GlobalConfig;

/**
 *
 * @author SuperNova
 */
public class ServiceComment implements IServiceNewsfeed<Comment>{

    Connection cnx = GlobalConfig.getInstance().getCnx() ;
        
    @Override
    public void add(Comment t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Comment t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Comment> display() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
     public List<Comment> displayCommentByEvent(int idEvent) {
         List<Comment> listComment = new ArrayList<>(); 

        try {
           String query = "SELECT idComment , postedDTM , idUser , idPost , comment FROM `tbl_comment` WHERE idPost ="+idEvent; 
           Statement st = cnx.createStatement();
           ResultSet rs = st.executeQuery(query) ;
           while (rs.next()) {
               listComment.add(new Comment (
                                   rs.getInt(1),
                                   rs.getDate(2) ,
                                   rs.getInt(3),
                                   rs.getInt(4),
                                   rs.getString(5))) ;
           }
       }catch (SQLException e) {
           System.err.println(e.getMessage());
       }
       return listComment ; 
    }
     
     public int countCommentsbyPost (int idPost) {
         
         int nbComment = 0 ;

        try {
           String query = "SELECT count(idComment) FROM tbl_comment WHERE idPost = "+idPost; 
           Statement st = cnx.createStatement();
           ResultSet rs = st.executeQuery(query) ;
           if (rs.next()) {
                nbComment = rs.getInt(1) ;
           }
           
        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }
       return nbComment ; 
    }
    
}
