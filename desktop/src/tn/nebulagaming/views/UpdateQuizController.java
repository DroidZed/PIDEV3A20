/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tn.nebulagaming.views;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
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
import javafx.scene.image.Image;
import javafx.stage.Stage;
import tn.nebulagaming.models.Post;
import tn.nebulagaming.models.Quiz;
import tn.nebulagaming.services.ServiceOption;
import tn.nebulagaming.services.ServiceQuiz;
import static tn.nebulagaming.utils.Consts.IMG_PATH_LOAD;

/**
 * FXML Controller class
 *
 * @author SuperNova
 */
public class UpdateQuizController implements Initializable {

    @FXML
    private TextField tfTitleQuiz;
    @FXML
    private TextField tfDescQuiz;
    @FXML
    private ComboBox<String> cbVisibilityQuiz;
    @FXML
    private DatePicker tfStartDateQuiz;
    @FXML
    private DatePicker tfEndDateQuiz;
    @FXML
    private ComboBox<String> cbCorrectAnswer;
    @FXML
    private Button btnGoBack;

    Quiz quiz = ManageQuizController.quizRecup;
    ServiceOption so = new ServiceOption();
    ServiceQuiz sq = new ServiceQuiz();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateListViewVisibility();
        if (quiz.getStatusPost() == 1) {
            cbVisibilityQuiz.getSelectionModel().selectLast();
        } else {
            cbVisibilityQuiz.getSelectionModel().selectFirst();
        }

        populateListViewOptions();

        btnGoBack.setOnAction(event -> {
            try {
                Parent page1 = FXMLLoader.load(getClass().getResource("ManageQuiz.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(UpdateEventController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        tfTitleQuiz.setText(ManageQuizController.quizRecup.getTitlePost());
        tfDescQuiz.setText(ManageQuizController.quizRecup.getDescPost());
       
        
        tfStartDateQuiz.setValue(ManageQuizController.quizRecup.getStartDTM().toLocalDate());
        tfEndDateQuiz.setValue(ManageQuizController.quizRecup.getEndDTM().toLocalDate());
        
    }

    public boolean validateInputs() {
        if (tfTitleQuiz.getText().isEmpty()) {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Failed !");
            alert1.setContentText("Title cannot be empty , try entering one !");
            alert1.setHeaderText(null);
            alert1.show();
            return false;
        } else if (tfTitleQuiz.getText().chars().allMatch(Character::isDigit)) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Failed !");
            alert2.setContentText("Title cannot be a number , try entering a string ");
            alert2.setHeaderText(null);
            alert2.show();
            return false;
        } else if (tfDescQuiz.getText().chars().allMatch(Character::isDigit)) {
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
        } else if (tfStartDateQuiz.getValue().isAfter(tfEndDateQuiz.getValue())) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Failed !");
            alert2.setContentText("Start Date must be inferior to End Date !");
            alert2.setHeaderText(null);
            alert2.show();
            return false;
        } else if (tfStartDateQuiz.getValue().isBefore(LocalDate.now())) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Failed !");
            alert2.setContentText("Start Date must be equal or superior to current date !");
            alert2.setHeaderText(null);
            alert2.show();
            return false;
        }
        return true;
    }

    public void populateListViewVisibility() {
        ObservableList<String> items = FXCollections.observableArrayList();
        cbVisibilityQuiz.setItems(items);
        items.add("Not Visible");
        items.add("Visible");

        /*String visibilityStr = cbVisibilityPost.getValue() ;
        cbVisibilityPost.setValue(visibilityStr);*/
    }

    public void populateListViewOptions() {
        ObservableList<String> items = FXCollections.observableArrayList(so.getOptionsByQuiz(quiz.getIdPost()));
        cbCorrectAnswer.setItems(items);

    }
    

    @FXML
    private void UpdateQuiz(ActionEvent event) {
        if (validateInputs()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Update Quiz");
            alert.setHeaderText(null);
            alert.setContentText("Please confirm your modifications ");
            Optional<ButtonType> action = alert.showAndWait();
            if (action.get() == ButtonType.OK) {
                String visibilityStr = cbVisibilityQuiz.getValue();
                String correctAnswer = cbCorrectAnswer.getValue();

                int visibilityInt;
                if (visibilityStr.equals("Visible")) {
                    visibilityInt = 1;
                } else {
                    visibilityInt = 0;
                }
                java.sql.Date startDTM =java.sql.Date.valueOf(tfStartDateQuiz.getValue());
                java.sql.Date endDTM =java.sql.Date.valueOf(tfEndDateQuiz.getValue());

                sq.update(new Quiz(ManageQuizController.quizRecup.getIdPost(), tfTitleQuiz.getText(), tfDescQuiz.getText(), visibilityInt,startDTM,endDTM, correctAnswer));
                try {
                    Parent page1 = FXMLLoader.load(getClass().getResource("ManageContent.fxml"));
                    Scene scene = new Scene(page1);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(UpdateQuizController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
