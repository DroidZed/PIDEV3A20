/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.nebulagaming.services;

import tn.nebulagaming.models.Entreprise;

import tn.nebulagaming.utils.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import tn.nebulagaming.models.Membre;

public class ServiceEntreprise extends ServiceUser implements IServiceU<Entreprise> {

    Connection cnx;
    JavaMail sMail;

    public ServiceEntreprise() {
	cnx = GlobalConfig.getInstance().getCONNECTION();
	sMail = new JavaMail();
    }

    public void ajouter(Entreprise e) {

	UserUtiles sUser = new UserUtiles();

	if (e.getNom() == null) {
	    System.out.println("Verifier votre nom Votre nom ne doit pas être vide");
	} else if (!sUser.testEmail(e.getEmail())) {
	    System.out.println("Verifier votre mail veillez saisir une adresse mail valide");
	} else if (!sUser.testPassword(e.getPassword())) {
	    System.out.println("Verifier mot de passe Votre mot de passe doit doit contenir au moins une une majuscule et un chiffre ");
	} else if (verifierEmailBd(e.getEmail())) {
	    System.out.println("Verifier email, email doit etre unique");
	} else if (!sUser.testTel(e.getTel())) {
	    System.out.println("Verifier votre numero telephone Veillez mettre un numero de telephone valide");

	}// else if (filePhoto == null) {
	//alert_Box("Verifier la photo", "inserer une photo");}
	//else if (e.getCv().isEmpty()) {
	// alert_Box("Verifier le cv", "Ecrire quelque chose ici");}
	else {
	    //String photo = copyPhoto();
	    e = new Entreprise(e.getNom(), e.getEmail(), sUser.crypterPassword(e.getPassword()), e.getTel(), e.getPhoto(), e.getRole(), e.getCv(), e.getEtatCompte());
	    PreparedStatement stmt = null;
	    try {

		String sql = "INSERT INTO tbl_user (nameUser,emailUser,pwdUser,phone,photoUser,roleUser ,cv,stateUser) VALUES(?,?,?,?,?,?,?,?)";
		stmt = cnx.prepareStatement(sql);
		stmt.setString(1, e.getNom());
		stmt.setString(2, e.getEmail());
		stmt.setString(3, e.getPassword());
		stmt.setString(4, e.getTel());
		stmt.setString(5, e.getPhoto());
		stmt.setString(6, "Entreprise");
		stmt.setString(7, e.getCv());
		stmt.setString(8, "0");

		stmt.executeUpdate();
		sMail.sendTextMail("Compte créer avec succès", e.getEmail(), "Bonjour M." + e.getNom() + " et bienvenua a votre entreprise sur notre platforme ");
		System.out.println("Compte créer avec succès Vous venez de recevoir un e-mail de confirmation");

	    } catch (SQLException ex) {
		Logger.getLogger(ServiceAdmin.class.getName()).log(Level.SEVERE, null, ex);
	    }

	}

    }

    public void modifier(Entreprise e) {
	PreparedStatement stmt;
	try {

	    String sql = "UPDATE tbl_user SET nameUser=?, phone=? ,cv=? WHERE emailUser=?";
	    stmt = cnx.prepareStatement(sql);
	    stmt.setString(1, e.getNom());
	    stmt.setString(2, e.getTel());
	    stmt.setString(3, e.getCv());
	    stmt.setString(4, e.getEmail());
	    stmt.executeUpdate();

	} catch (SQLException ex) {
	    Logger.getLogger(ServiceMembre.class.getName()).log(Level.SEVERE, null, ex);
	}

    }

    public void modifierCv(String mail, String cv) {
	PreparedStatement stmt;
	try {

	    String sql = "UPDATE tbl_user SET cv=? WHERE emailUser=?";
	    stmt = cnx.prepareStatement(sql);
	    stmt.setString(1, cv);
	    stmt.setString(2, mail);
	    stmt.executeUpdate();
	} catch (SQLException ex) {
	    Logger.getLogger(ServiceMembre.class.getName()).log(Level.SEVERE, null, ex);
	}

    }

    @Override
    public Entreprise loadDataModify(String id) {
	Entreprise e = new Entreprise();
	PreparedStatement stmt;

	try {
            String sql = "SELECT * FROM tbl_user WHERE emailUser=?";
	    stmt = cnx.prepareStatement(sql);
	    stmt.setString(1, id);

	    ResultSet rst = stmt.executeQuery();
	    while (rst.next()) {
		e.setNom(rst.getString("nameUser"));
		e.setEmail(rst.getString("emailUser"));
		e.setTel(rst.getString("phone"));
		e.setPhoto(rst.getString("photoUser"));
		e.setCv(rst.getString("cv"));
	    }
	} catch (SQLException ex) {

	}
	return e;
    }

