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
import java.util.Calendar;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import tn.nebulagaming.models.Badge;
import tn.nebulagaming.models.Post;
import tn.nebulagaming.services.ServiceBadge;
import static tn.nebulagaming.utils.Consts.IMG_PATH;
import static tn.nebulagaming.utils.Consts.IMG_PATH_LOAD;
import tn.nebulagaming.utils.PostUtils;
import static tn.nebulagaming.views.ManagePostController.postRecup;

/**
 * FXML Controller class
 *
 * @author SuperNova
 */
public class ManageBadgeController implements Initializable {

    @FXML
    private TableView<Badge> tvBadge;
    @FXML
    private TableColumn<Badge, String> nameBadgeCol;
    @FXML
    private TableColumn<Badge, String> descBadgeCol;
    private TableColumn<Badge, String> photoBadgeCol;
    @FXML
    private ImageView coverPhotoPost;
    @FXML
    private Button btnGoBack;
    @FXML
    private TextField tfNameBadge;
    @FXML
    private TextField tfDescBadge;
    
    File filePhotoPost = null;
    ServiceBadge sb = new ServiceBadge () ; 
    static Badge badgeRecup;
    
    
    @FXML
    private TableColumn<Badge, String> idBadgeCol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayBadges () ; 
        addButtonUpdateToTable () ; 
        addButtonDeleteToTable();
        btnGoBack.setOnAction(event -> {
            try {
                Parent page1 = FXMLLoader.load(getClass().getResource("MainBackOffice.fxml"));
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

    @FXML
    private void addBadge(ActionEvent event) throws IOException {
        
        if (validateInputs()) {
           
            if (filePhotoPost == null) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Warning !");
                alert2.setContentText("Select a photCover !");
                alert2.setHeaderText(null);
                alert2.show();
            }
            String photoPost = copyPhoto();
            
            sb.add(new Badge (tfNameBadge.getText() , tfDescBadge.getText() , photoPost)) ;
            JOptionPane.showMessageDialog(null, "Badge added !");
            
            displayBadges () ;
        }  
    }
    
    private void displayBadges () {
        idBadgeCol.setCellValueFactory(new PropertyValueFactory<>("idBadge"));
        idBadgeCol.setVisible(false);
        
        nameBadgeCol.setCellValueFactory(new PropertyValueFactory<>("nameBadge"));
        descBadgeCol.setCellValueFactory(new PropertyValueFactory<>("descBadge"));
       
        
        ObservableList<Badge> listBadge = FXCollections.observableArrayList(sb.display()) ;
        tvBadge.setItems(listBadge) ;
        
    } 
    
    private void addButtonUpdateToTable() {        
        TableColumn<Badge, Void> colBtn = new TableColumn("Update");

        Callback<TableColumn<Badge, Void>, TableCell<Badge, Void>> cellFactory = new Callback<TableColumn<Badge, Void>, TableCell<Badge, Void>>() {
            
            @Override
            public TableCell<Badge, Void> call(final TableColumn<Badge, Void> param) {
                
                final TableCell<Badge, Void> cell = new TableCell<Badge, Void>() {
                    private final Button btn = new Button("Update");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            badgeRecup = getTableView().getItems().get(getIndex());
                              try {
                                Parent page1 = FXMLLoader.load(getClass().getResource("UpdateBadge.fxml"));
                                Scene scene = new Scene(page1);
                                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException ex) {
                                Logger.getLogger(ManageBadgeController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                    }
                    
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);
        tvBadge.getColumns().add(colBtn);
    }
    
    private void addButtonDeleteToTable() {
        TableColumn<Badge, Void> colBtn = new TableColumn("Remove");

        Callback<TableColumn<Badge, Void>, TableCell<Badge, Void>> cellFactory = new Callback<TableColumn<Badge, Void>, TableCell<Badge, Void>>() {
            @Override
            public TableCell<Badge, Void> call(final TableColumn<Badge, Void> param) {
                final TableCell<Badge, Void> cell = new TableCell<Badge, Void>() {

                    private final Button btn = new Button("Remove");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Badge f = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Remove");
                            alert.setHeaderText(null);
                            alert.setContentText("Delete this Badge ? ");
                            Optional<ButtonType> action = alert.showAndWait();
                            if (action.get() == ButtonType.OK) {
                                sb.delete(f.getIdBadge());

                            }

                            try {
                                Parent page1 = FXMLLoader.load(getClass().getResource("ManageBadge.fxml"));
                                Scene scene = new Scene(page1);
                                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException ex) {
                                Logger.getLogger(ManageBadgeController.class.getName()).log(Level.SEVERE, null, ex);
                            }
        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);

        tvBadge.getColumns().add(colBtn);
    }
    
}
