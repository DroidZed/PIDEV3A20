/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import jdbc.jdbc;
import models.offer;
import models.test;
/**
 *
 * @author User
 */
public class serviceTest implements services <test>{
    Connection cnx = jdbc.getInstance().getCnx();
    String req = "";
    Statement st;
    ResultSet rs;
    Scanner sc = new Scanner(System.in);

    public serviceTest() {
    }


    
    @Override
    public void ajouter(test t) 
    {
        try {
            req = "INSERT INTO tbl_test ( durationTest, questionTest, nbQuestion, choix, question) VALUES ('" + t.getDurationTest()+ "','"+ t.getQuestionTest()+"','"+ t.getNbQuestion()+"','"+ t.getChoix()+"','"+ t.getQuestion()+"')";
            st = cnx.prepareStatement(req);
            st.executeUpdate(req);
            System.out.println("Test ajouté !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    public void modifier(test t) {
        
        try {

                String query2="update tbl_test set (durationTest=?,questionTest=?,nbQuestion=?,choix=?,question=?) where idTest = ?";

                PreparedStatement smt = cnx.prepareStatement(query2);
                smt.setString(1, t.getDurationTest());
                smt.setString(2, t.getQuestionTest());
                smt.setInt(3, t.getNbQuestion());
                smt.setString(4, t.getChoix());
                smt.setString(5, t.getQuestion());
                smt.setInt(6, t.getIdTest());
                

                smt.executeUpdate();
                System.out.println("Test modifié !");
            } 
            catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
    }
@Override
   public List<test> afficher()
{
        List<test> list = new ArrayList<>();
        
        try {
             req = "SELECT * FROM tbl_test";
             st = cnx.createStatement();
             rs = st.executeQuery(req);
            while(rs.next()) {
                list.add( new test(rs.getString(2),rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6)));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return list;
}

public String corrigerTest(String a, String b, String c, String d, String e)
{
    String result = "";

    System.out.println(this.afficher());

    System.out.println("ecrivez vos reponses:\n");
    a = sc.nextLine();
    b = sc.nextLine();
    c = sc.nextLine();
    d = sc.nextLine();
    e = sc.nextLine();

    if ((a == "c") && (b == "c") && (c == "c") && (d == "c") && (e == "a"))
        result = "ADMIS";
    else
        result = "REFUSE";

    System.out.println(result);
    return result;
}

    @Override
    public void supprimer(test t) {
        try {
        String query2="DELETE FROM tbl_test WHERE idTest = ?";
        PreparedStatement smt = cnx.prepareStatement(query2) ;
        smt.setInt(1, t.getIdTest());
        smt.executeUpdate();
        System.out.println("Test supprimé !");}
    catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    }
  
}
