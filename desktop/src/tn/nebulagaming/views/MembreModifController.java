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
import tn.nebulagaming.models.Membre;
import tn.nebulagaming.services.ServiceEntreprise;
import tn.nebulagaming.services.ServiceMembre;
import tn.nebulagaming.services.ServiceUser;
import static tn.nebulagaming.utils.Consts.IMG_PATH;
import static tn.nebulagaming.utils.Consts.IMG_PATH_LOAD;
import tn.nebulagaming.utils.UserUtiles;

/**
 * FXML Controller class
 *
 * @author houba
 */
public class MembreModifController implements Initializable {

    Membre user2;
    String id;
    @FXML
    private Button modifMembre;
    @FXML
    private TextField nomMembre;
    @FXML
    private TextField mailMembre;
    @FXML
    private TextField telMembre;
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
    private Button nvPhoto;
    @FXML
    private Button modifPhoto;
    @FXML
    private ImageView photoMembre;
    @FXML
    private Label photoName;
    @FXML
    private TextField descr;
File filePhotoMembre = null;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

  public void iniializeFxml(Membre e) {
        System.out.println(e.getNom());
        System.out.println(e.getTel());

        Image image = new Image(IMG_PATH_LOAD + e.getPhoto());
        System.out.println(IMG_PATH_LOAD);
        ImageView iv1 = new ImageView();
        photoMembre.setImage(image);
        

    }
    void showData(Membre e) {
          nomMembre.setText(e.getNom());
        mailMembre.setText(e.getEmail());
        telMembre.setText(e.getTel());
      //  usernameLab.setText("bienvenue " + e.getNom());
        mailMembre.setDisable(true);
       
        descr.setText(e.getDescription());
        Image image = new Image((IMG_PATH_LOAD + e.getPhoto()));
        
        photoMembre.setImage(image);
        System.out.println("test");
    }

    @FXML
    private void modifMembre(ActionEvent event) {
        
         String nom = nomMembre.getText();
        String tel = telMembre.getText();
        String email = mailMembre.getText();
   String desc=descr.getText();
        
        Membre e = new Membre(nom, email, tel,desc);
        ServiceMembre sU = new ServiceMembre();
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
            this.user2 = sU.loadDataModify(email);
            showData(this.user2);
        }
    }

    @FXML
    private void modifPw(ActionEvent event) {
         ServiceUser sUser = new ServiceUser();
        UserUtiles uUser = new UserUtiles();
        String actualmdp = actualPw.getText();
        String newMdp = newPw.getText();
        String cnewMdp = cnewPw.getText();
        String mail = mailMembre.getText();

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
         FXMLLoader loader = new FXMLLoader(getClass().getResource("./MembreHome.fxml"));
        Parent root = loader.load();
        MembreHomeController HomeScene = loader.getController();
        HomeScene.user2 = this.user2;
        HomeScene.id=id;
        Membre a= this.user2;
        HomeScene.iniializeFxml(a);
        HomeScene.showData(a);
        Stage window = (Stage) logOut.getScene().getWindow();
        window.setScene(new Scene(root, 800, 800));
    }

    @FXML
    private void nvPhoto(ActionEvent event) {
         UserUtiles uUtiles = new UserUtiles();
        try {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("image files", "jpeg");
            chooser.setFileFilter(filter);
            chooser.showOpenDialog(null);
              filePhotoMembre = chooser.getSelectedFile();
        } catch (Exception x) {
            uUtiles.alert_Box("Warning", "veillez choisir une photo");

        }
    }

    @FXML
    private void modifPhoto(ActionEvent event) throws IOException {
        //File filePhotoMembre = null;
           File filePhoto = filePhotoMembre;
        String mail = mailMembre.getText();
        ServiceUser sUser = new ServiceUser();
        UserUtiles uUtiles = new UserUtiles();

        if (filePhoto == null) {
            uUtiles.alert_Box("Verifiez le cv", "inserez cv");
        }
        String photo = copyPhoto();
        sUser.modifierPhoto(mail, photo);

        uUtiles.information_Box("Changement", "photo modifié avec Succes");
        showData(this.user2);
        Image image = new Image((IMG_PATH_LOAD + this.user2.getPhoto()));
        ServiceMembre sE=new ServiceMembre();
         this.user2= sE.loadDataModify(mailMembre.getText());
        photoMembre.setImage(image);
        showData(this.user2);
    }
     public String copyPhoto() throws IOException, IOException {
        UserUtiles sU = new UserUtiles();
        Date d = new Date();
        String strTimestamp = String.valueOf(d.getTime());
        String randomString = sU.randomString() + "_" + strTimestamp + ".jpeg";
        Path copied = Paths.get(IMG_PATH + randomString);
        Path originalPath = Paths.get(filePhotoMembre.getAbsolutePath());
        Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);
        showData(this.user2);
        return randomString;

    }
}
