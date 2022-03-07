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
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
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
public class AddPostController implements Initializable {

    @FXML
    private TextField tfPhotoPost;
    @FXML
    private TextField tfTitlePost;
    @FXML
    private TextField tfDescPost;
    private ListView<String> lvVisibilityPost;
    @FXML
    private Button btnSubmitAddPost;
    @FXML
    private Button btnGoBack;
    @FXML
    private ComboBox<String> cbVisibilityPost;
    
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
    
    public void populateListViewVisibility () {
        ObservableList<String> items = FXCollections.observableArrayList();
        cbVisibilityPost.setItems(items);
        items.add("Not Visible");
        items.add("Visible");
        
        cbVisibilityPost.getSelectionModel().selectFirst();
    }

    @FXML
    private void addPost(ActionEvent event) throws IOException {
        if (validateInputs()) {
            java.sql.Date postedDTM = new java.sql.Date (Calendar.getInstance().getTime().getTime());
            String visibilityStr = cbVisibilityPost.getValue() ; 
            int visibilityInt ; 
            if (visibilityStr.equals("Visible")) 
                visibilityInt = 1 ;
            else 
                visibilityInt = 0 ; 
            
            if (filePhotoPost == null) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Warning !");
                alert2.setContentText("Select a photCover !");
                alert2.setHeaderText(null);
                alert2.show();
            }
            String photoPost = copyPhoto();
            
            sp.add(new Post (postedDTM,tfTitlePost.getText(),tfDescPost.getText(),visibilityInt,"Post",photoPost,1)) ;
            JOptionPane.showMessageDialog(null, "Post added !");
        }                       
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

    @FXML
    private void photoUpload(ActionEvent event) throws IOException {
        
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
