/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tn.nebulagaming.views;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import tn.nebulagaming.models.Event;
import tn.nebulagaming.services.ServiceEvent;

/**
 * FXML Controller class
 *
 * @author SuperNova
 */
public class AddEventController implements Initializable {

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
    private TextField tfAddressEvent;
    @FXML
    private TextField tfNbTickets;
    @FXML
    private Button btnAddEvent;
    @FXML
    private Button btnGoBack;
    
    ServiceEvent se = new ServiceEvent () ;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         populateListViewVisibility() ; 
         
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
    

    
    public void populateListViewVisibility () {
        ObservableList<String> items = FXCollections.observableArrayList();
        cbVisibilityEvent.setItems(items);
        items.add("Not Visible");
        items.add("Visible");
        
        cbVisibilityEvent.getSelectionModel().selectFirst();
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
    
    @FXML
    private void addEvent(ActionEvent event) {
        
        if (validateInputs()) {
         java.sql.Date postedDTM = new java.sql.Date (Calendar.getInstance().getTime().getTime());
         java.sql.Date startDTM =java.sql.Date.valueOf(tfStartDateEvent.getValue());
         java.sql.Date endDTM =java.sql.Date.valueOf(tfEndDateEvent.getValue());
         
         String visibilityStr = cbVisibilityEvent.getValue() ; 
            int visibilityInt ; 
            if (visibilityStr.equals("Visible")) 
                visibilityInt = 1 ;
            else 
                visibilityInt = 0 ;
            
        se.add(new Event (postedDTM , tfTitleEvent.getText(), tfDescEvent.getText(),visibilityInt, tfPhotoEvent.getText(),"Event" , startDTM,endDTM,1,tfAddressEvent.getText(), Integer.parseInt(tfNbTickets.getText()))) ; 
        JOptionPane.showMessageDialog(null, "Event Added !");                                   
        }
        
    }
    
}
