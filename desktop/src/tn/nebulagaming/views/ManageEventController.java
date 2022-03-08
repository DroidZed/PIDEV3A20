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
import tn.nebulagaming.models.Post;
import tn.nebulagaming.services.ServiceEvent;
import tn.nebulagaming.utils.GlobalConfig;

/**
 * FXML Controller class
 *
 * @author SuperNova
 */
public class ManageEventController implements Initializable {

    @FXML
    private Button btnAddEvent;
    @FXML
    private TableView<Event> tvEvent;
    @FXML
    private TableColumn<Event, String> titleEventCol;
    @FXML
    private TableColumn<Event, String> descEventCol;
    @FXML
    private TableColumn<Event, String> startDateEventCol;
    @FXML
    private TableColumn<Event, String> endDateEventCol;
    @FXML
    private TableColumn<Event, String> addressEventCol;
    @FXML
    private TableColumn<Event, String> nbTicketEventCol;
    @FXML
    private TableColumn<Event, String> postedDateEventCol;
    @FXML
    private TableColumn<Event, String> postedByEventCol;
    @FXML
    private TableColumn<Event, String> visibilityEventCol;
    @FXML
    private TableColumn<Event, String> photoEventCol;
    @FXML
    private Button btnGoBackEvent;
    @FXML
    private TableColumn<Event, String> idEventCol;
    
    ServiceEvent se = new ServiceEvent () ; 
    
    static Event eventRecup ;
    static Event eventRecupParticipation ;
    @FXML
    private TableColumn<Event, String> latCol;
    @FXML
    private TableColumn<Event, String> longCol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        displayEvents () ; 
        addButtonUpdateToTableEvent() ;
        addButtonDeleteToTableEvent() ;
        addButtonDisplayToTableEvent() ;
        
         btnAddEvent.setOnAction(event -> {
            try {
                Parent page1 = FXMLLoader.load(getClass().getResource("AddEvent.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(ManageContentController.class.getName()).log(Level.SEVERE, null, ex);
            }    
        });
         
         btnGoBackEvent.setOnAction(event -> {
            try {
                Parent page1 = FXMLLoader.load(getClass().getResource("MainBackOffice.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(ManageContentController.class.getName()).log(Level.SEVERE, null, ex);
            }    
        });
    }    
    
    
    private void displayEvents () {
        idEventCol.setCellValueFactory(new PropertyValueFactory<>("idPost"));
        latCol.setCellValueFactory(new PropertyValueFactory<>("latitude"));
        longCol.setCellValueFactory(new PropertyValueFactory<>("longitude"));
        
        idEventCol.setVisible(false);
        latCol.setVisible(false);
        longCol.setVisible(false);
 
        titleEventCol.setCellValueFactory(new PropertyValueFactory<>("titlePost"));
        descEventCol.setCellValueFactory(new PropertyValueFactory<>("descPost"));
        postedDateEventCol.setCellValueFactory(new PropertyValueFactory<>("postedDTM"));
        //visibilityEventCol.setCellValueFactory(new PropertyValueFactory<>("statusPost"));
        startDateEventCol.setCellValueFactory(new PropertyValueFactory<>("startDTM")) ; 
        endDateEventCol.setCellValueFactory(new PropertyValueFactory<>("endDTM")) ; 
        addressEventCol.setCellValueFactory(new PropertyValueFactory<>("addressEvent")) ; 
        nbTicketEventCol.setCellValueFactory(new PropertyValueFactory<>("nbTicketAvailable")) ; 
        
        //get User Name jointure 
        postedByEventCol.setCellValueFactory(data->{
           Event event = data.getValue();
           Connection cnx = GlobalConfig.getInstance().getCONNECTION();
           String strUserName = "SELECT nameUser FROM tbl_user where idUser ="+event.getIdOwnerUser();
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
           return new ReadOnlyStringWrapper(result);
        }
        ); 
        
        
        //Set visibility as String 
        visibilityEventCol.setCellValueFactory(data->{
           Event event = data.getValue() ;
           String visibility = "" ; 
                   if (event.getStatusPost() == 1) {
                       visibility = "Visible" ; 
                   } else {
                       visibility = "Not Visible" ;
                   }
                   return new ReadOnlyStringWrapper(visibility);
                   });
        
        
        ObservableList<Event> listEvent = FXCollections.observableArrayList(se.display()) ;
        tvEvent.setItems(listEvent) ;
    }
    
    private void addButtonUpdateToTableEvent() {        
        TableColumn<Event, Void> colBtn = new TableColumn("Update");

        Callback<TableColumn<Event, Void>, TableCell<Event, Void>> cellFactory = new Callback<TableColumn<Event, Void>, TableCell<Event, Void>>() {
            
            @Override
            public TableCell<Event, Void> call(final TableColumn<Event, Void> param) {
                
                final TableCell<Event, Void> cell = new TableCell<Event, Void>() {
                    private final Button btn = new Button("Update");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            eventRecup = getTableView().getItems().get(getIndex());
                              try {
                                Parent page1 = FXMLLoader.load(getClass().getResource("UpdateEvent.fxml"));
                                Scene scene = new Scene(page1);
                                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException ex) {
                                Logger.getLogger(ManageContentController.class.getName()).log(Level.SEVERE, null, ex);
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
        tvEvent.getColumns().add(colBtn);
    }
    
    private void addButtonDeleteToTableEvent() {
        TableColumn<Event, Void> colBtn = new TableColumn("Remove");

        Callback<TableColumn<Event, Void>, TableCell<Event, Void>> cellFactory = new Callback<TableColumn<Event, Void>, TableCell<Event, Void>>() {
            @Override
            public TableCell<Event, Void> call(final TableColumn<Event, Void> param) {
                final TableCell<Event, Void> cell = new TableCell<Event, Void>() {

                    private final Button btn = new Button("Remove");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Event f = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Remove");
                            alert.setHeaderText(null);
                            alert.setContentText("Delete this event ? ");
                            Optional<ButtonType> action = alert.showAndWait();
                            if (action.get() == ButtonType.OK) {
                                se.delete(f.getIdPost());

                            }

                            try {
                                Parent page1 = FXMLLoader.load(getClass().getResource("ManageContent.fxml"));
                                Scene scene = new Scene(page1);
                                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException ex) {
                                Logger.getLogger(ManageContentController.class.getName()).log(Level.SEVERE, null, ex);
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
        tvEvent.getColumns().add(colBtn);
    }
    
    private void addButtonDisplayToTableEvent() {
        TableColumn<Event, Void> colBtn = new TableColumn("Display");

        Callback<TableColumn<Event, Void>, TableCell<Event, Void>> cellFactory = new Callback<TableColumn<Event, Void>, TableCell<Event, Void>>() {
            @Override
            public TableCell<Event, Void> call(final TableColumn<Event, Void> param) {
                final TableCell<Event, Void> cell = new TableCell<Event, Void>() {

                    private final Button btn = new Button("Details");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            eventRecupParticipation = getTableView().getItems().get(getIndex());
                            try {
                                Parent page1 = FXMLLoader.load(getClass().getResource("ManageDetailEvent.fxml"));
                                Scene scene = new Scene(page1);
                                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException ex) {
                                Logger.getLogger(ManageContentController.class.getName()).log(Level.SEVERE, null, ex);
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
        tvEvent.getColumns().add(colBtn);
    }

    @FXML
    private void goBackToMain(ActionEvent event) {
        
    }
    
}
