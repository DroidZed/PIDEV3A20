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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import tn.nebulagaming.models.Badge;
import tn.nebulagaming.models.Event;
import tn.nebulagaming.services.ServiceBadge;
import static tn.nebulagaming.utils.Consts.IMG_PATH;
import static tn.nebulagaming.utils.Consts.IMG_PATH_LOAD;
import tn.nebulagaming.utils.PostUtils;

/**
 * FXML Controller class
 *
 * @author SuperNova
 */
public class UpdateBadgeController implements Initializable {

    @FXML
    private TextField tfNameBadge;
    @FXML
    private TextField tfDescBadge;
    @FXML
    private ImageView coverPhotoBadge;
    @FXML
    private Button btnGoBack;
    File filePhotoBadge = null;
    
    ServiceBadge sb = new ServiceBadge () ; 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         tfNameBadge.setText(ManageBadgeController.badgeRecup.getNameBadge());
         tfDescBadge.setText(ManageBadgeController.badgeRecup.getDescBadge());
         Image image = new Image((IMG_PATH_LOAD + ManageBadgeController.badgeRecup.getPhotoBadge()));
         coverPhotoBadge.setImage(image);
         
         btnGoBack.setOnAction(event -> {
            try {
                Parent page1 = FXMLLoader.load(getClass().getResource("ManageBadge.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(UpdateBadgeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }  
    
    public boolean validateInputs() {
        if (tfNameBadge.getText().isEmpty()) {
                Alert alert1 = new Alert(Alert.AlertType.WARNING);
                alert1.setTitle("Failed !");
                alert1.setContentText("Title cannot be empty , try entering one !");
                alert1.setHeaderText(null);
                alert1.show();
                return false;
        } else if (tfNameBadge.getText().chars().allMatch( Character::isDigit)) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Failed !");
                alert2.setContentText("Name of the badge cannot be a number , try entering a string ");
                alert2.setHeaderText(null);
                alert2.show();
                return false;
        } else if (tfDescBadge.getText().chars().allMatch( Character::isDigit)) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Failed !");
                alert2.setContentText("Description of the badge cannot be a number , try entering a string ");
                alert2.setHeaderText(null);
                alert2.show();
                return false;
        } else if (tfDescBadge.getText().isEmpty()) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Failed !");
                alert2.setContentText("Description of the badge cannot be empty , try entering one !");
                alert2.setHeaderText(null);
                alert2.show();
                return false;
        }
        return true;
    }
    
    

    @FXML
    private void photoUpload(ActionEvent event) {
         try {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("image files", "jpg","png","jpeg");
            chooser.setFileFilter(filter);
            chooser.showOpenDialog(null);
            filePhotoBadge = chooser.getSelectedFile();
            String photo = copyPhoto() ;
        } catch (Exception x) {
            Alert dg = new Alert(Alert.AlertType.INFORMATION);
            dg.setTitle("Warning");
            dg.setContentText("Please select a Photo for the Badge !");
            dg.show();
        }
        
    }
    
    public String copyPhoto() throws IOException, IOException {
        PostUtils pu = new PostUtils();
        Date d = new Date();
        String strTimestamp = String.valueOf(d.getTime());
        String randomString = pu.randomString() + "_" + strTimestamp + ".jpg";
        Path copied = Paths.get(IMG_PATH + randomString);
        Path originalPath = Paths.get(filePhotoBadge.getAbsolutePath());
        System.out.println(copied);
        Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);
            Image image = new Image((IMG_PATH_LOAD + randomString));
        coverPhotoBadge.setImage(image);
        return randomString;
    }

    @FXML
    private void updateBadge(ActionEvent event) throws IOException {
        if (validateInputs()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Update Badge");
                alert.setHeaderText(null);
                alert.setContentText("Please confirm your modifications ");
                            Optional<ButtonType> action = alert.showAndWait();
                            if (action.get() == ButtonType.OK)
                            {  
                                
                                //Image
                                File filePhoto = filePhotoBadge;
                                
                                //Display Post photo
                                Image image = new Image((IMG_PATH_LOAD + ManageBadgeController.badgeRecup.getPhotoBadge()));
                                coverPhotoBadge.setImage(image);
                                
                                //Get name
                                String photo = ManageBadgeController.badgeRecup.getPhotoBadge();
                                
                                if (filePhoto != null) {
                                    photo = copyPhoto() ;
                                }
                                
                                
                                sb.update(new Badge (ManageBadgeController.badgeRecup.getIdBadge(),tfNameBadge.getText() , tfDescBadge.getText(),photo ));
                                 try {
                                    Parent page1 = FXMLLoader.load(getClass().getResource("ManageBadge.fxml"));
                                    
                                    Scene scene = new Scene(page1);
                                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                    stage.setScene(scene);
                                    stage.show();
                                } catch (IOException ex) {
                                    Logger.getLogger(UpdateBadgeController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                         }
                      } 
    }
    
}
