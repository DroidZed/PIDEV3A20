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
import models.JeuVideo;
import utils.jdbc;

/**
 *
 * @author dell
 */
public abstract class serviceJeuVideo implements Services<JeuVideo> {
    
Connection cnx = jdbc.getInstance().getCnx();
    String req = "";
    Statement st;
    ResultSet rs;

    @Override
    public void ajouter(JeuVideo t) {
        try {
            req = "INSERT INTO tbl_videogame (statusVg, nameVg, imageVg, idUser, IdPub) VALUES ('" + t.getStatusVg()+ "','" + t.getNameVg()+ "','" + t.getImageVg()+ "','"+ t.getIdUser()+"','"+ t.getIdPub()+"')";
            st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Jeu video ajoutée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(JeuVideo t) {
        try {
            req = "DELETE FROM tbl_videogame where idUser=" + t.getIdUser();
            st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Jeu video supprimée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(JeuVideo t) {
        try {
            req = "UPDATE tbl_videogame SET statusVg='" + t.getStatusVg()+ "',nameVg='" + t.getNameVg()+ "',imageVg='"+t.getImageVg()+ "' WHERE idUser=" + t.getIdUser();
            st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Jeu video modifée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<JeuVideo> afficher() {
        List<JeuVideo> list = new ArrayList<>();
        
        try {
            req = "SELECT * FROM tbl_videogame";
            st = cnx.createStatement();
            rs = st.executeQuery(req);
            while(rs.next()) {
                list.add(new JeuVideo(rs.getInt(1), rs.getString(2),rs.getBlob(3),rs.getInt(4),rs.getInt(5)));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return list;
    }

    @Override
    public List<JeuVideo> rechercher(JeuVideo t){
        

        List<JeuVideo> list = new ArrayList<>();
        try
        {
            req = "SELECT * FROM tbl_videogame where nameVg='"+ t.getNameVg();
            st = cnx.createStatement();
            rs = st.executeQuery(req);
            while (rs.next())
            {
                list.add(new JeuVideo(rs.getInt(1), rs.getString(2), rs.getBlob(3), rs.getInt(4), rs.getInt(5)));
            }
        }catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }

    return list;

} 
}
