/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import tn.nebulagaming.models.Promos;
import tn.nebulagaming.utils.GlobalConfig;

/**
 *
 * @author rayen
 */
public class ServicePromos implements IService<Promos> {

    Connection cnx;

    public ServicePromos() {
	cnx = GlobalConfig.getInstance().getCONNECTION();

    }

    public void ajouter(Promos t) {
	try {
	    String request = "INSERT INTO tbl_promo (codePromo, discountPromo,idProduct,createdDTM)VALUES(?, ?,?, ?)";
	    PreparedStatement st = cnx.prepareStatement(request);
	    st.setString(1, t.getCodePromo());
	    st.setInt(2, t.getDiscountPromo());
	    st.setInt(3, t.getIdProduct());
	    st.setDate(4, Date.valueOf(LocalDate.now()));
	    st.executeUpdate();
	    System.out.println("Code Promo ajouté");
	} catch (SQLException ex) {
	    System.err.println(ex.getMessage());
	}
    }

    public void supprimer(Promos t) {
	try {
	    String request = "DELETE FROM tbl_promo WHERE id=?";
	    PreparedStatement st = cnx.prepareStatement(request);
	    st.setInt(1, t.getIdPromo());
	    st.executeUpdate();
	    System.out.println("Code Promo supprimée!");
	} catch (SQLException ex) {
	    System.err.println(ex.getMessage());
	}

    }

    public void modifier(Promos t) {
	try {
	    String request = "UPDATE tbl_promo SET codePromo=?, discountPromo=? ,idProduct = ? createdDTM = ?  WHERE id=?";
	    PreparedStatement st = cnx.prepareStatement(request);

	    st.setString(1, t.getCodePromo());
	    st.setInt(2, t.getDiscountPromo());
	    st.setInt(3, t.getIdProduct());
	    st.setDate(4, Date.valueOf(LocalDate.now()));
	    st.setInt(5, t.getIdPromo());

	    st.executeUpdate();
	    System.out.println("Code promo modifié");
	} catch (SQLException ex) {
	    System.err.println(ex.getMessage());

	}

    }

    public List<Promos> afficher() {
	List<Promos> list = new ArrayList<>();
	try {
	    String request = "SELECT* FROM tbl_promo";
	    PreparedStatement st = cnx.prepareStatement(request);
	    ResultSet rs = st.executeQuery();
	    while (rs.next()) {
		list.add(new Promos(rs.getInt("idPromo"), rs.getString(2), rs.getDate("createdDTM"), rs.getInt(4), rs.getInt(5)));
	    }
	} catch (SQLException ex) {
	    System.err.println(ex.getMessage());
	}
	return list;
    }

    public List<Promos> getOfProduct(int prodId) {

	return this.afficher().stream().filter(c -> c.getIdProduct() == prodId).collect(Collectors.toList());
    }
}
