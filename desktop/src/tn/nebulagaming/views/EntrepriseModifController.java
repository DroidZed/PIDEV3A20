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
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import tn.nebulagaming.models.Entreprise;
import tn.nebulagaming.services.ServiceEntreprise;
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
public class EntrepriseModifController implements Initializable {

    @FXML
    private Button modifEnt;
    @FXML
    private TextField nomEnt;
    @FXML
    private TextField mailEnt;
    @FXML
    private TextField telEnt;
    @FXML
    private Button modifPw;
    @FXML
    private Label usernameEnt;
    @FXML
    private Hyperlink logOut;
    @FXML
    private PasswordField actualPw;
    @FXML
    private PasswordField newPw;
    @FXML
    private PasswordField cnewPw;
    @FXML
    private Label cvEnt;
    @FXML
    private Button nvCv;
    @FXML
    private Button modifCv;
    @FXML
    private Button nvPhoto;
    @FXML
    private Button modifPhoto;
    @FXML
    private ImageView photoEnt;
    @FXML
    private Label photoName;
   public Entreprise user1=new Entreprise();
       File filePhotoEnt = null;
 File fileCvEnt = null;
    String id;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
 public void iniializeFxml(Entreprise e) {
        System.out.println(e.getNom());
        System.out.println(e.getTel());

        Image image = new Image(IMG_PATH_LOAD + e.getPhoto());
        System.out.println(IMG_PATH_LOAD);
        ImageView iv1 = new ImageView();
        photoEnt.setImage(image);
        

    }
    @FXML
    private void modifEnt(ActionEvent event) {
        String nom = nomEnt.getText();
        String tel = telEnt.getText();
        String email = mailEnt.getText();
   
        
        Entreprise e = new Entreprise(nom, email, tel,this.user1.getCv());
        ServiceEntreprise sU = new ServiceEntreprise();
        UserUtiles uUtiles = new UserUtiles();
        if (nom.isEmpty()) {
            uUtiles.alert_Box("Verifier votre nom", "Votre nom ne doit pas être vide");

        } else if (!uUtiles.testNom(nom)) {
            uUtiles.alert_Box("Verifier votre nom", "Veuillez mettre un nom valide");

        } else if (!uUtiles.testTel(tel)) {
            uUtiles.alert_Box("Verifier votre numero telephone", "Veillez mettre un numero de telephone valide");
        } else {
            sU.modifier(e);
            uUtiles.information_Box("modification ", "compte modifier avec succes");
            this.user1 = sU.loadDataModify(email);
            showData(this.user1);
        }
    }

    @FXML
    private void modifPw(ActionEvent event) {
        ServiceUser sUser = new ServiceUser();
        UserUtiles uUser = new UserUtiles();
        String actualmdp = actualPw.getText();
        String newMdp = newPw.getText();
        String cnewMdp = cnewPw.getText();
        String mail = mailEnt.getText();

        if (!sUser.verifierPwBd(uUser.crypterPassword(actualmdp))) {
            uUser.alert_Box("verifiez mot de passe", "veillez verifier le mot de passe introduit");
        } else if (!uUser.testPassword(newMdp)) {
            uUser.alert_Box("Verifiez mot de passe", "Votre mot de passe doit doit contenir au moins une une majuscule et un chiffre ");
        } else if (!newMdp.equals(cnewMdp)) {
            uUser.alert_Box("Verifiez mot de passe", "Veillez verifier votre mot de passe ");
        } else {
            sUser.modifierPassword(mail, uUser.crypterPassword(newMdp));
            uUser.information_Box("mot de passe", "mot de passe changé avec succes");
        }
    }

    @FXML
    private void logOut(ActionEvent event) throws IOException {
     
       FXMLLoader loader = new FXMLLoader(getClass().getResource("./EntrepriseHome.fxml"));
        Parent root = loader.load();
        EntrepriseHomeController HomeScene = loader.getController();
        HomeScene.user1 = this.user1;
        HomeScene.id=id;
        Entreprise a= this.user1;
        HomeScene.iniializeFxml(a);
        HomeScene.showData(a);
        Stage window = (Stage) logOut.getScene().getWindow();
        window.setScene(new Scene(root, 800, 800));
    }
    

    @FXML
    private void nvCv(ActionEvent event) {
        UserUtiles uUtiles = new UserUtiles();
        try {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("pdf files", "pdf");
            chooser.setFileFilter(filter);
            chooser.showOpenDialog(null);
            fileCvEnt = chooser.getSelectedFile();
            cvEnt.setText(fileCvEnt.getName());
        } catch (Exception x) {
            uUtiles.alert_Box("Warning", "veillez choisir un cv");

        }
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

    @FXML
    private void modifCv(ActionEvent event) throws IOException {

        File fileCv = fileCvEnt;
        String mail = mailEnt.getText();
        ServiceEntreprise sEtu = new ServiceEntreprise();
        UserUtiles uUtiles = new UserUtiles();

        if (fileCv == null) {
            uUtiles.alert_Box("Verifier le cv", "inserez cv");
        }
        String cv = copyCV();
        sEtu.modifierCv(mail, cv);

        uUtiles.information_Box("Changement", "CV modifié avec Succes");

    }

    @FXML
    private void modifPhoto(ActionEvent event) throws IOException {
        File filePhoto = filePhotoEnt;
        String mail = mailEnt.getText();
        ServiceUser sUser = new ServiceUser();
        UserUtiles uUtiles = new UserUtiles();

        if (filePhoto == null) {
            uUtiles.alert_Box("Verifiez le cv", "inserez cv");
        }
        String photo = copyPhoto();
        sUser.modifierPhoto(mail, photo);

        uUtiles.information_Box("Changement", "photo modifié avec Succes");
        showData(this.user1);
        Image image = new Image((IMG_PATH_LOAD + this.user1.getPhoto()));
        ServiceEntreprise sE=new ServiceEntreprise();
         this.user1 = sE.loadDataModify(mailEnt.getText());
        photoEnt.setImage(image);
        showData(this.user1);
    }

    @FXML
    private void nvPhoto(ActionEvent event) {
        UserUtiles uUtiles = new UserUtiles();
        try {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("image files", "jpeg");
            chooser.setFileFilter(filter);
            chooser.showOpenDialog(null);
            filePhotoEnt= chooser.getSelectedFile();
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
        Path originalPath = Paths.get(filePhotoEnt.getAbsolutePath());
        Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);
        showData(this.user1);
        return randomString;

    }

  
    void showData(Entreprise e) {
        nomEnt.setText(e.getNom());
        mailEnt.setText(e.getEmail());
        telEnt.setText(e.getTel());
        System.out.println(e.getTel());
        cvEnt.setText(e.getCv());
        usernameEnt.setText("bienvenue " + e.getNom());
        mailEnt.setDisable(true);
          Image image = new Image((IMG_PATH_LOAD + e.getPhoto()));
        
         
        photoEnt.setImage(image);
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
