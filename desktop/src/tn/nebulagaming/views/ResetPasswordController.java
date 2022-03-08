/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tn.nebulagaming.views;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.nebulagaming.services.ServiceUser;
import tn.nebulagaming.utils.JavaMail;
import tn.nebulagaming.utils.UserUtiles;

public class ResetPasswordController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label emailLab;
    @FXML
    private TextField email;
    @FXML
    private Button codeConfirmation;
    @FXML
    private TextField code;
    @FXML
    private Label codeLab;
    @FXML
    private Label cpwLab;
    @FXML
    private Label pwLab;
    @FXML
    private Button resetPW;
    @FXML
    private Button verifCode;
    @FXML
    private Hyperlink back;
    @FXML
    private PasswordField nvPw;
    @FXML
    private PasswordField cnvPW;
    String emailPW = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	// TODO
	visibility(true, false, false);
    }

    public void visibility(boolean phase1, boolean phase2, boolean phase3) {
	emailLab.setVisible(phase1);
	email.setVisible(phase1);
	codeConfirmation.setVisible(phase1); // fin phase 1
	codeLab.setVisible(phase2);
	code.setVisible(phase2);
	verifCode.setVisible(phase2); // fin phase 2
	pwLab.setVisible(phase3);
	nvPw.setVisible(phase3);
	cpwLab.setVisible(phase3);
	cnvPW.setVisible(phase3);
	resetPW.setVisible(phase3); // fin phase 3
    }
    Random rand = new Random();
    int codeVerification = rand.nextInt((1000) + (9999));
    String codeVerif = String.valueOf(codeVerification);

    @FXML
    private void codeConfirmation(ActionEvent event) {

	String mail = email.getText();
	ServiceUser sU = new ServiceUser();
	JavaMail uMail = new JavaMail();
	UserUtiles uUtiles = new UserUtiles();

	if (!uUtiles.testEmail(mail) || !sU.verifierEmailBd(mail)) {
	    uUtiles.alert_Box("Verifier adresse", "Veillez saisir une adresse mail valide");
	} else {
	    emailPW = mail;
	    uMail.sendTextMail("Compte créé avec succès", mail, "voici votre code de " + codeVerification + "  ");
	    uUtiles.information_Box("Succes", "verifier votre boite mail");
	    visibility(false, true, false);
	}
    }

    @FXML
    private void resetPW(ActionEvent event) throws IOException {
	ServiceUser sUser = new ServiceUser();
	UserUtiles uUser = new UserUtiles();
	String password = nvPw.getText();
	String cPassword = cnvPW.getText();

	if (!uUser.testPassword(password)) {
	    uUser.alert_Box("Verifier mot de passe", "Votre mot de passe doit doit contenir au moins une une majuscule et un chiffre ");
	} else if (!password.equals(cPassword)) {
	    uUser.alert_Box("Verifier mot de passe", "Veillez verifier votre mot de passe ");
	} else {
	    sUser.modifierPassword(emailPW, uUser.crypterPassword(password));
	    uUser.information_Box("mot de passe", "mot de passe changer avec succes");
	    BackToLogin();

	}
    }

    @FXML
    private void verifCode(ActionEvent event) {
	UserUtiles uUtiles = new UserUtiles();
	String codeV = code.getText();
	if (!codeV.equals(this.codeVerif)) {
	    uUtiles.alert_Box("Verifier ", "Veillez verifier le code ");
	} else {
	    uUtiles.information_Box("VCode verifier", "Vous pouvez desoermer changer votre mot de passe");
	    visibility(false, false, true);
	}
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
	BackToLogin();
    }

    private void BackToLogin() throws IOException {

	FXMLLoader loader = new FXMLLoader(getClass().getResource("./Login.fxml"));
	Parent root = loader.load();
	Stage window = (Stage) resetPW.getScene().getWindow();
	window.setScene(new Scene(root, 800, 800));
    }
}
