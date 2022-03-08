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
import tn.nebulagaming.models.Event;
import tn.nebulagaming.models.Participation;
import tn.nebulagaming.services.ServiceEvent;
import tn.nebulagaming.services.ServiceParticipation;
import tn.nebulagaming.utils.GlobalConfig;

/**
 * FXML Controller class
 *
 * @author SuperNova
 */
public class ManageParticipationController implements Initializable {

    @FXML
    private TableView<Participation> tvParticipation;
    @FXML
    private TableColumn<Participation, String> userIdCol;
    @FXML
    private TableColumn<Participation, String> bookedDateCol;
    @FXML
    private TableColumn<Participation, String> payTypeCol;
    @FXML
    private TableColumn<Participation, String> rankCol;
    @FXML
    private TableColumn<Participation, String> resultCol;
    @FXML
    private TableColumn<Participation, String> idParticipationCol;
    @FXML
    private Label lbEventName;
    @FXML
    private Label createdAtEvent;

    //Event 
    int idEvent = ManageEventController.eventRecupParticipation.getIdPost();
    ServiceParticipation spar = new ServiceParticipation();
    ServiceEvent se = new ServiceEvent();
    @FXML
    private Button btnGoBack;
    @FXML
    private Label nbParticipation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	displayParticipations(idEvent);
	nbParticipation.setText(String.valueOf(spar.countParticipationByEvent(idEvent)));
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

    public void displayParticipations(int idEvent) {

	lbEventName.setText(se.getEventById(idEvent).getTitlePost());
	createdAtEvent.setText(String.valueOf(se.getEventById(idEvent).getPostedDTM()));

	List<Participation> list = spar.displayParticipationByEvent(idEvent);

	idParticipationCol.setCellValueFactory(new PropertyValueFactory<>("idParticipation"));
	idParticipationCol.setVisible(false);

	bookedDateCol.setCellValueFactory(new PropertyValueFactory<>("bookedDTM"));
	payTypeCol.setCellValueFactory(new PropertyValueFactory<>("idPayType"));
	rankCol.setCellValueFactory(new PropertyValueFactory<>("rank"));
	resultCol.setCellValueFactory(new PropertyValueFactory<>("result"));

	//get Pay Type as String 
	payTypeCol.setCellValueFactory(data -> {
	    Participation participation = data.getValue();
	    Connection cnx = GlobalConfig.getInstance().getCONNECTION();
	    String strPayType = "SELECT payType  FROM tbl_paytype where idPayType =" + participation.getPayType();
	    Statement st = null;
	    String result = "";
	    try {
		st = cnx.createStatement();
	    } catch (SQLException ex) {
		Logger.getLogger(ManageParticipationController.class.getName()).log(Level.SEVERE, null, ex);
	    }
	    ResultSet rs;
	    try {
		rs = st.executeQuery(strPayType);
		if (rs.next()) {
		    result = rs.getString("payType");
		}
	    } catch (SQLException ex) {
		Logger.getLogger(ManageParticipationController.class.getName()).log(Level.SEVERE, null, ex);
	    }
	    return new ReadOnlyStringWrapper(result);
	}
	);

	//get User Name jointure 
	userIdCol.setCellValueFactory(data -> {
	    Participation participation = data.getValue();
	    Connection cnx = GlobalConfig.getInstance().getCONNECTION();
	    String strUserName = "SELECT nameUser FROM tbl_user where idUser =" + participation.getIdUser();
	    Statement st = null;
	    String result = "";
	    try {
		st = cnx.createStatement();
	    } catch (SQLException ex) {
		Logger.getLogger(ManagePostController.class.getName()).log(Level.SEVERE, null, ex);
	    }
	    ResultSet rs;
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

	ObservableList<Participation> listParticipants = FXCollections.observableArrayList(list);
	tvParticipation.setItems(listParticipants);
    }

}
