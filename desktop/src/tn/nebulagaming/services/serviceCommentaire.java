/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tn.nebulagaming.models.Commentaire;
import tn.nebulagaming.utils.GlobalConfig;

/**
 *
 * @author dell
 */
public class ServiceCommentaire implements Services<Commentaire> {

    Connection cnx;
    String req = "";
    Statement st;
    ResultSet rs;

    public ServiceCommentaire() {

	cnx = GlobalConfig.getInstance().getCONNECTION();
    }

    @Override
    public void ajouter(Commentaire t) {
	try {
	    req = "INSERT INTO tbl_Commentaire (descriptionCom, IdPub) VALUES ('" + t.getDescriptionCom() + "','" + t.getIdPub() + "')";
	    st = cnx.createStatement();
	    st.executeUpdate(req);
	    System.out.println("Commentaire ajouté !");
	} catch (SQLException ex) {
	    System.out.println(ex.getMessage());
	}
    }

    @Override
    public void supprimer(Commentaire t) {
	try {
	    String query2 = "DELETE FROM tbl_Commentaire WHERE descriptionCom = ?";
	    PreparedStatement smt = cnx.prepareStatement(query2);
	    smt.setString(1, t.getDescriptionCom());
	    smt.executeUpdate();
	    System.out.println("Commentaire supprimé !");
	} catch (SQLException ex) {
	    System.out.println(ex.getMessage());
	}
    }

    @Override
    public void modifier(Commentaire t) {
	try {

	    String query2 = "UPDATE `tbl_Commentaire` SET `descriptionCom`=? WHERE `idCom`=? ";

	    PreparedStatement smt = cnx.prepareStatement(query2);
	    smt.setString(1, t.getDescriptionCom());
	    smt.setInt(2, t.getIdCom());

	    smt.executeUpdate();
	    System.out.println("Commentaire modifié !");
	} catch (SQLException ex) {
	    System.out.println(ex.getMessage());
	}
    }

    @Override
    public List<Commentaire> afficher() {

	List<Commentaire> list = new ArrayList<>();

	try {
	    req = "SELECT * FROM tbl_Commentaire";
	    st = cnx.createStatement();
	    rs = st.executeQuery(req);
	    while (rs.next()) {
		list.add(new Commentaire(rs.getDate(2), rs.getString(3)));
	    }
	} catch (SQLException ex) {
	    System.out.println(ex.getMessage());
	}

	return list;
    }
    /*

    public List<Commentaire> rechercher( String a)
    {

        List<Commentaire> list = new ArrayList<>();
        try
        {
            req = "SELECT * FROM tbl_Commentaire where descriptionCom='"+ a;
            st = cnx.createStatement();
            rs = st.executeQuery(req);
            while (rs.next())
            {
               // list.add(new Commentaire(rs.getInt(1), rs.getString(2), rs.getBlob(3), rs.getInt(4), rs.getInt(5)));
            }
        }catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }

    return list;
    } */

}
