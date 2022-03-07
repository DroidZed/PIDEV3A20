/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tn.nebulagaming.views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author SuperNova
 */
public class MainBackOfficeController implements Initializable {

    @FXML
    private Button btnManagePosts;
    @FXML
    private Button btnManageGamification;
    @FXML
    private Button btnManageBadges;
    @FXML
    private Button btnViewAsUser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnManagePosts.setOnAction(event -> {
            try {
                Parent page1 = FXMLLoader.load(getClass().getResource("ManageContent.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(MainBackOfficeController.class.getName()).log(Level.SEVERE, null, ex);
            }    
        });
        
        btnManageGamification.setOnAction(event -> {
            try {
                Parent page1 = FXMLLoader.load(getClass().getResource("ManageContent.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(MainBackOfficeController.class.getName()).log(Level.SEVERE, null, ex);
            }    
        });
        
        btnManageBadges.setOnAction(event -> {
            try {
                Parent page1 = FXMLLoader.load(getClass().getResource("ManageBadge.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(MainBackOfficeController.class.getName()).log(Level.SEVERE, null, ex);
            }    
        });
        
        btnViewAsUser.setOnAction(event -> {
            try {
                Parent page1 = FXMLLoader.load(getClass().getResource("NewsFeed.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(MainBackOfficeController.class.getName()).log(Level.SEVERE, null, ex);
            }    
        });
        
        
    }    
    
}
