/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.candidacy;
import jdbc.jdbc;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.domain;
import models.offer;


/**
 *
 * @author User
 */
public class serviceCandidacy implements services <candidacy> {
    
    Connection cnx ;
    String req = "";
    Statement st;
    ResultSet rs;

    public serviceCandidacy() {
                cnx = jdbc.getInstance().getCnx();
    }
 
    @Override
    public void ajouter(candidacy a) 
    {
        try {
        PreparedStatement PS = cnx.prepareStatement("INSERT INTO `tbl_candidacy` (`idOffer`, `idUser`, `imageCV`, `etat`) VALUES (?, ?, ?, ? );");
        PS.setInt(1, a.getIdOffer());
        PS.setInt(2, a.getIdUser());
        PS.setString(3, a.getImageCV());
        PS.setString(4, a.getEtat());
        PS.executeUpdate();

            System.out.println("candidature ajoutée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
  
    @Override
    public void supprimer(candidacy t) {
        try {
        String query2="DELETE FROM tbl_candidacy WHERE id = ?";
        PreparedStatement smt = cnx.prepareStatement(query2) ;
        smt.setInt(1, t.getId());
        smt.executeUpdate();
        System.out.println("candidature supprimée !");}
    catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    }

       
       @Override
    public void modifier(candidacy t) {
        
        try {

                String query2="update tbl_candidacy set `etat`=? where `id` = ?";

                PreparedStatement smt = cnx.prepareStatement(query2);
                smt.setString(1, t.getEtat());
                smt.setInt(2, t.getId());
                

                smt.executeUpdate();
                System.out.println("candidature modifiée !");
            } 
            catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
    }

          @Override
          
    public List<candidacy> afficher() {
           List<candidacy> list = new ArrayList<>();
        
        try {
            req = "SELECT * FROM tbl_candidacy";
            st = cnx.createStatement();
            rs = st.executeQuery(req);
            while(rs.next()) {
           // list.add(new candidacy(rs.getInt(1),rs.getString(2),rs.getString(3)));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }
    
    public candidacy getById(int iduser,int idoffre) {
          candidacy a = null;
         String requete = " select* from tbl_candidacy where idUser="+iduser+" and idOffer="+idoffre ;
        try {
           
            st = cnx.createStatement();
            rs=st.executeQuery(requete);
            if (rs.next())
            {
               a=new candidacy(rs.getInt(1),rs.getDate(2),rs.getInt(3),rs.getInt(4),rs.getString(5),rs.getString(6));
            }
        } catch (SQLException ex) {
            Logger.getLogger(serviceDomain.class.getName()).log(Level.SEVERE, null, ex);
        }
        return a ;
        
    }

           
           
             public candidacy getCandidacy(int idcan) {
          candidacy a = null;
         String requete = " select* from tbl_candidacy where id="+idcan ;
        try {
           
            st = cnx.createStatement();
            rs=st.executeQuery(requete);
            if (rs.next())
            {
               a=new candidacy(rs.getInt(1),rs.getDate(2),rs.getInt(3),rs.getInt(4),rs.getString(5),rs.getString(6));
            }
        } catch (SQLException ex) {
            Logger.getLogger(serviceDomain.class.getName()).log(Level.SEVERE, null, ex);
        }
        return a ;
        
    }  

    }
 





