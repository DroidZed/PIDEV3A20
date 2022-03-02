/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tn.nebulagaming.views;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import tn.nebulagaming.services.OrderLineService;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class DisplayUserOrderController implements Initializable {

    @FXML
    private Label orderNum;
    @FXML
    private Label subAt;
    @FXML
    private Label status;
    @FXML
    private Label by;

    @FXML
    private Label sum;

    @FXML
    private JFXButton btnBack;

    @FXML
    private Label payType;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	// TODO

	
	
    }

    public void setOrderNum(String orderNum) {
	this.orderNum.setText(orderNum);

	OrderLineService ord = new OrderLineService();

	this.sum.setText(this.sum.getText()+" "+ ord.calculateTotal(Integer.parseInt(orderNum)));
    }

    public void setSubAt(String subAt) {
	this.subAt.setText(subAt);
    }

    public void setStatus(String status) {
	this.status.setText(status);
    }

    public void setBy(String by) {
	this.by.setText(by);
    }

    public void setPayType(String payType) {
	this.payType.setText(payType);
    }
    

    @FXML
    private void GoBack(ActionEvent event) {

	try {

	    FXMLLoader loader = new FXMLLoader(getClass().getResource("OrderListsAdmin.fxml"));

	    Parent root = loader.load();

	    btnBack.getScene().setRoot(root);

	} catch (IOException ex) {
	    System.out.println(ex.getMessage());
	}
    }

}
