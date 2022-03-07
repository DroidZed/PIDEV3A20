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
import tn.nebulagaming.models.Answer;
import tn.nebulagaming.models.Badge;
import tn.nebulagaming.utils.GlobalConfig;

/**
 *
 * @author SuperNova
 */
public class ServiceAnswer implements IService<Answer>{

    Connection cnx = GlobalConfig.getInstance().getCnx() ;
        
    @Override
    public void add(Answer t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Answer t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Answer> display() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public int nbCorrectAnswers (int idPost , String correcAnswer) {
        int nbCorrectAnswers = 0 ;
         try {
            String query = "SELECT count(idAnswer) FROM tbl_answerpost WHERE idPost="+idPost+" AND answer='"+correcAnswer+"'"; 
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(query) ;
            while (rs.next()) {
                nbCorrectAnswers = rs.getInt(1) ; 
            }

        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return nbCorrectAnswers ;
    }
    
    public int nbIncorrectAnswers (int idPost , String correctAnswer) {
        int nbIncorrectAnswers = 0 ;
         try {
            String query = "SELECT count(idAnswer) FROM tbl_answerpost WHERE idPost="+idPost+" AND answer != '"+correctAnswer+"'"; 
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(query) ;
            while (rs.next()) {
                nbIncorrectAnswers = rs.getInt(1) ; 
            }

        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return nbIncorrectAnswers ;
    }
    
    public List<Answer> getAnswersByQuiz (int idPost) {
        List<Answer> listAnswer = new ArrayList<> () ; 
         try {
            String query = "SELECT answeredDTM , answer , idUser , idPost FROM tbl_answerpost WHERE idPost= "+idPost; 
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(query) ;
            while (rs.next()) {
                listAnswer.add(new Answer ( 
                        rs.getDate(1) , 
                        rs.getString(2) ,
                        rs.getInt(3) ,
                        rs.getInt (4)  
                ));
            }

        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return listAnswer ;
    }
    
}
