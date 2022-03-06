/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tn.nebulagaming.views;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tn.nebulagaming.models.Entreprise;
import tn.nebulagaming.models.Membre;
import tn.nebulagaming.models.Reclamation;
import tn.nebulagaming.models.Streaming;
import tn.nebulagaming.services.ServiceMembre;
import tn.nebulagaming.services.ServiceReclamation;
import tn.nebulagaming.utils.GlobalConfig;
import static tn.nebulagaming.utils.Consts.IMG_PATH_LOAD;

/**
 * FXML Controller class
 *
 * @author houba
 */
public class MembreHomeController implements Initializable {

    @FXML
    private TextField nomMembre;
    @FXML
    private TextField mailMembre;
    @FXML
    private TextField telMembre;
    @FXML
    private Button modifInfo;
    @FXML
    private Label usernameLab;
    @FXML
    private Hyperlink logOut;
    @FXML
    private ImageView photoMembre;
    @FXML
    private Label photoName;
    @FXML
    private TableView<Reclamation> tabRec;
    @FXML
    private TableColumn<Reclamation, String> nomUser;
    @FXML
    private TableColumn<Reclamation, String> etatRec;
    @FXML
    private TableColumn<Reclamation, String> typeRec;
    @FXML
    private TextField rechercherRec;
    @FXML
    private CheckBox triNomRec;
    @FXML
    private CheckBox triTypeRec;
    @FXML
    private CheckBox triEtatRec;
public Membre user2;
String id;
    @FXML
    private TextField descr;
    @FXML
    private Button passerRec;
    @FXML
    private TableColumn<Reclamation, String> rep;
    @FXML
    private TableColumn<Reclamation, String> message;
    @FXML
    private TableView<Streaming> tabStream;
    @FXML
    private TableColumn<Streaming, String> userStream;
    @FXML
    private TableColumn<Streaming, String> descStream;
    @FXML
    private TableColumn<Streaming, String> vuStream;
    @FXML
    private Button stream;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
          List<String> listCritereRec;
        listCritereRec = Arrays.asList("nomUser", "statusComplaint", "typeComplaint","message","answerComplaint");
        ObservableList<String> listCritereR;
        listCritereR = FXCollections.observableArrayList(listCritereRec);
        affichageTabRec();
        
        
        List<String> listCritereStream;
        listCritereStream = Arrays.asList("nomUser", "description", "link","nbVu");
        ObservableList<String> listCritereS;
        listCritereS = FXCollections.observableArrayList(listCritereRec);
        affichageTabStreaming();
    }    
 public void iniializeFxml(Membre e) {
        System.out.println(e.getNom());
        System.out.println(e.getTel());

        Image image = new Image(IMG_PATH_LOAD + e.getPhoto());
        System.out.println(IMG_PATH_LOAD);
        ImageView iv1 = new ImageView();
        photoMembre.setImage(image);
        

    }
    @FXML
   public void affichageTabRec() {
     if(rechercherRec.getText()==null || rechercherRec.getText().trim().isEmpty()) {
         if(triNomRec.isSelected()==false && triTypeRec.isSelected()==false && triEtatRec.isSelected()==false)
         {ServiceReclamation sa = new ServiceReclamation();
        ObservableList<Reclamation> list = FXCollections.observableArrayList(sa.afficher());
        nomUser.setCellValueFactory(new PropertyValueFactory<>("nomUser"));
        etatRec.setCellValueFactory(new PropertyValueFactory<>("statusComplaint"));
        typeRec.setCellValueFactory(new PropertyValueFactory<>("typeComplaint"));
                message.setCellValueFactory(new PropertyValueFactory<>("message"));
//System.out.println(Connexion.getSession());
        rep.setCellValueFactory(new PropertyValueFactory<>("answerComplaint"));

        
        tabRec.setItems(list);
     }
         else if( triTypeRec.isSelected()==true && triEtatRec.isSelected()==false)
         {
             ServiceReclamation sa = new ServiceReclamation();
        ObservableList<Reclamation> list = FXCollections.observableArrayList(sa.trierType());
        nomUser.setCellValueFactory(new PropertyValueFactory<>("nomUser"));
        etatRec.setCellValueFactory(new PropertyValueFactory<>("statusComplaint"));
        typeRec.setCellValueFactory(new PropertyValueFactory<>("typeComplaint"));
                message.setCellValueFactory(new PropertyValueFactory<>("message"));
//System.out.println(Connexion.getSession());
        rep.setCellValueFactory(new PropertyValueFactory<>("answerComplaint"));

        
        tabRec.setItems(list);
         }
         else if(triTypeRec.isSelected()==false && triEtatRec.isSelected()==true)
                 {
                      ServiceReclamation sa = new ServiceReclamation();
        ObservableList<Reclamation> list = FXCollections.observableArrayList(sa.trierEtat());
        nomUser.setCellValueFactory(new PropertyValueFactory<>("nomUser"));
        etatRec.setCellValueFactory(new PropertyValueFactory<>("statusComplaint"));
        typeRec.setCellValueFactory(new PropertyValueFactory<>("typeComplaint"));
                message.setCellValueFactory(new PropertyValueFactory<>("message"));
//System.out.println(Connexion.getSession());
        rep.setCellValueFactory(new PropertyValueFactory<>("answerComplaint"));

        
        tabRec.setItems(list);
                 }
         else if(triTypeRec.isSelected()==true && triEtatRec.isSelected()==true)
                 {
                      ServiceReclamation sa = new ServiceReclamation();
        ObservableList<Reclamation> list = FXCollections.observableArrayList(sa.trierMulti());
        nomUser.setCellValueFactory(new PropertyValueFactory<>("nomUser"));
        etatRec.setCellValueFactory(new PropertyValueFactory<>("statusComplaint"));
        typeRec.setCellValueFactory(new PropertyValueFactory<>("typeComplaint"));
                message.setCellValueFactory(new PropertyValueFactory<>("message"));
//System.out.println(Connexion.getSession());
        rep.setCellValueFactory(new PropertyValueFactory<>("answerComplaint"));

        
        tabRec.setItems(list);
                 }
     }
     else{
          ServiceReclamation sa = new ServiceReclamation();
        ObservableList<Reclamation> list = FXCollections.observableArrayList(sa.rechercher(rechercherRec.getText()));
        nomUser.setCellValueFactory(new PropertyValueFactory<>("nomUser"));
        etatRec.setCellValueFactory(new PropertyValueFactory<>("statusComplaint"));
        typeRec.setCellValueFactory(new PropertyValueFactory<>("typeComplaint"));
                message.setCellValueFactory(new PropertyValueFactory<>("message"));
//System.out.println(Connexion.getSession());
        rep.setCellValueFactory(new PropertyValueFactory<>("answerComplaint"));

        
        tabRec.setItems(list);
     }
    
        System.out.println( "  test");
    }
  void showData(Membre e) {
          nomMembre.setText(e.getNom());
        mailMembre.setText(e.getEmail());
        telMembre.setText(e.getTel());
        usernameLab.setText("bienvenue " + e.getNom());
        mailMembre.setDisable(true);
        nomMembre.setDisable(true);
        telMembre.setDisable(true);
        descr.setText(e.getDescription());
        descr.setDisable(true);
        
        Image image = new Image((IMG_PATH_LOAD + e.getPhoto()));
        
        photoMembre.setImage(image);
        System.out.println("test");
    }
    @FXML
    private void modifPw(ActionEvent event) throws IOException {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("./MembreModif.fxml"));
        Parent root = loader.load();
        MembreModifController HomeScene = loader.getController();
        HomeScene.user2 = this.user2;
        HomeScene.id=id;
        Membre a= this.user2;
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

    @FXML
    private void passeRec(ActionEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("./passerReclamation.fxml"));
        Parent root = loader.load();
        PasserReclamationController HomeScene = loader.getController();
        HomeScene.user2 = this.user2;
        HomeScene.id=id;
        Membre a= this.user2;
        HomeScene.iniializeFxml(a);
        HomeScene.showData(a);
        Stage window = (Stage) modifInfo.getScene().getWindow();
        window.setScene(new Scene(root, 800, 800));
    }

    private void affichageTabStreaming() {
       ServiceMembre sm=new ServiceMembre();
        ObservableList<Streaming> list = FXCollections.observableArrayList(sm.afficherStreamers());
        userStream.setCellValueFactory(new PropertyValueFactory<>("nomUser"));
        descStream.setCellValueFactory(new PropertyValueFactory<>("description"));
        vuStream.setCellValueFactory(new PropertyValueFactory<>("nbVu"));
               

        
        tabStream.setItems(list);
        
    }

    @FXML
    private void streamer(ActionEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("./Streamer.fxml"));
        Parent root = loader.load();
        StreamerController HomeScene = loader.getController();
        HomeScene.user2 = this.user2;
        HomeScene.id=id;
        Membre a= this.user2;
        HomeScene.iniializeFxml(a);
        HomeScene.showData(a);
        Stage window = (Stage) modifInfo.getScene().getWindow();
        window.setScene(new Scene(root, 800, 800));
        
    }
    
}
