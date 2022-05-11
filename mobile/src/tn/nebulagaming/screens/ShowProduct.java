/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.screens;

import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import tn.nebulagaming.entities.Product;
import tn.nebulagaming.services.ServiceProduct;

/**
 *
 * @author anony
 */
public class ShowProduct extends BaseForm {

    private final ServiceProduct productService = ServiceProduct.getInstance();

    Product prod;

     public ShowProduct(Resources res, int idProd) {

	super("Products list",BoxLayout.y());

	prod = productService.getById(idProd);

	setLayout(BoxLayout.y());

	setTitle("Product Details");

	add(new Label("Product Name: " + prod.getNameproduct()));

	add(new Label("Product Price: " + prod.getPriceproduct()));

	add(new Label("Product Category: " +prod.getIdCategory().getNameCategory()));

	add(new Label("Product Quantity: " + prod.getQtyproduct()));

	add(new Label("Publiser: " + prod.getIdUser().getNameuser()));
	
    }

}
