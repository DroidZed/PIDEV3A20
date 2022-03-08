/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tn.nebulagaming.views;

import java.util.regex.Pattern;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.nebulagaming.models.UserOrderCombined;
import tn.nebulagaming.services.UserOrderService;
import static tn.nebulagaming.utils.Constantes.SEARCH_REGEX;

/**
 * FXML Controller class
 *
 * @author Aymen Dhahri
 */
public class OrderListsAdminController implements Initializable {

    @FXML
    private TextField tfSearch;

    @FXML
    private JFXButton btnHome;

    @FXML
    private DatePicker dateSub;

    private ObservableList<UserOrderCombined> orders;

    private UserOrderService usr;
    @FXML
    private TableView<UserOrderCombined> tabStats;
    @FXML
    private TableColumn<UserOrderCombined, String> colFullName;
    @FXML
    private TableColumn<UserOrderCombined, String> colSubAt;
    @FXML
    private TableColumn<UserOrderCombined, String> colPaidAt;
    @FXML
    private TableColumn<UserOrderCombined, String> colPayT;
    @FXML
    private TableColumn<UserOrderCombined, String> colStatus;
    @FXML
    private TableColumn<UserOrderCombined, Float> colTot;
    @FXML
    private JFXComboBox<String> cbxSort;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	// TODO

	usr = new UserOrderService();

	orders = FXCollections.observableArrayList(usr.getAssociated());

	colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
	colSubAt.setCellValueFactory(new PropertyValueFactory<>("createdDTM"));
	colPaidAt.setCellValueFactory(new PropertyValueFactory<>("payDTM"));
	colPayT.setCellValueFactory(new PropertyValueFactory<>("payType"));
	colStatus.setCellValueFactory(new PropertyValueFactory<>("statusOrder"));
	colTot.setCellValueFactory(new PropertyValueFactory<>("tot"));

	tabStats.setItems(orders);

	tfSearch.textProperty().addListener((obs, oldValue, newValue) -> {

	    Matcher matcher = SEARCH_REGEX.matcher(newValue);
	    boolean matchFound = matcher.find();

	    if (matchFound && !newValue.isEmpty()) {

		String g2 = matcher.group(1); // first word before :
		String g4 = matcher.group(3); // phrase after :

		switch (g2) {
		    case "Full Name":
			tabStats.setItems(filterByFullName(orders, g4));
			break;
		    case "Status":
			tabStats.setItems(filterStatus(orders, g4.toUpperCase()));
			break;
		    case "Total":
			tabStats.setItems(filterTotal(orders, Float.parseFloat(g4)));
		    default:
			break;
		}
	    } else {
		tabStats.setItems(orders);
	    }
	});

	tabStats.setRowFactory(tv -> {

	    TableRow<UserOrderCombined> row = new TableRow<>();

	    row.setOnMouseClicked(event -> {

		if ((event.getClickCount() == 2) && (!row.isEmpty())) {

		    try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("DisplayUserOrder.fxml"));
			Parent root = loader.load();
			btnHome.getScene().setRoot(root);

			DisplayUserOrderController duo = loader.getController();
			final UserOrderCombined selectedItem = tabStats.getSelectionModel().getSelectedItem();

			try {
			    duo.setBy(selectedItem.getFullName());
			    duo.setOrderNum(selectedItem.getOrderNumber() + "");
			    duo.setPayType(selectedItem.getPayType());
			    duo.setStatus(selectedItem.getStatusOrder());
			    duo.setSubAt(selectedItem.getCreatedDTM().toString());
			    duo.setSum(selectedItem.getTot());
			} catch (NumberFormatException n) {
			    System.out.println(selectedItem);
			}

		    } catch (IOException ex) {
			Logger.getLogger(OrderListsAdminController.class.getName()).log(Level.SEVERE, null, ex);
		    }
		}
	    });

	    return row;
	});

	String[] sortingTypes = {"TOTAL ▲", "TOTAL ▼", "Status ▲", "Status ▼", "Payment Date ▲", "Payment Date ▼"};

	ObservableList<String> values = FXCollections.observableArrayList(sortingTypes);

	cbxSort.setItems(values);

    }

    @FXML
    void Home(ActionEvent event) {

	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
	    Parent root = loader.load();
	    btnHome.getScene().setRoot(root);
	} catch (IOException ex) {
	    Logger.getLogger(OrderListsAdminController.class.getName()).log(Level.SEVERE, null, ex);
	}

    }

    @FXML
    void searchBySubDate(ActionEvent event) {

	String subDate = dateSub.getValue().toString();

	tabStats.setItems(orders.filtered(p -> p.getCreatedDTM().toLocalDate().toString().equals(subDate)));

    }

    private ObservableList<UserOrderCombined> filterByFullName(ObservableList<UserOrderCombined> orders, String g4) {

	return orders.filtered(p -> p.getFullName().contains(g4));

    }

    private ObservableList<UserOrderCombined> filterStatus(ObservableList<UserOrderCombined> orders, String g4) {

	return orders.filtered(p -> p.getStatusOrder().equals(g4));
    }

    private ObservableList<UserOrderCombined> filterTotal(ObservableList<UserOrderCombined> orders, Float g4) {

	return orders.filtered(p -> p.getTot().equals(g4));
    }

    @FXML
    void sortTable(ActionEvent event) {

	switch (cbxSort.getValue()) {

	    case "TOTAL ▲":
		tabStats.setItems(orders.sorted((o1, o2) -> o1.getTot().compareTo(o2.getTot())));
		break;
	    case "TOTAL ▼":
		tabStats.setItems(orders.sorted((o1, o2) -> -1 * o1.getTot().compareTo(o2.getTot())));
		break;
	    case "Status ▲":
		tabStats.setItems(orders.sorted((o1, o2) -> o1.getStatusOrder().compareTo(o2.getStatusOrder())));
		break;
	    case "Status ▼":
		tabStats.setItems(orders.sorted((o1, o2) -> -1 * o1.getStatusOrder().compareTo(o2.getStatusOrder())));
		break;
	    case "Payment Date ▲":
		tabStats.setItems(orders.sorted((o1, o2) -> o1.getPayDTM().compareTo(o2.getPayDTM())));
		break;
	    case "Payment Date ▼":
		tabStats.setItems(orders.sorted((o1, o2) -> -1 * o1.getPayDTM().compareTo(o2.getPayDTM())));
		break;
	    default:
		break;

	}

    }
}
