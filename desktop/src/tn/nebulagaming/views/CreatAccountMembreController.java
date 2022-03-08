/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tn.nebulagaming.views;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import tn.nebulagaming.models.Membre;
import tn.nebulagaming.services.ServiceMembre;
import static tn.nebulagaming.utils.Constantes.CV_PATH;
import static tn.nebulagaming.utils.Constantes.IMG_PATH;
import tn.nebulagaming.utils.JavaMail;
import tn.nebulagaming.utils.UserUtiles;

/**
 * FXML Controller class
 *
 * @author houba
 */
public class CreatAccountMembreController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField nomMembre;
    @FXML
    private TextField mailMembre;
    @FXML
    private PasswordField passwordMembre;
    @FXML
    private PasswordField cpasswordMembre;
    @FXML
    private TextField telMembre;
    @FXML
    private TextField descMembre;
    @FXML
    private Button ajouterMembre;
    @FXML
    private Hyperlink back;
    @FXML
    private Button ajoutPhoto;
    @FXML
    private Label photoName;
    String filePhotoMembre = null;
    File filePhotoMembreFile = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	// TODO
    }

    public void information_Box(String title, String message) {
	Alert dg = new Alert(Alert.AlertType.INFORMATION);
	dg.setTitle(title);
	dg.setContentText(message);
	dg.show();
    }

    public void alert_Box(String title, String message) {
	Alert dg = new Alert(Alert.AlertType.WARNING);
	dg.setTitle(title);
	dg.setContentText(message);
	dg.show();
    }

    private void backToLogin() throws IOException {

	FXMLLoader loader = new FXMLLoader(getClass().getResource("./Login.fxml"));

	Parent root = loader.load();

	Stage window = (Stage) ajouterMembre.getScene().getWindow();
	window.setScene(new Scene(root, 800, 800));
    }

    @FXML
    private void ajouterMembre(ActionEvent event) throws IOException {

	ServiceMembre sMem = new ServiceMembre();
	UserUtiles sUser = new UserUtiles();
	JavaMail sMail = new JavaMail();
	String nom = nomMembre.getText();
	String mail = mailMembre.getText();
	String password = passwordMembre.getText();
	String cpassword = cpasswordMembre.getText();
	String tel = telMembre.getText();
	String filePhoto = filePhotoMembre;
	String description = descMembre.getText();

	//controle de saisie
	if (nom.isEmpty()) {
	    alert_Box("Verifier votre nom", "Votre nom ne doit pas être vide");
	} else if (!sUser.testEmail(mail)) {
	    alert_Box("Verifier votre mail", "veillez saisir une adresse mail valide");
	} else if (!sUser.testPassword(password)) {
	    alert_Box("Verifier mot de passe", "Votre mot de passe doit doit contenir au moins une une majuscule et un chiffre ");
	} else if (!password.equals(cpassword)) {
	    alert_Box("Verifier mot de passe", "Veillez verifier votre mot de passe ");
	} else if (!sUser.testTel(tel)) {
	    alert_Box("Verifier votre numero telephone", "Veillez mettre un numero de telephone valide");

	} else if (filePhoto == null) {
	    alert_Box("Verifier la photo", "inserer une photo");
	} else if (description.isEmpty()) {
	    alert_Box("Verifier le cv", "Ecrire quelque chose ici");
	} else { // public Membre(String nom, String email, String password, String tel, String photo, String role, String description, String etatCompte)
	    String photo = copyPhoto();
	    Membre e = new Membre(nom, mail, password, tel, photo, "Membre", description, "0");
	    sMem.ajouter(e);
	    sMail.sendTextMail("Compte créé avec succès", mail, "Bonjour M." + nom + " et bienvenue sur notre platforme ");
	    information_Box("Compte créé avec succès", "Vous venez de recevoir un e-mail de confirmation");
	    backToLogin();

	}
    }

    @FXML
    private void ajoutPhoto(ActionEvent event) {
	JFileChooser chooser = new JFileChooser();
	FileNameExtensionFilter filter = new FileNameExtensionFilter("Images files", "jpeg");
	chooser.setFileFilter(filter);
	chooser.showOpenDialog(null);
	filePhotoMembre = chooser.getSelectedFile().getName();
	filePhotoMembreFile = chooser.getSelectedFile();
	photoName.setText(filePhotoMembre);
    }

    public String copyPhoto() throws IOException {
	UserUtiles sU = new UserUtiles();
	Date d = new Date();
	String strTimestamp = String.valueOf(d.getTime());
	String randomString = sU.randomString() + "_" + strTimestamp + ".jpeg";
	Path copied = Paths.get(IMG_PATH + randomString);
	Path originalPath = Paths.get(filePhotoMembreFile.getAbsolutePath());
	Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);
	return randomString;
    }

    private void backLogin(ActionEvent event) throws IOException {
	AnchorPane pane = FXMLLoader.load(getClass().getResource("./Login.fxml"));
	rootPane.getChildren().setAll(pane);
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
	backToLogin();
    }

}
