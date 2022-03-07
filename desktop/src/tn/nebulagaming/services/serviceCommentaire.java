/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.commentaire;
import utils.jdbc;

/**
 *
 * @author dell
 */
public class serviceCommentaire implements Services<commentaire>{

    Connection cnx = jdbc.getInstance().getCnx();
    String req = "";
    Statement st;
    ResultSet rs;
    DateFormat df = new SimpleDateFormat("MM-dd-yyyy");

    @Override
    public void ajouter(commentaire t) 
    {
        try {
            req = "INSERT INTO tbl_commentaire (descriptionCom, IdPub) VALUES ('" + t.getDescriptionCom()+ "','"+ t.getIdPub()+"')";
            st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Commentaire ajouté !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public void supprimer(commentaire t) {
        try {
        String query2="DELETE FROM tbl_commentaire WHERE descriptionCom = ?";
        PreparedStatement smt = cnx.prepareStatement(query2) ;
        smt.setString(1, t.getDescriptionCom());
        smt.executeUpdate();
        System.out.println("Commentaire supprimé !");}
    catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    }

    @Override
    public void modifier(commentaire t) 
    {
        try {

                String query2="UPDATE `tbl_commentaire` SET `descriptionCom`=? WHERE `idCom`=? ";

                PreparedStatement smt = cnx.prepareStatement(query2);
                smt.setString(1, t.getDescriptionCom());
                smt.setInt(2, t.getIdCom());
                

                smt.executeUpdate();
                System.out.println("Commentaire modifié !");
            } 
            catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
    }

    public serviceCommentaire() {
        cnx = jdbc.getInstance().getCnx();
    }

    
    
    @Override
    public List<commentaire> afficher() {
        
        List<commentaire> list = new ArrayList<>();
        
        try {
             req = "SELECT * FROM tbl_commentaire";
             st = cnx.createStatement();
             rs = st.executeQuery(req);
            while(rs.next()) {
                list.add(new commentaire(rs.getDate(2), rs.getString(3)));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return list;
    }
    /*

    public List<commentaire> rechercher( String a)
    {

        List<commentaire> list = new ArrayList<>();
        try
        {
            req = "SELECT * FROM tbl_commentaire where descriptionCom='"+ a;
            st = cnx.createStatement();
            rs = st.executeQuery(req);
            while (rs.next())
            {
               // list.add(new commentaire(rs.getInt(1), rs.getString(2), rs.getBlob(3), rs.getInt(4), rs.getInt(5)));
            }
        }catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }

    return list;
    } */
    
    
}
