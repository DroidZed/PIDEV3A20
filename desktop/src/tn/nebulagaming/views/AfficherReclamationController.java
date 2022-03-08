/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tn.nebulagaming.views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import tn.nebulagaming.models.Admin;
import tn.nebulagaming.models.Membre;
import tn.nebulagaming.models.Reclamation;
import tn.nebulagaming.services.ServiceAdmin;
import tn.nebulagaming.services.ServiceReclamation;
import tn.nebulagaming.services.ServiceUser;
import static tn.nebulagaming.utils.Constantes.IMG_PATH_LOAD;

/**
 * FXML Controller class
 *
 * @author houba
 */
public class AfficherReclamationController implements Initializable {

    @FXML
    private Label nomUser;
    @FXML
    private Label typeRec;
    @FXML
    private Label etatRec;
    @FXML
    private Label message;
    @FXML
    private Button valider;
    Reclamation rec;
    @FXML
    private TextField rep;
    @FXML
    private Hyperlink retour;
    private String id;
    Admin user;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	// TODO
    }

    void iniializeFxml(Reclamation r) {
	ServiceUser su = new ServiceUser();
	ServiceReclamation sr = new ServiceReclamation();

	rec = r;

    }

    void showData(Reclamation r) {

	message.setText(r.getMessage());
	nomUser.setText(r.getNomUser());
	typeRec.setText(r.getTypeComplaint());
	etatRec.setText(r.getStatusComplaint());

	System.out.println("test rec");

    }

    @FXML
    private void valider(ActionEvent event) {
	ServiceReclamation sr = new ServiceReclamation();
	sr.traiterReclamation(rec, rep.getText());
    }

    @FXML
    private void retour(ActionEvent event) throws IOException {
	FXMLLoader loader = new FXMLLoader(getClass().getResource("./AdminHome.fxml"));
	Parent root = loader.load();
	AdminHomeController HomeScene = loader.getController();
	HomeScene.user = this.user;
	HomeScene.id = id;
	Admin a = this.user;
	HomeScene.iniializeFxml(a);
	HomeScene.showData(a);
	Stage window = (Stage) retour.getScene().getWindow();
	window.setScene(new Scene(root, 800, 800));
    }

}
