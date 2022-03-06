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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.chart.PieChart;
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
import javafx.stage.Stage;
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
import static tn.nebulagaming.utils.Consts.CV_PATH;
import static tn.nebulagaming.utils.Consts.IMG_PATH;
import static tn.nebulagaming.utils.Consts.IMG_PATH_LOAD;
import tn.nebulagaming.utils.UserUtiles;

/**
 * FXML Controller class
 *
 * @author houba
 */
public class ModifAdminController implements Initializable {

    @FXML
    private Button modifAdm;
    @FXML
    private TextField nomAdm;
    @FXML
    private TextField mailAdm;
    @FXML
    private TextField telAdmin;
    @FXML
    private Button modifPw;
    @FXML
    private Label usernameLab;
    private Hyperlink logOut;
    @FXML
    private PasswordField actualPw;
    @FXML
    private PasswordField newPw;
    @FXML
    private PasswordField cnewPw;
    @FXML
    private Button nvPhoto;
    @FXML
    private Button modifPhoto;
    @FXML
    private ImageView photoAdmin;
    @FXML
    private Label photoName;
    private TableView<Entreprise> tabEnt;
    private TableColumn<Entreprise, String> nomEnt;
    private TableColumn<Entreprise, String> emailEnt;
    private TableColumn<Entreprise, Hyperlink> cvEnt;
    private TableColumn<Entreprise, String> telEnt;
    private TableColumn<Entreprise, String> etatCompteEnt;
    private TableColumn<Entreprise, String> photoEnt;
    private TableColumn<Entreprise, String> dateEnt;
    private TableView<Membre> tabMembre;
    private TableColumn<Membre, String> nomMembre;
    private TableColumn<Membre, String> emailMembre;
    private TableColumn<Membre, String> descMembre;
    private TableColumn<Membre, String> telMembre;
    private TableColumn<Membre, String> etatCompteMembre;
    private TableColumn<Membre, String> photoMembre;
    public Admin user = new Admin();
    public String id;
    private Tab listAdminTab;
    private Tab ajouterAdminTab;
    private CheckBox triNomEnt;
    private TableView<Reclamation> tabRec;
    private TableColumn<Reclamation, String> typeRec;
    private TableColumn<Reclamation, String> etatRec;
    private CheckBox triTelEnt;
    private TextField rechercherEnt;

    File filePhotoAdmin = null;
    private ComboBox<?> etatsEnt;
    private CheckBox trierNomMembre;
    private CheckBox trierTelMembre;
    private ComboBox<?> etatsMembre;
    private TextField rechercherMembre;
    private TableColumn<Membre, String> dateMembre;
    private TableColumn<Reclamation, String> nomUser;
    @FXML
    private Hyperlink retour;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //  tab Entre

        //
    }

    /*
    else if (triNomEnt.isSelected()==true ) { 
        if(triTelEnt.isSelected()==false) {
    ServiceEntreprise se = new ServiceEntreprise();
        ObservableList<Entreprise> list = FXCollections.observableArrayList(se.trier());
        nomEnt.setCellValueFactory(new PropertyValueFactory<>("nom"));
        emailEnt.setCellValueFactory(new PropertyValueFactory<>("email"));
        cvEnt.setCellValueFactory(new PropertyValueFactory<>("cv"));
        telEnt.setCellValueFactory(new PropertyValueFactory<>("tel"));
        etatCompteEnt.setCellValueFactory(new PropertyValueFactory<>("etatCompte"));
        
        photoEnt.setCellValueFactory(new PropertyValueFactory<>("photo"));
        dateEnt.setCellValueFactory(new PropertyValueFactory<>("date"));
        tabEnt.setItems(list); }
        else {
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
    }
    else if ( triTelEnt.isSelected()==true) { 
        if(triNomEnt.isSelected()==false) {
    ServiceEntreprise se = new ServiceEntreprise();
        ObservableList<Entreprise> list = FXCollections.observableArrayList(se.trierTel());
        nomEnt.setCellValueFactory(new PropertyValueFactory<>("nom"));
        emailEnt.setCellValueFactory(new PropertyValueFactory<>("email"));
        cvEnt.setCellValueFactory(new PropertyValueFactory<>("cv"));
        telEnt.setCellValueFactory(new PropertyValueFactory<>("tel"));
        etatCompteEnt.setCellValueFactory(new PropertyValueFactory<>("etatCompte"));
        
        photoEnt.setCellValueFactory(new PropertyValueFactory<>("photo"));
        dateEnt.setCellValueFactory(new PropertyValueFactory<>("date"));
        tabEnt.setItems(list);}
        else {
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
 
        }
         else {
             
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
        System.out.println(rechercherEnt.getText()+ "test");
        
    
    
    
    
    }*/
    @FXML
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

        ServiceUser sUser = new ServiceUser();
        UserUtiles uUser = new UserUtiles();
        String actualmdp = actualPw.getText();
        String newMdp = newPw.getText();
        String cnewMdp = cnewPw.getText();
        String mail = mailAdm.getText();

        if (!sUser.verifierPwBd(uUser.crypterPassword(actualmdp))) {
            uUser.alert_Box("verifier mot de passe", "veillez verifier le mot de passe introduit");
        } else if (!uUser.testPassword(newMdp)) {
            uUser.alert_Box("Verifier mot de passe", "Votre mot de passe doit doit contenir au moins une une majuscule et un chiffre ");
        } else if (!newMdp.equals(cnewMdp)) {
            uUser.alert_Box("Verifier mot de passe", "Veillez verifier votre mot de passe ");
        } else {
            sUser.modifierPassword(mail, uUser.crypterPassword(newMdp));
            uUser.information_Box("mot de passe", "mot de passe changer avec succes");
            actualPw.setText("");
            cnewPw.setText("");
            newPw.setText("");
        }
    }

    private void logOut(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./Login.fxml"));
        Parent root = loader.load();

        Stage window = (Stage) logOut.getScene().getWindow();
        window.setScene(new Scene(root, 800, 800));
    }

    @FXML
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

    @FXML
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

    public void iniializeFxml(Admin a) {
        ServiceUser su = new ServiceUser();
        ServiceAdmin sA = new ServiceAdmin();

        System.out.println(a.getPhoto());
        Image image = new Image((IMG_PATH_LOAD + a.getPhoto()));
        System.out.println(a.getPhoto());

        photoAdmin.setImage(image);

    }

    void showData(Admin a) {

        nomAdm.setText(a.getNom());
        mailAdm.setText(a.getEmail());
        telAdmin.setText(a.getTel());
        usernameLab.setText("bienvenue " + a.getNom());
        mailAdm.setDisable(true);

        Image image = new Image((IMG_PATH_LOAD + a.getPhoto()));

        photoAdmin.setImage(image);
        System.out.println("test");

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
