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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.nebulagaming.models.Promos;
import tn.nebulagaming.services.ServicePromos;

/**
 * FXML Controller class
 *
 * @author anony
 */
public class PromoController implements Initializable {

    @FXML
    private Button retour;

    @FXML
    private TableView<Promos> tblProm;
    @FXML
    private TableColumn<Promos, String> col_prom;
    @FXML
    private TableColumn<Promos, Integer> dis_prom;
    @FXML
    private Button AddC;
    @FXML
    private Button DelC;
    @FXML
    private Button EditC;

    private ServicePromos servPr;

    @FXML
    private TextField tfCode;

    @FXML
    private TextField tfPromo;

    ObservableList<Promos> listPromos;

    private int prodId;

    public void setProdId(int prodId) {

        this.prodId = prodId;
        System.out.println("in set prod id: " + this.prodId);

        servPr = new ServicePromos();

        listPromos = FXCollections.observableArrayList(servPr.getOfProduct(prodId));

        col_prom.setCellValueFactory(new PropertyValueFactory<>("codePromo"));

        dis_prom.setCellValueFactory(new PropertyValueFactory<>("discountPromo"));

        tblProm.setItems(listPromos);

        System.out.println(listPromos);

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @FXML
    private void returnb(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GestProduits.fxml"));
            Parent root = loader.load();
            retour.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(GestCategoriesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void AddC(ActionEvent event) {

        servPr.ajouter(new Promos(tfCode.getText(), Integer.parseInt(tfPromo.getText()), prodId));
        tblProm.setItems(FXCollections.observableArrayList(servPr.getOfProduct(prodId)));
        tblProm.refresh();
    }

    @FXML
    private void DelC(ActionEvent event) {
    }

    @FXML
    private void EditC(ActionEvent event) {
    }

}
