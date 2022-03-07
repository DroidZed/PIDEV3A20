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
import tn.nebulagaming.models.Post;
import tn.nebulagaming.services.ServicePost;
import tn.nebulagaming.utils.GlobalConfig;

/**
 * FXML Controller class
 *
 * @author SuperNova
 */
public class ManagePostController implements Initializable {

    @FXML
    private Button btnGoBackPost;
    @FXML
    private TableView<Post> tvPost;
    @FXML
    private TableColumn<Post, String> titlePostCol;
    @FXML
    private TableColumn<Post, String> descPostCol;
    @FXML
    private TableColumn<Post, String> postedAtPostCol;
    @FXML
    private TableColumn<Post, String> postedByPostCol;
    @FXML
    private TableColumn<Post, String> photoPostCol;
    @FXML
    private TableColumn<Post, String> visibilityPostCol;
    @FXML
    private TableColumn<Post, String> idPostCol;
    @FXML
    private Button btnAddPost;
    
    ServicePost sp = new ServicePost () ; 
    static Post postRecup;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayPosts();
        addButtonUpdateToTable () ; 
        addButtonDeleteToTable () ;
        btnAddPost.setOnAction(event -> {
            try {
                Parent page1 = FXMLLoader.load(getClass().getResource("AddPost.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(ManageContentController.class.getName()).log(Level.SEVERE, null, ex);
            }    
        });
        
        btnGoBackPost.setOnAction(event -> {
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

private void displayPosts () {
        idPostCol.setCellValueFactory(new PropertyValueFactory<>("idPost"));
        idPostCol.setVisible(false);
        
        titlePostCol.setCellValueFactory(new PropertyValueFactory<>("titlePost"));
        descPostCol.setCellValueFactory(new PropertyValueFactory<>("descPost"));
        postedAtPostCol.setCellValueFactory(new PropertyValueFactory<>("postedDTM"));
        //visibilityPostCol.setCellValueFactory(new PropertyValueFactory<>("statusPost"));
        
        
        
        postedByPostCol.setCellValueFactory(new PropertyValueFactory<>("idOwnerUser")) ;
        
        visibilityPostCol.setCellValueFactory(data->{
           Post post = data.getValue() ;
           String visibility = "" ; 
                   if (post.getStatusPost() == 1) {
                       visibility = "Visible" ; 
                   } else {
                       visibility = "Not Visible" ;
                   }
                   return new ReadOnlyStringWrapper(visibility);
                   });
        
        //get User Name jointure 
        postedByPostCol.setCellValueFactory(data->{
           Post post = data.getValue();
           Connection cnx = GlobalConfig.getInstance().getCnx() ;
           String strUserName = "SELECT nameUser FROM tbl_user where idUser ="+post.getIdOwnerUser();
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
        
        
        
        ObservableList<Post> listPost = FXCollections.observableArrayList(sp.display()) ;
        tvPost.setItems(listPost) ;
    } 

private void addButtonUpdateToTable() {        
        TableColumn<Post, Void> colBtn = new TableColumn("Update");

        Callback<TableColumn<Post, Void>, TableCell<Post, Void>> cellFactory = new Callback<TableColumn<Post, Void>, TableCell<Post, Void>>() {
            
            @Override
            public TableCell<Post, Void> call(final TableColumn<Post, Void> param) {
                
                final TableCell<Post, Void> cell = new TableCell<Post, Void>() {
                    private final Button btn = new Button("Update");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            postRecup = getTableView().getItems().get(getIndex());
                              try {
                                Parent page1 = FXMLLoader.load(getClass().getResource("UpdatePost.fxml"));
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
        tvPost.getColumns().add(colBtn);
    }

private void addButtonDeleteToTable() {
        TableColumn<Post, Void> colBtn = new TableColumn("Remove");

        Callback<TableColumn<Post, Void>, TableCell<Post, Void>> cellFactory = new Callback<TableColumn<Post, Void>, TableCell<Post, Void>>() {
            @Override
            public TableCell<Post, Void> call(final TableColumn<Post, Void> param) {
                final TableCell<Post, Void> cell = new TableCell<Post, Void>() {

                    private final Button btn = new Button("Remove");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Post f = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Remove");
                            alert.setHeaderText(null);
                            alert.setContentText("Delete this post? ");
                            Optional<ButtonType> action = alert.showAndWait();
                            if (action.get() == ButtonType.OK) {
                                sp.delete(f.getIdPost());

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

        tvPost.getColumns().add(colBtn);
    }

    @FXML
    private void goBackToMain(ActionEvent event) {
    }
    
}
