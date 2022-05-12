/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.gui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import java.util.List;
import tn.nebulagaming.entities.Product;
import tn.nebulagaming.services.ServiceProduct;

/**
 *
 * @author anony
 */
public class ListProducts extends BaseForm {

    private ServiceProduct productService = ServiceProduct.getInstance();

    private List<Product> productList;

    public ListProducts(Resources res) {

	super("Shop", BoxLayout.y());

	super.addSideMenu(res);

	productList = productService.getAllProducts();

	if (!productList.isEmpty()) {

	    Container main = new Container(BoxLayout.y());

	    main.setScrollableY(true);

	    for (Product product : productList) {
		Container c = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		Button btnAdd = new Button("Details");

		btnAdd.addActionListener((ActionListener) (ActionEvent evt) -> {
		    new ShowProduct(res, product.getIdproduct()).show();
		});

		c.addAll(
			new SpanLabel(product.getNameproduct()),
			new Label(product.getIdCategory().getNameCategory()),
			new Label(product.getQtyproduct() + " TND"),
			btnAdd
		);
		main.add(c);
	    }

	    add(main);
	} else {
	    add(new Label("Nothing in here..."));
	}

    }

}
