/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javax.swing.JOptionPane;
import tn.nebulagaming.utils.JavaMail;

/**
 * FXML Controller class
 *
 * @author Aymen Dhahri
 */
public class PaymentController implements Initializable {

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
    @FXML
    private Label totalPrice;

    private JavaMail mailer;

    public void setTotalPrice(String price) {
	this.totalPrice.setText(price);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	// TODO
    }

    @FXML
    private void sendReceipt(ActionEvent event) {

	JOptionPane.showMessageDialog(null, "Payment successful !!");
	mailer.sendTextMail("[NEBULA GAMING] Payment Notification", "aymen.dhahri@esprit.tn", "Payment successful !!");
    }

}
