/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.services;

import tn.nebulagaming.models.Publication;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import tn.nebulagaming.utils.GlobalConfig;

/**
 *
 * @author dell
 */
public class ServicePublication implements Services<Publication> {

    private Connection con;
    private Statement ste;
    private PreparedStatement st;
    private ResultSet res;
    private Connection cnx;
    private String req;

    public ServicePublication() {

	cnx = GlobalConfig.getInstance().getCONNECTION();
    }

    @Override
    public void ajouter(Publication t) {
	try {
	    req = "INSERT INTO tbl_publication ( idGV, descriptionPub) VALUES ('" + t.getIdVG() + "','" + t.getDescriptionPub() + "')";
	    st = cnx.prepareStatement(req);
	    st.executeUpdate(req);
	    System.out.println("Publication ajoutée !");
	} catch (SQLException ex) {
	    System.out.println(ex.getMessage());
	}
    }

    @Override
    public void supprimer(Publication t) {
	try {
	    String query2 = "DELETE FROM tbl_publication WHERE id = ?";
	    PreparedStatement smt = cnx.prepareStatement(query2);
	    smt.setInt(1, t.getIdPub());
	    smt.executeUpdate();
	    System.out.println("Publication supprimée !");
	} catch (SQLException ex) {
	    System.out.println(ex.getMessage());
	}
    }

    @Override
    public void modifier(Publication t) {

	try {

	    String query2 = "update tbl_publication set descriptionPub=? where DatePub = ?";

	    PreparedStatement smt = cnx.prepareStatement(query2);
	    smt.setString(1, t.getDescriptionPub());
	    smt.setDate(2, t.getDatePub());

	    smt.executeUpdate();
	    System.out.println("Publication modifiée !");
	} catch (SQLException ex) {
	    System.out.println(ex.getMessage());
	}
    }

    @Override
    public List<Publication> afficher() {
	List<Publication> list = new ArrayList<>();

	try {
	    req = "SELECT * FROM tbl_publication";
	    st = cnx.prepareStatement(req);
	    res = st.executeQuery(req);
	    while (res.next()) {
		list.add(new Publication(res.getInt(1), res.getString(4)));
	    }
	} catch (SQLException ex) {
	    System.out.println(ex.getMessage());
	}

	return list;
    }

    public void TriDate() {
	List<Publication> sortedByDatePub = this.afficher().stream()
		.sorted(Comparator.comparing(Publication::getDatePub))
		.collect(Collectors.toList());

	sortedByDatePub.forEach(System.out::println);
    }

    public void Rechercher(String desc) {
	List<Publication> result = this.afficher();

	result.stream()
		.filter(t -> t.getDescriptionPub().startsWith(desc))
		.findFirst()
		.ifPresent(t -> System.out.println(t));
    }

    public void print() {
// Récupère un PrinterJob
	PrinterJob job = PrinterJob.getPrinterJob();
	// Définit son contenu à imprimer
	job.defaultPage();
	// Affiche une boîte de choix d'imprimante
	if (job.printDialog()) {
	    try {
		// Effectue l'impression
		job.print();
	    } catch (PrinterException ex) {
		ex.printStackTrace();
	    }
	}
    }

    public Publication GetByDesc(String n) {
	Publication a = null;
	String requete = " select * from tbl_publication WHERE descriptionPub='" + n + "';";
	try {
	    ste = con.createStatement();
	    res = ste.executeQuery(requete);
	    if (res.next()) {
		a = new Publication(res.getDate(3), res.getString(5));
	    }
	} catch (SQLException ex) {
	    Logger.getLogger(ServicePublication.class.getName()).log(Level.SEVERE, null, ex);
	}
	return a;

    }

    public void modifier2(Publication t, String ref) throws SQLException {
	PreparedStatement PS = con.prepareStatement("UPDATE `tbl_publication` SET `descriptionPub`=? WHERE `descriptionPub`='" + ref + "';");
	PS.setString(1, t.getDescriptionPub());
	PS.executeUpdate();
    }

    /*public ObservableList<publication> getPubs() throws SQLException {
           
        ObservableList<publication> pubList = FXCollections.observableArrayList();
        
         List <publication> id = new ArrayList<>(); 
        Statement stm = cnx.createStatement();
        String query = "select * from tbl_publication";

        //ResultSet rs;
        rs = stm.executeQuery(query);
        publication pub;
        while (rs.next()) {
           publication P= new publication(rs.getDate(3), rs.getString(5)); 
            //System.out.println(events);
            pubList.add(P);

        }
        return pubList;

    }*/
    public int calculer(int idVG) {
	int l = 0;
	String requete = "SELECT COUNT(*) FROM tbl_publication WHERE idGV= " + idVG;
	try {

	    Statement st = cnx.createStatement();
	    ResultSet rs = st.executeQuery(requete);
	    if (rs.next()) {
		String chaine = String.valueOf(rs.getString(1));
		l = Integer.parseInt(chaine);
		System.out.println(l);
	    }
	} catch (SQLException ex) {
	    Logger.getLogger(ServicePublication.class.getName()).log(Level.SEVERE, null, ex);
	}

	return l;
    }
}
