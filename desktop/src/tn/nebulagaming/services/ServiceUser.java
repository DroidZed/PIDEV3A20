/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.nebulagaming.services;

import tn.nebulagaming.models.Admin;
import tn.nebulagaming.utils.GlobalConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceUser implements IServiceUser {

    Connection cnx;

    public ServiceUser() {
        cnx = GlobalConfig.getInstance().getCONNECTION();
    }

    @Override
    public String verifierData(String id, String password) { //Login user 

        PreparedStatement stmt = null;
        ResultSet rst = null;
        String resultat = "";
        try {
            String sql = "SELECT * FROM tbl_user WHERE (phone=? OR emailUser=?) AND pwdUser=? ";
            stmt = cnx.prepareStatement(sql);
            stmt.setString(1, id);
            stmt.setString(2, id);
            stmt.setString(3, password);

            rst = stmt.executeQuery();

            if (rst.next()) {
                if (rst.getInt("stateUser") != 3) {
                    resultat = rst.getString("roleUser");
                    int session = rst.getInt("idUser");
                    GlobalConfig.getInstance().setSession(session);
                } else {
                    System.out.println("compte banni");
                }

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }

        return resultat;
    }

    public void verifierSession(String id, String password) { //Login user 

        PreparedStatement stmt = null;
        ResultSet rst = null;
        int resultat = -1;
        try {
            String sql = "SELECT * FROM tbl_user WHERE (phone=? OR emailUser=?) AND pwdUser=?";
            stmt = cnx.prepareStatement(sql);
            stmt.setString(1, id);
            stmt.setString(2, id);
            stmt.setString(3, password);
            rst = stmt.executeQuery();

            if (rst.next()) {
                resultat = rst.getInt("idUser");
                GlobalConfig.getInstance().setSession(resultat);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public int verifEtatCompte(String mail) {

        PreparedStatement stmt = null;
        ResultSet rst = null;
        int resultat = -1;
        try {
            String sql = "SELECT * FROM tbl_user WHERE emailUser=?";
            stmt = cnx.prepareStatement(sql);
            stmt.setString(1, mail);
            rst = stmt.executeQuery();
            if (rst.next()) {
                resultat = rst.getInt("stateUser");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return resultat;
    }

    public boolean verifierEmailBd(String email) {
        PreparedStatement stmt = null;
        ResultSet rst = null;
        try {
            String sql = "SELECT * FROM tbl_user WHERE emailUser=?";
            stmt = cnx.prepareStatement(sql);
            stmt.setString(1, email);
            rst = stmt.executeQuery();
            if (rst.next()) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public boolean verifierNomBd(String nom) {
        PreparedStatement stmt = null;
        ResultSet rst = null;
        try {
            String sql = "SELECT * FROM tbl_user WHERE nameUser=?";
            stmt = cnx.prepareStatement(sql);
            stmt.setString(1, nom);
            rst = stmt.executeQuery();
            if (rst.next()) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean verifierPwBd(String password) {
        PreparedStatement stmt = null;
        ResultSet rst = null;
        try {
            String sql = "SELECT pwdUser FROM tbl_user WHERE pwdUser=?";
            stmt = cnx.prepareStatement(sql);
            stmt.setString(1, password);
            rst = stmt.executeQuery();
            if (rst.next()) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    @Override
    public void modifierPassword(String mail, String password) {
        PreparedStatement stmt;
        try {

            String sql = "UPDATE tbl_user SET pwdUser=? WHERE emailUser=?";
            stmt = cnx.prepareStatement(sql);
            stmt.setString(1, password);
            stmt.setString(2, mail);
            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ServiceMembre.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void modifierEtatCompte(String mail, String etat) {
        PreparedStatement stmt;
        try {

            String sql = "UPDATE tbl_user SET tbl_user.stateUser = (SELECT idStateUser FROM tbl_stateuser WHERE name=?) WHERE tbl_user.emailUser=? ";
            stmt = cnx.prepareStatement(sql);
            stmt.setString(1, etat);
            stmt.setString(2, mail);

            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ServiceMembre.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<String> afficherEtats() {
        PreparedStatement stmt = null;
        List<String> etats = new ArrayList<>();
        try {
            String sql = "SELECT name FROM tbl_stateuser";
            stmt = cnx.prepareStatement(sql);

            ResultSet rst = stmt.executeQuery();
            while (rst.next()) {
                String e;
                e = (rst.getString("name"));
                System.out.println(e);
                etats.add(e);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return etats;

    }

    public int getId(String mail) {
        PreparedStatement stmt = null;
        ResultSet rst = null;
        int resultat = -1;
        try {
            String sql = "SELECT idUser FROM tbl_user WHERE emailUser=?";
            stmt = cnx.prepareStatement(sql);
            stmt.setString(1, mail);
            rst = stmt.executeQuery();
            if (rst.next()) {
                resultat = rst.getInt("idUser");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return resultat;

    }

    public void modifierPhoto(String mail, String photo) {
        PreparedStatement stmt;
        try {

            String sql = "UPDATE tbl_user SET photoUser=? WHERE emailUser=?";
            stmt = cnx.prepareStatement(sql);
            stmt.setString(1, photo);
            stmt.setString(2, mail);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceMembre.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void supprimerUtilisateur(String mail) {
        PreparedStatement stmt;
        try {

            String sql = "UPDATE  tbl_user set stateUser =? WHERE emailUser=?";
            stmt = cnx.prepareStatement(sql);
            stmt.setInt(1, 3);
            stmt.setString(2, mail);

            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ServiceMembre.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String getEtat(String mail) {
        String etat = null;
        ResultSet rst = null;
        PreparedStatement stmt;
        try {

            String sql = "select name from tbl_stateuser inner join tbl_user on tbl_stateuser.idStateUser=tbl_user.stateUser where emailUser=?";
            stmt = cnx.prepareStatement(sql);

            stmt.setString(1, mail);
            rst = stmt.executeQuery();
            if (rst.next()) {
                etat = rst.getString("name");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return etat;
    }

    public int nbAdmins() {
        Statement stm = null;
        List<Admin> admins = new ArrayList<>();

        try {
            stm = cnx.createStatement();
            String admin = "SELECT email FROM `user` WHERE role='3'";
            ResultSet rst = stm.executeQuery(admin);

            while (rst.next()) {
                Admin c = new Admin();
                c.setEmail(rst.getString("email"));
                admins.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        return admins.size();

    }

    public int nbEntreprises() {
        Statement stm = null;
        List<Admin> Coachs = new ArrayList<>();

        try {
            stm = cnx.createStatement();
            String CoachQ = "SELECT email FROM `user` WHERE role='2'";
            ResultSet rst = stm.executeQuery(CoachQ);

            while (rst.next()) {
                Admin c = new Admin();
                c.setEmail(rst.getString("email"));
                Coachs.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        return Coachs.size();

    }

    public int nbMembres() {
        Statement stm = null;
        List<Admin> Membres = new ArrayList<>();

        try {
            stm = cnx.createStatement();
            String CoachQ = "SELECT email FROM `user` WHERE role='1'";
            ResultSet rst = stm.executeQuery(CoachQ);

            while (rst.next()) {
                Admin c = new Admin();
                c.setEmail(rst.getString("email"));
                Membres.add(c);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        return Membres.size();

    }

}
