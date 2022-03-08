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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import tn.nebulagaming.models.Admin;
import tn.nebulagaming.services.ServiceAdmin;
import tn.nebulagaming.utils.GlobalConfig;

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
    @FXML
    private Button btnBackAd;
    private Admin user;
    private String id;

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

    @FXML
    private void goToAdmin(ActionEvent event) throws IOException {

	FXMLLoader loader = new FXMLLoader(getClass().getResource("./AdminHome.fxml"));
	Parent root = loader.load();

	ServiceAdmin sem = new ServiceAdmin();

	int conf = GlobalConfig.getInstance().getSession();

	this.user = sem.loadDataModifyId(conf);

	AdminHomeController HomeScene = loader.getController();

	HomeScene.user = this.user;
	HomeScene.id = id;
	Admin a = this.user;
	HomeScene.iniializeFxml(a);
	HomeScene.showData(a);
	Stage window = (Stage) btnBackAd.getScene().getWindow();
	window.setScene(new Scene(root, 1920, 1080));
    }
    
}
