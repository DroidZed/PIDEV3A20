/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.gui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import tn.nebulagaming.entities.CartItem;
import tn.nebulagaming.services.ShoppingCartService;

/**
 *
 * @author ASUS
 */
public class ShoppingCart extends BaseForm {

    private final ShoppingCartService shoppingCartService = ShoppingCartService.getInstance();

    public ShoppingCart(Resources res) {

	super("Shopping Cart", BoxLayout.y());

	super.addSideMenu(res);

	Button checkOutBtn = new Button("Check out with subtotal: " + shoppingCartService.getSum() + " TND");

	checkOutBtn.addActionListener(e -> {

	    new CheckOutOrderForm(res).show();
	});

	ArrayList<CartItem> items = shoppingCartService.collectCartItems();

	if (!items.isEmpty()) {
	    Container main = new Container(BoxLayout.y());

	    main.setScrollableY(true);

	    main.add(checkOutBtn);
	    for (CartItem item : items) {
		Container c = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		Button btnRem = new Button("-");
		Button btnAdd = new Button("+");

		btnRem.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent evt) {

			Dialog.show("Notif",
				shoppingCartService.takeFromCart(item).getMessage(),
				"OK",
				null);

			new ShoppingCart(res).show();
		    }

		});

		btnAdd.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent evt) {

			Dialog.show("Notif",
				shoppingCartService.addToCart(item.getProduct()).getMessage(),
				"OK",
				null);

			new ShoppingCart(res).show();
		    }

		});

		Container cBtn = new Container(new FlowLayout());

		cBtn.addAll(btnAdd, btnRem);

		c.addAll(
			new SpanLabel(item.getProduct().getNameproduct()),
			new Label(item.getProduct().getIdCategory().getNameCategory()),
			new Label(item.getProduct().getPriceproduct() *  item.getQuantity() + " TND"),
			new Label("Quantity:" + item.getQuantity()),
			cBtn
		);
		main.add(c);
	    }

	    add(main);

	} else {
	    add(new Label("Empty list..."));
	}
    }
}
