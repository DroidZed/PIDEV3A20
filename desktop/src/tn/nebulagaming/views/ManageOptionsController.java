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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import tn.nebulagaming.models.Event;
import tn.nebulagaming.models.Option;
import tn.nebulagaming.services.ServiceOption;
import tn.nebulagaming.models.Quiz;
import tn.nebulagaming.services.ServiceQuiz;

/**
 * FXML Controller class
 *
 * @author SuperNova
 */
public class ManageOptionsController implements Initializable {

    @FXML
    private HBox hboxCorrectOption;
    @FXML
    private RadioButton lbOpt1;
    @FXML
    private RadioButton lbOpt2;
    @FXML
    private RadioButton lbOpt3;
    @FXML
    private RadioButton lbOpt4;
    @FXML
    private TextField tfOpt1;
    @FXML
    private TextField tfOpt2;
    @FXML
    private TextField tfOpt3;
    @FXML
    private TextField tfOpt4;

    ServiceOption so = new ServiceOption () ;
    ServiceQuiz sq = new ServiceQuiz () ; 
    Quiz quiz = ManageQuizController.quizRecupDetails ; 
    
    @FXML
    private ToggleGroup toggleOptions;
    @FXML
    private Button btnGoBack;
    @FXML
    private Label lbQuizTitle;
    @FXML
    private HBox hboxAddOption;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        lbQuizTitle.setText("Quiz Title : "+quiz.getTitlePost());
        btnGoBack.setOnAction(event -> {
            try {
                Parent page1 = FXMLLoader.load(getClass().getResource("ManageContent.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(ManageOptionsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        /*if (quiz.getCorrectAnswer() != "" || quiz.getCorrectAnswer() != null) {
            hboxAddOption.setVisible(false);
            hboxCorrectOption.setVisible(true);
            
            setOptionsRadioBox () ; 
            if (lbOpt1.getText() == quiz.getCorrectAnswer()) {
                lbOpt1.setSelected(true);
            } else if (lbOpt2.getText() == quiz.getCorrectAnswer()) {
                lbOpt2.setSelected(true);
            } else if (lbOpt3.getText() == quiz.getCorrectAnswer()) {
                lbOpt3.setSelected(true);
            } else if (lbOpt4.getText() == quiz.getCorrectAnswer()) {
                lbOpt4.setSelected(true);
            }   
        }*/
    }    

    @FXML
    private void addOptions(ActionEvent event) {
        
        so.add(new Option (1, tfOpt1.getText(),quiz.getIdPost())) ; 
        so.add(new Option (1, tfOpt2.getText(),quiz.getIdPost())) ;
        so.add(new Option (1, tfOpt3.getText(),quiz.getIdPost())) ;
        so.add(new Option (1, tfOpt4.getText(),quiz.getIdPost())) ;
        JOptionPane.showMessageDialog(null, "Options Added with success , please select the correct one !"); 
        
        hboxCorrectOption.setVisible(true) ; 
        setOptionsRadioBox () ; 
        
    }
    
    private void setOptionsRadioBox () {
        List<String> listOptions = so.getOptionsByQuiz(quiz.getIdPost()) ; 
        lbOpt1.setText(listOptions.get(0));
        lbOpt2.setText(listOptions.get(1));
        lbOpt3.setText(listOptions.get(2));
        lbOpt4.setText(listOptions.get(3));   
        System.out.println(listOptions);
    }

    @FXML
    private void assignCorrectAnswer(ActionEvent event) {
        RadioButton checked = (RadioButton) toggleOptions.getSelectedToggle();
        sq.assignCorrectAnswer(quiz, checked.getText());
            try {
               Parent page1 = FXMLLoader.load(getClass().getResource("ManageQuiz.fxml"));

               Scene scene = new Scene(page1);
               Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
               stage.setScene(scene);
               stage.show();
           } catch (IOException ex) {
               Logger.getLogger(ManageOptionsController.class.getName()).log(Level.SEVERE, null, ex);
           }
    }
    
    
    
}
