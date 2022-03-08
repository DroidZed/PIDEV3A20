/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tn.nebulagaming.views;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.text.Document;
import tn.nebulagaming.models.Feed;
import tn.nebulagaming.models.FeedMessage;
import static tn.nebulagaming.utils.Consts.IMG_PATH_LOAD;
import tn.nebulagaming.utils.GlobalConfig;
import tn.nebulagaming.utils.RSSFeedParser;

/**
 * FXML Controller class
 *
 * @author SuperNova
 */
public class NewsfeedRSSPostController implements Initializable {

    @FXML
    private ImageView ivUserPhoto;
    @FXML
    private Label lbTitlePost;
    @FXML
    private Label lbPostedOn;
    @FXML
    private Hyperlink linkWebSite;
    @FXML
    private Label lbAuthor;
    @FXML
    private Label lbDescPost;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    void setData(FeedMessage feedMessage) {
	Connection cnx = GlobalConfig.getInstance().getCONNECTION();

	lbTitlePost.setText(feedMessage.getTitle());
	lbPostedOn.setText(feedMessage.getPubDate());
	String description = feedMessage.getDescription().replaceAll("\\<.*?\\>", "");
	lbDescPost.setText(description);
	lbAuthor.setText(feedMessage.getAuthor());

	linkWebSite.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent t) {
		URI uri = null;
		try {
		    uri = new URI(feedMessage.getLink());
		} catch (URISyntaxException ex) {
		    Logger.getLogger(NewsfeedRSSPostController.class.getName()).log(Level.SEVERE, null, ex);
		}
		try {
		    Desktop.getDesktop().browse(uri);
		} catch (IOException ex) {
		    Logger.getLogger(NewsfeedRSSPostController.class.getName()).log(Level.SEVERE, null, ex);
		}
	    }
	});

	/*
            URI uri = null ;
           try { 
                uri = new URI (feedMessage.getLink()) ;
           } catch (URISyntaxException ex) {
               Logger.getLogger(NewsfeedRSSPostController.class.getName()).log(Level.SEVERE, null, ex);
           }
           try {
               Desktop.getDesktop().browse(uri);
           } catch (IOException ex) {
               Logger.getLogger(NewsfeedRSSPostController.class.getName()).log(Level.SEVERE, null, ex);
           }
            
            //System.out.println("hello");
	 */
    }

}
