/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tn.nebulagaming.views;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import static java.lang.Double.parseDouble;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import tn.nebulagaming.models.Event;
import tn.nebulagaming.services.ServiceEvent;
import static tn.nebulagaming.utils.Consts.IMG_PATH;
import static tn.nebulagaming.utils.Consts.IMG_PATH_LOAD;
import tn.nebulagaming.utils.MapHandler;
import tn.nebulagaming.utils.PostUtils;


/**
 * FXML Controller class
 *
 * @author SuperNova
 */
public class UpdateEventController implements Initializable {

    @FXML
    private TextField tfTitleEvent;
    @FXML
    private TextField tfDescEvent;
    @FXML
    private ComboBox<String> cbVisibilityEvent;
    @FXML
    private DatePicker tfStartDateEvent;
    @FXML
    private DatePicker tfEndDateEvent;
    @FXML
    private TextField tfNbTickets;
    @FXML
    private Button btnGoBack;
    @FXML
    private Button btnUpdateEvent;
    @FXML
    private TextField tfAddressEvent;

    ServiceEvent se = new ServiceEvent () ;

    
    @FXML
    private TextField tfLat;
    @FXML
    private TextField tfLong;
    @FXML
    private ImageView coverPhotoPost;
    
    File filePhotoPost = null;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //System.out.println (ManageEventController.eventRecup.getPhotoPost()) ;
        populateListViewVisibility() ;
        tfTitleEvent.setText(ManageEventController.eventRecup.getTitlePost());
        tfDescEvent.setText(ManageEventController.eventRecup.getDescPost());
        //Display Post photo
        Image image = new Image((IMG_PATH_LOAD + ManageEventController.eventRecup.getPhotoPost()));
        coverPhotoPost.setImage(image);
        
        tfStartDateEvent.setValue(ManageEventController.eventRecup.getStartDTM().toLocalDate());
        tfEndDateEvent.setValue(ManageEventController.eventRecup.getEndDTM().toLocalDate());
        tfAddressEvent.setText(ManageEventController.eventRecup.getAddressEvent());
        tfNbTickets.setText(Integer.toString(ManageEventController.eventRecup.getNbTicketAvailable()));
        tfLat.setText(Double.toString(ManageEventController.eventRecup.getLatitude()));
        tfLong.setText(Double.toString(ManageEventController.eventRecup.getLongitude()));
        
        if (ManageEventController.eventRecup.getStatusPost() == 1) 
            cbVisibilityEvent.getSelectionModel().selectLast();
        else 
            cbVisibilityEvent.getSelectionModel().selectFirst();
        
        btnGoBack.setOnAction(event -> {
            try {
                Parent page1 = FXMLLoader.load(getClass().getResource("ManageContent.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(UpdateEventController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    public boolean validateInputs() {
        if (tfTitleEvent.getText().isEmpty()) {
                Alert alert1 = new Alert(Alert.AlertType.WARNING);
                alert1.setTitle("Failed !");
                alert1.setContentText("Title cannot be empty , try entering one !");
                alert1.setHeaderText(null);
                alert1.show();
                return false;
        } else if (tfTitleEvent.getText().chars().allMatch( Character::isDigit)) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Failed !");
                alert2.setContentText("Title cannot be a number , try entering a string ");
                alert2.setHeaderText(null);
                alert2.show();
                return false;
        } else if (tfDescEvent.getText().chars().allMatch( Character::isDigit)) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Failed !");
                alert2.setContentText("Description cannot be a number , try entering a string ");
                alert2.setHeaderText(null);
                alert2.show();
                return false;
        } else if (tfDescEvent.getText().isEmpty()) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Failed !");
                alert2.setContentText("Description cannot be empty , try entering one !");
                alert2.setHeaderText(null);
                alert2.show();
                return false;
        }else if (tfAddressEvent.getText().isEmpty()) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Failed !");
                alert2.setContentText("Address cannot be empty , try entering one !");
                alert2.setHeaderText(null);
                alert2.show();
                return false;
        }else if (tfAddressEvent.getText().chars().allMatch( Character::isDigit)) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Failed !");
                alert2.setContentText("Address cannot be a number , try entering a string !");
                alert2.setHeaderText(null);
                alert2.show();
                return false;
        }else if (tfNbTickets.getText().isEmpty()) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Failed !");
                alert2.setContentText("Address cannot be empty , try entering one !");
                alert2.setHeaderText(null);
                alert2.show();
                return false;
        }else if (! tfNbTickets.getText().chars().allMatch( Character::isDigit)) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Failed !");
                alert2.setContentText("Number of tickets should be a number , try entering one !");
                alert2.setHeaderText(null);
                alert2.show();
                return false;
        }/*else if (! tfLat.getText().chars().allMatch( Character::isDigit) || ! tfLong.getText().chars().allMatch( Character::isDigit)) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Failed !");
                alert2.setContentText("Latitude and Longitude must of type doubles !");
                alert2.setHeaderText(null);
                alert2.show();
                return false;
        }*/else if (tfStartDateEvent.getValue().isAfter(tfEndDateEvent.getValue())) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Failed !");
                alert2.setContentText("Start Date must be inferior to End Date !");
                alert2.setHeaderText(null);
                alert2.show();
                return false;
        }else if (tfStartDateEvent.getValue().isBefore(LocalDate.now())) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Failed !");
                alert2.setContentText("Start Date must be equal or superior to current date !");
                alert2.setHeaderText(null);
                alert2.show();
                return false;
        }
        return true;
    }

