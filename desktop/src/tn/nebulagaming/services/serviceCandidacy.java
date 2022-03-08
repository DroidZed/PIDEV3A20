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
import tn.nebulagaming.models.Candidacy;
import java.util.logging.Level;
import java.util.logging.Logger;
import tn.nebulagaming.utils.GlobalConfig;

/**
 *
 * @author User
 */
public class ServiceCandidacy implements Services<Candidacy> {

    Connection cnx;
    String req = "";
    Statement st;
    ResultSet rs;

    public ServiceCandidacy() {
	cnx = GlobalConfig.getInstance().getCONNECTION();
    }

    @Override
    public void ajouter(Candidacy a) {
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
    public void supprimer(Candidacy t) {
	try {
	    String query2 = "DELETE FROM tbl_candidacy WHERE id = ?";
	    PreparedStatement smt = cnx.prepareStatement(query2);
	    smt.setInt(1, t.getId());
	    smt.executeUpdate();
	    System.out.println("candidature supprimée !");
	} catch (SQLException ex) {
	    System.out.println(ex.getMessage());
	}
    }

    @Override
    public void modifier(Candidacy t) {

	try {

	    String query2 = "update tbl_candidacy set `etat`=? where `id` = ?";

	    PreparedStatement smt = cnx.prepareStatement(query2);
	    smt.setString(1, t.getEtat());
	    smt.setInt(2, t.getId());

	    smt.executeUpdate();
	    System.out.println("candidature modifiée !");
	} catch (SQLException ex) {
	    System.out.println(ex.getMessage());
	}
    }

    @Override
    public List<Candidacy> afficher() {
	List<Candidacy> list = new ArrayList<>();

	try {
	    req = "SELECT * FROM tbl_candidacy";
	    st = cnx.createStatement();
	    rs = st.executeQuery(req);
	    while (rs.next()) {
		// list.add(new candidacy(rs.getInt(1),rs.getString(2),rs.getString(3)));
	    }
	} catch (SQLException ex) {
	    System.out.println(ex.getMessage());
	}
	return list;
    }

    public Candidacy getById(int iduser, int idoffre) {
	Candidacy a = null;
	String requete = " select * from tbl_candidacy where idUser=" + iduser + " and idOffer=" + idoffre;
	try {

	    st = cnx.createStatement();
	    rs = st.executeQuery(requete);
	    if (rs.next()) {
		a = new Candidacy(rs.getInt(1), rs.getDate(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6));
	    }
	} catch (SQLException ex) {
	    Logger.getLogger(ServiceCandidacy.class.getName()).log(Level.SEVERE, null, ex);
	}
	return a;

    }

    public Candidacy getCandidacy(int idcan) {
	Candidacy a = null;
	String requete = " select * from tbl_candidacy where id=" + idcan;
	try {

	    st = cnx.createStatement();
	    rs = st.executeQuery(requete);
	    if (rs.next()) {
		a = new Candidacy(rs.getInt(1), rs.getDate(2), rs.getInt(3), rs.getInt(4), rs.getString(5), rs.getString(6));
	    }
	} catch (SQLException ex) {
	    Logger.getLogger(ServiceCandidacy.class.getName()).log(Level.SEVERE, null, ex);
	}
	return a;

    }

}
