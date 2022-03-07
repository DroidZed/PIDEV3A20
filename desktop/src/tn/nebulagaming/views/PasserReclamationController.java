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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.nebulagaming.models.Entreprise;
import tn.nebulagaming.models.Membre;
import tn.nebulagaming.models.Reclamation;
import tn.nebulagaming.models.User;
import tn.nebulagaming.services.ServiceReclamation;
import tn.nebulagaming.services.ServiceUser;

/**
 * FXML Controller class
 *
 * @author houba
 */
public class PasserReclamationController implements Initializable {

    @FXML
    private ComboBox<?> typeRec;
    @FXML
    private TextField message;
    @FXML
    private Button envoyerRec;
    Membre user2;
    String id;
String mail;
    Entreprise user1;
    @FXML
    private Hyperlink retour;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void envoyer(ActionEvent event) {
        
        String Message=message.getText();
        System.out.println(Message);
        ServiceUser su=new ServiceUser();
        Reclamation r=new Reclamation(su.getId(mail),typeRec.getValue().toString(),Message);
        ServiceReclamation sr=new ServiceReclamation();
        sr.ajouter(r);
        
    }

    void iniializeFxml(Membre e ) {
        ServiceReclamation sr=new ServiceReclamation();
        ObservableList etats = FXCollections.observableArrayList(sr.afficherTypeDb());
        typeRec.setItems(etats);
        user1=null;
        
    }
       void iniializeFxml(Entreprise e ) {
        ServiceReclamation sr=new ServiceReclamation();
        ObservableList etats = FXCollections.observableArrayList(sr.afficherTypeDb());
        typeRec.setItems(etats);
        user2=null;
        
    }

    void showData(Membre a) {
           ServiceReclamation sr=new ServiceReclamation();
        ObservableList etats = FXCollections.observableArrayList(sr.afficherTypeDb());
        typeRec.setItems(etats);
        mail=this.user2.getEmail();
    }
    void showData(Entreprise a) {
           ServiceReclamation sr=new ServiceReclamation();
        ObservableList etats = FXCollections.observableArrayList(sr.afficherTypeDb());
        typeRec.setItems(etats);
        mail=this.user1.getEmail();
    }

    @FXML
    private void retour(ActionEvent event) throws IOException {
        if(user2!=null){
         FXMLLoader loader = new FXMLLoader(getClass().getResource("./MembreHome.fxml"));
        Parent root = loader.load();
        MembreHomeController HomeScene = loader.getController();
        HomeScene.user2 = this.user2;
        HomeScene.id=id;
        Membre a= this.user2;
        HomeScene.iniializeFxml(a);
        HomeScene.showData(a);
        Stage window = (Stage) retour.getScene().getWindow();
        window.setScene(new Scene(root, 800, 800));}
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("./EntrepriseHome.fxml"));
        Parent root = loader.load();
        EntrepriseHomeController HomeScene = loader.getController();
        HomeScene.user1 = this.user1;
        HomeScene.id=id;
        Entreprise a= this.user1;
        HomeScene.iniializeFxml(a);
        HomeScene.showData(a);
        Stage window = (Stage) retour.getScene().getWindow();
        window.setScene(new Scene(root, 800, 800));
        }
    }
    
}
