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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.nebulagaming.models.Event;
import tn.nebulagaming.models.Post;
import tn.nebulagaming.services.ServiceEvent;

/**
 * FXML Controller class
 *
 * @author SuperNova
 */
public class UpdateEventController implements Initializable {

    @FXML
    private TextField tfTitleEvent;
    @FXML
    private TextField tfDescEvent;
    @FXML
    private ComboBox<String> cbVisibilityEvent;
    @FXML
    private TextField tfPhotoEvent;
    @FXML
    private DatePicker tfStartDateEvent;
    @FXML
    private DatePicker tfEndDateEvent;
    @FXML
    private TextField tfNbTickets;
    @FXML
    private Button btnGoBack;
    @FXML
    private Button btnUpdateEvent;
    @FXML
    private TextField tfAddressEvent;

    ServiceEvent se = new ServiceEvent () ;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        populateListViewVisibility() ;
        tfTitleEvent.setText(ManageEventController.eventRecup.getTitlePost());
        tfDescEvent.setText(ManageEventController.eventRecup.getDescPost());
        tfPhotoEvent.setText(ManageEventController.eventRecup.getPhotoPost());
        tfStartDateEvent.setValue(ManageEventController.eventRecup.getStartDTM().toLocalDate());
        tfEndDateEvent.setValue(ManageEventController.eventRecup.getEndDTM().toLocalDate());
        tfAddressEvent.setText(ManageEventController.eventRecup.getAddressEvent());
        tfNbTickets.setText(Integer.toString(ManageEventController.eventRecup.getNbTicketAvailable()));
        
        
        if (ManageEventController.eventRecup.getStatusPost() == 1) 
            cbVisibilityEvent.getSelectionModel().selectLast();
        else 
            cbVisibilityEvent.getSelectionModel().selectFirst();
        
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
    
    public boolean validateInputs() {
        if (tfTitleEvent.getText().isEmpty()) {
                Alert alert1 = new Alert(Alert.AlertType.WARNING);
                alert1.setTitle("Failed !");
                alert1.setContentText("Title cannot be empty , try entering one !");
                alert1.setHeaderText(null);
                alert1.show();
                return false;
        } else if (tfTitleEvent.getText().chars().allMatch( Character::isDigit)) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Failed !");
                alert2.setContentText("Title cannot be a number , try entering a string ");
                alert2.setHeaderText(null);
                alert2.show();
                return false;
        } else if (tfDescEvent.getText().chars().allMatch( Character::isDigit)) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Failed !");
                alert2.setContentText("Description cannot be a number , try entering a string ");
                alert2.setHeaderText(null);
                alert2.show();
                return false;
        } else if (tfDescEvent.getText().isEmpty()) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Failed !");
                alert2.setContentText("Description cannot be empty , try entering one !");
                alert2.setHeaderText(null);
                alert2.show();
                return false;
        }else if (tfAddressEvent.getText().isEmpty()) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Failed !");
                alert2.setContentText("Address cannot be empty , try entering one !");
                alert2.setHeaderText(null);
                alert2.show();
                return false;
        }else if (tfAddressEvent.getText().chars().allMatch( Character::isDigit)) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Failed !");
                alert2.setContentText("Address cannot be a number , try entering a string !");
                alert2.setHeaderText(null);
                alert2.show();
                return false;
        }else if (tfNbTickets.getText().isEmpty()) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Failed !");
                alert2.setContentText("Address cannot be empty , try entering one !");
                alert2.setHeaderText(null);
                alert2.show();
                return false;
        }else if (! tfNbTickets.getText().chars().allMatch( Character::isDigit)) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Failed !");
                alert2.setContentText("Number of tickets should be a number , try entering one !");
                alert2.setHeaderText(null);
                alert2.show();
                return false;
        }
        return true;
    }

public void populateListViewVisibility () {
        ObservableList<String> items = FXCollections.observableArrayList();
        cbVisibilityEvent.setItems(items);
        items.add("Not Visible");
        items.add("Visible");
        
        /*String visibilityStr = cbVisibilityPost.getValue() ;
        cbVisibilityPost.setValue(visibilityStr);*/
    }    

    @FXML
    private void updateEvent(ActionEvent event) {
        if (validateInputs()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Update event");
                alert.setHeaderText(null);
                alert.setContentText("Please confirm your modifications ");
                            Optional<ButtonType> action = alert.showAndWait();
                            if (action.get() == ButtonType.OK)
                            {  
                                
                                //Visibility 
                                String visibilityStr = cbVisibilityEvent.getValue() ; 
                                int visibilityInt ; 
                                if (visibilityStr.equals("Visible")) 
                                    visibilityInt = 1 ;
                                else 
                                    visibilityInt = 0 ; 
                                
                                //Dates
                                java.sql.Date startDTM =java.sql.Date.valueOf(tfStartDateEvent.getValue());
                                java.sql.Date endDTM =java.sql.Date.valueOf(tfEndDateEvent.getValue());
                                
                             se.update(new Event(ManageEventController.eventRecup.getIdPost(),tfTitleEvent.getText() , tfDescEvent.getText(),visibilityInt , tfPhotoEvent.getText() ,startDTM , endDTM , tfAddressEvent.getText() , Integer.parseInt(tfNbTickets.getText())));
                                 try {
                                    Parent page1 = FXMLLoader.load(getClass().getResource("ManagePosts.fxml"));
                                    
                                    Scene scene = new Scene(page1);
                                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                    stage.setScene(scene);
                                    stage.show();
                                } catch (IOException ex) {
                                    Logger.getLogger(UpdateEventController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                         }
                      }
    }
    
}
