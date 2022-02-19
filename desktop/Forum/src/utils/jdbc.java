/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;
import java.sql.*;

/**
 *
 * @author dell
 */
public class jdbc {

    private static jdbc instance;
    private Connection cnx;
    
    private final String URL = "jdbc:mysql://localhost:3306/nebulagaming";
    private final String USER = "root";
    private final String PASSWORD = "";
    
    private jdbc() {
        try {
            cnx = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connecting !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static jdbc getInstance() {
        if(instance == null)
            instance = new jdbc();
        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }
    
}
