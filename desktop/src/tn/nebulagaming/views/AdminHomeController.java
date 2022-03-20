/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tn.nebulagaming.views;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Time;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import tn.nebulagaming.models.Admin;
import tn.nebulagaming.models.Entreprise;
import tn.nebulagaming.models.Membre;
import tn.nebulagaming.models.Reclamation;
import tn.nebulagaming.services.ServiceAdmin;
import tn.nebulagaming.services.ServiceEntreprise;
import tn.nebulagaming.services.ServiceMembre;
import tn.nebulagaming.services.ServiceReclamation;
import tn.nebulagaming.services.ServiceUser;
import tn.nebulagaming.utils.GlobalConfig;
import static tn.nebulagaming.utils.Constantes.CV_PATH;
import static tn.nebulagaming.utils.Constantes.IMG_PATH;
import static tn.nebulagaming.utils.Constantes.IMG_PATH_LOAD;
import tn.nebulagaming.utils.UserUtiles;

/**
 * FXML Controller class
 *
 * @author houba
 */
public class AdminHomeController implements Initializable {

    @FXML
    private TextField nomAdm;
    @FXML
    private TextField mailAdm;
    @FXML
    private TextField telAdmin;
    @FXML
    private Label usernameLab;
    @FXML
    private Hyperlink logOut;
    @FXML
    private ImageView photoAdmin;
    @FXML
    private Label photoName;
    @FXML
    private TableView<Entreprise> tabEnt;
    @FXML
    private TableColumn<Entreprise, String> nomEnt;
    @FXML
    private TableColumn<Entreprise, String> emailEnt;
    @FXML
    private TableColumn<Entreprise, Hyperlink> cvEnt;
    @FXML
    private TableColumn<Entreprise, String> telEnt;
    @FXML
    private TableColumn<Entreprise, String> etatCompteEnt;
    @FXML
    private TableColumn<Entreprise, String> photoEnt;
    @FXML
    private TableColumn<Entreprise, String> dateEnt;
    @FXML
    private TableView<Membre> tabMembre;
    @FXML
    private TableColumn<Membre, String> nomMembre;
    @FXML
    private TableColumn<Membre, String> emailMembre;
    @FXML
    private TableColumn<Membre, String> descMembre;
    @FXML
    private TableColumn<Membre, String> telMembre;
    @FXML
    private TableColumn<Membre, String> etatCompteMembre;
    @FXML
    private TableColumn<Membre, String> photoMembre;
    public Admin user = new Admin();
    public String id;
    private Tab listAdminTab;
    private Tab ajouterAdminTab;
    @FXML
    private CheckBox triNomEnt;
    @FXML
    private TableView<Reclamation> tabRec;
    @FXML
    private TableColumn<Reclamation, String> typeRec;
    @FXML
    private TableColumn<Reclamation, String> etatRec;
    @FXML
    private CheckBox triTelEnt;
    @FXML
    private TextField rechercherEnt;

    File filePhotoAdmin = null;
    @FXML
    private ComboBox<?> etatsEnt;
    @FXML
    private CheckBox trierNomMembre;
    @FXML
    private CheckBox trierTelMembre;
    @FXML
    private ComboBox<?> etatsMembre;
    @FXML
    private TextField rechercherMembre;
    @FXML
    private TableColumn<Membre, String> dateMembre;
    @FXML
    private TableColumn<Reclamation, String> nomUser;
    @FXML
    private Button modifInfo;
    @FXML
    private Button afficherCv;
    @FXML
    private Button afficherPhotoEnt;
    @FXML
    private Button modifEtatCompteEnt;
    @FXML
    private Button afficherPhotoMembre;
    @FXML
    private Button modifEtatMembre;
    @FXML
    private TextField rechercherRec;
    @FXML
    private CheckBox triNomRec;
    @FXML
    private CheckBox triTypeRec;
    @FXML
    private CheckBox triEtatRec;
    @FXML
    private ImageView photoEntr;
    MediaPlayer mediaPlayer;
    @FXML
    private ImageView animation;
    @FXML
    private ImageView crown;
    @FXML
    private Hyperlink pdfEnt;
    @FXML
    private ImageView photoMem;
    Entreprise user1;
    @FXML
    private TableColumn<Reclamation, String> message;
    @FXML
    private TableColumn<Reclamation, String> rep;
    @FXML
    private Button traiterRec;
    @FXML
    private Button btnNFAd;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	//  tab Entre

