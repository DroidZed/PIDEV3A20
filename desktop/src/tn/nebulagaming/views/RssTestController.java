/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tn.nebulagaming.views;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import tn.nebulagaming.models.Feed;
import tn.nebulagaming.models.FeedMessage;
import tn.nebulagaming.utils.RSSFeedParser;

/**
 * FXML Controller class
 *
 * @author SuperNova
 */
public class RssTestController implements Initializable {

    @FXML
    private Label welcomeText;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onHelloButtonClick(ActionEvent event) {
        
         RSSFeedParser parser = new RSSFeedParser(
                "https://kotaku.com/rss");
        Feed feed = parser.readFeed();
        System.out.println(feed);
        for (FeedMessage message : feed.getMessages()) {
            System.out.println(message);
        }
    }
    
}
