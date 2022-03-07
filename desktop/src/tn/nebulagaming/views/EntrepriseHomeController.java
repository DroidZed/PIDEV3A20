/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tn.nebulagaming.views;

import java.awt.Desktop;
import java.io.File;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import tn.nebulagaming.models.Admin;
import tn.nebulagaming.models.Entreprise;
import tn.nebulagaming.models.Membre;
import tn.nebulagaming.models.Reclamation;
import tn.nebulagaming.services.ServiceReclamation;
import tn.nebulagaming.utils.GlobalConfig;
import static tn.nebulagaming.utils.Constantes.CV_PATH;
import static tn.nebulagaming.utils.Constantes.IMG_PATH_LOAD;
import tn.nebulagaming.utils.UserUtiles;


/**
 * FXML Controller class
 *
 * @author houba
 */
public class EntrepriseHomeController implements Initializable {
     public Entreprise user1;
     public String id;

    @FXML
    private TextField nomEnt;
    @FXML
    private TextField mailEnt;
    @FXML
    private TextField telEnt;
    @FXML
    private Button modifInfo;
    @FXML
    private Label usernameLab;
    @FXML
    private Hyperlink logOut;
    @FXML
    private Label photoName;
    @FXML
    private Hyperlink cvEnt;
    @FXML
    private ImageView photoEnt;
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
    Membre user2;
    @FXML
    private TableColumn<?, ?> message;
    @FXML
    private TableColumn<?, ?> rep;

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
   public void iniializeFxml(Entreprise e) {
        System.out.println(e.getNom());
        System.out.println(e.getTel());

        Image image = new Image(IMG_PATH_LOAD + e.getPhoto());
        System.out.println(IMG_PATH_LOAD);
        ImageView iv1 = new ImageView();
        photoEnt.setImage(image);
        cvEnt.setText(this.user1.getCv());

    }
    @FXML
    private void modifPw(ActionEvent event) throws IOException {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("./EntrepriseModif.fxml"));
        Parent root = loader.load();
        EntrepriseModifController HomeScene = loader.getController();
        HomeScene.user1 = this.user1;
        HomeScene.id=id;
        Entreprise a= this.user1;
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
    private void afficherCv(ActionEvent event) throws IOException {
        String cv = this.user1.getCv();
        File file = new File(CV_PATH + cv);
        Desktop desktop = Desktop.getDesktop();
        if (file.exists()) {
            desktop.open(file);
        }
    }

    void showData(Entreprise e) {
          nomEnt.setText(e.getNom());
        mailEnt.setText(e.getEmail());
        telEnt.setText(e.getTel());
        usernameLab.setText("bienvenue " + e.getNom());
        mailEnt.setDisable(true);
        nomEnt.setDisable(true);
        telEnt.setDisable(true);
        
        Image image = new Image((IMG_PATH_LOAD + e.getPhoto()));
        
        photoEnt.setImage(image);
        System.out.println("test");
    }


    private void nvCv(ActionEvent event) {
         UserUtiles uUtiles = new UserUtiles();
        try {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("pdf files", "pdf");
            chooser.setFileFilter(filter);
            chooser.showOpenDialog(null);
             File fileCvEnt = chooser.getSelectedFile();
            cvEnt.setText(fileCvEnt.getName());
        } catch (Exception x) {
            uUtiles.alert_Box("Warning", "veillez choisir un cv");

        }
    }

    @FXML
    private void passerRec(ActionEvent event) throws IOException {
          FXMLLoader loader = new FXMLLoader(getClass().getResource("./passerReclamation.fxml"));
        Parent root = loader.load();
        PasserReclamationController HomeScene = loader.getController();
        HomeScene.user1 = this.user1;
        HomeScene.id=id;
        Entreprise a= this.user1;
        HomeScene.iniializeFxml(a);
        HomeScene.showData(a);
        Stage window = (Stage) modifInfo.getScene().getWindow();
        window.setScene(new Scene(root, 800, 800));
    }






    
}