public void populateListViewVisibility () {
        ObservableList<String> items = FXCollections.observableArrayList();
        cbVisibilityEvent.setItems(items);
        items.add("Not Visible");
        items.add("Visible");
        
        /*String visibilityStr = cbVisibilityPost.getValue() ;
        cbVisibilityPost.setValue(visibilityStr);*/
    }    

    @FXML
    private void updateEvent(ActionEvent event) throws IOException {
        if (validateInputs()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Update event");
                alert.setHeaderText(null);
                alert.setContentText("Please confirm your modifications ");
                            Optional<ButtonType> action = alert.showAndWait();
                            if (action.get() == ButtonType.OK)
                            {  
                                
                                //Visibility 
                                String visibilityStr = cbVisibilityEvent.getValue() ; 
                                int visibilityInt ; 
                                if (visibilityStr.equals("Visible")) 
                                    visibilityInt = 1 ;
                                else 
                                    visibilityInt = 0 ; 
                                
                                //Dates
                                java.sql.Date startDTM =java.sql.Date.valueOf(tfStartDateEvent.getValue());
                                java.sql.Date endDTM =java.sql.Date.valueOf(tfEndDateEvent.getValue());
                                
                                //Image
                                File filePhoto = filePhotoPost;
                                
                                //Display Post photo
                                Image image = new Image((IMG_PATH_LOAD + ManageEventController.eventRecup.getPhotoPost()));
                                coverPhotoPost.setImage(image);
                                
                                //Get name
                                String photo = ManageEventController.eventRecup.getPhotoPost();
                                
                                if (filePhoto != null) {
                                    photo = copyPhoto() ;
                                }
                                
                                System.out.println (filePhoto) ;
                                
                                
                                se.update(new Event(ManageEventController.eventRecup.getIdPost(),tfTitleEvent.getText() , tfDescEvent.getText(),visibilityInt , photo ,startDTM , endDTM , tfAddressEvent.getText() , Integer.parseInt(tfNbTickets.getText()) , Double.parseDouble(tfLat.getText()) ,Double.parseDouble(tfLong.getText()) ));
                                 try {
                                    Parent page1 = FXMLLoader.load(getClass().getResource("ManageContent.fxml"));
                                    
                                    Scene scene = new Scene(page1);
                                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                    stage.setScene(scene);
                                    stage.show();
                                } catch (IOException ex) {
                                    Logger.getLogger(UpdateEventController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                         }
                      }
    }

    
    @FXML
    private void map(ActionEvent event) {
        MapHandler m = new MapHandler("Map");
        m.getInformation(parseDouble(tfLat.getText()),parseDouble(tfLong.getText())); 
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

    @FXML
    private void openGetLatLong(ActionEvent event) throws URISyntaxException, IOException { 
        Desktop.getDesktop().browse(new URI("https://www.latlong.net"));
    }

    @FXML
    private void openLinkGoogleMaps(ActionEvent event) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://www.google.com/maps/@36.4455434,10.9813806,10z"));
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