   public Entreprise loadDataModifyId(int id) {
	Entreprise e = new Entreprise();
	PreparedStatement stmt;

	try {
	    String sql = "SELECT * FROM tbl_user WHERE idUser=?";
	    stmt = cnx.prepareStatement(sql);
	    stmt.setInt(1, id);

	    ResultSet rst = stmt.executeQuery();
	    while (rst.next()) {
		e.setNom(rst.getString("nameUser"));
		e.setEmail(rst.getString("emailUser"));
		e.setTel(rst.getString("phone"));
		e.setPhoto(rst.getString("photoUser"));
		e.setCv(rst.getString("cv"));
		e.setRole(rst.getString("roleUser"));
	    }
	} catch (SQLException ex) {
	    System.out.println(ex.getMessage());
	}
	return e;
    }

    public List<Entreprise> afficher() {
	PreparedStatement stmt = null;
	List<Entreprise> entreprises = new ArrayList<>();
	try {
	    String sql = " SELECT tbl_user.nameUser,tbl_user.emailUser,tbl_user.cv,tbl_user.phone,tbl_user.stateUser,tbl_stateuser.name,tbl_user.photoUser,tbl_user.createdDTM FROM tbl_user,tbl_stateuser WHERE roleUser=? AND tbl_user.stateUser=tbl_stateuser.idStateUser";
	    stmt = cnx.prepareStatement(sql);
	    stmt.setString(1, "Entreprise");

	    ResultSet rst = stmt.executeQuery();
	    while (rst.next()) {
		Entreprise e = new Entreprise();
		e.setNom(rst.getString("tbl_user.nameUser"));
		e.setEmail(rst.getString("tbl_user.emailUser"));
		e.setCv(rst.getString("tbl_user.cv"));
		e.setTel(rst.getString("tbl_user.phone"));
		e.setEtatCompte(rst.getString("name"));
		e.setPhoto(rst.getString("tbl_user.photoUser"));
		e.setDate(rst.getString("tbl_user.createdDTM"));
		// System.out.println("nom :  "+e.getNom()+  ", mail : "+e.getEmail() +", tel : "+e.getTel() + " ");
		entreprises.add(e);
		//System.out.println(e.getEtatCompte());
	    }
	} catch (SQLException ex) {
	    System.out.println(ex.getMessage());
	}
	return entreprises;

    }

    public List rechercher(String index) {
	UserUtiles sUser = new UserUtiles();
	ServiceUser su = new ServiceUser();
	List<Entreprise> result = null;
	if (su.verifierEmailBd(index)) {
	    result = afficher().stream().filter(line -> index.equals(line.getEmail())).collect(Collectors.toList());
	    System.out.println("----------");
	    result.forEach(System.out::println);
	    return result;

	} else if (su.verifierNomBd(index)) {
	    result = afficher().stream().filter(line -> index.equals(line.getNom())).collect(Collectors.toList());
	    System.out.println("----------");
	    result.forEach(System.out::println);
	    return result;

	} else if (sUser.testTel(index)) {
	    result = afficher().stream().filter(line -> index.equals(line.getTel())).collect(Collectors.toList());
	    System.out.println("----------");
	    result.forEach(System.out::println);
	    return result;

	} else {
	    result = afficher().stream().filter(line -> index.equals(line.getEtatCompte())).collect(Collectors.toList());
	    System.out.println("----------");
	    result.forEach(System.out::println);

	    return result;
	}

    }

    public List<Entreprise> trier() {
	List<Entreprise> sortedByName = afficher().stream().sorted(Comparator.comparing(Entreprise::getNom)).collect(Collectors.toList());
	sortedByName.forEach(System.out::println);
	return sortedByName;
    }

    public List<Entreprise> trierTel() {
	List<Entreprise> sortedByName = afficher().stream().sorted(Comparator.comparing(Entreprise::getTel)).collect(Collectors.toList());
	sortedByName.forEach(System.out::println);
	return sortedByName;
    }

    public List<Entreprise> trierMulti() {
	Comparator<Entreprise> compareByName = Comparator.comparing(Entreprise::getNom).thenComparing(Entreprise::getTel);

	List<Entreprise> sortedByNameAndTel = afficher().stream()
		.sorted(compareByName)
		.collect(Collectors.toList());

	sortedByNameAndTel.forEach(System.out::println);
	return sortedByNameAndTel;
    }

    public List<Entreprise> trierMultiTelFirst() {
	Comparator<Entreprise> compareByName = Comparator.comparing(Entreprise::getTel).thenComparing(Entreprise::getNom);

	List<Entreprise> sortedByNameAndTel = afficher().stream()
		.sorted(compareByName)
		.collect(Collectors.toList());

	sortedByNameAndTel.forEach(System.out::println);
	return sortedByNameAndTel;
    }

}
