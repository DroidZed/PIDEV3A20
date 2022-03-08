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
import tn.nebulagaming.models.Badge;
import tn.nebulagaming.utils.GlobalConfig;

/**
 *
 * @author SuperNova
 */
public class ServiceBadge implements IServiceNewsfeed<Badge> {

    Connection cnx;

    public ServiceBadge() {

	cnx = GlobalConfig.getInstance().getCONNECTION();
    }

    @Override
    public void add(Badge badge) {
	try {
	    String query = "INSERT INTO tbl_Badge (nameBadge,descBadge,photoBadge) VALUES ('" + badge.getNameBadge() + "' , '" + badge.getDescBadge() + "','" + badge.getPhotoBadge() + "')";
	    Statement st = cnx.createStatement();
	    st.executeUpdate(query);
	    System.out.println("Badge added with success !");

	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
    }

    @Override
    public void delete(int idBadge) {
	try {
	    String query = "DELETE FROM tbl_badge WHERE idBadge =" + idBadge;
	    Statement st = cnx.createStatement();
	    st.executeUpdate(query);
	    System.out.println("Badge deleted with success !");

	} catch (SQLException e) {
	    System.err.println(e.getMessage());
	}
    }

    @Override
    public void update(Badge badge) {
	try {
	    String req = "UPDATE tbl_badge SET nameBadge='" + badge.getNameBadge() + "',descBadge='" + badge.getDescBadge() + "',photoBadge='" + badge.getPhotoBadge() + "' WHERE idBadge=" + badge.getIdBadge();
	    Statement st = cnx.createStatement();
	    st.executeUpdate(req);
	    System.out.println("Badge edited with success !");
	} catch (SQLException ex) {
	    System.out.println(ex.getMessage());
	}
    }

    @Override
    public List<Badge> display() {
	List<Badge> listBadge = new ArrayList<>();

	try {
	    String query = "SELECT * FROM tbl_badge";
	    Statement st = cnx.createStatement();
	    ResultSet rs = st.executeQuery(query);
	    while (rs.next()) {
		listBadge.add(new Badge(rs.getInt(1),
			rs.getString(2),
			rs.getString(3),
			rs.getString(4)));
	    }

	} catch (SQLException e) {
	    System.err.println(e.getMessage());
	}
	return listBadge;
    }

}
