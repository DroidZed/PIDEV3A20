/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tn.nebulagaming.views;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.nebulagaming.models.Comment;
import tn.nebulagaming.models.Reaction;
import tn.nebulagaming.services.ServiceEvent;
import tn.nebulagaming.services.ServiceReaction;

/**
 * FXML Controller class
 *
 * @author SuperNova
 */
public class ManageReactionController implements Initializable {

    @FXML
    private TableView<Reaction> tvReaction;
    @FXML
    private TableColumn<Reaction, String> userNameCol;
    @FXML
    private TableColumn<Reaction, String> typeReactionCol;
    @FXML
    private TableColumn<Reaction, String> reactedAtCol;
    @FXML
    private Label lbEventName;
    @FXML
    private Label createdAtEvent;
    @FXML
    private Label nbLikes;
    @FXML
    private Label nbDislikes;

    int idEvent =ManageEventController.eventRecupParticipation.getIdPost();
    ServiceEvent se = new ServiceEvent () ; 
    ServiceReaction sr = new ServiceReaction () ; 
    @FXML
    private Button btnGoBack;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        nbLikes.setText (String.valueOf(sr.countLikesByPost(idEvent ))); 
        nbDislikes.setText(String.valueOf(sr.countDislikesByPost(idEvent))); 
        
        displayReactions (idEvent) ;
        btnGoBack.setOnAction(event -> {
            try {
                Parent page1 = FXMLLoader.load(getClass().getResource("ManageContent.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(AddPostController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }   
    
    public void displayReactions (int idEvent) {
        
       lbEventName.setText(se.getEventById(idEvent).getTitlePost());
       createdAtEvent.setText(String.valueOf(se.getEventById(idEvent).getPostedDTM()));
        
       List<Reaction> list=  sr.displayReactionByEvent(idEvent) ;
       
       userNameCol.setCellValueFactory(new PropertyValueFactory<>("idUser"));
       typeReactionCol.setCellValueFactory(new PropertyValueFactory<>("idTypeReact"));
       reactedAtCol.setCellValueFactory(new PropertyValueFactory<>("reactedDTM"));
       
       ObservableList<Reaction> listReactions = FXCollections.observableArrayList(list) ;
       tvReaction.setItems (listReactions) ;
    }
    
}
