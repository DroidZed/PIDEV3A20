/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbc.jdbc;
import models.domain;


/**
 *
 * @author User
 */
public class serviceDomain implements services <domain>{
   
       Connection cnx ;
       String req = "";
       Statement st;
       ResultSet rs;
       Scanner sc = new Scanner(System.in);

    public serviceDomain() {
                cnx = jdbc.getInstance().getCnx();
    }
    
    
    @Override
    public void ajouter(domain t) 
    {
        try {
            req = "INSERT INTO tbl_domain ( name, description) VALUES ('" + t.getName()+ "','"+ t.getDesc()+"')";
            st = cnx.prepareStatement(req);
            st.executeUpdate(req);
            System.out.println("Domaine ajouté !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
      
    @Override
    public void supprimer(domain t) {
        try {
        String query2="DELETE FROM tbl_domain WHERE id = ?";
        PreparedStatement smt = cnx.prepareStatement(query2) ;
        smt.setInt(1, t.getId());
        smt.executeUpdate();
        System.out.println("Domaine supprimé !");}
    catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    }


    @Override
    public void modifier(domain t) {
        
        try {

                String query2="UPDATE `tbl_domain` SET `name`=?,`description`=? WHERE `id`=? ";

                PreparedStatement smt = cnx.prepareStatement(query2);
                smt.setString(1, t.getName());
                smt.setString(2, t.getDesc());
                smt.setInt(3, t.getId());
                

                smt.executeUpdate();
                System.out.println("Domaine modifié !");
            } 
            catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
    }
 
    @Override
    public List<domain> afficher() {
           List<domain> list = new ArrayList<>();
        
        try {
            req = "SELECT * FROM tbl_domain";
            st = cnx.createStatement();
            rs = st.executeQuery(req);
            while(rs.next()) {
            list.add(new domain(rs.getInt(1),rs.getString(2),rs.getString(3)));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }
    
      public domain getById(int id) {
          domain a = null;
         String requete = " select* from tbl_domain where id="+id ;
        try {
           
            st = cnx.createStatement();
            rs=st.executeQuery(requete);
            if (rs.next())
            {a=new domain(rs.getInt(1),rs.getString(2),rs.getString(3));}
        } catch (SQLException ex) {
            Logger.getLogger(serviceDomain.class.getName()).log(Level.SEVERE, null, ex);
        }
        return a ;
        
    }
      public domain getByName(String name) {
          domain a = null;
         String requete = " select* from tbl_domain where name='"+name+"'" ;
        try {
           
            st = cnx.createStatement();
            rs=st.executeQuery(requete);
            if (rs.next())
            {a=new domain(rs.getInt(1),rs.getString(2),rs.getString(3));}
        } catch (SQLException ex) {
            Logger.getLogger(serviceDomain.class.getName()).log(Level.SEVERE, null, ex);
        }
        return a ;
        
    }


}
