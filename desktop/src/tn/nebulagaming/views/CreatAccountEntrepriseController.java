/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tn.nebulagaming.views;

import com.github.sarxos.webcam.Webcam;
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
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import tn.nebulagaming.models.Entreprise;
import tn.nebulagaming.services.ServiceEntreprise;
import tn.nebulagaming.services.ServiceNotification;
import tn.nebulagaming.services.ServiceUser;
import static tn.nebulagaming.utils.Constantes.CV_PATH;
import static tn.nebulagaming.utils.Constantes.IMG_PATH;
import tn.nebulagaming.utils.JavaMail;
import tn.nebulagaming.utils.UserUtiles;

/**
 * FXML Controller class
 *
 * @author houba
 */
public class CreatAccountEntrepriseController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField nomEnt;
    @FXML
    private TextField mailEnt;
    @FXML
    private PasswordField passwordEnt;
    @FXML
    private PasswordField cpasswordEnt;
    @FXML
    private TextField telEnt;
    @FXML
    private Button ajouterEnt;
    @FXML
    private Button cv;
    @FXML
    private Label cvName;
    @FXML
    private Hyperlink back;
    @FXML
    private Label photoName;
    @FXML
    private Button photo;
    File fileCvEnt = null;
    String filePhotoEnt = null;
    File filePhotoEntFile = null;

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

    private void BackToLogin() throws IOException {

	FXMLLoader loader = new FXMLLoader(getClass().getResource("./Login.fxml"));

	Parent root = loader.load();

	Stage window = (Stage) ajouterEnt.getScene().getWindow();
	window.setScene(new Scene(root, 800, 800));
    }

    @FXML
    private void ajouterEnt(ActionEvent event) throws IOException {

	ServiceEntreprise sEnt = new ServiceEntreprise();
	ServiceUser sUser = new ServiceUser();
	UserUtiles uUtiles = new UserUtiles();
	JavaMail sMail = new JavaMail();
	String nom = nomEnt.getText();
	String mail = mailEnt.getText();
	String password = passwordEnt.getText();
	String cpassword = cpasswordEnt.getText();
	String tel = telEnt.getText();
	File fileCv = fileCvEnt;
	String filePhoto = filePhotoEnt;

	//controle de saisie
	if (nom.isEmpty()) {
	    alert_Box("Verifier votre nom", "Votre nom ne doit pas être vide");
	} else if (!uUtiles.testEmail(mail)) {
	    alert_Box("Verifier votre mail", "veillez saisir une adresse mail valide");
	} else if (sUser.verifierEmailBd(mail)) {
	    alert_Box("Verifier votre mail", "veillez saisir une adresse non existant");
	} else if (!uUtiles.testPassword(password)) {
	    alert_Box("Verifier mot de passe", "Votre mot de passe doit doit contenir au moins une une majuscule et un chiffre ");
	} else if (!password.equals(cpassword)) {
	    alert_Box("Verifier mot de passe", "Veillez verifier votre mot de passe ");
	} else if (!uUtiles.testTel(tel)) {
	    alert_Box("Verifier votre numero telephone", "Veillez mettre un numero de telephone valide");
	} else if (fileCv == null) {
	    alert_Box("Verifier le cv", "inserer cv");
	} else if (filePhoto == null) {
	    alert_Box("Verifier la photo", "inserer une photo");
//nameUser,emailUser,pwdUser,phone,photoUser,roleUser ,cv,stateUser
	} else {
	    String cv = copyCV();
	    String photo = copyPhoto();
	    Entreprise e = new Entreprise(nom, mail, uUtiles.crypterPassword(password), tel, photo, "Entreprise", cv, "0");
	    Entreprise ee = new Entreprise();
	    ee.setNom(nom);
	    ee.setEmail(mail);
	    ee.setCv(cv);
	    ee.setTel(tel);
	    ee.setPassword(password);
	    ee.setPhoto(photo);
	    sEnt.ajouter(ee);
	    sMail.sendTextMail("Compte créé avec succès", mail, "Bonjour M." + nom + " et bienvenu sur notre platforme ");
	    information_Box("Compte créé avec succès", "Vous venez de recevoir un e-mail de confirmation");
	    BackToLogin();
	}
    }

    private void backLogin(ActionEvent event) throws IOException {
	AnchorPane pane = FXMLLoader.load(getClass().getResource("./Login.fxml"));
	rootPane.getChildren().setAll(pane);
    }

    @FXML
    private void back(ActionEvent event) throws IOException {

	BackToLogin();

    }

    @FXML
    private void ajoutPhoto(ActionEvent event) {

	JFileChooser chooser = new JFileChooser();
	FileNameExtensionFilter filter = new FileNameExtensionFilter("Images files", "jpeg");
	chooser.setFileFilter(filter);
	chooser.showOpenDialog(null);
	filePhotoEnt = chooser.getSelectedFile().getName();
	filePhotoEntFile = chooser.getSelectedFile();
	photoName.setText(filePhotoEnt);
    }

    @FXML
    private void take_picture(ActionEvent event) throws IOException {
	ServiceNotification sn = new ServiceNotification();

	String code_random = code_random();

	Webcam webcam = Webcam.getDefault();
	webcam.open();
	String filename = "";
	filename = code_random + "_" + telEnt.getText() + ".jpeg";
	ImageIO.write(webcam.getImage(), "JPG", new File("Images/" + filename));
	filePhotoEnt = filename;
	webcam.close();

	sn.Notification("Felicitation", "Photo Prise");

    }

    private String code_random() {

	String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
		+ "0123456789"
		+ "abcdefghijklmnopqrstuvxyz";

	// create StringBuffer size of AlphaNumericString 
	StringBuilder sb = new StringBuilder(4);

	for (int i = 0; i < 4; i++) {

	    // generate a random number between 
	    // 0 to AlphaNumericString variable length 
	    int index
		    = (int) (AlphaNumericString.length()
		    * Math.random());

	    // add Character one by one in end of sb 
	    sb.append(AlphaNumericString
		    .charAt(index));
	}

	return sb.toString();
    }

    @FXML
    public void ajoutCv(ActionEvent event) throws IOException {

	JFileChooser chooser = new JFileChooser();
	FileNameExtensionFilter filter = new FileNameExtensionFilter("pdf files", "pdf");
	chooser.setFileFilter(filter);
	chooser.showOpenDialog(null);
	fileCvEnt = chooser.getSelectedFile();
	cvName.setText(fileCvEnt.getName());

    }

    public String copyCV() throws IOException {
	UserUtiles sU = new UserUtiles();
	Date d = new Date();
	String strTimestamp = String.valueOf(d.getTime());
	String randomString = sU.randomString() + "_" + strTimestamp + ".pdf";
	Path copied = Paths.get(CV_PATH + randomString);
	Path originalPath = Paths.get(fileCvEnt.getAbsolutePath());
	Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);
	return randomString;
    }

    public String copyPhoto() throws IOException {
	UserUtiles sU = new UserUtiles();
	Date d = new Date();
	String strTimestamp = String.valueOf(d.getTime());
	String randomString = sU.randomString() + "_" + strTimestamp + ".jpeg";
	Path copied = Paths.get(IMG_PATH + randomString);
	Path originalPath = Paths.get(filePhotoEntFile.getAbsolutePath());
	Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);
	return randomString;
    }

}
