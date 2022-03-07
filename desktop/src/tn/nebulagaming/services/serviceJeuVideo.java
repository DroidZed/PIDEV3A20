/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import models.JeuVideo;
import models.publication;
import utils.jdbc;

/**
 *
 * @author dell
 */
public class serviceJeuVideo implements Services<JeuVideo>{
    
    Connection cnx = jdbc.getInstance().getCnx();
    String req = "";
    Statement st;
    ResultSet rs;
    Scanner sc = new Scanner(System.in);

    public serviceJeuVideo() {
        con = jdbc.getInstance().getCnx();
    }

    private Connection con;
    private Statement ste;
    private PreparedStatement pst ;
    private ResultSet res ;

    
    public void supprimer(String a) {

        System.out.println("Veuillez saisir le nom du jeu vidéo:\n");
        a = sc.nextLine();
        try {
             req = "DELETE FROM tbl_videogame where nameVg=" + a;
             st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Jeu vidéo supprimée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    
    public void modifier(String a) {

        
        try {
            req = "UPDATE tbl_videogame SET nameVg= ? WHERE nameVg=" + a;
            st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Jeu vidéo modifée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    


    public void Get_Image(String nameVg, String filename) {
        // update sql
        String req = "SELECT imageVg FROM tbl_videogame WHERE nameVg=?";
        rs = null;

        try (
                PreparedStatement pstmt = cnx.prepareStatement(req);) {
            // set parameter;
            pstmt.setString(1, nameVg);
            rs = pstmt.executeQuery();

            // write binary stream into file
            File file = new File(filename);
            FileOutputStream output = new FileOutputStream(file);

            System.out.println("Writing to file " + file.getAbsolutePath());
            while (rs.next()) {
                InputStream input = rs.getBinaryStream("imageVg");
                byte[] buffer = new byte[1024];
                while (input.read(buffer) > 0) {
                    output.write(buffer);
                }
            }
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    }


    public List<JeuVideo> affrrricher() {

        String nomJ=sc.nextLine();
        String fileN = sc.nextLine();

        this.Get_Image(nomJ, fileN);
        List<JeuVideo> list = new ArrayList<>();
        
        try {
            req = "SELECT * FROM tbl_videogame";
            st = cnx.createStatement();
            rs = st.executeQuery(req);
            while(rs.next()) {
                //list.add(new JeuVideo(rs.getString(2)));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return list;
    }

    public List<JeuVideo> rechercher(String a)
    {
        

        List<JeuVideo> list = new ArrayList<>();
        try
        {
            req = "SELECT * FROM tbl_videogame where nameVg='"+ a;
            st = cnx.createStatement();
            rs = st.executeQuery(req);
            while (rs.next())
            {
                //list.add(new JeuVideo(rs.getInt(1), rs.getString(2), rs.getBlob(3), rs.getInt(4), rs.getInt(5)));
            }
        }catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
        }

    return list;
    } 
/*public void DonnerAvis()
{
    System.out.println("vous avez aimé le jeu? donnez votre note!(1:nulle, 2:moyenne, 3:excellente)\n");
    float note = sc.nextFloat();
    if (note == 1)
    {
        
    }
}

public List<JeuVideo> rating()
{
    List<JeuVideo> JeuVideoRate = this.afficher();

    
    JeuVideoRate.stream().mapToDouble(t -> t.setRate(note));

    double average = JeuVideoRate.stream()
                .mapToDouble(t -> t.getRate())
                .average()
                .getAsDouble();

    return JeuVideoRate;
}*/

    @Override
    public void ajouter(JeuVideo a) throws SQLException  {
        PreparedStatement PS = con.prepareStatement("INSERT INTO `tbl_videogame` (`nameVg`, `imageVg`) VALUES (?, ?);");
        PS.setString(1, a.getNameVg());
        PS.setString(2, a.getImageVg());
        PS.executeUpdate();
  }

    @Override
    public void supprimer(JeuVideo t) throws SQLException  {
        PreparedStatement PS = con.prepareStatement("DELETE FROM `tbl_videogame` WHERE `id`=?");
        PS.setInt(1,t.getId());
        PS.executeUpdate();
    }

    @Override
    public void modifier(JeuVideo t) throws SQLException  {
        PreparedStatement PS = con.prepareStatement("UPDATE `tbl_videogame` SET `nameVg`=?,`imageVg`=? ,`rating`=? WHERE `id`=?");
        PS.setString(1, t.getNameVg());
        PS.setString(2, t.getImageVg());
        PS.setFloat(3, t.getRating());
        PS.setInt(4, t.getId());
        PS.executeUpdate();
    }
    
    @Override
    public List<JeuVideo> afficher() throws SQLException {
        List<JeuVideo> AL = new ArrayList<>();
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select * from tbl_videogame");
        while (rs.next()) {
            int id = rs.getInt(1);
            String nom = rs.getString(3);
            String image = rs.getString(4);
            JeuVideo a = new JeuVideo(id, nom, image);
            AL.add(a);
        }
        return AL;
    }
    
    
  public JeuVideo getByName(String n) {
          JeuVideo a = null;
         String requete = " select * from tbl_videogame WHERE nameVg='"+n+"';";
        try {
            ste = con.createStatement();
            res=ste.executeQuery(requete);
            if (res.next())
            {a=new JeuVideo(res.getInt(1),res.getString(3),res.getString(4));}
        } catch (SQLException ex) {
            Logger.getLogger(serviceJeuVideo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return a ;
        
    }
  
  public int getRating(String nom) throws SQLException
  {
      
      int rating = 0;
        String req = ("SELECT * FROM `tbl_videogame` WHERE `nameVg`='"+nom+"';");
        ste = con.createStatement();
        
        res=ste.executeQuery(req);
        while(rs.next())
        {
            rating = rs.getInt(6);       
        }
        
        return rating;
  }
  
  public void print ()
    {
// Récupère un PrinterJob
      PrinterJob job = PrinterJob.getPrinterJob();
      // Définit son contenu à imprimer
      job.defaultPage();
      // Affiche une boîte de choix d'imprimante
      if (job.printDialog()){
         try {
            // Effectue l'impression
            job.print();
         } catch (PrinterException ex) {
            ex.printStackTrace();
         }
      }
   }
  
  public ObservableList<JeuVideo> getGamesList() throws SQLException {
           
        ObservableList<JeuVideo> GameList = FXCollections.observableArrayList();
        
        List <JeuVideo> id = new ArrayList<>(); 
        Statement stm = cnx.createStatement();
        String query = "select * from tbl_videogame";

        //ResultSet rs;
        rs = stm.executeQuery(query);
        JeuVideo vg;
        while (rs.next()) {
            vg= new JeuVideo(rs.getString(3)); 
            //System.out.println(events);
            GameList.add(vg);

        }
        return GameList;

    }
  
    public ObservableList<JeuVideo> getNames_Rating() throws SQLException {
           
        ObservableList<JeuVideo> GameList = FXCollections.observableArrayList();
        
        List <JeuVideo> id = new ArrayList<>(); 
        Statement stm = cnx.createStatement();
        String query = "select * from tbl_videogame";

        //ResultSet rs;
        rs = stm.executeQuery(query);
        JeuVideo vg;
        while (rs.next()) {
            vg= new JeuVideo(rs.getString(3), rs.getInt(6)); 
            //System.out.println(events);
            GameList.add(vg);

        }
        return GameList;

    }
    
    public List <JeuVideo> liste()
    {
        String req = "select * from tbl_videogame"; 
        
       List <JeuVideo> list = new ArrayList<>(); 
       try {
       ste = cnx.createStatement(); 
       rs=ste.executeQuery(req); 
       
       while (rs.next())
       {
           list.add(new JeuVideo(rs.getString(3))); 
       }
       
       }
       catch (SQLException ex)
       {
       Logger.getLogger(JeuVideo.class.getName()).log(Level.SEVERE, null, ex);
       }
    return list; 
    }
    
    

  



}


