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
import tn.nebulagaming.services.ServiceCategories;

/**
 * FXML Controller class
 *
 * @author anony
 */
public class HomeController implements Initializable {

    @FXML
    private Button GestP;
    @FXML
    private ComboBox<String> Cat;
    @FXML
    private Button GestCat;

    ServiceCategories servCat;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        servCat = new ServiceCategories();

        ObservableList<String> list = FXCollections
                .observableArrayList(
                        servCat.afficher().stream().map(c -> c.getNameCategory()).collect(Collectors.toList())
                );

        Cat.setItems(list);

    }

    @FXML
    private void GestP(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GestProduits.fxml"));
            Parent root = loader.load();
            GestP.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(GestCategoriesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void GestCat(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GestCategories.fxml"));
            Parent root = loader.load();
            GestCat.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(GestCategoriesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleSelection(ActionEvent event) {

        String catName = Cat.getValue();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProductList.fxml"));
            Parent root = loader.load();
            GestCat.getScene().setRoot(root);

            ProductListController prodContr = loader.getController();

            prodContr.setCatName(catName);

        } catch (IOException ex) {
            Logger.getLogger(GestCategoriesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
