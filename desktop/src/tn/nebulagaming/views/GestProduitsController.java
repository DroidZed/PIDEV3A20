/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tn.nebulagaming.views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.nebulagaming.models.ProductsByCategory;
import tn.nebulagaming.models.Products;
import tn.nebulagaming.services.ServiceCategories;
import tn.nebulagaming.services.ServiceProducts;

/**
 * FXML Controller class
 *
 * @author anony
 */
public class GestProduitsController implements Initializable {

    ServiceProducts Sp = new ServiceProducts();
    @FXML
    private Button retour;
    @FXML
    private TableView<ProductsByCategory> tblProd;
    @FXML
    private TableColumn<ProductsByCategory, String> tbNom;
    @FXML
    private TableColumn<ProductsByCategory, Float> tbPrix;
    @FXML
    private TableColumn<ProductsByCategory, Integer> tbQuant;
    @FXML
    private TableColumn<ProductsByCategory, String> tbCat;
    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfPrix;
    @FXML
    private TextField tfQuant;
    @FXML
    private TextField tfimg;
    @FXML
    private Button AddP;
    @FXML
    private Button DelP;
    @FXML
    private Button EdP;
    @FXML
    private Button AddC;

    @FXML
    private ComboBox<String> combCat;

    @FXML
    private Label error;

    ObservableList<ProductsByCategory> list;

    ServiceCategories servCat;

    private int selectedProdID;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //Affichage
        list = FXCollections.observableArrayList(Sp.getProductsByCategory());

        tbNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        tbPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        tbQuant.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tbCat.setCellValueFactory(new PropertyValueFactory<>("category"));

        tblProd.setItems(list);
        // TODO
        //SelectOnclick
        tblProd.setRowFactory(tv -> {

            TableRow<ProductsByCategory> row = new TableRow<>();

            row.setOnMouseClicked(event -> {

                if (!row.isEmpty()) {
                    final ProductsByCategory selectedItem = tblProd.getSelectionModel().getSelectedItem();

                    tfNom.setText(selectedItem.getNom());
                    tfPrix.setText(selectedItem.getPrix().toString());
                    tfQuant.setText(selectedItem.getQuantity().toString());
                    tfimg.setText(selectedItem.getImageProduct());
                    combCat.setValue(selectedItem.getCategory());

                    AddC.setDisable(false);

                    selectedProdID = selectedItem.getIdProd();
                }
            });

            return row;
        });

        servCat = new ServiceCategories();

        ObservableList<String> catNames = FXCollections
                .observableArrayList(
                        servCat.afficher().stream().map(c -> c.getNameCategory()).collect(Collectors.toList())
                );

        combCat.setItems(catNames);
    }

    @FXML
    private void returnb(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
            Parent root = loader.load();
            retour.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void AddP(ActionEvent event) {

        final int idCategory = servCat.getIdByCategoryName(combCat.getValue());
        if (tfPrix.getText().isEmpty()){
        error.setText("Verifier les entrées s'il vous plait");}
       else
        {// FIXME: change the id user from 1 to the current logged in user.
        Sp.ajouter(new Products(tfNom.getText(), Float.parseFloat(tfPrix.getText()), Integer.parseInt(tfQuant.getText()), tfimg.getText(), 1, idCategory));
        tblProd.setItems(FXCollections.observableArrayList(Sp.getProductsByCategory()));
        Sp.Notificationmanager(3);
        tblProd.refresh();
}
    }

    @FXML
    private void DelP(ActionEvent event) {
        final ProductsByCategory selectedItem = tblProd.getSelectionModel().getSelectedItem();
        Products prod = Sp.findByID_Product(selectedItem.getIdProd());
        Sp.supprimer(prod);
        Sp.Notificationmanager(1);
        list.remove(selectedItem);
    }

    @FXML
    private void EdP(ActionEvent event) {

        final int idCategory = servCat.getIdByCategoryName(combCat.getValue());

        // FIXME: change the id user from 1 to the current logged in user.
        final ProductsByCategory selectedItem = tblProd.getSelectionModel().getSelectedItem();
        Products prod = Sp.findByID_Product(selectedItem.getIdProd());
        prod.setNameProduct(tfNom.getText());
        prod.setPriceProduct(Float.parseFloat(tfPrix.getText()));
        prod.setQtyProduct(Integer.parseInt(tfQuant.getText()));
        prod.setImageProduct(tfimg.getText());
        prod.setIdCategory(idCategory);
        Sp.modifier(prod);
        tblProd.setItems(FXCollections.observableArrayList(Sp.getProductsByCategory()));
        tblProd.refresh();
        Sp.Notificationmanager(2);
    }

    @FXML
    private void AddC(ActionEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Promo.fxml"));

            Parent root = loader.load();
            retour.getScene().setRoot(root);

            PromoController promoController = loader.getController();

            promoController.setProdId(selectedProdID);

        } catch (IOException ex) {
            Logger.getLogger(GestCategoriesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
/*
    @FXML
    private void chooseImage(ActionEvent event) {
        final FileChooser fileChooser = new FileChooser();
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        selectedImage = fileChooser.showOpenDialog(thisStage);

        if (selectedImage != null) {
            selectedImagePath = selectedImage.toURI().toString();
            Image image = new Image(selectedImage.toURI().toString());
            
            File outputFile = new File("C:\\Users\\anony\\Desktop\\Nebula-Gaming\\desktop\\src\\tn\\nebulagaming\\res");

            BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
            try {
                ImageIO.write(bImage, "png", outputFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
*/
}
