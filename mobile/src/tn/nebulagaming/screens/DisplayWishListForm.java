package tn.nebulagaming.screens;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import java.util.List;
import tn.nebulagaming.entities.JsonResponseDAO;
import tn.nebulagaming.entities.Wishlist;
import tn.nebulagaming.services.WishlistService;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author ASUS
 */
public class DisplayWishListForm extends BaseForm {

    private WishlistService wishListService = WishlistService.getInstance();

    private List<Wishlist> wishListItems;

    public DisplayWishListForm(Resources theme) {

	super("My Wishlist", BoxLayout.y());
        
        super.addSideMenu(theme);

	Toolbar tb = getToolbar();

	Image icon = theme.getImage("nebula_firstshot.png");

	Container topBar = BorderLayout.east(new Label(icon));

	topBar.add(BorderLayout.SOUTH, new Label("Nebula Gaming"));

	topBar.setUIID("SideCommand");
	tb.addComponentToSideMenu(topBar);

	tb.addMaterialCommandToSideMenu("Home", FontImage.MATERIAL_HOME, e -> {
	    new HomeScreen(theme).show();
	});

	tb.addMaterialCommandToSideMenu("My Wishlist", FontImage.MATERIAL_LIST, e -> {
	    this.showBack();
	});
	tb.addMaterialCommandToSideMenu("Products List", FontImage.MATERIAL_WEB, e -> {
	    new ShowProductsList(theme).show();
	});

	wishListItems = wishListService.getWishedItems(1);

	if (!wishListItems.isEmpty()) {

	    Container main = new Container(BoxLayout.y());

	    main.setScrollableY(true);

	    for (Wishlist item : wishListItems) {
		Container c = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		Button btnRem = new Button("Remove");

		btnRem.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent evt) {
			JsonResponseDAO delOperation = WishlistService.getInstance().removeItem(item.getIdwishlist());
			Dialog.show("Information", delOperation.getMessage(), "OK", null);

			current.repaint();

		    }

		});

		c.addAll(
			new SpanLabel(item.getIdproduct().getNameproduct()),
			new Label(item.getIdproduct().getIdcategory().getNamecategory()),
			new Label(item.getIdproduct().getQtyproduct() + " TND"),
			btnRem
		);
		main.add(c);
	    }

	    add(main);

	} else {
	    add(new Label("Empty list..."));
	}

	getContentPane()
		.addPullToRefresh(() -> {
		    wishListItems = wishListService.getWishedItems(1);
		    current.repaint();
		}
		);

	// String delOperation = WishlistService.getInstance().removeItem(idWishLisht);
	// Dialog.show("Alert !", delOperation.getMessage(), "OK", null);
    }

}
