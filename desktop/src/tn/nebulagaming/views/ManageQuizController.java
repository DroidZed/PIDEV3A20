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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyStringWrapper;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.nebulagaming.models.Event;
import tn.nebulagaming.models.Quiz;
import tn.nebulagaming.services.ServiceQuiz;
import tn.nebulagaming.utils.GlobalConfig;
import static tn.nebulagaming.views.ManageEventController.eventRecup;
import static tn.nebulagaming.views.ManageEventController.eventRecupParticipation;

/**
 * FXML Controller class
 *
 * @author SuperNova
 */
public class ManageQuizController implements Initializable {

    @FXML
    private TableView<Quiz> tvQuiz;
    @FXML
    private TableColumn<Quiz, String> titleQuizCol;
    @FXML
    private TableColumn<Quiz, String> descQuizCol;
    @FXML
    private TableColumn<Quiz, String> startDateCol;
    @FXML
    private TableColumn<Quiz, String> endDateCol;
    @FXML
    private TableColumn<Quiz, String> visibilityCol;
    @FXML
    private TableColumn<Quiz, String> postedDateCol;
    @FXML
    private TableColumn<Quiz, String> postedByCol;
    @FXML
    private TableColumn<Quiz, String> correctAnswerCol;
    @FXML
    private Button btnGoBack;
    @FXML
    private Button btnAddQuiz;
    @FXML
    private TableColumn<Quiz, String> idPostCol;
    
    ServiceQuiz sq = new ServiceQuiz () ; 
    static Quiz quizRecup ;
    static Quiz quizRecupDetails ;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayQuiz () ;
        addButtonUpdateToTableQuiz () ;
        addButtonDeleteToTableQuiz() ;
        addButtonDisplayToTableQuiz() ;
        
        btnGoBack.setOnAction(event -> {
            try {
                Parent page1 = FXMLLoader.load(getClass().getResource("ManageContent.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(ManageQuizController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btnAddQuiz.setOnAction(event -> {
            try {
                Parent page1 = FXMLLoader.load(getClass().getResource("AddQuiz.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(ManageQuizController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        
    } 
    
    public void displayQuiz () {
        idPostCol.setCellValueFactory(new PropertyValueFactory<>("idPost"));
      
 
        titleQuizCol.setCellValueFactory(new PropertyValueFactory<>("titlePost"));
        descQuizCol.setCellValueFactory(new PropertyValueFactory<>("descPost"));
        postedDateCol.setCellValueFactory(new PropertyValueFactory<>("postedDTM"));
        //visibilityEventCol.setCellValueFactory(new PropertyValueFactory<>("statusPost"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDTM")) ; 
        endDateCol.setCellValueFactory(new PropertyValueFactory<>("endDTM")) ; 
        correctAnswerCol.setCellValueFactory(new PropertyValueFactory<>("correctAnswer"));
        
        //get User Name jointure 
        postedByCol.setCellValueFactory(data->{
           Quiz quiz = data.getValue();
               Connection cnx = GlobalConfig.getInstance().getCONNECTION();
           String strUserName = "SELECT nameUser FROM tbl_user where idUser ="+quiz.getIdOwnerUser();
           Statement st = null ;
           String result = "" ;
            try {
                st = cnx.createStatement();
            } catch (SQLException ex) {
                Logger.getLogger(ManageQuizController.class.getName()).log(Level.SEVERE, null, ex);
            }
           ResultSet rs ;
            try {
                rs = st.executeQuery(strUserName);
                if (rs.next()) {
                    result = rs.getString("nameUser");
                }
            } catch (SQLException ex) {
                Logger.getLogger(ManageQuizController.class.getName()).log(Level.SEVERE, null, ex);
            } 
           return new ReadOnlyStringWrapper(result);
        }
        ); 
        
        
        //Set visibility as String 
        visibilityCol.setCellValueFactory(data->{
           Quiz quiz = data.getValue();
           String visibility = "" ; 
                   if (quiz.getStatusPost() == 1) {
                       visibility = "Visible" ; 
                   } else {
                       visibility = "Not Visible" ;
                   }
                   return new ReadOnlyStringWrapper(visibility);
                   });
        
         
        
        ObservableList<Quiz> listQuiz = FXCollections.observableArrayList(sq.display()) ;
        tvQuiz.setItems(listQuiz) ;
    }
    
    private void addButtonUpdateToTableQuiz() {        
        TableColumn<Quiz, Void> colBtn = new TableColumn("Update");

        Callback<TableColumn<Quiz, Void>, TableCell<Quiz, Void>> cellFactory = new Callback<TableColumn<Quiz, Void>, TableCell<Quiz, Void>>() {
            
            @Override
            public TableCell<Quiz, Void> call(final TableColumn<Quiz, Void> param) {
                
                final TableCell<Quiz, Void> cell = new TableCell<Quiz, Void>() {
                    private final Button btn = new Button("Update");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            quizRecup = getTableView().getItems().get(getIndex());
                              try {
                                Parent page1 = FXMLLoader.load(getClass().getResource("UpdateQuiz.fxml"));
                                Scene scene = new Scene(page1);
                                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException ex) {
                                Logger.getLogger(ManageQuizController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                    }
                    
                    @Override
                        public void updateItem(Void item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                            } else {
                                setGraphic(btn);
                            }
                        }
                    };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);
        tvQuiz.getColumns().add(colBtn);
    }
    
    private void addButtonDeleteToTableQuiz() {
        TableColumn<Quiz, Void> colBtn = new TableColumn("Remove");

        Callback<TableColumn<Quiz, Void>, TableCell<Quiz, Void>> cellFactory = new Callback<TableColumn<Quiz, Void>, TableCell<Quiz, Void>>() {
            @Override
            public TableCell<Quiz, Void> call(final TableColumn<Quiz, Void> param) {
                final TableCell<Quiz, Void> cell = new TableCell<Quiz, Void>() {

                    private final Button btn = new Button("Remove");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Quiz f = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Remove");
                            alert.setHeaderText(null);
                            alert.setContentText("Delete this quiz ? ");
                            Optional<ButtonType> action = alert.showAndWait();
                            if (action.get() == ButtonType.OK) {
                                sq.delete(f.getIdPost());

                            }

                            try {
                                Parent page1 = FXMLLoader.load(getClass().getResource("ManageQuiz.fxml"));
                                Scene scene = new Scene(page1);
                                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException ex) {
                                Logger.getLogger(ManageQuizController.class.getName()).log(Level.SEVERE, null, ex);
                            }
        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);
        tvQuiz.getColumns().add(colBtn);
    }
    
     private void addButtonDisplayToTableQuiz() {
        TableColumn<Quiz, Void> colBtn = new TableColumn("Display");

        Callback<TableColumn<Quiz, Void>, TableCell<Quiz, Void>> cellFactory = new Callback<TableColumn<Quiz, Void>, TableCell<Quiz, Void>>() {
            @Override
            public TableCell<Quiz, Void> call(final TableColumn<Quiz, Void> param) {
                final TableCell<Quiz, Void> cell = new TableCell<Quiz, Void>() {

                    private final Button btn = new Button("Details");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            quizRecupDetails = getTableView().getItems().get(getIndex());
                            try {
                                Parent page1 = FXMLLoader.load(getClass().getResource("ManageDetailsQuiz.fxml"));
                                Scene scene = new Scene(page1);
                                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException ex) {
                                Logger.getLogger(ManageQuizController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                    }
                    
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);
        tvQuiz.getColumns().add(colBtn);
    }


    
}
