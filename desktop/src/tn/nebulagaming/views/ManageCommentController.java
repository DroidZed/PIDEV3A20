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
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyStringWrapper;
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
import tn.nebulagaming.models.Participation;
import tn.nebulagaming.models.Post;
import tn.nebulagaming.services.ServiceComment;
import tn.nebulagaming.services.ServiceEvent;
import tn.nebulagaming.utils.GlobalConfig;

/**
 * FXML Controller class
 *
 * @author SuperNova
 */
public class ManageCommentController implements Initializable {

    @FXML
    private TableView<Comment> tvComments;
    @FXML
    private TableColumn<Comment ,String> userNameCol;
    @FXML
    private TableColumn<Comment ,String> commentCol;
    @FXML
    private TableColumn<Comment ,String> postedAtCol;
    @FXML
    private Label lbEventName;
    @FXML
    private Label createdAtEvent;

    int idEvent =ManageEventController.eventRecupParticipation.getIdPost();
    ServiceComment sc = new ServiceComment () ; 
    ServiceEvent se = new ServiceEvent () ; 
    @FXML
    private Button btnGoBack;
    @FXML
    private Label nbComment;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayComments (idEvent) ;
        nbComment.setText(String.valueOf(sc.countCommentsbyPost(idEvent)));
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
    
    
    public void displayComments (int idEvent) {
        
       lbEventName.setText(se.getEventById(idEvent).getTitlePost());
       createdAtEvent.setText(String.valueOf(se.getEventById(idEvent).getPostedDTM()));
        
       List<Comment> list=  sc.displayCommentByEvent(idEvent) ;
       
       //get User Name jointure (to be verified)
        userNameCol.setCellValueFactory(data->{
           Comment comment = data.getValue();
           Connection cnx = GlobalConfig.getInstance().getCONNECTION();
           String strUserName = "SELECT nameUser FROM tbl_user where idUser ="+comment.getIdUser();
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
        
        
       commentCol.setCellValueFactory(new PropertyValueFactory<>("comment"));
       postedAtCol.setCellValueFactory(new PropertyValueFactory<>("postedDTM"));
       
       ObservableList<Comment> listComments = FXCollections.observableArrayList(list) ;
       tvComments.setItems (listComments) ;
    }
    
}
