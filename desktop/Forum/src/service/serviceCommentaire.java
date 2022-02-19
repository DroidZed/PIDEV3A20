/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.commentaire;
import utils.jdbc;

/**
 *
 * @author dell
 */
public abstract class serviceCommentaire implements Services<commentaire>{

Connection cnx = jdbc.getInstance().getCnx();

    @Override
    public void ajouter(commentaire t) {
        try {
            String req = "INSERT INTO tbl_commentaire (idCom, dateCom, descriptionCom, IdPub) VALUES ('" + t.getIdCom() + "','" + t.getDateCom() + "','" + t.getDescriptionCom() + "','"+ t.getIdPub()+"')";
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Commentaire ajoutée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(commentaire t) {
        try {
            String req = "DELETE FROM tbl_commentaire where idCom=" + t.getIdCom();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Commentaire supprimée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(commentaire t) {
        try {
            String req = "UPDATE tbl_commentaire SET dateCom='" + t.getDateCom()+ "',descriptionCom='"+t.getDescriptionCom()+ "' WHERE idCom=" + t.getIdCom();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Commentaire modifée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<commentaire> afficher() {
        List<commentaire> list = new ArrayList<>();
        
        try {
            String req = "SELECT * FROM tbl_commentaire";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()) {
                list.add(new commentaire(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getInt(4)));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return list;
    }
    
}
