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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.nebulagaming.models.UserOrderCombined;
import tn.nebulagaming.services.UserOrderService;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class OrderListsAdminController implements Initializable {

    static final String SEARCH_REGEX = "(^[a-zA-Z ]+)(:) ([a-zA-Z/0-9\\. ]+)";

    @FXML
    private TextField tfSearch;

    @FXML
    private JFXButton btnHome;

    @FXML
    private DatePicker dateSub;

    private ObservableList<UserOrderCombined> orders;

    private Pattern pat;

    @FXML
    private JFXButton chkBtn;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	// TODO

	usr = new UserOrderService();

	pat = Pattern.compile(SEARCH_REGEX);

	orders = FXCollections.observableArrayList(usr.getAssociated());

	colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
	colSubAt.setCellValueFactory(new PropertyValueFactory<>("createdDTM"));
	colPaidAt.setCellValueFactory(new PropertyValueFactory<>("payDTM"));
	colPayT.setCellValueFactory(new PropertyValueFactory<>("payType"));
	colStatus.setCellValueFactory(new PropertyValueFactory<>("statusOrder"));

	tabStats.setItems(orders);

	tfSearch.textProperty().addListener((obs, oldValue, newValue) -> {

	    Matcher matcher = pat.matcher(newValue);
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
		    default:
			break;
		}
	    }

	    else {
		tabStats.setItems(orders);
	    }
	});

    }

    @FXML
    void Home(ActionEvent event) {

    }

    @FXML
    void checkOrderDetails(ActionEvent event) {

	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("DisplayUserOrder.fxml"));
	    Parent root = loader.load();
	    btnHome.getScene().setRoot(root);

	    DisplayUserOrderController duo = loader.getController();

	    try {
		duo.setBy(tabStats.getSelectionModel().getSelectedItem().getFullName());
		duo.setOrderNum(tabStats.getSelectionModel().getSelectedItem().getOrderNumber() + "");
		duo.setPayType(tabStats.getSelectionModel().getSelectedItem().getPayType());
		duo.setStatus(tabStats.getSelectionModel().getSelectedItem().getStatusOrder());
		duo.setSubAt(tabStats.getSelectionModel().getSelectedItem().getCreatedDTM().toString());
	    } catch (NumberFormatException n) {
		System.out.println(tabStats.getSelectionModel().getSelectedItem());
	    }

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

}
