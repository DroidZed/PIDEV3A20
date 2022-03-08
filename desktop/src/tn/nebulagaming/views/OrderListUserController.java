/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tn.nebulagaming.views;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.nebulagaming.models.UserOrderCombined;
import tn.nebulagaming.services.UserOrderService;
import tn.nebulagaming.utils.GlobalConfig;

/**
 * FXML Controller class
 *
 * @author Aymen Dhahri
 */
public class OrderListUserController implements Initializable {

    private ObservableList<UserOrderCombined> orders;

    @FXML
    private TableView<UserOrderCombined> tabStats;
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

    private int idUser;
    @FXML
    private JFXButton btnHome;
    @FXML
    private Pagination paginationOrders;

    private UserOrderService usr;

    private GlobalConfig conf;


    public void setIdUser(int idUser) {
	this.idUser = idUser;
	
	conf = GlobalConfig.getInstance();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	// TODO
	usr = new UserOrderService();

	orders = FXCollections.observableArrayList(usr.getAssociated().stream().filter(o -> o.getIdUser() == conf.getSession()).collect(Collectors.toList()));

	colSubAt.setCellValueFactory(new PropertyValueFactory<>("createdDTM"));
	colPaidAt.setCellValueFactory(new PropertyValueFactory<>("payDTM"));
	colPayT.setCellValueFactory(new PropertyValueFactory<>("payType"));
	colStatus.setCellValueFactory(new PropertyValueFactory<>("statusOrder"));
	colTot.setCellValueFactory(new PropertyValueFactory<>("tot"));

	tabStats.setItems(orders);

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
	
    }

    @FXML
    void goBack(ActionEvent event) {

	try {

	    FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeMembre.fxml"));

	    Parent root = loader.load();

	    tabStats.getScene().setRoot(root);
	} catch (IOException ex) {
	    Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
	}

    }

}
