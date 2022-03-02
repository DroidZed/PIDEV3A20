/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tn.nebulagaming.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import static java.lang.Math.round;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import tn.nebulagaming.models.ProductByCategory;
import tn.nebulagaming.models.SortingCriteria;
import tn.nebulagaming.services.WishListService;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class WishListController implements Initializable {

    ObservableList<ProductByCategory> obsvWL;

    WishListService wsl = new WishListService();

    @FXML
    private JFXListView<ProductByCategory> wishList;
    @FXML
    private JFXComboBox<SortingCriteria> sortBy;
    @FXML
    private JFXButton del;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	// TODO

	obsvWL = FXCollections.observableArrayList(wsl.groupByCategory(1));

	SortingCriteria criteriasRef[] = {SortingCriteria.NAME, SortingCriteria.PRICE, SortingCriteria.PRICE_D, SortingCriteria.NAME_D};

	ObservableList<SortingCriteria> criterias = FXCollections.observableArrayList(criteriasRef);

	sortBy.setItems(criterias);

	wishList.setItems(obsvWL);
    }

    @FXML
    private void HandleSort(ActionEvent event) {

	switch (sortBy.getValue()) {

	    case NAME:
		wishList.setItems(obsvWL.sorted((p1, p2) -> p1.getNameProduct().compareTo(p2.getNameProduct())));
		break;
	    case PRICE:
		wishList.setItems(obsvWL.sorted((p1, p2) -> round(p1.getPriceProduct()) - round(p2.getPriceProduct())));
		break;
	    case NAME_D:
		wishList.setItems(obsvWL.sorted((p1, p2) -> -1 * p1.getNameProduct().compareTo(p2.getNameProduct())));
		break;
	    case PRICE_D:
		wishList.setItems(obsvWL.sorted((p1, p2) -> -1 * (round(p1.getPriceProduct()) - round(p2.getPriceProduct()))));
		break;
	    default:
		break;
	}
    }

    @FXML
    private void Delete(ActionEvent event) {

	ProductByCategory p = wishList.getSelectionModel().getSelectedItem();

	wsl.delete(p.getIdProd(), 1);

	obsvWL.remove(p);
    }

}
