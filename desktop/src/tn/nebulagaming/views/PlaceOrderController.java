/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tn.nebulagaming.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import tn.nebulagaming.models.FidelityCard;
import tn.nebulagaming.models.UserOrder;
import tn.nebulagaming.models.UserOrderCombined;
import tn.nebulagaming.services.FidelityCardService;
import tn.nebulagaming.services.UserOrderService;
import tn.nebulagaming.utils.JavaMail;

/**
 * FXML Controller class
 *
 * @author Aymen Dhahri
 */
public class PlaceOrderController implements Initializable {

    @FXML
    private JFXButton paybtn;
    @FXML
    private JFXListView<UserOrderCombined> orderList;
    @FXML
    private Label total;

    private UserOrderService usr;

    private ObservableList orders;

    @FXML
    private JFXButton backBtn;

    private UserOrderCombined selectedUserO;
    @FXML
    private JFXTextField tfAddr;
    @FXML
    private JFXComboBox<String> payTypeCbx;
    @FXML
    private Label thx;

    private JavaMail mailer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	// TODO
	usr = new UserOrderService();

	mailer = new JavaMail();

	orders = FXCollections.observableArrayList(usr.getAssociated().stream().filter(o -> o.getIdUser() == 1).filter(uo -> uo.getStatusOrder().equals("PENDING")).collect(Collectors.toList()));

	orderList.setItems(orders);

	String[] list = {"Credit Card", "Delivery"};

	ObservableList<String> payTypesList = FXCollections.observableArrayList(list);

	payTypeCbx.setItems(payTypesList);

	orderList.getSelectionModel().selectedItemProperty()
		.addListener((ObservableValue<? extends UserOrderCombined> observable, UserOrderCombined oldValue, UserOrderCombined newValue) -> {
		    this.selectedUserO = newValue;

		    this.total.setText("" + newValue.getTot());
		});
    }

    @FXML
    void PayCheckout(ActionEvent event) {

	try {

	    UserOrder oneValue = usr.getOne(selectedUserO.getOrderNumber(), selectedUserO.getIdUser());

	    oneValue.setOrderAddress(tfAddr.getText());

	    switch (payTypeCbx.getValue()) {
		case "Credit Card":
		    handleCCPayment();
		    break;

		case "Delivery":
		    handleDeliveryPayment(oneValue);
		    break;
	    }

	    FidelityCardService fdS = new FidelityCardService();

	    FidelityCard ofUser = fdS.getOfUser(1);

	    if (ofUser.getIdCardType() != 3) {

		fdS.addPoints(1, getRandomNumber(1, 10));

		if (ofUser.getNbPointsFid() > 100) {

		    ofUser.setIdCardType(2);

		    fdS.upgradeCardType(ofUser);

		} else if (ofUser.getNbPointsFid() > 200) {

		    ofUser.setIdCardType(3);

		    fdS.upgradeCardType(ofUser);
		}
	    }

	} catch (IOException ex) {
	    System.out.println(ex.getMessage());
	}
    }

    private int getRandomNumber(int min, int max) {
	return (int) ((Math.random() * (max - min)) + min);
    }

    private void handleDeliveryPayment(UserOrder oneValue) {
	oneValue.setIdPayType(3);
	oneValue.setIdStatusOrder(3);
	oneValue.setPayDTM(Date.valueOf(LocalDate.now().plusDays(2)));
	usr.updateOnPayment(oneValue, selectedUserO.getOrderNumber());

	mailer.sendTextMail("[NEBULA GAMING] Payment Notification",
		"aymen.dhahri@esprit.tn",
		"Payment successful for Order Number: " + selectedUserO.getOrderNumber() + " with price: " + selectedUserO.getTot() + " TND.");

	thx.setOpacity(1);
    }

    private void handleCCPayment() throws IOException {
	FXMLLoader loader = new FXMLLoader(getClass().getResource("CreditCardPayment.fxml"));
	Parent root = loader.load();
	backBtn.getScene().setRoot(root);

	CreditCardPaymentController ccp = loader.getController();

	ccp.setUserOrder(selectedUserO);
    }

    @FXML
    void goBack(ActionEvent event) {

	try {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
	    Parent root = loader.load();
	    backBtn.getScene().setRoot(root);
	} catch (IOException ex) {
	    System.out.println(ex.getMessage());
	}
    }

}
