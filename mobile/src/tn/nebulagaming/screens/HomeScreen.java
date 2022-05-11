/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.screens;

import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;

/**
 *
 * @author ASUS
 */
public class HomeScreen extends Form {

    Form current;

    public HomeScreen(Resources theme) {
	    
	current  = this;

	setTitle("Vente");

	setLayout(BoxLayout.y());

	add(new Label("<- Swipe left to being !"));

	Toolbar tb = getToolbar();

	Image icon = theme.getImage("nebula_firstshot.png");

	Container topBar = BorderLayout.east(new Label(icon));

	topBar.add(BorderLayout.SOUTH, new Label("Nebula Gaming"));

	topBar.setUIID("SideCommand");
	tb.addComponentToSideMenu(topBar);

	tb.addMaterialCommandToSideMenu("Home", FontImage.MATERIAL_HOME, e -> {
	     this.showBack();
	});

	tb.addMaterialCommandToSideMenu("My Wishlist", FontImage.MATERIAL_LIST, e -> {
	     new DisplayWishListForm(theme).show();
	});
	tb.addMaterialCommandToSideMenu("Products List", FontImage.MATERIAL_WEB, e -> {
	     new ShowProductsList(theme).show();
	});
	
    }
    
}
