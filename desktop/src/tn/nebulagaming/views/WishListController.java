/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tn.nebulagaming.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import java.io.IOException;
import static java.lang.Math.round;
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
import tn.nebulagaming.models.ProductByCategory;
import tn.nebulagaming.services.WishListService;
import tn.nebulagaming.utils.GlobalConfig;

/**
 * FXML Controller class
 *
 * @author Aymen Dhahri
 */
public class WishListController implements Initializable {

    ObservableList<ProductByCategory> obsvWL;

    WishListService wsl = new WishListService();

    @FXML
    private JFXListView<ProductByCategory> wishList;
    @FXML
    private JFXComboBox<String> sortBy;
    @FXML
    private JFXButton del;
    @FXML
    private JFXButton btnHome;

    private GlobalConfig conf;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	// TODO

	conf = GlobalConfig.getInstance();

	obsvWL = FXCollections.observableArrayList(wsl.groupByCategory(conf.getSession()));

	String criteriasRef[] = {"NAME ▲", "PRICE ▲", "PRICE ▼", "NAME ▼"};

	ObservableList<String> criterias = FXCollections.observableArrayList(criteriasRef);

	sortBy.setItems(criterias);

	wishList.setItems(obsvWL);
    }

    @FXML
    void handleSort(ActionEvent event) {

	switch (sortBy.getValue()) {

	    case "NAME ▲":
		wishList.setItems(obsvWL.sorted((p1, p2) -> p1.getNameProduct().compareTo(p2.getNameProduct())));
		break;
	    case "PRICE ▲":
		wishList.setItems(obsvWL.sorted((p1, p2) -> round(p1.getPriceProduct()) - round(p2.getPriceProduct())));
		break;
	    case "NAME ▼":
		wishList.setItems(obsvWL.sorted((p1, p2) -> -1 * p1.getNameProduct().compareTo(p2.getNameProduct())));
		break;
	    case "PRICE ▼":
		wishList.setItems(obsvWL.sorted((p1, p2) -> -1 * (round(p1.getPriceProduct()) - round(p2.getPriceProduct()))));
		break;
	    default:
		break;
	}
    }

    @FXML
    void delete(ActionEvent event) {

	ProductByCategory p = wishList.getSelectionModel().getSelectedItem();

	wsl.delete(p.getIdProd(), 1);

	obsvWL.remove(p);
    }

    @FXML
    private void Home(ActionEvent event) {

	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("NewsFeed.fxml"));
	    Parent root = loader.load();
	    btnHome.getScene().setRoot(root);
	} catch (IOException ex) {
	    Logger.getLogger(WishListController.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

}
