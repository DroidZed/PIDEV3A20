/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import tn.nebulagaming.entities.Category;
import tn.nebulagaming.entities.Product;
import tn.nebulagaming.services.CategoryService;
import tn.nebulagaming.services.ServiceProduct;

/**
 *
 * @author anony
 */
public class AddProduct extends BaseForm {

    private static final CategoryService catService = CategoryService.getInstance();
    private static final ServiceProduct serviceProd = ServiceProduct.getInstance();

    public AddProduct(Resources res) {

	super("Add Product", BoxLayout.y());

	Toolbar tb = new Toolbar(true);
	setToolbar(tb);
	getTitleArea().setUIID("Container");
	setTitle("Publish Product");
	getContentPane().setScrollVisible(false);

	super.addSideMenu(res);

	tb.addSearchCommand(e -> {
	});

	Image img = res.getImage("profile-background.jpg");
	if (img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
	    img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
	}
	ScaleImageLabel sl = new ScaleImageLabel(img);
	sl.setUIID("BottomPad");
	sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

	add(LayeredLayout.encloseIn(
		sl,
		BorderLayout.south(
			GridLayout.encloseIn(3,
				FlowLayout.encloseCenter(
					new Label(res.getImage("profile-pic.jpg"), "PictureWhiteBackgrond"))
			)
		)
	));

	Label pNameL = new Label("Details about your product");

	Label qtityL = new Label("Quantity: ");

	Label priceL = new Label("Price: ");

	Label categoryL = new Label("Category: ");

	ComboBox cbxCategory = new ComboBox();

	for (Category cat : catService.getAllCategories()) {
	    cbxCategory.addItem(cat.getNameCategory());
	}

	TextField tfN = new TextField("", "Name of your product");
	TextField tfQ = new TextField("", "Quantity of your product");
	TextField tfP = new TextField("", "Price of your product");

	Button submit = new Button("Submit");

	submit.addActionListener(e -> {
	    String name = tfN.getText();
	    int quant = Integer.parseInt(tfQ.getText());
	    float price = Float.parseFloat(tfP.getText());
	    String category = cbxCategory.getSelectedItem().toString();
	    System.out.println(name + "\n" + quant + "\n" + price + "\n" + category);
	    Dialog.show("Info",
		    serviceProd.addProduct(new Product(name, price, quant), category).getMessage(),
		    "OK",
		    null);
	});

	addAll(pNameL, tfN, qtityL, tfQ, priceL, tfP, categoryL, cbxCategory, submit);
    }

}
