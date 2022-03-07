/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tn.nebulagaming.views;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextFlow;
import tn.nebulagaming.models.Post;
import static tn.nebulagaming.utils.Consts.IMG_PATH_LOAD;
import tn.nebulagaming.utils.GlobalConfig;

/**
 * FXML Controller class
 *
 * @author SuperNova
 */
public class NewsfeedPostController implements Initializable {

    @FXML
    private ImageView ivUserPhoto;
    @FXML
    private Label lbUserName;
    @FXML
    private Label lbTitlePost;
    @FXML
    private Label lbPostedOn;
    @FXML
    private ImageView ivPostCoverPhoto;
    @FXML
    private Button btnLike;
    @FXML
    private Button btnDislike;
    @FXML
    private Button btnComment;
    
    private Post post ; 
    @FXML
    private Label lbDescPost;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setData(Post post) {
        
        Connection cnx = GlobalConfig.getInstance().getCnx() ;
        
        
        this.post = post ;
        lbTitlePost.setText(post.getTitlePost()) ;
        String strUserName = "SELECT nameUser FROM tbl_user where idUser ="+post.getIdOwnerUser();
           Statement st = null ;
           String result = "" ;
            try {
                st = cnx.createStatement();
            } catch (SQLException ex) {
                Logger.getLogger(NewsfeedPostController.class.getName()).log(Level.SEVERE, null, ex);
            }
           ResultSet rs ;
            try {
                rs = st.executeQuery(strUserName);
                if (rs.next()) {
                    result = rs.getString("nameUser");
                    lbUserName.setText (result) ;
                }
            } catch (SQLException ex) {
                Logger.getLogger(NewsfeedPostController.class.getName()).log(Level.SEVERE, null, ex);
            } 
           
        
        Image image = new Image((IMG_PATH_LOAD + post.getPhotoPost()));
        ivPostCoverPhoto.setImage(image);
        lbPostedOn.setText(String.valueOf(post.getPostedDTM()));
        lbDescPost.setText(post.getDescPost());
        
    }
    
}
