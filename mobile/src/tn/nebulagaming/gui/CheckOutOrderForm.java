/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.gui;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import tn.nebulagaming.entities.Order;
import tn.nebulagaming.services.OrderService;
import tn.nebulagaming.services.ShoppingCartService;
import tn.nebulagaming.utils.SingletonUser;

/**
 *
 * @author ASUS
 */
public class CheckOutOrderForm extends BaseForm {

    private final ShoppingCartService shoppingCartService = ShoppingCartService.getInstance();
    private final SingletonUser singUser = SingletonUser.getInstance();
    private final OrderService orderService = OrderService.getInstance();

    public CheckOutOrderForm(Resources res) {

	super("Checkout Your Order", BoxLayout.y());

	super.addSideMenu(res);

	add(new Label("Tax fees: 8.00 TND"));
	add(new Label("Delivery (Standard): 15.00 TND"));

	add(new Label("Total sum: " + shoppingCartService.getSum() + 23));

	TextField tfName = new TextField(singUser.getUsername());
	tfName.setEnabled(false);

	TextField tfTel = new TextField(singUser.getTel());
	tfTel.setEnabled(false);

	TextField tfMail = new TextField(singUser.getEmail());
	tfMail.setEnabled(false);

	TextField tfAddr = new TextField("", "Shipping destination...");

	ComboBox cbxPayType = new ComboBox();

	cbxPayType.addItem("CREDIT_CARD");
	cbxPayType.addItem("DELIVERY");

	Button btnSubmit = new Button("Submit");

	btnSubmit.addActionListener(e -> {

	    Order o = new Order(singUser.getIdUser(), tfAddr.getText(), cbxPayType.getSelectedItem().toString());

	    Dialog.show("Notif", orderService.placeOrder(o).getMessage(), "OK",
		    null);

	});

	addAll(tfName, tfTel, tfMail, tfAddr, cbxPayType, btnSubmit);
    }
}
