/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package tn.nebulagaming;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.nebulagaming.utils.GlobalConfig;

/**
 *
 * @author Aymen Dhahri
 */
public class NebulaGamingMain extends Application {
    
    @Override
    public void start(Stage primaryStage) {

    	try {

	    GlobalConfig.getInstance();
	    
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("./views/Login.fxml")); //load view

	    Parent root = loader.load();
	    Scene scene = new Scene(root);

	    primaryStage.setTitle("Nebula Gaming");
	    primaryStage.setScene(scene);
	    primaryStage.show();
	} catch (IOException ex) {
	    System.out.println(ex.getMessage());
	}

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	launch(args);
    }
    
}
