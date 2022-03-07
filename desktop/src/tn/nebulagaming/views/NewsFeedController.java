/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tn.nebulagaming.views;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tn.nebulagaming.models.Event;
import tn.nebulagaming.models.Feed;
import tn.nebulagaming.models.FeedMessage;
import tn.nebulagaming.models.Post;
import tn.nebulagaming.services.ServiceEvent;
import tn.nebulagaming.services.ServicePost;
import tn.nebulagaming.services.ServiceQuiz;
import tn.nebulagaming.utils.RSSFeedParser;

/**
 * FXML Controller class
 *
 * @author SuperNova
 */
public class NewsFeedController implements Initializable {

    @FXML
    private HBox hbox;
    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane grid;

    ServicePost sp = new ServicePost () ; 
    ServiceEvent se = new ServiceEvent () ; 
    ServiceQuiz sq = new ServiceQuiz () ; 
    
    
    List<Post> listPost = sp.display() ;
    List<Event> listEvent = se.display() ;
    
    RSSFeedParser parser = new RSSFeedParser("https://kotaku.com/rss");
    Feed feed = parser.readFeed();
    List<FeedMessage> feedmessage = feed.getMessages() ;
    
    //List for search fct
    List<Object> listAll = new ArrayList<>() ;   
       
    @FXML
    private Button btnGoBack;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        displayPosts () ; 
        displayRSS () ; 
        displayEvent () ; 
 
            btnGoBack.setOnAction(event -> {
            try {
                Parent page1 = FXMLLoader.load(getClass().getResource("MainBackOffice.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(AddPostController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
               
       
    }


public void displayEvent () {
    int nbEvent = se.display().size() ;
    int column = 0 ;
    int row = 0 ;
    try { 
        for (int i=0 ; i<nbEvent ; i++) {
             FXMLLoader fxmlLoader = new FXMLLoader() ; 
             fxmlLoader.setLocation(getClass().getResource("NewsfeedEvent.fxml"));
             AnchorPane anchorPane = fxmlLoader.load () ;
             NewsfeedEventController itemController = fxmlLoader.getController() ; 
             itemController.setData(listEvent.get(i)) ; 

             if (column == 3) {
                 column = 0 ; 
                 row++ ; 
             }

             grid.add(anchorPane,column++ , row) ; 
             GridPane.setMargin(anchorPane, new Insets (10));
        }
    } catch (IOException ex) {
       Logger.getLogger(NewsFeedController.class.getName()).log(Level.SEVERE, null, ex);
    }
}    

public void displayPosts () {
 int nbPost = sp.display().size() ;
 int column = 0 ;
 int row = 0 ;     

    try { 
     for (int i=0 ; i<nbPost ; i++) {
          FXMLLoader fxmlLoader = new FXMLLoader() ; 
          fxmlLoader.setLocation(getClass().getResource("NewsfeedPost.fxml"));
          AnchorPane anchorPane = fxmlLoader.load () ;
          NewsfeedPostController itemController = fxmlLoader.getController() ; 
          itemController.setData(listPost.get(i)) ; 

          if (column == 3) {
              column = 0 ; 
              row++ ; 
          }
          grid.add(anchorPane,column++ , row) ; 
          GridPane.setMargin(anchorPane, new Insets (10));
     }
     } catch (IOException ex) {
        Logger.getLogger(NewsFeedController.class.getName()).log(Level.SEVERE, null, ex);
     }
}

public void displayRSS () {
 int column = 0 ;
 int row = 0 ;      
try { 
    for (int i=0 ; i<feed.getMessages().size() ; i++) {
         FXMLLoader fxmlLoader = new FXMLLoader() ; 
         fxmlLoader.setLocation(getClass().getResource("NewsfeedRSSPost.fxml"));
         AnchorPane anchorPane = fxmlLoader.load () ;
         NewsfeedRSSPostController itemController = fxmlLoader.getController() ; 
         itemController.setData(feed.getMessages().get(i)) ; 

         if (column == 3) {
             column = 0 ; 
             row++ ; 
         }

         grid.add(anchorPane,column++ , row) ; 
         GridPane.setMargin(anchorPane, new Insets (10));
    }
} catch (IOException ex) {
   Logger.getLogger(NewsFeedController.class.getName()).log(Level.SEVERE, null, ex);
}

}
    
}
