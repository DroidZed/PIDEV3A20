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
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import tn.nebulagaming.utils.GlobalConfig;
import tn.nebulagaming.models.Reclamation;

/**
 *
 * @author houba
 */
public class ServiceReclamation implements IServiceU<Reclamation> {

    Connection cnx;

    public ServiceReclamation() {
	cnx = GlobalConfig.getInstance().getCONNECTION();
    }

    public void ajouter(Reclamation r) {
	PreparedStatement stmt = null;
	try {
	    String sql = "INSERT INTO tbl_reclamation (idUser,typeComplaint,message,statusComplaint) VALUES(?,?,?,?)";
	    stmt = cnx.prepareStatement(sql);
	    stmt.setInt(1, GlobalConfig.getInstance().getSession());
	    stmt.setInt(2, getTypeRec(r.getTypeComplaint()));
	    stmt.setString(3, r.getMessage());

	    stmt.setString(4, "NON TRAITEE");
	    //stmt.setString(5, r.getResponse());
	    // stmt.setInt(7, 0);

	    stmt.executeUpdate();

	} catch (SQLException ex) {
	    Logger.getLogger(ServiceAdmin.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    public int getTypeRec(String type) {
	PreparedStatement stmt;
	int id = -1;
	try {
	    String sql = "SELECT idType FROM tbl_typecomplaint WHERE nameType=?";
	    stmt = cnx.prepareStatement(sql);
	    stmt.setString(1, type);

	    ResultSet rst = stmt.executeQuery();
	    while (rst.next()) {

		id = rst.getInt("idType");
	    }
	} catch (SQLException ex) {
	    System.out.println(ex.getMessage());
	}
	return id;
    }

    public void modifier(Reclamation r) {
	PreparedStatement stmt;
	try {
	    String sql = "UPDATE tbl_reclamation SET message=? ,typeComplaint=? WHERE idComplaint=?";
	    stmt = cnx.prepareStatement(sql);

	    stmt.setString(1, r.getMessage());
	    stmt.setString(2, r.getTypeComplaint());
	    stmt.setInt(3, r.getIdRec());

	} catch (SQLException ex) {
	    Logger.getLogger(ServiceMembre.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    public Reclamation loadDataModify(Reclamation rec) {
	Reclamation r = new Reclamation();
	PreparedStatement stmt;
	try {
	    String sql = "SELECT idComplaint, nameUser,nameType,message,statusComplaint "
		    + "FROM tbl_typecomplaint, tbl_user,tbl_reclamation WHERE tbl_typecomplaint.idType=tbl_reclamation.typeComplaint AND tbl_user.idUser=tbl_reclamation.idUser"
		    + " AND nameType=? AND message=? AND statusComplaint=? AND nameUser=?";
	    stmt = cnx.prepareStatement(sql);
	    stmt.setString(1, rec.getTypeComplaint());
	    stmt.setString(2, rec.getMessage());
	    stmt.setString(3, rec.getStatusComplaint());
	    stmt.setString(4, rec.getNomUser());

	    ResultSet rst = stmt.executeQuery();
	    while (rst.next()) {
		r.setIdComplaint(rst.getInt("idComplaint"));
		r.setStatus(rst.getString("statusComplaint"));
		r.setType(rst.getString("nameType"));
		r.setMessage(rst.getString("message"));
		r.setNameUser(rst.getString("nameUser"));
		// r.setIdUser(rst.getInt("tbl_user.idUser"));
	    }
	} catch (SQLException ex) {
	    System.out.println(ex.getMessage());
	}
	return r;
    }

    public List<Reclamation> afficher() {
	PreparedStatement stmt = null;
	List<Reclamation> reclamations = new ArrayList<>();

	try {

	    String sql = "SELECT message,nameUser,nameType,statusComplaint,answerComplaint FROM tbl_user,tbl_reclamation,tbl_typecomplaint WHERE tbl_reclamation.idUser=? AND tbl_user.idUser=? AND tbl_reclamation.typeComplaint=tbl_typecomplaint.idType";
	    stmt = cnx.prepareStatement(sql);
	    stmt.setInt(1, GlobalConfig.getInstance().getSession());
	    stmt.setInt(2, GlobalConfig.getInstance().getSession());
	    ResultSet rst = stmt.executeQuery();

	    while (rst.next()) {
		Reclamation r = new Reclamation();
		r.setNameUser(rst.getString("nameUser"));
		r.setStatus(rst.getString("statusComplaint"));
		r.setType(rst.getString("nameType"));
		r.setMessage(rst.getString("message"));
		r.setReponse(rst.getString("answerComplaint"));

		reclamations.add(r);
		// System.out.println(" nom: "+e.getNom()+ ", desciption:  "+e.getDescription()+ ", mail: "+e.getEmail() +", tel: "+e.getTel() + " ");
	    }
	} catch (SQLException ex) {
	    System.out.println(ex.getMessage());

	}

	return reclamations;

    }

    public List<Reclamation> afficherAll() {
	PreparedStatement stmt = null;
	List<Reclamation> reclamations = new ArrayList<>();

	try {

	    String sql = "SELECT statusComplaint,idComplaint,message,answerComplaint,tbl_typecomplaint.nameType,tbl_reclamation.idUser,tbl_user.nameUser FROM tbl_reclamation,tbl_user,tbl_typecomplaint where tbl_user.idUser=tbl_reclamation.idUser and tbl_reclamation.typeComplaint=tbl_typecomplaint.idType";
	    stmt = cnx.prepareStatement(sql);

	    ResultSet rst = stmt.executeQuery();

	    while (rst.next()) {
		Reclamation r = new Reclamation();
		r.setNameUser(rst.getString("tbl_user.nameUser"));
		r.setStatus(rst.getString("statusComplaint"));
		r.setType(rst.getString("tbl_typecomplaint.nameType"));

		r.setIdComplaint(rst.getInt("idComplaint"));
		r.setMessage(rst.getString("message"));
		r.setReponse(rst.getString("answerComplaint"));
		r.setIdUser(rst.getInt("idUser"));

		reclamations.add(r);
	    }
	} catch (SQLException ex) {
	    System.out.println(ex.getMessage());

	}

	return reclamations;

    }

    public List<Reclamation> rechercher(String index) {
	ServiceUser su = new ServiceUser();
	List<Reclamation> result = null;
	if (su.verifierNomBd(index)) {
	    result = afficher().stream().filter(line -> index.equals(line.getNomUser())).collect(Collectors.toList());
	    System.out.println("----------");
	    result.forEach(System.out::println);
	    return result;
	}
	if ("NON TRAITEE".equals(index) || "TRAITEE".equals(index)) {
	    result = afficher().stream().filter(line -> index.equals(line.getStatusComplaint())).collect(Collectors.toList());
	    System.out.println("----------");
	    result.forEach(System.out::println);
	} else {
	    result = afficher().stream().filter(line -> index.equals(line.getTypeComplaint())).collect(Collectors.toList());
	    System.out.println("----------");
	    result.forEach(System.out::println);
	    return result;
	}
	return result;
    }

    public List<Reclamation> rechercherAdm(String index) {
	ServiceUser su = new ServiceUser();
	List<Reclamation> result = null;
	if (su.verifierNomBd(index)) {
	    result = afficherAll().stream().filter(line -> index.equals(line.getNomUser())).collect(Collectors.toList());
	    System.out.println("----------");
	    result.forEach(System.out::println);
	    return result;
	}
	if ("NON TRAITEE".equals(index) || "TRAITEE".equals(index)) {
	    result = afficherAll().stream().filter(line -> index.equals(line.getStatusComplaint())).collect(Collectors.toList());
	    System.out.println("----------");
	    result.forEach(System.out::println);
	} else {
	    result = afficherAll().stream().filter(line -> index.equals(line.getTypeComplaint())).collect(Collectors.toList());
	    System.out.println("----------");
	    result.forEach(System.out::println);
	    return result;
	}
	return result;
    }

    public List<String> afficherTypeDb() {
	PreparedStatement stmt = null;
	List<String> liste = new ArrayList<>();

	try {

	    String sql = "SELECT nameType FROM tbl_typecomplaint ";
	    stmt = cnx.prepareStatement(sql);

	    ResultSet rst = stmt.executeQuery();
	    int i = 0;
	    while (rst.next()) {
		String typeR;
		i++;
		typeR = rst.getString("nameType");
		System.out.println(i + ")" + typeR);
		liste.add(typeR);
		// System.out.println(" nom: "+e.getNom()+ ", desciption:  "+e.getDescription()+ ", mail: "+e.getEmail() +", tel: "+e.getTel() + " ");
	    }
	} catch (SQLException ex) {
	    System.out.println(ex.getMessage());
	}
	return liste;
    }

    public List<Reclamation> trier() {
	List<Reclamation> sortedByName = afficher().stream().sorted(Comparator.comparing(Reclamation::getNomUser)).collect(Collectors.toList());
	sortedByName.forEach(System.out::println);
	return sortedByName;
    }

    public List<Reclamation> trierAdm() {
	List<Reclamation> sortedByName = afficherAll().stream().sorted(Comparator.comparing(Reclamation::getNomUser)).collect(Collectors.toList());
	sortedByName.forEach(System.out::println);
	return sortedByName;
    }

    public List<Reclamation> trierEtat() {
	List<Reclamation> sortedByName = afficher().stream().sorted(Comparator.comparing(Reclamation::getStatusComplaint)).collect(Collectors.toList());
	sortedByName.forEach(System.out::println);
	return sortedByName;
    }

    public List<Reclamation> trierEtatAdm() {
	List<Reclamation> sortedByName = afficherAll().stream().sorted(Comparator.comparing(Reclamation::getStatusComplaint)).collect(Collectors.toList());
	sortedByName.forEach(System.out::println);
	return sortedByName;
    }

    public List<Reclamation> trierType() {
	List<Reclamation> sortedByName = afficher().stream().sorted(Comparator.comparing(Reclamation::getTypeComplaint)).collect(Collectors.toList());
	sortedByName.forEach(System.out::println);
	return sortedByName;
    }

    public List<Reclamation> trierTypeAdm() {
	List<Reclamation> sortedByName = afficherAll().stream().sorted(Comparator.comparing(Reclamation::getTypeComplaint)).collect(Collectors.toList());
	sortedByName.forEach(System.out::println);
	return sortedByName;
    }

    public List<Reclamation> trierMulti() {
	Comparator<Reclamation> compareByName = Comparator.comparing(Reclamation::getTypeComplaint).thenComparing(Reclamation::getStatusComplaint);

	List<Reclamation> sortedByNameAndTel = afficher().stream()
		.sorted(compareByName)
		.collect(Collectors.toList());

	sortedByNameAndTel.forEach(System.out::println);
	return sortedByNameAndTel;
    }

    public List<Reclamation> trierMultiAdm() {
	Comparator<Reclamation> compareByName = Comparator.comparing(Reclamation::getTypeComplaint).thenComparing(Reclamation::getStatusComplaint);

	List<Reclamation> sortedByNameAndTel = afficherAll().stream()
		.sorted(compareByName)
		.collect(Collectors.toList());

	sortedByNameAndTel.forEach(System.out::println);
	return sortedByNameAndTel;
    }

    public List<Reclamation> trierNomTypeAdm() {
	Comparator<Reclamation> compareByName = Comparator.comparing(Reclamation::getNomUser).thenComparing(Reclamation::getTypeComplaint);

	List<Reclamation> sortedByNameAndTel = afficherAll().stream()
		.sorted(compareByName)
		.collect(Collectors.toList());

	sortedByNameAndTel.forEach(System.out::println);
	return sortedByNameAndTel;
    }

    public List<Reclamation> trierMultiThree() {
	Comparator<Reclamation> compareByName = Comparator.comparing(Reclamation::getNomUser).thenComparing(Reclamation::getTypeComplaint).thenComparing(Reclamation::getStatusComplaint);

	List<Reclamation> sortedByNameAndTel = afficherAll().stream()
		.sorted(compareByName)
		.collect(Collectors.toList());

	sortedByNameAndTel.forEach(System.out::println);
	return sortedByNameAndTel;
    }

    public void traiterReclamation(Reclamation r, String rep) {
	PreparedStatement stmt;
	try {
	    String sql = "UPDATE tbl_reclamation SET answerComplaint = ?, statusComplaint = ? WHERE idComplaint = ?";
	    if (r.getStatusComplaint().equals("NON TRAITEE")) {
		stmt = cnx.prepareStatement(sql);

		stmt.setString(1, rep);
		stmt.setString(2, "TRAITEE");
		stmt.setInt(3, r.getIdRec());

		stmt.executeUpdate();
	    }
	} catch (SQLException ex) {
	    Logger.getLogger(ServiceMembre.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    public Reclamation loadDataModify(String id) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
