/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tn.nebulagaming.views;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.nebulagaming.models.Event;
import tn.nebulagaming.models.Participation;
import tn.nebulagaming.models.Post;
import tn.nebulagaming.services.ServiceEvent;
import tn.nebulagaming.services.ServicePost;

/**
 * FXML Controller class
 *
 * @author SuperNova
 */
public class ManageContentController implements Initializable {

    
    
    
    
    
    

    
    
    @FXML
    public static TabPane tabPane;
    @FXML
    public static Tab tabPosts;
    @FXML
    public static Tab tabEvents;
            
            
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        
        
    }    
    private void goBackToMain(ActionEvent event) {
         try {
                Parent page1 = FXMLLoader.load(getClass().getResource("MainBackOffice.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(ManageContentController.class.getName()).log(Level.SEVERE, null, ex);
            }   
    }
}
