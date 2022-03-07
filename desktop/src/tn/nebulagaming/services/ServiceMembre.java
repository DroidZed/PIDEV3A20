/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.nebulagaming.services;

import tn.nebulagaming.models.Membre;

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
import tn.nebulagaming.models.Entreprise;
import tn.nebulagaming.models.Streaming;
import tn.nebulagaming.utils.GlobalConfig;
import tn.nebulagaming.utils.MailUtiles;
import tn.nebulagaming.utils.UserUtiles;
import tn.nebulagaming.services.IServiceU;

public class ServiceMembre extends ServiceUser implements IServiceU<Membre> {

    Connection cnx;

    public ServiceMembre() {
        cnx = GlobalConfig.getInstance().getCONNECTION();
    }

    @Override
    public void ajouter(Membre e) {
        UserUtiles sUser = new UserUtiles();
        MailUtiles sMail = new MailUtiles();
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

            e = new Membre(e.getNom(), e.getEmail(), sUser.crypterPassword(e.getPassword()), e.getTel(), e.getPhoto(), e.getRole(), e.getDescription(), e.getEtatCompte());
            PreparedStatement stmt = null;

            try {
                String sql = "INSERT INTO tbl_user (nameUser,emailUser,pwdUser,phone,photoUser,roleUser,descUser,stateUser) VALUES(?,?,?,?,?,?,?,?)";
                stmt = cnx.prepareStatement(sql);
                stmt.setString(1, e.getNom());
                stmt.setString(2, e.getEmail());
                stmt.setString(3, e.getPassword());
                stmt.setString(4, e.getTel());
                stmt.setString(5, e.getPhoto());
                stmt.setString(6, "Membre");
                stmt.setString(7, e.getDescription());
                stmt.setInt(8, 0);

                stmt.executeUpdate();

            } catch (SQLException ex) {
                Logger.getLogger(ServiceAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void ajouterMembreConsole(Membre e) {

        PreparedStatement stmt;
        try {
            String sql = "INSERT INTO tbl_user (nameUser,emailUser,pwdUser,phone,photoUser,roleUser,descriptionUser,stateUser) VALUES(?,?,?,?,NULL,?,?)";
            stmt = cnx.prepareStatement(sql);
            stmt.setString(1, e.getNom());
            stmt.setString(2, e.getEmail());
            stmt.setString(3, e.getPassword());
            stmt.setString(4, e.getTel());

            stmt.setString(5, String.valueOf(2));
            stmt.setString(6, e.getDescription());

            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ServiceAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void modifier(Membre e) {
        PreparedStatement stmt;
        try {
            String sql = "UPDATE tbl_user SET nameUser=? ,phone=? ,descUser=? WHERE emailUser=?";
            stmt = cnx.prepareStatement(sql);
            stmt.setString(1, e.getNom());
            stmt.setString(2, e.getTel());
            stmt.setString(3, e.getDescription());
            stmt.setString(4, e.getEmail());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceMembre.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Membre loadDataModify(String id) {
        Membre e = new Membre();
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
                e.setDescription(rst.getString("descUser"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return e;
    }

    @Override
    public List<Membre> afficher() {
        PreparedStatement stmt = null;
        List<Membre> members = new ArrayList<>();

        try {

            String sql = "SELECT tbl_user.nameUser,tbl_user.emailUser,tbl_user.descUser,tbl_user.phone,tbl_user.stateUser,tbl_stateuser.name,tbl_user.photoUser ,createdDTM FROM tbl_user,tbl_stateuser WHERE roleUser=? AND tbl_user.stateUser=tbl_stateuser.idStateUser ";
            stmt = cnx.prepareStatement(sql);
            stmt.setString(1, "Membre");

            ResultSet rst = stmt.executeQuery();

            while (rst.next()) {
                Membre e = new Membre();
                e.setNom(rst.getString("nameUser"));

                e.setEmail(rst.getString("emailUser"));

                e.setTel(rst.getString("phone"));
                e.setDescription(rst.getString("DescUser"));
                e.setPhoto(rst.getString("photoUser"));
                e.setEtatCompte(rst.getString("name"));
                e.setDate(rst.getString("createdDTM"));
                members.add(e);
                // System.out.println(" nom: "+e.getNom()+ ", desciption:  "+e.getDescription()+ ", mail: "+e.getEmail() +", tel: "+e.getTel() + " ");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }

        return members;

    }

    /**
     *
     * @param index
     */
    public List rechercher(String index) {
        UserUtiles sUser = new UserUtiles();
        ServiceUser su = new ServiceUser();
        List<Membre> result = null;
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

    public List<Membre> trier() {
        List<Membre> sortedByName = afficher().stream().sorted(Comparator.comparing(Membre::getNom)).collect(Collectors.toList());
        sortedByName.forEach(System.out::println);
        return sortedByName;
    }

    public List<Membre> trierMulti() {
        Comparator<Membre> compareByName = Comparator.comparing(Membre::getNom).thenComparing(Membre::getTel);

        List<Membre> sortedByNameAndTel = afficher().stream()
                .sorted(compareByName)
                .collect(Collectors.toList());

        sortedByNameAndTel.forEach(System.out::println);
        return sortedByNameAndTel;
    }

    public List<Membre> trierTel() {
        List<Membre> sortedByName = afficher().stream().sorted(Comparator.comparing(Membre::getTel)).collect(Collectors.toList());
        sortedByName.forEach(System.out::println);
        return sortedByName;
    }

    public List<Membre> trierMultiTelFirst() {
        Comparator<Membre> compareByName = Comparator.comparing(Membre::getTel).thenComparing(Membre::getNom);

        List<Membre> sortedByNameAndTel = afficher().stream()
                .sorted(compareByName)
                .collect(Collectors.toList());

        sortedByNameAndTel.forEach(System.out::println);
        return sortedByNameAndTel;
    }
   public List<Streaming> afficherStreamers()
   {
       PreparedStatement stmt = null;
        List<Streaming> streamers = new ArrayList<>();

        try {

            String sql = "select description,idStream,tbl_streaming.idUser,link,nbVu,etat,nameUser from tbl_streaming,tbl_user where tbl_streaming.idUser=tbl_user.idUser and etat=1";
            stmt = cnx.prepareStatement(sql);
            

            ResultSet rst = stmt.executeQuery();

            while (rst.next()) {
                Streaming s = new Streaming();
                s.setIdStream(rst.getInt("idStream"));
               s.setNameUser(rst.getString("nameUser"));
               s.setDescription(rst.getString("description"));

                s.setLink(rst.getString("link"));
                s.setNbVu(rst.getInt("nbVu"));
               
                streamers.add(s);
                // System.out.println(" nom: "+e.getNom()+ ", desciption:  "+e.getDescription()+ ", mail: "+e.getEmail() +", tel: "+e.getTel() + " ");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }

        return streamers;
   }
   public boolean verifStream( Streaming s)
   {
        PreparedStatement stmt = null;
        ResultSet rst = null;
        try {
            String sql = "SELECT idUser FROM tbl_streaming WHERE idUser=?";
            stmt = cnx.prepareStatement(sql);
            stmt.setInt(1, s.getIdUser());
            rst = stmt.executeQuery();
            if (rst.next()) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
       
   }
   public void ajouterStram(Streaming s)
   {
            PreparedStatement stmt = null;
if(!verifStream(s)){
            try {
                String sql = "INSERT INTO tbl_streaming (idUser,link,description,nbVu,etat) VALUES(?,?,?,?,?)";
                stmt = cnx.prepareStatement(sql);
                stmt.setInt(1,GlobalConfig.getInstance().getSession() );
                stmt.setString(2, s.getLink());
                stmt.setString(3, s.getDescription());
                stmt.setInt(4, s.getNbVu());
                stmt.setInt(5, Integer.parseInt("1"));
               

                stmt.executeUpdate();

            } catch (SQLException ex) {
                Logger.getLogger(ServiceAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }
}

else {
    try {
                String sql = "update  tbl_streaming set description=? ,link=? , etat=1 where idUser=?";
                stmt = cnx.prepareStatement(sql);
                stmt.setString(1, s.getDescription());
                stmt.setString(2, s.getLink());
                stmt.setInt(3,s.getIdUser() );
                
                
              

                stmt.executeUpdate();

            } catch (SQLException ex) {
                Logger.getLogger(ServiceAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }
}
   }
 public void envoyerM(String m)
 {
     PreparedStatement stmt = null;

            try {
                String sql = "INSERT INTO tbl_chat (idUser,message) VALUES(?,?)";
                stmt = cnx.prepareStatement(sql);
                stmt.setInt(1,GlobalConfig.getInstance().getSession() );
                stmt.setString(2, m);
            
               

                stmt.executeUpdate();

            } catch (SQLException ex) {
                Logger.getLogger(ServiceAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }
 }
 public String afficherM()
 {
     PreparedStatement stmt = null;
      String message = null;

        try {

            String sql = "select message from tbl_chat ";
            stmt = cnx.prepareStatement(sql);
            

            ResultSet rst = stmt.executeQuery();

            while (rst.next()) {

               String newLine = System.getProperty("line.separator");

               message=rst.getString("message");

                
               
                
                // System.out.println(" nom: "+e.getNom()+ ", desciption:  "+e.getDescription()+ ", mail: "+e.getEmail() +", tel: "+e.getTel() + " ");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }

        return message;
 }
 public String getVue(Streaming s){
     PreparedStatement stmt = null;
      int message = 0;

        try {

            String sql = "select nbVu from tbl_streaming where idUser=?";
            stmt = cnx.prepareStatement(sql);
            
       stmt.setInt(1, s.getIdUser());
            ResultSet rst = stmt.executeQuery();

            while (rst.next()) {

               

               message=rst.getInt("nbVu");

                
               
                
                // System.out.println(" nom: "+e.getNom()+ ", desciption:  "+e.getDescription()+ ", mail: "+e.getEmail() +", tel: "+e.getTel() + " ");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }

        return String.valueOf(message);
 }

 
 public void finishStream(Streaming s){
       PreparedStatement stmt = null;

            try {
                String sql = "UPDATE tbl_streaming set etat=0 where idUser=?";
                stmt = cnx.prepareStatement(sql);
                stmt.setInt(1,GlobalConfig.getInstance().getSession() );
               
            
               

                stmt.executeUpdate();

            } catch (SQLException ex) {
                Logger.getLogger(ServiceAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }
 }
 public void augmenterVu(Streaming s)
 {
     PreparedStatement stmt = null;

            try {
                String sql = "UPDATE tbl_streaming set nbVu=? where idUser=? ";
                stmt = cnx.prepareStatement(sql);
                
                stmt.setInt(1, Integer.parseInt(getVue(s))+1);
                stmt.setInt(2,s.getIdUser() );
                System.out.println(Integer.parseInt(getVue(s)+1)+" user id est"+ s.getIdUser());
               
            
               

                stmt.executeUpdate();

            } catch (SQLException ex) {
                Logger.getLogger(ServiceAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }
 }
 public void reduireVu(Streaming s)
 {
     PreparedStatement stmt = null;

            try {
                String sql = "UPDATE tbl_streaming set nbVu=? where idUser=? ";
                stmt = cnx.prepareStatement(sql);
                stmt.setInt(1, Integer.parseInt(getVue(s))-1);
                stmt.setInt(2,s.getIdUser() );
               
            
               

                stmt.executeUpdate();

            } catch (SQLException ex) {
                Logger.getLogger(ServiceAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }
 }
 public Streaming loadS(String link,String nom) throws SQLException
 {
     
    Streaming s = new Streaming();
        PreparedStatement stmt;

        try {
            String sql = "SELECT idStream,tbl_streaming.idUser,link,description,nbVu,etat FROM tbl_streaming,tbl_user WHERE link=? and (select nameUser from tbl_user where tbl_user.idUser=tbl_streaming.idUser)=?";
            stmt = cnx.prepareStatement(sql);
            stmt.setString(1, link);
            stmt.setString(2, nom);
            ResultSet rst = stmt.executeQuery();
            while (rst.next()) {
                s.setDescription(rst.getString("description"));
                s.setIdStream(rst.getInt("idStream"));
                s.setLink(rst.getString("link"));
                s.setNbVu(rst.getInt("nbVu"));
                s.setIdUser(Integer.parseInt(rst.getString("idUser")));
                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return s;
 }
 
}
