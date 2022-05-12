/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.screens;

import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import tn.nebulagaming.entities.Product;
import tn.nebulagaming.services.ServiceProduct;
import tn.nebulagaming.services.WishlistService;

/**
 *
 * @author anony
 */
public class ShowProduct extends BaseForm {

    private final ServiceProduct productService = ServiceProduct.getInstance();
    private final WishlistService wishListService = WishlistService.getInstance();

    Product prod;

    public ShowProduct(Resources res, int idProd) {

	super("Products Details", BoxLayout.y());
        
        super.addSideMenu(res);

	prod = productService.getById(idProd);

	setLayout(BoxLayout.y());

	setTitle("Product Details");

	add(new Label("Product Name: " + prod.getNameproduct()));

	add(new Label("Product Price: " + prod.getPriceproduct()));

	add(new Label("Product Category: " + prod.getIdCategory().getNameCategory()));

	add(new Label("Product Quantity: " + prod.getQtyproduct()));

	add(new Label("Publiser: " + prod.getIdUser().getNameuser()));

	Button btnDel = new Button("Delete");

	Button btnWl = new Button("Add to wishlist");

	btnDel.addActionListener(e -> {

	    Dialog.show("Info",
		    productService.delProd(prod.getIdproduct()).getMessage(),
		    "OK",
		    null);
		    new ListProducts(res).show();
	});

	btnWl.addActionListener(e -> {

	    Dialog.show("Info",
		wishListService.addToWishlist(idProd, 1).getMessage(),
		        "OK",
		    null);

		new DisplayWishListForm(res).show();
	    
	});

	addAll(btnDel, btnWl);

    }

}
