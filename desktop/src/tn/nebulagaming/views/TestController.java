/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.nebulagaming.views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import tn.nebulagaming.utils.JavaMail;
import tn.nebulagaming.models.Candidacy;
import tn.nebulagaming.services.ServiceCandidacy;
import tn.nebulagaming.services.ServiceUser;
import tn.nebulagaming.utils.GlobalConfig;

/**
 * FXML Controller class
 *
 * @author User
 */
public class TestController implements Initializable {

    @FXML
    private CheckBox rep1;
    @FXML
    private CheckBox rep2;
    @FXML
    private CheckBox rep3;
    @FXML
    private CheckBox rep4;

    ServiceCandidacy sc = new ServiceCandidacy();

    int idoffre;
    int idUser;
    @FXML
    private Button bntenv;
    @FXML
    private CheckBox rep5;
    @FXML
    private CheckBox rep6;
    @FXML
    private CheckBox rep7;
    @FXML
    private CheckBox rep8;
    @FXML
    private CheckBox rep9;
    @FXML
    private CheckBox rep10;
    @FXML
    private CheckBox rep11;
    @FXML
    private CheckBox rep12;

    private GlobalConfig conf;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	// TODO

	conf = GlobalConfig.getInstance();
    }

    @FXML
    private void checkrep1(ActionEvent event) {
	if (rep1.isSelected()) {
	    rep2.setSelected(false);
	}
    }

    @FXML
    private void checkrep2(ActionEvent event) {
	if (rep2.isSelected()) {
	    rep1.setSelected(false);
	}
    }

    @FXML
    private void checkrep3(ActionEvent event) {
	if (rep3.isSelected()) {
	    rep4.setSelected(false);
	}
    }

    @FXML
    private void checkrep4(ActionEvent event) {
	if (rep4.isSelected()) {
	    rep3.setSelected(false);
	}
    }

    @FXML
    private void checkrep5(ActionEvent event) {
	if (rep5.isSelected()) {
	    rep6.setSelected(false);
	}
    }

    @FXML
    private void checkrep6(ActionEvent event) {
	if (rep6.isSelected()) {
	    rep5.setSelected(false);
	}
    }

    @FXML
    private void checkrep7(ActionEvent event) {
	if (rep7.isSelected()) {
	    rep8.setSelected(false);
	}
    }

    @FXML
    private void checkrep8(ActionEvent event) {
	if (rep8.isSelected()) {
	    rep7.setSelected(false);
	}
    }

    @FXML
    private void checkrep9(ActionEvent event) {
	if (rep9.isSelected()) {
	    rep10.setSelected(false);
	}
    }

    @FXML
    private void checkrep10(ActionEvent event) {
	if (rep10.isSelected()) {
	    rep9.setSelected(false);
	}
    }

    @FXML
    private void checkrep11(ActionEvent event) {
	if (rep11.isSelected()) {
	    rep12.setSelected(false);
	}
    }

    @FXML
    private void checkrep12(ActionEvent event) {
	if (rep12.isSelected()) {
	    rep11.setSelected(false);
	}
    }

    @FXML
    private void Envoyer(ActionEvent event) throws IOException {
	if ((rep1.isSelected() || rep2.isSelected()) && (rep3.isSelected() || rep4.isSelected())
		&& (rep5.isSelected() || rep6.isSelected()) && (rep7.isSelected() || rep8.isSelected())
		&& (rep9.isSelected() || rep10.isSelected()) && (rep11.isSelected() || rep12.isSelected())) {
	    if (rep1.isSelected() && rep3.isSelected() && rep5.isSelected() && rep8.isSelected()
		    && rep9.isSelected() && rep12.isSelected()) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Resultat");
		alert.setHeaderText(null);
		alert.setContentText("Felicitations, vous etes admis!");

		alert.showAndWait();
		Candidacy c = sc.getById(idUser, idoffre);
		c.setEtat("Admis");
		sc.modifier(c);
		JavaMail mailer = new JavaMail();
		ServiceUser serv = new ServiceUser();

		String email = serv.getEmailById(conf.getSession());
		mailer.sendTextMail("Candidat Approuvé", email, this.idUser + " à etait approuvée");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
		Parent root = loader.load();

		MenuController mc = loader.getController();
		bntenv.getScene().setRoot(root);

	    } else {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Resultat");
		alert.setHeaderText(null);
		alert.setContentText("Desole, vous etes echoué!");

		alert.showAndWait();
		Candidacy c = sc.getById(idUser, idoffre);
		c.setEtat("Refuser");
		sc.modifier(c);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
		Parent root = loader.load();

		MenuController mc = loader.getController();
		bntenv.getScene().setRoot(root);
	    }
	} else {
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle("ERROR");
	    alert.setHeaderText(null);
	    alert.setContentText("réponse manquante!");

	    alert.showAndWait();
	}

    }

    void setvalues(int idOffer, int idUser) {
	this.idoffre = idOffer;
	this.idUser = idUser;
    }

}
