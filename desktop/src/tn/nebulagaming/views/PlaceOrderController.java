/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tn.nebulagaming.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javax.swing.JOptionPane;
import tn.nebulagaming.models.UserOrder;
import tn.nebulagaming.services.OrderLineService;
import tn.nebulagaming.services.UserOrderService;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class PlaceOrderController implements Initializable {

    @FXML
    private JFXButton paybtn;
    @FXML
    private JFXListView<UserOrder> orderList;
    @FXML
    private Label total;

    private int orderNumber;

    private UserOrderService usr ;

    private OrderLineService ord ;

    private ObservableList orders;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	// TODO
	usr = new UserOrderService();

	ord = new OrderLineService();

	orders = FXCollections.observableArrayList(usr.getOfUser(1));

	orderList.setItems(orders);

	this.total.setText(""+ord.calculateTotal(orderNumber));
    }    

    @FXML
    private void PayCheckout(ActionEvent event) {

	usr.updateOnPayment(usr.getOne(orderNumber, orderNumber), orderNumber);

	JOptionPane.showMessageDialog(null, "Payment successful !!");
    }
    
}
