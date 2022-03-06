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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.mail.Service;
import tn.nebulagaming.models.Entreprise;
import tn.nebulagaming.models.Membre;
import tn.nebulagaming.models.Streaming;
import tn.nebulagaming.services.ServiceMembre;

/**
 * FXML Controller class
 *
 * @author houba
 */
public class StreamerController implements Initializable {

    Membre user2;
    String id;
    @FXML
    private Button ajouterS;
    @FXML
    private TextField linkS;
    @FXML
    private TextField descS;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    void iniializeFxml(Membre a) {
        
    }

    void showData(Membre a) {
        
    }

    @FXML
    private void ajouterS(ActionEvent event) throws IOException {
        ServiceMembre se=new ServiceMembre();
        Streaming s=new Streaming();
        s.setDescription(descS.getText());
        s.setLink(linkS.getText());
        se.ajouterStram(s);
          FXMLLoader loader = new FXMLLoader(getClass().getResource("./StreamHome.fxml"));
        Parent root = loader.load();
        StreamHomeController HomeScene = loader.getController();
      
        HomeScene.iniializeFxml(s);
        HomeScene.showData(s);
        Stage window = (Stage) ajouterS.getScene().getWindow();
        window.setScene(new Scene(root, 800, 800));
    }
        
        
    
    
}
