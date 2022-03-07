/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.nebulagaming.models.Admin;
import tn.nebulagaming.models.Entreprise;
import tn.nebulagaming.models.Membre;
import tn.nebulagaming.services.ServiceAdmin;
import tn.nebulagaming.services.ServiceEntreprise;
import tn.nebulagaming.services.ServiceMembre;
import tn.nebulagaming.services.ServiceUser;
import static tn.nebulagaming.utils.Constantes.IMG_PATH_LOAD;
import tn.nebulagaming.utils.UserUtiles;

/**
 * FXML Controller class
 *
 * @author houba
 */
public class LoginController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField mailLog;
    @FXML
    private Button inscriMembre;
    @FXML
    private Button inscriEnt;
    @FXML
    private Button connectLog;
    @FXML
    private Button pwLog;
    @FXML
    private PasswordField pwdLog;
    @FXML
    private Label label_adresse;

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
    
    public void goToHomeAdmin(String id) throws Exception {
        Admin a = new Admin();
        ServiceAdmin sa = new ServiceAdmin();        
        a = sa.loadDataModify(id);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("./AdminHome.fxml"));
        Parent root = loader.load();
        AdminHomeController HomeScene = loader.getController();
        HomeScene.user = a;
        HomeScene.id=id;
        HomeScene.iniializeFxml(a);
        HomeScene.showData(a);
        Stage window = (Stage) connectLog.getScene().getWindow();
        window.setScene(new Scene(root, 800, 800));

    }
public void goToHomeEntreprise(String id) throws Exception {
        Entreprise e = new Entreprise();
        ServiceEntreprise se = new ServiceEntreprise();
        e = se.loadDataModify(id);
        System.out.println(e.getEmail());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("./EntrepriseHome.fxml"));

        Parent root = loader.load();

        EntrepriseHomeController HomeScene = loader.getController();

        HomeScene.user1 = e;
        HomeScene.iniializeFxml(e);

        HomeScene.showData(e);
        Stage window = (Stage) connectLog.getScene().getWindow();
        window.setScene(new Scene(root, 800, 800));

    }
public void goToHomeMembre(String id) throws Exception {
        Membre e = new Membre();
        ServiceMembre se = new ServiceMembre();
        e = se.loadDataModify(id);
        System.out.println(e.getEmail());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("./MembreHome.fxml"));

        Parent root = loader.load();

        MembreHomeController HomeScene = loader.getController();

        HomeScene.user2 = e;
        HomeScene.iniializeFxml(e);

        HomeScene.showData(e);
        Stage window = (Stage) connectLog.getScene().getWindow();
        window.setScene(new Scene(root, 800, 800));

    }

    @FXML
    private void loadInscriMembre(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("./CreatAccountMembre.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    private void loadInscriEnt(ActionEvent event) throws IOException {
          AnchorPane pane = FXMLLoader.load(getClass().getResource("./CreatAccountEntreprise.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    @FXML
    private void connectLog(ActionEvent event) throws Exception {
        UserUtiles uUtiles = new UserUtiles();
        ServiceUser sU = new ServiceUser();
        String username = mailLog.getText();
        String password = uUtiles.crypterPassword(pwdLog.getText());

        String role = sU.verifierData(username, password);
        int etat = sU.verifEtatCompte(username);
        if (role.equals("")){
            uUtiles.alert_Box("Verification", "Veillez verifier vos cordonée");
        } else if (etat != 0) {
            uUtiles.alert_Box("etat compte", "Votre compte est désactivé, Veillez contacter l'administrateur");
        } else if (role.equals("Admin")  ) { 
            goToHomeAdmin(username);

        } else if (role.equals("Membre")) {
           goToHomeMembre(username);

        } else if (role.equals("Entreprise")) {
            goToHomeEntreprise(username);

        

    }
    }

    @FXML
    private void pwLog(ActionEvent event) throws IOException {
         AnchorPane pane = FXMLLoader.load(getClass().getResource("./ResetPassword.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
}
