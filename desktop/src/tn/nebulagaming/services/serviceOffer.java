/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import jdbc.jdbc;
import models.candidacy;
import models.domain;

import models.offer;
/**
 *
 * @author User
 */
public class serviceOffer implements services <offer>{
    
    Connection cnx ;
    String req = "";
    Statement st;
    ResultSet rs;
    
                serviceDomain sd = new serviceDomain();


    public serviceOffer() {
                cnx = jdbc.getInstance().getCnx();
    }

    
    @Override
    public void ajouter(offer t) 
    {
        try {
            req = "INSERT INTO tbl_offer ( titleOffer, Typeoffer, descOffer, startDTM, endDTM, salaryOffer, regionOffer, addressOffer,idDomain) VALUES ('" + t.getTitleOffer()+ "','"+ t.getTypeoffer()+"','"+ t.getDescOffer()+"','"+ t.getStartDTM()+"','"+ t.getEndDTM()+"','"+ t.getSalaryOffer()+"','"+ t.getRegionOffer()+"','"+ t.getAddressOffer()+"','"+ t.getIdDomain()+"')";
            st = cnx.prepareStatement(req);
            st.executeUpdate(req);
            System.out.println("Offre ajoutée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
      
    @Override
    public void supprimer (offer t) {
    try {
        String query2="DELETE FROM tbl_offer WHERE idOffer = ?";
        PreparedStatement smt = cnx.prepareStatement(query2) ;
        smt.setInt(1, t.getIdOffer());
        smt.executeUpdate();
        System.out.println("Offre supprimée !");}
    catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }}
        
    @Override
    public void modifier(offer t) {        
        try {
                String query2="update `tbl_offer` set `titleOffer`=?,`TypeOffer`=?, `descOffer`=?, `startDTM`=?, `endDTM`=?, `salaryOffer`=?, `regionOffer`=?, `addressOffer`=? where `idOffer` = ?";
                PreparedStatement smt = cnx.prepareStatement(query2);
                smt.setString(1, t.getTitleOffer());
                smt.setString(2, t.getTypeoffer());
                smt.setString(3, t.getDescOffer());
                smt.setDate(4, t.getStartDTM());
                smt.setDate(5, t.getEndDTM());
                smt.setFloat(6, t.getSalaryOffer());
                smt.setString(7, t.getRegionOffer());
                smt.setString(8, t.getAddressOffer());
                smt.setInt(9, t.getIdOffer());
                smt.executeUpdate();
            } 
            catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
    }

      
    @Override
    public List<offer> afficher() {
        List<offer> list = new ArrayList<>();
        
        try {
            req = "SELECT * FROM tbl_offer";
            st = cnx.prepareStatement(req);
            rs = st.executeQuery(req);
            while(rs.next()) {
                offer f = new offer(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5), rs.getDate(6), rs.getFloat(7), rs.getString(8), rs.getString(9), rs.getInt(10), rs.getInt(11));
                domain d = sd.getById(f.getIdDomain());
                f.setDomain(d.getName());
                list.add(f);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return list;
    }

    public void TriSalary () 
    {
            List<offer> sortedByDatePub = this.afficher().stream()
            .sorted(Comparator.comparing(offer::getSalaryOffer))
            .collect(Collectors.toList());

            sortedByDatePub.forEach(System.out::println);
    }

public void Rechercher(String reg)
    {
        List<offer> result = this.afficher();

        result.stream()
                .filter(t -> t.getRegionOffer().startsWith(reg))
                .findFirst()
                .ifPresent(t -> System.out.println(t));
    }

    
 public offer getById(int idoffre) {
          offer a = null;
         String requete = " select* from tbl_offer where idOffer="+idoffre ;
        try {
           
            st = cnx.createStatement();
            rs=st.executeQuery(requete);
            if (rs.next())
            {
               a=new offer(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5), rs.getDate(6), rs.getFloat(7), rs.getString(8), rs.getString(9), rs.getInt(10), rs.getInt(11));
            }
        } catch (SQLException ex) {
            Logger.getLogger(serviceOffer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return a ;
        
    }
 
  public int calculer(int iddomaine) {
          int l = 0 ;
         String requete = "SELECT COUNT(*) FROM tbl_offer WHERE idDomain= "+iddomaine ;
        try {
           
           Statement st =cnx.createStatement();
           ResultSet rs=st.executeQuery(requete);
           if (rs.next()){
          String chaine = String.valueOf(rs.getString(1));
           l=Integer.parseInt(chaine);
            System.out.println(l);}
        } catch (SQLException ex) {
            Logger.getLogger(serviceOffer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      return l ;
    }
     
   
}


