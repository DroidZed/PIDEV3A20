/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tn.nebulagaming.models.Categories;
import tn.nebulagaming.utils.GlobalConfig;

/**
 *
 * @author rayen
 */
public class ServiceCategories implements IService<Categories> {

    Connection cnx = GlobalConfig.getInstance().getCONNECTION();

    public void ajouter(Categories t) {
	try {
	    String request = "INSERT INTO tbl_category (nameCategory)VALUES(?)";
	    PreparedStatement st = cnx.prepareStatement(request);
	    st.setString(1, t.getNameCategory());
	    st.executeUpdate();
	    System.out.println("Category ajouté");
	} catch (SQLException ex) {
	    System.err.println(ex.getMessage());
	}
    }

    public void supprimer(Categories t) {
	try {
	    String request = "DELETE FROM tbl_category WHERE idCategory=?";
	    PreparedStatement st = cnx.prepareStatement(request);
	    st.setInt(1, t.getIdCategory());
	    st.executeUpdate();
	    System.out.println("Categorie supprimée!");
	} catch (SQLException ex) {
	    System.err.println(ex.getMessage());
	}

    }

    public void modifier(Categories t) {
	try {
	    String request = "UPDATE tbl_category SET nameCategory=? WHERE idCategory=?";
	    PreparedStatement st = cnx.prepareStatement(request);
	    st.setInt(2, t.getIdCategory());
	    st.setString(1, t.getNameCategory());
	    st.executeUpdate();
	    System.out.println(t);
	    System.out.println("Categorie modifiée");
	} catch (SQLException ex) {
	    System.err.println(ex.getMessage());

	}

    }

    public List<Categories> afficher() {
	List<Categories> list = new ArrayList<>();
	try {
	    String request = "SELECT* FROM tbl_category";
	    PreparedStatement st = cnx.prepareStatement(request);
	    ResultSet rs = st.executeQuery();
	    while (rs.next()) {
		list.add(new Categories(rs.getInt("idCategory"), rs.getString(2)));
	    }
	} catch (SQLException ex) {
	    System.err.println(ex.getMessage());
	}
	return list;
    }

    public int getIdByCategoryNameJoinedProduct(String name) {

	int idc = 0;

	try {
	    String request = "select DISTINCT(c.idCategory) from tbl_category c join tbl_product p using (idCategory) where c.nameCategory like ?";
	    PreparedStatement st = cnx.prepareStatement(request);
	    st.setString(1, name);
	    ResultSet rs = st.executeQuery();
	    while (rs.next()) {
		idc = rs.getInt(1);
	    }
	} catch (SQLException ex) {
	    System.err.println(ex.getMessage());
	}

	return idc;

    }

    public int getIdByCategoryName(String name) {

	int idc = 0;

	try {
	    String request = "select idCategory from tbl_category where nameCategory like ?";
	    PreparedStatement st = cnx.prepareStatement(request);
	    st.setString(1, name);
	    ResultSet rs = st.executeQuery();
	    while (rs.next()) {
		idc = rs.getInt(1);
	    }
	} catch (SQLException ex) {
	    System.err.println(ex.getMessage());
	}

	return idc;
    }

}
