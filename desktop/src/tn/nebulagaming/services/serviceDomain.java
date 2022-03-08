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
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import tn.nebulagaming.models.Domain;
import tn.nebulagaming.utils.GlobalConfig;

/**
 *
 * @author User
 */
public class ServiceDomain implements Services<Domain> {

    Connection cnx;
    String req = "";
    Statement st;
    ResultSet rs;

    public ServiceDomain() {
	cnx = GlobalConfig.getInstance().getCONNECTION();
    }

    @Override
    public void ajouter(Domain t) {
	try {
	    req = "INSERT INTO tbl_Domain ( name, description) VALUES ('" + t.getName() + "','" + t.getDesc() + "')";
	    st = cnx.prepareStatement(req);
	    st.executeUpdate(req);
	    System.out.println("Domaine ajouté !");
	} catch (SQLException ex) {
	    System.out.println(ex.getMessage());
	}
    }

    @Override
    public void supprimer(Domain t) {
	try {
	    String query2 = "DELETE FROM tbl_Domain WHERE id = ?";
	    PreparedStatement smt = cnx.prepareStatement(query2);
	    smt.setInt(1, t.getId());
	    smt.executeUpdate();
	    System.out.println("Domaine supprimé !");
	} catch (SQLException ex) {
	    System.out.println(ex.getMessage());
	}
    }

    @Override
    public void modifier(Domain t) {

	try {

	    String query2 = "UPDATE `tbl_Domain` SET `name`=?,`description`=? WHERE `id`=? ";

	    PreparedStatement smt = cnx.prepareStatement(query2);
	    smt.setString(1, t.getName());
	    smt.setString(2, t.getDesc());
	    smt.setInt(3, t.getId());

	    smt.executeUpdate();
	    System.out.println("Domaine modifié !");
	} catch (SQLException ex) {
	    System.out.println(ex.getMessage());
	}
    }

    @Override
    public List<Domain> afficher() {
	List<Domain> list = new ArrayList<>();

	try {
	    req = "SELECT * FROM tbl_Domain";
	    st = cnx.createStatement();
	    rs = st.executeQuery(req);
	    while (rs.next()) {
		list.add(new Domain(rs.getInt(1), rs.getString(2), rs.getString(3)));
	    }
	} catch (SQLException ex) {
	    System.out.println(ex.getMessage());
	}
	return list;
    }

    public Domain getById(int id) {
	Domain a = null;
	String requete = " select* from tbl_Domain where id=" + id;
	try {

	    st = cnx.createStatement();
	    rs = st.executeQuery(requete);
	    if (rs.next()) {
		a = new Domain(rs.getInt(1), rs.getString(2), rs.getString(3));
	    }
	} catch (SQLException ex) {
	    Logger.getLogger(ServiceDomain.class.getName()).log(Level.SEVERE, null, ex);
	}
	return a;

    }

    public Domain getByName(String name) {
	Domain a = null;
	String requete = " select* from tbl_Domain where name='" + name + "'";
	try {

	    st = cnx.createStatement();
	    rs = st.executeQuery(requete);
	    if (rs.next()) {
		a = new Domain(rs.getInt(1), rs.getString(2), rs.getString(3));
	    }
	} catch (SQLException ex) {
	    Logger.getLogger(ServiceDomain.class.getName()).log(Level.SEVERE, null, ex);
	}
	return a;

    }

}
