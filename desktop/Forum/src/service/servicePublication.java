/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import models.publication;
import utils.jdbc;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dell
 */
public abstract class servicePublication implements Services<publication> {
    
    Connection cnx = jdbc.getInstance().getCnx();

    @Override
    public void ajouter(publication t) {
        try {
            String req = "INSERT INTO tbl_publication (IdPub, idUser, DatePub, TypePub, descriptionPub, idVg) VALUES ('" + t.getIdPub() + "','" + t.getIdUser() + "','" + t.getDatePub() + "','"+ t.getTypePub()+"','"+ t.getDescriptionPub()+"','"+t.getIdVg()+"')";
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Publication ajoutée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(publication t) {
        try {
            String req = "DELETE FROM tbl_publication where IdPub=" + t.getIdPub();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Publication supprimée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(publication t) {
        try {
            String req = "UPDATE tbl_publication SET DatePub='" + t.getDatePub()+ "',TypePub='" + t.getTypePub()+ "',descriptionPub='"+t.getDescriptionPub()+ "' WHERE idPub=" + t.getIdPub();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Publication modifée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<publication> afficher() {
        List<publication> list = new ArrayList<>();
        
        try {
            String req = "SELECT * FROM tbl_publication";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while(rs.next()) {
                list.add(new publication(rs.getInt(1), rs.getInt(2),rs.getString(3),rs.getInt(4),rs.getString(5), rs.getInt(6)));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return list;
    }
}
