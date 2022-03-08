/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tn.nebulagaming.views;

import static java.lang.Double.parseDouble;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import tn.nebulagaming.models.Event;
import tn.nebulagaming.models.Participation;
import tn.nebulagaming.models.Post;
import tn.nebulagaming.services.ServiceParticipation;
import static tn.nebulagaming.utils.Consts.IMG_PATH_LOAD;
import tn.nebulagaming.utils.GlobalConfig;
import tn.nebulagaming.utils.MapHandler;

/**
 * FXML Controller class
 *
 * @author SuperNova
 */
public class NewsfeedEventController implements Initializable {

    @FXML
    private ImageView ivUserPhoto;
    @FXML
    private Label lbUserName;
    @FXML
    private Label lbTitlePost;
    @FXML
    private Label lbPostedOn;
    @FXML
    private Label lbDescPost;
    @FXML
    private ImageView ivPostCoverPhoto;
    @FXML
    private Button btnLike;
    @FXML
    private Button btnDislike;
    @FXML
    private Button btnComment;
    @FXML
    private Label lbNbTicket;
    @FXML
    private Label lbAddressEvent;
    @FXML
    private Label lbStartDate;

    private Event event ; 
    @FXML
    private Label lbEndDate;
    @FXML
    private Label lbLat;
    @FXML
    private Label lbLong;
    @FXML
    private Label lbIdPost;

    ServiceParticipation spar = new ServiceParticipation () ; 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    @FXML
    private void map(ActionEvent event) {
        MapHandler m = new MapHandler("Map");
        m.getInformation(parseDouble(lbLat.getText()),parseDouble(lbLong.getText())); 
    }
    
    public void setData(Event event) {
        
        Connection cnx = GlobalConfig.getInstance().getCONNECTION();
        
        
        this.event = event ;
        lbTitlePost.setText(event.getTitlePost()) ;
        String strUserName = "SELECT nameUser FROM tbl_user where idUser ="+event.getIdOwnerUser();
           Statement st = null ;
           String result = "" ;
            try {
                st = cnx.createStatement();
            } catch (SQLException ex) {
                Logger.getLogger(NewsfeedEventController.class.getName()).log(Level.SEVERE, null, ex);
            }
           ResultSet rs ;
            try {
                rs = st.executeQuery(strUserName);
                if (rs.next()) {
                    result = rs.getString("nameUser");
                    lbUserName.setText (result) ;
                }
            } catch (SQLException ex) {
                Logger.getLogger(NewsfeedEventController.class.getName()).log(Level.SEVERE, null, ex);
            } 
           
        
        Image image = new Image((IMG_PATH_LOAD + event.getPhotoPost()));
        ivPostCoverPhoto.setImage(image);
        lbPostedOn.setText(String.valueOf(event.getPostedDTM()));
        lbDescPost.setText(event.getDescPost());
        lbAddressEvent.setText(event.getAddressEvent());
        lbTitlePost.setText(String.valueOf(event.getTitlePost()));
        lbNbTicket.setText(String.valueOf(event.getNbTicketAvailable()) + " Tickets");
        lbStartDate.setText(String.valueOf(event.getStartDTM()));
        lbEndDate.setText(String.valueOf(event.getEndDTM()));
        lbLat.setText(String.valueOf(event.getLatitude()));
        lbLong.setText(String.valueOf(event.getLongitude()));
        lbIdPost.setText(String.valueOf(event.getIdPost()));
        
    }

    @FXML
    private void addParticipation(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation");
                            alert.setHeaderText(null);
                            alert.setContentText("Please confirm your action ! ");
                            Optional<ButtonType> action = alert.showAndWait();
                            if (action.get() == ButtonType.OK) {
                                int idUser = 2 ;  
                                int idPost = Integer.parseInt(lbIdPost.getText()) ;
                                java.sql.Date postedDTM = new java.sql.Date (Calendar.getInstance().getTime().getTime());

                                spar.add(new Participation (0,0,postedDTM,idUser,idPost,3)) ;
                                JOptionPane.showMessageDialog(null, "Participation done with success !");

                            }

        
        
       
    }
    
}
