/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tn.nebulagaming.views;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import tn.nebulagaming.models.Membre;
import tn.nebulagaming.models.Reclamation;
import tn.nebulagaming.models.Streaming;
import tn.nebulagaming.services.ServiceChat;
import tn.nebulagaming.services.ServiceMembre;
import tn.nebulagaming.utils.GlobalConfig;

/**
 * FXML Controller class
 *
 * @author houba
 */
public class StreamHomeController implements Initializable  {

    @FXML
    private WebView web;
    Streaming stream;
    public Label chat;
    public TextField message;
    private TableColumn<String, String> messageT;
    @FXML
    private Label nbVu;
    Membre user2;
    String id;
    @FXML
    private Button retour;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
      
          ServiceMembre se=new ServiceMembre();
          List<String> listCritereEn;
        listCritereEn = Arrays.asList("message");
        ObservableList<String> listCritereEnt;
        listCritereEnt = FXCollections.observableArrayList(listCritereEn);
      
         ServiceChat sc=new ServiceChat();
            
      
      
    }    

    void iniializeFxml(Streaming s,Membre a) throws InterruptedException {
        stream=s;
        this.user2=a;
        ServiceMembre se=new ServiceMembre();
        System.out.println(stream.getLink());
          final WebEngine Web=web.getEngine();
        String urlweb=stream.getLink();
        Web.load(urlweb);
      
            ServiceChat sc=new ServiceChat();
            String nb=se.getVue(s);
            System.out.println(nb);
nbVu.setText(nb);
           
        
    }
    @FXML
    void afficherChat() throws InterruptedException
    {
        ServiceMembre se=new ServiceMembre();
        String message=se.afficherM();

        //chat.setText(se.afficherM());
       
        ServiceChat sc=new ServiceChat();
      
        
        
        
    }

    void showData(Streaming s) {
        
    }
    

    private void envoyerm(ActionEvent event) {
        ServiceMembre sm =new ServiceMembre();
        sm.envoyerM(message.getText());
    }

    @FXML
    private void retour(ActionEvent event) throws IOException {
        ServiceMembre sm=new ServiceMembre();
        if(stream.getIdUser()==GlobalConfig.getInstance().getSession())
        { sm.finishStream(stream); }
        else
            sm.reduireVu(stream);
         Membre a= this.user2;
         System.out.println(this.user2.getNom());
         FXMLLoader loader = new FXMLLoader(getClass().getResource("./Streamer.fxml"));
        Parent root = loader.load();
        StreamerController HomeScene = loader.getController();
        HomeScene.user2 = this.user2;
        
        HomeScene.id=id;
       
        HomeScene.iniializeFxml(a);
        HomeScene.showData(a);
        Stage window = (Stage) retour.getScene().getWindow();
        window.setScene(new Scene(root, 800, 800));
    }

    void iniializeFxml(Membre a, Streaming s) {
        stream=s;
        this.user2=a;
        ServiceMembre se=new ServiceMembre();
        System.out.println(stream.getLink());
          final WebEngine Web=web.getEngine();
        String urlweb=stream.getLink();
        Web.load(urlweb);
      
        //    ServiceChat sc=new ServiceChat();
            String nb=se.getVue(s);
            System.out.println(nb);
nbVu.setText(nb);
    }

    @FXML
    private void iniializeFxml(MouseEvent event) {
        ServiceMembre se=new ServiceMembre();
           String nb=se.getVue(stream);
            System.out.println(nb);
nbVu.setText(nb);
    }

    
}