	List<String> listCritereEt;
	listCritereEt = Arrays.asList("nom", "email", "cv", "tel", "etatCompte", "photo", "date");
	ObservableList<String> listCritereA1;
	listCritereA1 = FXCollections.observableArrayList(listCritereEt);
	try {
	    affichageTabEntreprise();
	} catch (IOException ex) {
	    Logger.getLogger(AdminHomeController.class.getName()).log(Level.SEVERE, null, ex);
	}

	//  tab membre
	List<String> listCritereEn;
	listCritereEn = Arrays.asList("nom", "email", "tel", "description", "photo", "date");
	ObservableList<String> listCritereEnt;
	listCritereEnt = FXCollections.observableArrayList(listCritereEn);
	affichageTabMembre();
	// Tab rec

	List<String> listCritereRec;
	listCritereRec = Arrays.asList("nomUser", "statusComplaint", "typeComplaint", "message", "answerComplaint");
	ObservableList<String> listCritereR;
	listCritereR = FXCollections.observableArrayList(listCritereRec);
	affichageTabRec();
	//
    }

    @FXML
    public void affichageTabRec() {
	if (rechercherRec.getText() == null || rechercherRec.getText().trim().isEmpty()) {
	    if (triNomRec.isSelected() == false && triTypeRec.isSelected() == false && triEtatRec.isSelected() == false) {
		ServiceReclamation sa = new ServiceReclamation();
		ObservableList<Reclamation> list = FXCollections.observableArrayList(sa.afficherAll());
		nomUser.setCellValueFactory(new PropertyValueFactory<>("nomUser"));
		etatRec.setCellValueFactory(new PropertyValueFactory<>("statusComplaint"));
		typeRec.setCellValueFactory(new PropertyValueFactory<>("typeComplaint"));
		message.setCellValueFactory(new PropertyValueFactory<>("message"));
		System.out.println(GlobalConfig.getInstance().getSession());
		rep.setCellValueFactory(new PropertyValueFactory<>("answerComplaint"));

		tabRec.setItems(list);
	    } else if (triTypeRec.isSelected() == true && triEtatRec.isSelected() == false && triNomRec.isSelected() == false) {
		ServiceReclamation sa = new ServiceReclamation();
		ObservableList<Reclamation> list = FXCollections.observableArrayList(sa.trierTypeAdm());
		nomUser.setCellValueFactory(new PropertyValueFactory<>("nomUser"));
		etatRec.setCellValueFactory(new PropertyValueFactory<>("statusComplaint"));
		typeRec.setCellValueFactory(new PropertyValueFactory<>("typeComplaint"));
		message.setCellValueFactory(new PropertyValueFactory<>("message"));
		System.out.println(GlobalConfig.getInstance().getSession());
		rep.setCellValueFactory(new PropertyValueFactory<>("answerComplaint"));

		tabRec.setItems(list);
	    } else if (triTypeRec.isSelected() == false && triEtatRec.isSelected() == true && triNomRec.isSelected() == false) {
		ServiceReclamation sa = new ServiceReclamation();
		ObservableList<Reclamation> list = FXCollections.observableArrayList(sa.trierEtatAdm());
		nomUser.setCellValueFactory(new PropertyValueFactory<>("nomUser"));
		etatRec.setCellValueFactory(new PropertyValueFactory<>("statusComplaint"));
		typeRec.setCellValueFactory(new PropertyValueFactory<>("typeComplaint"));
		message.setCellValueFactory(new PropertyValueFactory<>("message"));
		System.out.println(GlobalConfig.getInstance().getSession());
		rep.setCellValueFactory(new PropertyValueFactory<>("answerComplaint"));

		tabRec.setItems(list);
	    } else if (triTypeRec.isSelected() == true && triEtatRec.isSelected() == true && triNomRec.isSelected() == false) {
		ServiceReclamation sa = new ServiceReclamation();
		ObservableList<Reclamation> list = FXCollections.observableArrayList(sa.trierMultiAdm());
		nomUser.setCellValueFactory(new PropertyValueFactory<>("nomUser"));
		etatRec.setCellValueFactory(new PropertyValueFactory<>("statusComplaint"));
		typeRec.setCellValueFactory(new PropertyValueFactory<>("typeComplaint"));
		message.setCellValueFactory(new PropertyValueFactory<>("message"));
		System.out.println(GlobalConfig.getInstance().getSession());
		rep.setCellValueFactory(new PropertyValueFactory<>("answerComplaint"));

		tabRec.setItems(list);
	    } else if (triNomRec.isSelected() == true && triTypeRec.isSelected() == false && triEtatRec.isSelected() == false) {
		ServiceReclamation sa = new ServiceReclamation();
		ObservableList<Reclamation> list = FXCollections.observableArrayList(sa.trierAdm());
		nomUser.setCellValueFactory(new PropertyValueFactory<>("nomUser"));
		etatRec.setCellValueFactory(new PropertyValueFactory<>("statusComplaint"));
		typeRec.setCellValueFactory(new PropertyValueFactory<>("typeComplaint"));
		message.setCellValueFactory(new PropertyValueFactory<>("message"));
		System.out.println(GlobalConfig.getInstance().getSession());
		rep.setCellValueFactory(new PropertyValueFactory<>("answerComplaint"));

		tabRec.setItems(list);
	    } else if (triNomRec.isSelected() == true && triTypeRec.isSelected() == true && triEtatRec.isSelected() == false) {
		ServiceReclamation sa = new ServiceReclamation();
		ObservableList<Reclamation> list = FXCollections.observableArrayList(sa.trierNomTypeAdm());
		nomUser.setCellValueFactory(new PropertyValueFactory<>("nomUser"));
		etatRec.setCellValueFactory(new PropertyValueFactory<>("statusComplaint"));
		typeRec.setCellValueFactory(new PropertyValueFactory<>("typeComplaint"));
		message.setCellValueFactory(new PropertyValueFactory<>("message"));
		System.out.println(GlobalConfig.getInstance().getSession());
		rep.setCellValueFactory(new PropertyValueFactory<>("answerComplaint"));

		tabRec.setItems(list);
	    } else if (triNomRec.isSelected() == true && triTypeRec.isSelected() == true && triEtatRec.isSelected() == true) {
		ServiceReclamation sa = new ServiceReclamation();
		ObservableList<Reclamation> list = FXCollections.observableArrayList(sa.trierMultiThree());
		nomUser.setCellValueFactory(new PropertyValueFactory<>("nomUser"));
		etatRec.setCellValueFactory(new PropertyValueFactory<>("statusComplaint"));
		typeRec.setCellValueFactory(new PropertyValueFactory<>("typeComplaint"));
		message.setCellValueFactory(new PropertyValueFactory<>("message"));
		System.out.println(GlobalConfig.getInstance().getSession());
		rep.setCellValueFactory(new PropertyValueFactory<>("answerComplaint"));

		tabRec.setItems(list);

	    }

	} else {
	    ServiceReclamation sa = new ServiceReclamation();
	    ObservableList<Reclamation> list = FXCollections.observableArrayList(sa.rechercherAdm(rechercherRec.getText()));
	    nomUser.setCellValueFactory(new PropertyValueFactory<>("nomUser"));
	    etatRec.setCellValueFactory(new PropertyValueFactory<>("statusComplaint"));
	    typeRec.setCellValueFactory(new PropertyValueFactory<>("typeComplaint"));
	    message.setCellValueFactory(new PropertyValueFactory<>("message"));
	    System.out.println(GlobalConfig.getInstance().getSession());
	    rep.setCellValueFactory(new PropertyValueFactory<>("answerComplaint"));

	    tabRec.setItems(list);
	}

	System.out.println("  test");
    }

    @FXML
    public void affichageTabEntreprise() throws IOException {
	if (rechercherEnt.getText() == null || rechercherEnt.getText().trim().isEmpty()) {
	    if (triNomEnt.isSelected() == false && triTelEnt.isSelected() == false) {
		ServiceEntreprise sa = new ServiceEntreprise();
		ObservableList<Entreprise> list = FXCollections.observableArrayList(sa.afficher());
		nomEnt.setCellValueFactory(new PropertyValueFactory<>("nom"));
		emailEnt.setCellValueFactory(new PropertyValueFactory<>("email"));
		cvEnt.setCellValueFactory(new PropertyValueFactory<>("cv"));
		telEnt.setCellValueFactory(new PropertyValueFactory<>("tel"));
		etatCompteEnt.setCellValueFactory(new PropertyValueFactory<>("etatCompte"));
		photoEnt.setPrefWidth(80);
		photoEnt.setCellValueFactory(new PropertyValueFactory<>("photo"));
		dateEnt.setCellValueFactory(new PropertyValueFactory<>("date"));
		tabEnt.setItems(list);
		System.out.println(rechercherEnt.getText() + "test");
		//afficherPhoto();

	    } else if (triNomEnt.isSelected() == true) {
		if (triTelEnt.isSelected() == false) {
		    ServiceEntreprise se = new ServiceEntreprise();
		    ObservableList<Entreprise> list = FXCollections.observableArrayList(se.trier());
		    nomEnt.setCellValueFactory(new PropertyValueFactory<>("nom"));
		    emailEnt.setCellValueFactory(new PropertyValueFactory<>("email"));
		    cvEnt.setCellValueFactory(new PropertyValueFactory<>("cv"));
		    telEnt.setCellValueFactory(new PropertyValueFactory<>("tel"));
		    etatCompteEnt.setCellValueFactory(new PropertyValueFactory<>("etatCompte"));

		    photoEnt.setCellValueFactory(new PropertyValueFactory<>("photo"));
		    dateEnt.setCellValueFactory(new PropertyValueFactory<>("date"));
		    tabEnt.setItems(list);
		} else {
		    ServiceEntreprise se = new ServiceEntreprise();
		    ObservableList<Entreprise> list = FXCollections.observableArrayList(se.trierMulti());
		    nomEnt.setCellValueFactory(new PropertyValueFactory<>("nom"));
		    emailEnt.setCellValueFactory(new PropertyValueFactory<>("email"));
		    cvEnt.setCellValueFactory(new PropertyValueFactory<>("cv"));
		    telEnt.setCellValueFactory(new PropertyValueFactory<>("tel"));
		    etatCompteEnt.setCellValueFactory(new PropertyValueFactory<>("etatCompte"));

		    photoEnt.setCellValueFactory(new PropertyValueFactory<>("photo"));
		    dateEnt.setCellValueFactory(new PropertyValueFactory<>("date"));
		    tabEnt.setItems(list);

		}
	    } else if (triTelEnt.isSelected() == true) {
		if (triNomEnt.isSelected() == false) {
		    ServiceEntreprise se = new ServiceEntreprise();
		    ObservableList<Entreprise> list = FXCollections.observableArrayList(se.trierTel());
		    nomEnt.setCellValueFactory(new PropertyValueFactory<>("nom"));
		    emailEnt.setCellValueFactory(new PropertyValueFactory<>("email"));
		    cvEnt.setCellValueFactory(new PropertyValueFactory<>("cv"));
		    telEnt.setCellValueFactory(new PropertyValueFactory<>("tel"));
		    etatCompteEnt.setCellValueFactory(new PropertyValueFactory<>("etatCompte"));

		    photoEnt.setCellValueFactory(new PropertyValueFactory<>("photo"));
		    dateEnt.setCellValueFactory(new PropertyValueFactory<>("date"));
		    tabEnt.setItems(list);
		} else {
		    ServiceEntreprise se = new ServiceEntreprise();
		    ObservableList<Entreprise> list = FXCollections.observableArrayList(se.trierMultiTelFirst());
		    nomEnt.setCellValueFactory(new PropertyValueFactory<>("nom"));
		    emailEnt.setCellValueFactory(new PropertyValueFactory<>("email"));
		    cvEnt.setCellValueFactory(new PropertyValueFactory<>("cv"));
		    telEnt.setCellValueFactory(new PropertyValueFactory<>("tel"));
		    etatCompteEnt.setCellValueFactory(new PropertyValueFactory<>("etatCompte"));

		    photoEnt.setCellValueFactory(new PropertyValueFactory<>("photo"));
		    dateEnt.setCellValueFactory(new PropertyValueFactory<>("date"));
		    tabEnt.setItems(list);
		}
	    }

	} else {

	    ServiceEntreprise sa = new ServiceEntreprise();
	    ObservableList<Entreprise> list = FXCollections.observableArrayList(sa.rechercher(rechercherEnt.getText()));
	    nomEnt.setCellValueFactory(new PropertyValueFactory<>("nom"));
	    emailEnt.setCellValueFactory(new PropertyValueFactory<>("email"));
	    cvEnt.setCellValueFactory(new PropertyValueFactory<>("cv"));
	    telEnt.setCellValueFactory(new PropertyValueFactory<>("tel"));
	    etatCompteEnt.setCellValueFactory(new PropertyValueFactory<>("etatCompte"));

	    photoEnt.setCellValueFactory(new PropertyValueFactory<>("photo"));
	    dateEnt.setCellValueFactory(new PropertyValueFactory<>("date"));
	    tabEnt.setItems(list);
	    System.out.println(rechercherEnt.getText() + "test");

	}
    }

    @FXML
    public void affichageTabMembre() {
	if (rechercherMembre.getText() == null || rechercherMembre.getText().trim().isEmpty()) {
	    if (trierNomMembre.isSelected() == false && trierTelMembre.isSelected() == false) {
		ServiceMembre sa = new ServiceMembre();
		ObservableList<Membre> list = FXCollections.observableArrayList(sa.afficher());
		nomMembre.setCellValueFactory(new PropertyValueFactory<>("nom"));
		emailMembre.setCellValueFactory(new PropertyValueFactory<>("email"));
		descMembre.setCellValueFactory(new PropertyValueFactory<>("description"));
		telMembre.setCellValueFactory(new PropertyValueFactory<>("tel"));
		etatCompteMembre.setCellValueFactory(new PropertyValueFactory<>("etatCompte"));

		photoMembre.setCellValueFactory(new PropertyValueFactory<>("photo"));
		dateMembre.setCellValueFactory(new PropertyValueFactory<>("date"));
		tabMembre.setItems(list);
		System.out.println(rechercherEnt.getText() + "test");

	    } else if (trierNomMembre.isSelected() == true) {
		if (trierTelMembre.isSelected() == false) {
		    ServiceEntreprise se = new ServiceEntreprise();
		    ServiceMembre sa = new ServiceMembre();
		    ObservableList<Membre> list = FXCollections.observableArrayList(sa.trier());
		    nomMembre.setCellValueFactory(new PropertyValueFactory<>("nom"));
		    emailMembre.setCellValueFactory(new PropertyValueFactory<>("email"));
		    descMembre.setCellValueFactory(new PropertyValueFactory<>("description"));
		    telMembre.setCellValueFactory(new PropertyValueFactory<>("tel"));
		    etatCompteMembre.setCellValueFactory(new PropertyValueFactory<>("etatCompte"));

		    photoMembre.setCellValueFactory(new PropertyValueFactory<>("photo"));
		    dateMembre.setCellValueFactory(new PropertyValueFactory<>("date"));
		    tabMembre.setItems(list);
		} else {
		    ServiceMembre sa = new ServiceMembre();
		    ObservableList<Membre> list = FXCollections.observableArrayList(sa.trierMulti());
		    nomMembre.setCellValueFactory(new PropertyValueFactory<>("nom"));
		    emailMembre.setCellValueFactory(new PropertyValueFactory<>("email"));
		    descMembre.setCellValueFactory(new PropertyValueFactory<>("description"));
		    telMembre.setCellValueFactory(new PropertyValueFactory<>("tel"));
		    etatCompteMembre.setCellValueFactory(new PropertyValueFactory<>("etatCompte"));

		    photoMembre.setCellValueFactory(new PropertyValueFactory<>("photo"));
		    dateMembre.setCellValueFactory(new PropertyValueFactory<>("date"));
		    tabMembre.setItems(list);

		}
	    } else if (trierTelMembre.isSelected() == true) {
		if (trierNomMembre.isSelected() == false) {
		    ServiceMembre sa = new ServiceMembre();
		    ObservableList<Membre> list = FXCollections.observableArrayList(sa.trierTel());
		    nomMembre.setCellValueFactory(new PropertyValueFactory<>("nom"));
		    emailMembre.setCellValueFactory(new PropertyValueFactory<>("email"));
		    descMembre.setCellValueFactory(new PropertyValueFactory<>("description"));
		    telMembre.setCellValueFactory(new PropertyValueFactory<>("tel"));
		    etatCompteMembre.setCellValueFactory(new PropertyValueFactory<>("etatCompte"));

		    photoMembre.setCellValueFactory(new PropertyValueFactory<>("photo"));
		    dateMembre.setCellValueFactory(new PropertyValueFactory<>("date"));
		    tabMembre.setItems(list);
		} else {
		    ServiceMembre sa = new ServiceMembre();
		    ObservableList<Membre> list = FXCollections.observableArrayList(sa.trierMultiTelFirst());
		    nomMembre.setCellValueFactory(new PropertyValueFactory<>("nom"));
		    emailMembre.setCellValueFactory(new PropertyValueFactory<>("email"));
		    descMembre.setCellValueFactory(new PropertyValueFactory<>("description"));
		    telMembre.setCellValueFactory(new PropertyValueFactory<>("tel"));
		    etatCompteMembre.setCellValueFactory(new PropertyValueFactory<>("etatCompte"));

		    photoMembre.setCellValueFactory(new PropertyValueFactory<>("photo"));
		    dateMembre.setCellValueFactory(new PropertyValueFactory<>("date"));
		    tabMembre.setItems(list);
		}
	    }

	} else {

	    ServiceMembre sa = new ServiceMembre();
	    ObservableList<Membre> list = FXCollections.observableArrayList(sa.rechercher(rechercherMembre.getText()));
	    nomMembre.setCellValueFactory(new PropertyValueFactory<>("nom"));
	    emailMembre.setCellValueFactory(new PropertyValueFactory<>("email"));
	    descMembre.setCellValueFactory(new PropertyValueFactory<>("description"));
	    telMembre.setCellValueFactory(new PropertyValueFactory<>("tel"));
	    etatCompteMembre.setCellValueFactory(new PropertyValueFactory<>("etatCompte"));

	    photoMembre.setCellValueFactory(new PropertyValueFactory<>("photo"));
	    dateMembre.setCellValueFactory(new PropertyValueFactory<>("date"));
	    tabMembre.setItems(list);

	}

    }

    private void modifAdm(ActionEvent event) {
	String nom = nomAdm.getText();
	String tel = telAdmin.getText();
	String email = mailAdm.getText();
	Admin a = new Admin(nom, email, tel);
	ServiceAdmin sU = new ServiceAdmin();
	UserUtiles uUtiles = new UserUtiles();

	if (nom.isEmpty()) {
	    alert_Box("Verifier votre nom", "Votre nom ne doit pas être vide");

	} else if (!uUtiles.testNom(nom)) {
	    alert_Box("Verifier votre nom", "Veuillez mettre un nom valide");

	} else if (!uUtiles.testTel(tel)) {
	    alert_Box("Verifier votre numero telephone", "Veillez mettre un numero de telephone valide");
	} else {
	    sU.modifier(a);
	    uUtiles.information_Box("modification ", "compte modifier avec succes");
	    this.user = sU.loadDataModify(email);
	    showData(this.user);

	}
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

    @FXML
    private void modifPw(ActionEvent event) throws IOException {
	FXMLLoader loader = new FXMLLoader(getClass().getResource("./ModifAdmin.fxml"));
	Parent root = loader.load();
	ModifAdminController HomeScene = loader.getController();
	HomeScene.user = this.user;
	HomeScene.id = id;
	Admin a = this.user;
	HomeScene.iniializeFxml(a);
	HomeScene.showData(a);
	Stage window = (Stage) modifInfo.getScene().getWindow();
	window.setScene(new Scene(root, 800, 800));

    }

    @FXML
    private void logOut(ActionEvent event) throws IOException {
	FXMLLoader loader = new FXMLLoader(getClass().getResource("./Login.fxml"));
	Parent root = loader.load();

	Stage window = (Stage) logOut.getScene().getWindow();
	window.setScene(new Scene(root, 800, 800));
    }

    private void nvPhoto(ActionEvent event) throws IOException {
	UserUtiles uUtiles = new UserUtiles();
	try {
	    JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("image files", "jpeg");
	    chooser.setFileFilter(filter);
	    chooser.showOpenDialog(null);
	    filePhotoAdmin = chooser.getSelectedFile();
	} catch (Exception x) {
	    uUtiles.alert_Box("Warning", "veillez choisir une photo");

	}
    }

    public String copyPhoto() throws IOException, IOException {
	UserUtiles sU = new UserUtiles();
	Date d = new Date();
	String strTimestamp = String.valueOf(d.getTime());
	String randomString = sU.randomString() + "_" + strTimestamp + ".jpeg";
	Path copied = Paths.get(IMG_PATH + randomString);
	Path originalPath = Paths.get(filePhotoAdmin.getAbsolutePath());
	System.out.println(copied);
	Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);
	Image image = new Image((IMG_PATH_LOAD + randomString));
	photoAdmin.setImage(image);
	return randomString;
    }

    private void modifPhoto(ActionEvent event) throws IOException {
	File filePhoto = filePhotoAdmin;
	String mail = mailAdm.getText();
	ServiceUser sUser = new ServiceUser();
	UserUtiles uUtiles = new UserUtiles();

	if (filePhoto == null) {
	    uUtiles.alert_Box("Verifier le cv", "inserer cv");
	}
	String photo = copyPhoto();
	sUser.modifierPhoto(mail, photo);

	uUtiles.information_Box("Changement", "photo modifier avec Succes");

    }

    @FXML
    private void afficherCv(ActionEvent event) throws IOException {
	TableView.TableViewSelectionModel<Entreprise> selectionModel = tabEnt.getSelectionModel();
	String cv = tabEnt.getSelectionModel().getSelectedItem().getCv();
	File file = new File(CV_PATH + cv);
	Desktop desktop = Desktop.getDesktop();
	if (file.exists()) {
	    desktop.open(file);
	}
    }

    @FXML
    private void afficherPhotoEnt(ActionEvent event) throws IOException {

	TableView.TableViewSelectionModel<Entreprise> selectionModel = tabEnt.getSelectionModel();
	String photo = tabEnt.getSelectionModel().getSelectedItem().getPhoto();
	File file = new File(IMG_PATH + photo);
	Desktop desktop = Desktop.getDesktop();
	if (file.exists()) {
	    desktop.open(file);
	}
    }

    @FXML
    private void afficherPhoto() throws IOException {

	TableView.TableViewSelectionModel<Entreprise> selectionModel = tabEnt.getSelectionModel();
	String photo = tabEnt.getSelectionModel().getSelectedItem().getPhoto();
	String pdf = tabEnt.getSelectionModel().getSelectedItem().getCv();
	Image image = new Image((IMG_PATH_LOAD + photo));
	System.out.println(photo);
	pdfEnt.setText(pdf);
	photoEntr.setImage(image);
    }

    @FXML
    private void afficherPhotoMem() throws IOException {

	TableView.TableViewSelectionModel<Membre> selectionModel = tabMembre.getSelectionModel();
	String photo = tabMembre.getSelectionModel().getSelectedItem().getPhoto();
	// String pdf=tabEnt.getSelectionModel().getSelectedItem().getCv();
	Image image = new Image((IMG_PATH_LOAD + photo));
	System.out.println(photo);
	//  pdfEnt.setText(pdf);
	photoMem.setImage(image);
    }

    @FXML
    private void etatCompteEnt(ActionEvent event) throws IOException {
	UserUtiles uUtiles = new UserUtiles();
	ServiceUser sUser = new ServiceUser();
	String mailentreprise = tabEnt.getSelectionModel().getSelectedItem().getEmail();
	String selectionModel = tabEnt.getSelectionModel().getSelectedItem().getEtatCompte();
	String oldEtat = sUser.getEtat(mailentreprise);

	if (!sUser.getEtat(mailentreprise).equals("Inactive")) {
	    sUser.modifierEtatCompte(mailentreprise, etatsEnt.getValue().toString());
	    uUtiles.information_Box("etat compte", "Compte " + sUser.getEtat(mailentreprise));
	}
	affichageTabEntreprise();

    }

    public void iniializeFxml(Admin a) {
	ServiceUser su = new ServiceUser();
	ServiceAdmin sA = new ServiceAdmin();

	System.out.println(a.getPhoto());
	Image image = new Image((IMG_PATH_LOAD + a.getPhoto()));
	System.out.println(a.getPhoto());

	photoAdmin.setImage(image);
	ObservableList etats = FXCollections.observableArrayList(su.afficherEtats());
	etatsEnt.setItems(etats);
	etatsMembre.setItems(etats);

    }

    void showData(Admin a) {

	nomAdm.setText(a.getNom());
	mailAdm.setText(a.getEmail());
	telAdmin.setText(a.getTel());
	usernameLab.setText("bienvenue " + a.getNom());
	mailAdm.setDisable(true);
	nomAdm.setDisable(true);
	telAdmin.setDisable(true);

	Image image = new Image((IMG_PATH_LOAD + a.getPhoto()));

	photoAdmin.setImage(image);
	System.out.println("test");

    }

    @FXML
    private void afficherPhotoMembre(ActionEvent event) throws IOException {

	TableView.TableViewSelectionModel<Membre> selectionModel = tabMembre.getSelectionModel();
	String photo = tabMembre.getSelectionModel().getSelectedItem().getPhoto();
	File file = new File(IMG_PATH + photo);
	Desktop desktop = Desktop.getDesktop();
	if (file.exists()) {
	    desktop.open(file);
	}
    }

    private void restreindreEnt(ActionEvent event) throws IOException {
	UserUtiles uUtiles = new UserUtiles();
	ServiceUser sUser = new ServiceUser();
	String mailentreprise = tabEnt.getSelectionModel().getSelectedItem().getEmail();
	String selectionModel = tabEnt.getSelectionModel().getSelectedItem().getEtatCompte();
	if (selectionModel.equals("Active")) {
	    sUser.modifierEtatCompte(mailentreprise, "Restricted");

	    uUtiles.information_Box("etat compte", "Compte Restreint");
	    affichageTabEntreprise();
	}

    }

    private void suppEnt(ActionEvent event) throws IOException {
	UserUtiles uUtiles = new UserUtiles();
	ServiceUser sUser = new ServiceUser();
	String mailentreprise = tabEnt.getSelectionModel().getSelectedItem().getEmail();
	String selectionModel = tabEnt.getSelectionModel().getSelectedItem().getEtatCompte();
	if (!selectionModel.equals("Inactive")) {
	    sUser.modifierEtatCompte(mailentreprise, "Inactive");

	    uUtiles.information_Box("etat compte", "Compte Supprimé");
	    affichageTabEntreprise();

	}
    }

    @FXML
    private void etatCompteMembre(ActionEvent event) {
	UserUtiles uUtiles = new UserUtiles();
	ServiceUser sUser = new ServiceUser();
	String mailmembre = tabMembre.getSelectionModel().getSelectedItem().getEmail();
	String selectionModel = tabMembre.getSelectionModel().getSelectedItem().getEtatCompte();
	String oldEtat = sUser.getEtat(mailmembre);

	if (!sUser.getEtat(mailmembre).equals("Inactive")) {
	    sUser.modifierEtatCompte(mailmembre, etatsMembre.getValue().toString());
	    uUtiles.information_Box("etat compte", "Compte " + sUser.getEtat(mailmembre));
	}
	affichageTabMembre();
    }

    @FXML
    private void animate(MouseEvent event) throws InterruptedException {
	if (this.user.getEmail().equals("iheb@esprit.tn")) {// photoAdmin.toBack();{
	    // photoAdmin.toFront();
	    String file = "snoop-dogg-smoke-weed-everyday-thug-life.mp3";
	    // String path = getClass().getResource(file).getPath();

	    Media media = new Media(new File(file).toURI().toString());
	    mediaPlayer = new MediaPlayer(media);
	    mediaPlayer.setCycleCount(2);
	    mediaPlayer.play();

	    System.out.println("anim");
	    Image image = new Image((IMG_PATH_LOAD + "ZTcz_.jpeg"));
	    Image image2 = new Image((IMG_PATH_LOAD + "lunettes.png"));
	    Image image3 = new Image((IMG_PATH_LOAD + "crown.jpeg"));
	    crown.setImage(image3);
	    animation.setImage(image2);
	    TranslateTransition translate = new TranslateTransition();
	    translate.setNode(animation);

	    translate.setDuration(Duration.millis(5000));
	    //translate.setByX(250);
	    translate.setByY(-320);
	    translate.play();
	    TranslateTransition translate2 = new TranslateTransition();
	    translate2.setNode(crown);

	    translate2.setDuration(Duration.millis(5000));
	    //translate.setByX(250);
	    translate2.setByX(420);
	    translate2.play();
	    photoAdmin.setImage(image);
	    usernameLab.setDisable(true);
	    //  Thread.sleep(1000);

	    usernameLab.setVisible(false);
	    //    Thread.sleep(1000);

	    usernameLab.setText("a9wa admin");
	    usernameLab.setDisable(true);
	    //  Thread.sleep(1000);
	    usernameLab.setVisible(true);
	    //    Thread.sleep(1000);

	    usernameLab.setDisable(false);

	    //.sleep(2000);
	    //System.out.println(image);
	    //photoAdmin.setImage(image);
	    image = new Image((IMG_PATH_LOAD + "fhpvmpfghb_1646122046718.jpeg"));

	    System.out.println(image);
	    photoAdmin.setImage(image);
	    //Thread.sleep(1000);
	    image = new Image((IMG_PATH_LOAD + "nHj3_99080844.jpeg"));

	    System.out.println(image);
	    photoAdmin.setImage(image);
	    //Thread.sleep(1000);
	}

    }

    @FXML
    private void afficherPhotoAd(MouseEvent event) {
	showData(this.user);
	if (this.user.getEmail().equals("iheb@esprit.tn")) {
	    mediaPlayer.stop();
	}
	animation.setVisible(false);
	crown.setVisible(false);
    }

    @FXML
    private void traiterRec(ActionEvent event) throws IOException {

	Reclamation r = new Reclamation();
	r.setMessage(tabRec.getSelectionModel().getSelectedItem().getMessage());
	r.setNameUser(tabRec.getSelectionModel().getSelectedItem().getNomUser());
	r.setStatus(tabRec.getSelectionModel().getSelectedItem().getStatusComplaint());
	r.setType(tabRec.getSelectionModel().getSelectedItem().getTypeComplaint());
	ServiceReclamation se = new ServiceReclamation();
	r = se.loadDataModify(r);
	//System.out.println(r.getEmail());
	if (r.getStatusComplaint().equals("NON TRAITEE")) {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("./AfficherReclamation.fxml"));

	    Parent root = loader.load();

	    AfficherReclamationController HomeScene = loader.getController();

	    HomeScene.user = this.user;
	    HomeScene.iniializeFxml(r);

	    HomeScene.showData(r);
	    Stage window = (Stage) traiterRec.getScene().getWindow();
	    window.setScene(new Scene(root, 800, 800));
	}
    }

    @FXML
    private void goNFAD(ActionEvent event) throws IOException {

    	ServiceAdmin sem = new ServiceAdmin();

	int conf = GlobalConfig.getInstance().getSession();

	this.user = sem.loadDataModifyId(conf);

	FXMLLoader loader = new FXMLLoader(getClass().getResource("./MainBackOffice.fxml"));
	Parent root = loader.load();
	Stage window = (Stage) btnNFAd.getScene().getWindow();
	window.setScene(new Scene(root, 1920, 1080));
    }

}
