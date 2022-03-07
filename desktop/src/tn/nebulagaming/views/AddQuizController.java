/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tn.nebulagaming.views;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import tn.nebulagaming.models.Quiz;
import tn.nebulagaming.services.ServiceQuiz;

/**
 * FXML Controller class
 *
 * @author SuperNova
 */
public class AddQuizController implements Initializable {

    @FXML
    private TextField tfTitleQuiz;
    @FXML
    private TextField tfDescQuiz;
    @FXML
    private ComboBox<String> cbVisibilityQuiz;
    @FXML
    private DatePicker dpStartDate;
    @FXML
    private DatePicker dpEndDate;
    @FXML
    private Button btnGoBack;

    ServiceQuiz sq = new ServiceQuiz () ; 
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
                Logger.getLogger(AddEventController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }  
    
    
     public void populateListViewVisibility () {
        ObservableList<String> items = FXCollections.observableArrayList();
        cbVisibilityQuiz.setItems(items);
        items.add("Not Visible");
        items.add("Visible");
        
        cbVisibilityQuiz.getSelectionModel().selectFirst();
    }
    
    
    public boolean validateInputs() {
        if (tfTitleQuiz.getText().isEmpty()) {
                Alert alert1 = new Alert(Alert.AlertType.WARNING);
                alert1.setTitle("Failed !");
                alert1.setContentText("Title cannot be empty , try entering one !");
                alert1.setHeaderText(null);
                alert1.show();
                return false;
        } else if (tfTitleQuiz.getText().chars().allMatch( Character::isDigit)) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Failed !");
                alert2.setContentText("Title cannot be a number , try entering a string ");
                alert2.setHeaderText(null);
                alert2.show();
                return false;
        } else if (tfDescQuiz.getText().chars().allMatch( Character::isDigit)) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Failed !");
                alert2.setContentText("Description cannot be a number , try entering a string ");
                alert2.setHeaderText(null);
                alert2.show();
                return false;
        } else if (tfDescQuiz.getText().isEmpty()) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Failed !");
                alert2.setContentText("Description cannot be empty , try entering one !");
                alert2.setHeaderText(null);
                alert2.show();
                return false;
        }else if (dpStartDate.getValue().isAfter(dpEndDate.getValue())) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Failed !");
                alert2.setContentText("Start Date must be inferior to End Date !");
                alert2.setHeaderText(null);
                alert2.show();
                return false;
        }else if (dpStartDate.getValue().isBefore(LocalDate.now())) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Failed !");
                alert2.setContentText("Start Date must be equal or superior to current date !");
                alert2.setHeaderText(null);
                alert2.show();
                return false;
        }
        return true;
    }
    
    

    @FXML
    private void addQuiz(ActionEvent event) {
        if (validateInputs()) {
         java.sql.Date startDTM =java.sql.Date.valueOf(dpStartDate.getValue());
         java.sql.Date endDTM =java.sql.Date.valueOf(dpEndDate.getValue());
         
         String visibilityStr = cbVisibilityQuiz.getValue() ; 
            int visibilityInt ; 
            if (visibilityStr.equals("Visible")) 
                visibilityInt = 1 ;
            else 
                visibilityInt = 0 ;
        
  
       
        sq.add(new Quiz (tfTitleQuiz.getText(), tfDescQuiz.getText(),visibilityInt,"Quiz" , startDTM,endDTM,1)) ; 
        JOptionPane.showMessageDialog(null, "Quiz Added , please add options and assign correct answer !");   
        
            try {
                Parent page1 = FXMLLoader.load(getClass().getResource("ManageQuiz.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(ManageQuizController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
