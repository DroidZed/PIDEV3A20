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
import javafx.event.ActionEvent;
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
import tn.nebulagaming.models.Entreprise;
import tn.nebulagaming.models.Event;
import tn.nebulagaming.models.Feed;
import tn.nebulagaming.models.FeedMessage;
import tn.nebulagaming.models.Membre;
import tn.nebulagaming.models.Post;
import tn.nebulagaming.services.ServiceEvent;
import tn.nebulagaming.services.ServiceMembre;
import tn.nebulagaming.services.ServicePost;
import tn.nebulagaming.services.ServiceQuiz;
import tn.nebulagaming.services.ServiceUser;
import tn.nebulagaming.utils.GlobalConfig;
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

    ServicePost sp = new ServicePost();
    ServiceEvent se = new ServiceEvent();
    ServiceQuiz sq = new ServiceQuiz();

    List<Post> listPost = sp.display();
    List<Event> listEvent = se.display();

    RSSFeedParser parser = new RSSFeedParser("https://kotaku.com/rss");
    Feed feed = parser.readFeed();
    List<FeedMessage> feedmessage = feed.getMessages();

    //List for search fct
    List<Object> listAll = new ArrayList<>();

    @FXML
    private Button btnGoBack;
    Membre user2;
    private String id;
    @FXML
    private Button btnmrkt;
    @FXML
    private Button btnForum;
    @FXML
    private Button wishListBtn;
    @FXML
    private Button btnJobs;
    Entreprise user1;
    private Entreprise usr1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

	int nbEvent = se.display().size();
	int nbPost = sp.display().size();
	int column = 0;
	int row = 0;

	ServiceMembre sem = new ServiceMembre();

	GlobalConfig config = GlobalConfig.getInstance();

	int session = config.getSession();

	System.out.println(session);

	this.user2 = sem.loadDataModifyId(session);

	try {
	    for (int i = 0; i < nbEvent; i++) {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("NewsfeedEvent.fxml"));
		AnchorPane anchorPane = fxmlLoader.load();
		NewsfeedEventController itemController = fxmlLoader.getController();
		itemController.setData(listEvent.get(i));

		if (column == 3) {
		    column = 0;
		    row++;
		}

		grid.add(anchorPane, column++, row);
		GridPane.setMargin(anchorPane, new Insets(10));
	    }
	} catch (IOException ex) {
	    Logger.getLogger(NewsFeedController.class.getName()).log(Level.SEVERE, null, ex);
	}

	try {
	    for (int i = 0; i < nbPost; i++) {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("NewsfeedPost.fxml"));
		AnchorPane anchorPane = fxmlLoader.load();
		NewsfeedPostController itemController = fxmlLoader.getController();
		itemController.setData(listPost.get(i));

		if (column == 3) {
		    column = 0;
		    row++;
		}
		grid.add(anchorPane, column++, row);
		GridPane.setMargin(anchorPane, new Insets(10));
	    }
	} catch (IOException ex) {
	    Logger.getLogger(NewsFeedController.class.getName()).log(Level.SEVERE, null, ex);
	}

	try {
	    for (int i = 0; i < feed.getMessages().size(); i++) {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("NewsfeedRSSPost.fxml"));
		AnchorPane anchorPane = fxmlLoader.load();
		NewsfeedRSSPostController itemController = fxmlLoader.getController();
		itemController.setData(feed.getMessages().get(i));

		if (column == 3) {
		    column = 0;
		    row++;
		}

		grid.add(anchorPane, column++, row);
		GridPane.setMargin(anchorPane, new Insets(10));
	    }
	} catch (IOException ex) {
	    Logger.getLogger(NewsFeedController.class.getName()).log(Level.SEVERE, null, ex);
	}

    }

    @FXML
    void retourP(ActionEvent event) throws IOException {

	FXMLLoader loader = new FXMLLoader(getClass().getResource("./MembreHome.fxml"));
	Parent root = loader.load();

	ServiceMembre sem = new ServiceMembre();

	int conf = GlobalConfig.getInstance().getSession();

	this.user2 = sem.loadDataModifyId(conf);

	MembreHomeController HomeScene = loader.getController();

	HomeScene.user2 = this.user2;
	HomeScene.id = id;
	Membre a = this.user2;
	HomeScene.iniializeFxml(a);
	HomeScene.showData(a);
	Stage window = (Stage) btnGoBack.getScene().getWindow();
	window.setScene(new Scene(root, 1920, 1080));
    }

    void initializeFxml(Membre e) {
	this.user2 = e;
    }

    @FXML
    void goToMarketplace(ActionEvent event) throws IOException {

	ServiceMembre sem = new ServiceMembre();

	int conf = GlobalConfig.getInstance().getSession();

	this.user2 = sem.loadDataModifyId(conf);

	FXMLLoader loader = new FXMLLoader(getClass().getResource("./Home.fxml"));
	Parent root = loader.load();
	Stage window = (Stage) btnGoBack.getScene().getWindow();
	window.setScene(new Scene(root, 1920, 1080));

    }

    @FXML
    void goForum(ActionEvent event) throws IOException {

	ServiceMembre sem = new ServiceMembre();

	int conf = GlobalConfig.getInstance().getSession();

	this.user2 = sem.loadDataModifyId(conf);

	FXMLLoader loader = new FXMLLoader(getClass().getResource("./VideoGame.fxml"));
	Parent root = loader.load();
	Stage window = (Stage) btnGoBack.getScene().getWindow();
	window.setScene(new Scene(root, 1920, 1080));

    }

    @FXML
    void goWishList(ActionEvent event) throws IOException {

	ServiceMembre sem = new ServiceMembre();

	int conf = GlobalConfig.getInstance().getSession();

	this.user2 = sem.loadDataModifyId(conf);

	FXMLLoader loader = new FXMLLoader(getClass().getResource("./WishList.fxml"));
	Parent root = loader.load();
	Stage window = (Stage) btnGoBack.getScene().getWindow();
	window.setScene(new Scene(root, 1920, 1080));
    }

    @FXML
    void goJobs(ActionEvent event) throws IOException {

	System.out.println(usr1);

	ServiceUser sem = new ServiceUser();

	int conf = GlobalConfig.getInstance().getSession();

	String role = sem.getRoleById(conf);

	 String postulerCandidaturefxml = "./PostulerCandidature.fxml";

	if (role.equals("Entreprise")) {
	 postulerCandidaturefxml = "./AjouterOffre.fxml";
	}
	
	FXMLLoader loader = new FXMLLoader(getClass().getResource(postulerCandidaturefxml));
	Parent root = loader.load();
	Stage window = (Stage) btnGoBack.getScene().getWindow();
	window.setScene(new Scene(root, 1920, 1080));
    }

    void initializeFxml(Entreprise e) {
	this.usr1 = e;
	System.out.println("email " + this.usr1.getEmail());
    }

}
