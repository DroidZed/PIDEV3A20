/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tn.nebulagaming.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javax.swing.JOptionPane;
import tn.nebulagaming.models.UserOrder;
import tn.nebulagaming.models.UserOrderCombined;
import tn.nebulagaming.services.UserOrderService;
import tn.nebulagaming.utils.JavaMail;

/**
 * FXML Controller class
 *
 * @author Aymen Dhahri
 */
public class CreditCardPaymentController implements Initializable {

    @FXML
    private Label totalPrice;
    @FXML
    private JFXPasswordField ccvTF;
    @FXML
    private JFXTextField monthTF;
    @FXML
    private JFXTextField yearTF;
    @FXML
    private JFXTextField carteTF;
    @FXML
    private JFXTextField emailTF;
    @FXML
    private JFXButton sendMailBTN;

    private JavaMail mailer;

    private UserOrderCombined userOrderJoined;

    private UserOrderService usr;

    public void setUserOrder(UserOrderCombined userOrderJoined) {

	try {

	    this.userOrderJoined = userOrderJoined;

	    this.totalPrice.setText(userOrderJoined.getTot().toString());

	    UserOrder oneValue = usr.getOne(userOrderJoined.getOrderNumber(), userOrderJoined.getIdUser());

	    System.out.println(oneValue);

	    oneValue.setIdPayType(2);
	    oneValue.setPayDTM(Date.valueOf(LocalDate.now()));
	    oneValue.setIdStatusOrder(2);

	    usr.updateOnPayment(oneValue, userOrderJoined.getOrderNumber());
	} catch (NullPointerException ex) {
	    System.out.println(userOrderJoined.getIdUser() + " " + userOrderJoined.getOrderNumber());
	}
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	// TODO

	mailer = new JavaMail();

	usr = new UserOrderService();
    }

    @FXML
    private void sendReceipt(ActionEvent event) {

	JOptionPane.showMessageDialog(null, "Payment successful !!");
	mailer.sendTextMail("[NEBULA GAMING] Payment Notification",
		"aymen.dhahri@esprit.tn",
		"Payment successful for Order Number: " + this.userOrderJoined.getOrderNumber() + " with price: " + this.totalPrice.getText() + " TND.");
    }

}
