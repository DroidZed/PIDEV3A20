/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tn.nebulagaming.views;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import static java.util.Comparator.naturalOrder;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toList;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.nebulagaming.models.Answer;
import tn.nebulagaming.models.Badge;
import tn.nebulagaming.models.Post;
import tn.nebulagaming.services.ServiceAnswer;
import tn.nebulagaming.services.ServiceGamify;
import tn.nebulagaming.utils.GlobalConfig;

/**
 * FXML Controller class
 *
 * @author SuperNova
 */
public class ManageAnswersController implements Initializable {
    
    ServiceAnswer sa = new ServiceAnswer () ;
    ServiceGamify sg = new ServiceGamify () ; 
    @FXML
    private PieChart pieChartAnswers;
    @FXML
    private Label lbCorrAnswers;
    @FXML
    private Label lbIncorrAnswers;
    @FXML
    private Label lbFastUserAnswer;
    @FXML
    private TableView<Answer> tvAnswer;
    @FXML
    private TableColumn<Answer, String> userNameCol;
    @FXML
    private TableColumn<Answer, String> answeredDateCol;
    @FXML
    private TableColumn<Answer, String> answerCol;
    @FXML
    private Button btnGoBack;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayStatAnswers () ;
        displayAnswers () ; 
        
        /*List<Integer> map = sg.LeastProcessTime(ManageQuizController.quizRecupDetails.getIdPost()) ;
        
        Connection cnx = GlobalConfig.getInstance().getCnx() ;
        String strUserName = "SELECT nameUser FROM tbl_user where idUser ="+map.get(0);
           Statement st = null ;
           String result = "" ;
            try {
                st = cnx.createStatement();
            } catch (SQLException ex) {
                Logger.getLogger(ManagePostController.class.getName()).log(Level.SEVERE, null, ex);
            }
           ResultSet rs ;
            try {
                rs = st.executeQuery(strUserName);
                if (rs.next()) {
                    result = rs.getString("nameUser");
                }
            } catch (SQLException ex) {
                Logger.getLogger(ManagePostController.class.getName()).log(Level.SEVERE, null, ex);
            } 
    
           lbFastUserAnswer.setText(result);*/
       
        
          
        
        btnGoBack.setOnAction(event -> {
            try {
                Parent page1 = FXMLLoader.load(getClass().getResource("ManageContent.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(ManageBadgeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void displayStatAnswers () {
    int correctAnswers = sa.nbCorrectAnswers(ManageQuizController.quizRecupDetails.getIdPost(), ManageQuizController.quizRecupDetails.getCorrectAnswer()) ;
    int incorrectAnswers = sa.nbIncorrectAnswers(ManageQuizController.quizRecupDetails.getIdPost(), ManageQuizController.quizRecupDetails.getCorrectAnswer()) ;
    ObservableList<PieChart.Data> pieChartData =FXCollections.observableArrayList();
     pieChartData.addAll(
        new PieChart.Data("Correct Answers",correctAnswers),
        new PieChart.Data("Incorrect Answers",incorrectAnswers)
        );
     
     pieChartAnswers.setData(pieChartData);
     pieChartAnswers.setLabelsVisible(true);
     lbCorrAnswers.setText(String.valueOf(correctAnswers)) ; 
     lbIncorrAnswers.setText(String.valueOf(incorrectAnswers)) ;
    }
    
    public void displayAnswers () {

        //get User Name jointure 
        userNameCol.setCellValueFactory(data->{
           Answer answer = data.getValue();
           Connection cnx = GlobalConfig.getInstance().getCnx() ;
           String strUserName = "SELECT nameUser FROM tbl_user where idUser ="+answer.getIdUser();
           Statement st = null ;
           String result = "" ;
            try {
                st = cnx.createStatement();
            } catch (SQLException ex) {
                Logger.getLogger(ManageAnswersController.class.getName()).log(Level.SEVERE, null, ex);
            }
           ResultSet rs ;
            try {
                rs = st.executeQuery(strUserName);
                if (rs.next()) {
                    result = rs.getString("nameUser");
                }
            } catch (SQLException ex) {
                Logger.getLogger(ManageAnswersController.class.getName()).log(Level.SEVERE, null, ex);
            } 
           return new ReadOnlyStringWrapper(result);
           
        }         
        );
        
        
        
        answeredDateCol.setCellValueFactory(new PropertyValueFactory<>("answeredDTM"));
        answerCol.setCellValueFactory(new PropertyValueFactory<>("answer"));
        
        ObservableList<Answer> listAnswer = FXCollections.observableArrayList(sa.getAnswersByQuiz(ManageQuizController.quizRecupDetails.getIdPost())) ;
        tvAnswer.setItems(listAnswer) ;
        
    }
    
}
