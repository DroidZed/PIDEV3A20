package tn.nebulagaming.gui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import java.util.List;
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

    private final List<Wishlist> wishListItems = wishListService.getWishedItems();;

    public DisplayWishListForm(Resources theme) {

	super("My Wishlist", BoxLayout.y());

	super.addSideMenu(theme);

	if (!wishListItems.isEmpty()) {

	    Container main = new Container(BoxLayout.y());

	    main.setScrollableY(true);

	    for (Wishlist item : wishListItems) {
		Container c = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		Button btnRem = new Button("Remove");

		btnRem.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent evt) {

			Dialog.show("Notif",
				wishListService.removeItem(item.getIdwishlist()).getMessage(),
				"OK",
				null);

			new DisplayWishListForm(theme).show();
		    }

		});

		c.addAll(
			new SpanLabel(item.getIdproduct().getNameproduct()),
			new Label(item.getIdproduct().getIdCategory().getNameCategory()),
			new Label(item.getIdproduct().getQtyproduct() + " TND"),
			btnRem
		);
		main.add(c);
	    }

	    add(main);

	} else {
	    add(new Label("Empty list..."));
	}
    }

}
