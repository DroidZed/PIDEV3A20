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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import tn.nebulagaming.models.Post;
import tn.nebulagaming.services.ServicePost;
import static tn.nebulagaming.utils.Consts.IMG_PATH;
import static tn.nebulagaming.utils.Consts.IMG_PATH_LOAD;
import tn.nebulagaming.utils.PostUtils;

/**
 * FXML Controller class
 *
 * @author SuperNova
 */
public class UpdatePostController implements Initializable {

    @FXML
    private TextField tfTitlePost;
    @FXML
    private TextField tfDescPost;
    @FXML
    private ComboBox<String> cbVisibilityPost;
    private TextField tfPhotoPost;
    @FXML
    private Button btnSubmitAddPost;
    @FXML
    private Button btnGoBack;

    ServicePost sp = new ServicePost () ;
    File filePhotoPost = null;
    @FXML
    private ImageView coverPhotoPost;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        populateListViewVisibility() ; 
        tfTitlePost.setText(ManagePostController.postRecup.getTitlePost());
        tfDescPost.setText(ManagePostController.postRecup.getDescPost());
        //Display Post photo
        Image image = new Image((IMG_PATH_LOAD + ManagePostController.postRecup.getPhotoPost()));
        coverPhotoPost.setImage(image);
        
        if (ManagePostController.postRecup.getStatusPost() == 1) 
            cbVisibilityPost.getSelectionModel().selectLast();
        else 
            cbVisibilityPost.getSelectionModel().selectFirst();
        
        btnGoBack.setOnAction(event -> {
            try {
                Parent page1 = FXMLLoader.load(getClass().getResource("ManageContent.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(AddPostController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }    

      public boolean validateInputs() {
        if (tfTitlePost.getText().isEmpty()) {
                Alert alert1 = new Alert(Alert.AlertType.WARNING);
                alert1.setTitle("Failed !");
                alert1.setContentText("Title cannot be empty , try entering one !");
                alert1.setHeaderText(null);
                alert1.show();
                return false;
        } else if (tfTitlePost.getText().chars().allMatch( Character::isDigit)) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Failed !");
                alert2.setContentText("Title cannot be a number , try entering a string ");
                alert2.setHeaderText(null);
                alert2.show();
                return false;
        } else if (tfDescPost.getText().chars().allMatch( Character::isDigit)) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Failed !");
                alert2.setContentText("Description cannot be a number , try entering a string ");
                alert2.setHeaderText(null);
                alert2.show();
                return false;
        } else if (tfDescPost.getText().isEmpty()) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Failed !");
                alert2.setContentText("Description cannot be empty , try entering one !");
                alert2.setHeaderText(null);
                alert2.show();
                return false;
        }
        return true;
    }
      
    public void populateListViewVisibility () {
        ObservableList<String> items = FXCollections.observableArrayList();
        cbVisibilityPost.setItems(items);
        items.add("Not Visible");
        items.add("Visible");
        
        /*String visibilityStr = cbVisibilityPost.getValue() ;
        cbVisibilityPost.setValue(visibilityStr);*/
    }

      
    @FXML
    private void updatePost(ActionEvent event) throws IOException {
        if (validateInputs()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Update Post");
                alert.setHeaderText(null);
                alert.setContentText("Please confirm your modifications ");
                            Optional<ButtonType> action = alert.showAndWait();
                            if (action.get() == ButtonType.OK)
                            {  
                                String visibilityStr = cbVisibilityPost.getValue() ;
                                File filePhoto = filePhotoPost;
                                
                                //Display Post photo
                                Image image = new Image((IMG_PATH_LOAD + ManagePostController.postRecup.getPhotoPost()));
                                coverPhotoPost.setImage(image);
                                
                                //Get name
                                String photo = ManagePostController.postRecup.getPhotoPost();
                                
                                if (filePhoto != null) {
                                    photo = copyPhoto() ;
                                }
                                

                                int visibilityInt ; 
                                if (visibilityStr.equals("Visible")) 
                                    visibilityInt = 1 ;
                                else 
                                    visibilityInt = 0 ; 
                                
                             sp.update(new Post (ManagePostController.postRecup.getIdPost(),tfTitlePost.getText() , tfDescPost.getText(), photo ,visibilityInt));
                                 try {
                                    Parent page1 = FXMLLoader.load(getClass().getResource("ManageContent.fxml"));
                                    Scene scene = new Scene(page1);
                                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                    stage.setScene(scene);
                                    stage.show();
                                } catch (IOException ex) {
                                    Logger.getLogger(UpdatePostController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                         }
                      }
        
    }
    
    

    
   

    @FXML
    private void photoUpload(ActionEvent event) {
          try {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("image files", "jpg","png","jpeg");
            chooser.setFileFilter(filter);
            chooser.showOpenDialog(null);
            filePhotoPost = chooser.getSelectedFile();
            String photo = copyPhoto() ;
        } catch (Exception x) {
            Alert dg = new Alert(Alert.AlertType.INFORMATION);
            dg.setTitle("Warning");
            dg.setContentText("Please select a Photo for your Post cover !");
            dg.show();
        }
    }
    
      public String copyPhoto() throws IOException, IOException {
        PostUtils pu = new PostUtils();
        Date d = new Date();
        String strTimestamp = String.valueOf(d.getTime());
        String randomString = pu.randomString() + "_" + strTimestamp + ".jpg";
        Path copied = Paths.get(IMG_PATH + randomString);
        Path originalPath = Paths.get(filePhotoPost.getAbsolutePath());
        System.out.println(copied);
        Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);
            Image image = new Image((IMG_PATH_LOAD + randomString));
        coverPhotoPost.setImage(image);
        return randomString;
    }
    
}
