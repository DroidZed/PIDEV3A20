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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.nebulagaming.models.Post;
import tn.nebulagaming.services.ServicePost;

/**
 * FXML Controller class
 *
 * @author SuperNova
 */
public class UpdatePostController implements Initializable {

    @FXML
    private TextField tfTitlePost;
    @FXML
    private TextField tfDescPost;
    @FXML
    private ComboBox<String> cbVisibilityPost;
    @FXML
    private TextField tfPhotoPost;
    @FXML
    private Button btnSubmitAddPost;
    @FXML
    private Button btnGoBack;

    ServicePost sp = new ServicePost () ;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        populateListViewVisibility() ; 
        tfTitlePost.setText(ManagePostsController.postRecup.getTitlePost());
        tfDescPost.setText(ManagePostsController.postRecup.getDescPost());
        tfPhotoPost.setText(ManagePostsController.postRecup.getPhotoPost());
        
        if (ManagePostsController.postRecup.getStatusPost() == 1) 
            cbVisibilityPost.getSelectionModel().selectLast();
        else 
            cbVisibilityPost.getSelectionModel().selectFirst();
        
        btnGoBack.setOnAction(event -> {
            try {
                Parent page1 = FXMLLoader.load(getClass().getResource("ManagePosts.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(AddPostController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }    

      public boolean validateInputs() {
        if (tfTitlePost.getText().isEmpty()) {
                Alert alert1 = new Alert(Alert.AlertType.WARNING);
                alert1.setTitle("Failed !");
                alert1.setContentText("Title cannot be empty , try entering one !");
                alert1.setHeaderText(null);
                alert1.show();
                return false;
        } else if (tfDescPost.getText().isEmpty()) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Failed !");
                alert2.setContentText("Description cannot be empty , try entering one !");
                alert2.setHeaderText(null);
                alert2.show();
                return false;
        }
        return true;
    }
      
    public void populateListViewVisibility () {
        ObservableList<String> items = FXCollections.observableArrayList();
        cbVisibilityPost.setItems(items);
        items.add("Not Visible");
        items.add("Visible");
        
        /*String visibilityStr = cbVisibilityPost.getValue() ;
        cbVisibilityPost.setValue(visibilityStr);*/
    }

      
    @FXML
    private void updatePost(ActionEvent event) {
        if (validateInputs()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Update Post");
                alert.setHeaderText(null);
                alert.setContentText("Please confirm your modifications ");
                            Optional<ButtonType> action = alert.showAndWait();
                            if (action.get() == ButtonType.OK)
                            {  
                                String visibilityStr = cbVisibilityPost.getValue() ; 
                                int visibilityInt ; 
                                if (visibilityStr.equals("Visible")) 
                                    visibilityInt = 1 ;
                                else 
                                    visibilityInt = 0 ; 
                                
                             sp.update(new Post (ManagePostsController.postRecup.getIdPost(),tfTitlePost.getText() , tfDescPost.getText(), tfPhotoPost.getText() ,visibilityInt));
                                 try {
                                    Parent page1 = FXMLLoader.load(getClass().getResource("ManagePosts.fxml"));
                                    Scene scene = new Scene(page1);
                                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                    stage.setScene(scene);
                                    stage.show();
                                } catch (IOException ex) {
                                    Logger.getLogger(UpdatePostController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                         }
                      }
        
                     }
    
}
