/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.nebulagaming.services;

import tn.nebulagaming.models.Admin;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import tn.nebulagaming.utils.GlobalConfig;

public class ServiceAdmin implements IService<Admin> {

    Connection cnx;

    public ServiceAdmin() {
        cnx = GlobalConfig.getInstance().getCONNECTION();
    }

    public void ajouter(Admin a) {

        PreparedStatement stmt;
        try {
            String sql = "INSERT INTO user (nom,email,password,tel,photo,role,etatCompte) VALUES( ?,? ,? ,? , ?,?,?)";
            stmt = cnx.prepareStatement(sql);
            stmt.setString(1, a.getNom());
            stmt.setString(2, a.getEmail());
            stmt.setString(3, a.getPassword());
            stmt.setString(4, a.getTel());
            stmt.setString(5, a.getPhoto());
            //  stmt.setString(6, a.getRole().getId().toString());

            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ServiceAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void modifier(Admin a) {

        PreparedStatement stmt;
        try {

            String sql = "UPDATE tbl_user SET nameUser=? ,phone=? WHERE emailUser=?";
            stmt = cnx.prepareStatement(sql);
            stmt.setString(1, a.getNom());
            stmt.setString(2, a.getTel());
            stmt.setString(3, a.getEmail());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ServiceAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void modifierCv(String mail, String photo) {
        PreparedStatement stmt;
        try {

            String sql = "UPDATE tbl_user SET photo=? WHERE email=?";
            stmt = cnx.prepareStatement(sql);
            stmt.setString(1, photo);
            stmt.setString(2, mail);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceMembre.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public Admin loadDataModify(String id) {
        Admin a = new Admin();
        PreparedStatement stmt;
        String photo;
        try {
            String sql = "SELECT * FROM tbl_user WHERE emailUser=?";
            stmt = cnx.prepareStatement(sql);
            stmt.setString(1, id);

            ResultSet rst = stmt.executeQuery();
            while (rst.next()) {
                a.setNom(rst.getString("nameUser"));
                a.setEmail(rst.getString("emailUser"));
                a.setTel(rst.getString("phone"));
                a.setPhoto(rst.getString("photoUser"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return a;
    }

    public List<Admin> afficher() {
        PreparedStatement stmt = null;
        List<Admin> Admin = new ArrayList<>();

        try {

            String sql = "SELECT * FROM tbl_user WHERE role=? ";
            stmt = cnx.prepareStatement(sql);
            stmt.setInt(1, 0);

            ResultSet rst = stmt.executeQuery();

            while (rst.next()) {
                Admin a = new Admin();
                a.setNom(rst.getString("Nom"));
                a.setEmail(rst.getString("Email"));
                a.setTel(rst.getString("Tel"));
                a.setPhoto(rst.getString("Photo"));
                // a.setEtatCompte(rst.getString("stateUser"));
                Admin.add(a);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        return Admin;

    }

    public boolean verif(String mail) {

        Statement stm;
        String role = "";

        try {
            stm = cnx.createStatement();

            String query = "SELECT * FROM tbl_user WHERE email='" + mail + "'";
            ResultSet rst = stm.executeQuery(query);

            while (rst.next()) {
                role = rst.getString("role");
                if ("3".equals(role)) {

                    return true;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ServiceAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public List<Admin> rechercher(String index) {
        List<Admin> result = afficher().stream().filter(line -> index.equals(line.getEmail())).collect(Collectors.toList());
        System.out.println("----------");
        result.forEach(System.out::println);
        return result;
    }

    public List<Admin> trier() {
        List<Admin> sortedByName = afficher().stream().sorted(Comparator.comparing(Admin::getNom)).collect(Collectors.toList());
        sortedByName.forEach(System.out::println);
        return sortedByName;
    }

    public List<Admin> trierMulti() {
        Comparator<Admin> compareByName = Comparator.comparing(Admin::getNom).thenComparing(Admin::getTel);

        List<Admin> sortedByNameAndTel = afficher().stream()
                .sorted(compareByName)
                .collect(Collectors.toList());

        sortedByNameAndTel.forEach(System.out::println);
        return sortedByNameAndTel;
    }

    public boolean verifAdmin(String mail) {

        Statement stm;
        String role = "";

        try {
            stm = cnx.createStatement();

            String query = "SELECT * FROM tbl_user WHERE emailUser='" + mail + "'";
            ResultSet rst = stm.executeQuery(query);

            while (rst.next()) {
                role = rst.getString("roleUser");
                if ("Admin".equals(role)) {

                    return true;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ServiceAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
