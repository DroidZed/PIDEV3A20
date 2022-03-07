/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tn.nebulagaming.views;

import java.io.IOException;
import static java.lang.Math.round;
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
import javafx.scene.image.ImageView;
import tn.nebulagaming.models.ProductsByCategory;
import tn.nebulagaming.models.Products;
import tn.nebulagaming.services.ServiceCategories;
import tn.nebulagaming.services.ServiceProducts;

/**
 * FXML Controller class
 *
 * @author anony
 */
public class ProductListController implements Initializable {
    ServiceProducts Sp = new ServiceProducts();

    @FXML
    private Button retour;

    @FXML
    private Label catTitle;

    ObservableList<Products> prodList;
    @FXML
    private TableView<Products> prodTable;
    @FXML
    private TableColumn<Products, String> colNom;
    @FXML
    private TableColumn<Products, Integer> colQuant;
    @FXML
    private TableColumn<Products, Float> colPrix;

    private ServiceProducts servP;
    @FXML
    private TextField tfRech;
    @FXML
    private ComboBox<String> cbxSort;

    private int idCat;
    @FXML
    private Button AddCart;
    @FXML
    private Button AddWish;
    @FXML
    private ImageView imgProd;
    @FXML
    private Label tfnomprod;

    public void setCatName(String catName) {
        this.catTitle.setText(catName);
        ServiceCategories servCat = new ServiceCategories();
        idCat = servCat.getIdByCategoryNameJoinedProduct(catName);

        servP = new ServiceProducts();

        prodList = FXCollections.observableArrayList(servP.afficher().stream().filter(p -> p.getIdCategory() == idCat).collect(Collectors.toList()));

        System.out.println(prodList);

        colNom.setCellValueFactory(new PropertyValueFactory<>("nameProduct"));
        colQuant.setCellValueFactory(new PropertyValueFactory<>("qtyProduct"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("priceProduct"));

        prodTable.setItems(prodList);
        
        prodTable.setRowFactory(tv -> {

            TableRow<Products> row = new TableRow<>();

            row.setOnMouseClicked(event -> {

                if (!row.isEmpty()) {
                    final Products selectedItem = prodTable.getSelectionModel().getSelectedItem();
                    tfnomprod.setText(selectedItem.getNameProduct());
                }
            });

            return row;
        });
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        String[] choices = {"PRICE ▲", "PRICE ▼"};

        ObservableList<String> values = FXCollections.observableArrayList(choices);

        cbxSort.setItems(values);

        tfRech.textProperty().addListener((obs, oldValue, newValue) -> {

            if (!newValue.isEmpty()) {

                prodTable.setItems(filterByName(prodList, newValue));

            } else {
                prodTable.setItems(prodList);
            }
        });

    }

    @FXML
    private void returnb(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
            Parent root = loader.load();
            retour.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(GestCategoriesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleSort(ActionEvent event) {

        switch (cbxSort.getValue()) {

            case "PRICE ▲":
                prodTable.setItems(prodList.sorted((p1, p2) -> round(p1.getPriceProduct()) - round(p2.getPriceProduct())));
                break;

            case "PRICE ▼":
                prodTable.setItems(prodList.sorted((p1, p2) -> -1 * (round(p1.getPriceProduct()) - round(p2.getPriceProduct()))));
                break;

            default:
                break;
        }

    }

    @FXML
    private void AddCart(ActionEvent event) {
    }

    @FXML
    private void AddWish(ActionEvent event) {
    }

    private ObservableList<Products> filterByName(ObservableList<Products> prodList, String newValue) {
        return prodList.filtered(p -> p.getNameProduct().contains(newValue));
    }

}
