/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.nebulagaming.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;

import tn.nebulagaming.services.ServiceMembre;
import java.io.IOException;
import tn.nebulagaming.entities.FidelityCard;
import tn.nebulagaming.entities.Membre;
import tn.nebulagaming.services.FidelityCardService;
import tn.nebulagaming.services.ShoppingCartService;
import static tn.nebulagaming.utils.Statics.TEMP_PATH;

/**
 *
 * @author ibeno
 */
public class ProfilMembre extends BaseForm {

    Form current;

    public static String username;
    public static Membre membre;
    private ShoppingCartService shoppingCartService = ShoppingCartService.getInstance();
    private FidelityCardService fidCardService = FidelityCardService.getInstance();

    public ProfilMembre(Form previous) {
	current = this; //Récupération de l'interface(Form) en cours
	setTitle("Profil Membre");

    }

    public ProfilMembre(String username, Resources res) throws IOException {

	super("Profil Membre", BoxLayout.y());

	getContentPane().setScrollVisible(false);

	current = this; //Récupération de l'interface(Form) en cours

	super.addSideMenu(res);

	this.username = username;
	this.membre = ServiceMembre.getInstance().getMembre(this.username);

	FidelityCard card = fidCardService.getUserFidCard();

	int w = current.getWidth() / 5;
	int h = current.getWidth() / 5;

	String type = card.getType();
	Image trophy = res.getImage(type.equals("GOLD") ? "trophies-icon-2.png" : type.equals("SILVER") ? "trophies-icon-3.png" : "trophies-icon-4.png");

	trophy = trophy.scaled(w, h);

	Label pts = new Label(card.getPoints() + "", trophy);

	Dialog d = new Dialog("Bienvenue");
	TextArea popupBody = new TextArea("Salut " + this.membre.getNom().toUpperCase() + " Bienvenu dans votre profil.", 3, 20);
	popupBody.setUIID("PopupBody");
	popupBody.setEditable(false);
	d.setLayout(new BorderLayout());
	d.add(BorderLayout.CENTER, popupBody);

	setTitle("Profile");

	Image image = Image.createImage(120, 120);

	try {
	    image = Image.createImage(TEMP_PATH + this.membre.getPhoto());

	} catch (Exception ex) {

	    System.out.println("here in the exception");

	    image = Image.createImage(TEMP_PATH + "default.png");
	}

	image = image.scaled(w, h);

	Image maskImage = Image.createImage(w, h);
	Graphics g = maskImage.getGraphics();
	g.setAntiAliased(true);
	g.setColor(0x000000);
	g.fillRect(0, 0, w, h);
	g.setColor(0xffffff);
	g.fillArc(0, 0, w, h, 0, 360);
	Object mask = maskImage.createMask();
	Image maskedImage = image.applyMask(mask);
	Label photo = new Label(maskedImage);

	TextField nom = new TextField(this.membre.getNom(), "Nom");
	TextField tel = new TextField(this.membre.getTel(), "tel");
	TextField description = new TextField(this.membre.getDescription(), "descuser");

	Button btnModifier = new Button("Confirmer");

	d.showPopupDialog(btnModifier);
	Button btnModifierPassword = new Button("Modifier mon mot de passe");

	btnModifier.addActionListener((e) -> {

	    Membre ent = new Membre(nom.getText(), this.membre.getEmail(), tel.getText(), description.getText(), this.membre.getPhoto());
	    System.out.println(ent.getEmail());

	    if (ServiceMembre.getInstance().modifierMembre(ent)) {
		Dialog.show("Success", "Modification effectuée", new Command("OK"));
	    } else {
		Dialog.show("ERROR", "Server error", new Command("OK"));
	    }
	});

	btnModifierPassword.addActionListener((e) -> {
	    ModifierPasswordMembre modiferPassword = new ModifierPasswordMembre(current, this.username);
	    // homeClient(current).show();
	    modiferPassword.show();
	});
	Button btnDeconnexion = new Button("Deconnexion");
	btnDeconnexion.addActionListener((e) -> {
	    Login1 login = new Login1(res);
	    this.membre = null;
	    this.username = null;
	    shoppingCartService.clearCart();
	    login.show();
	});

	System.out.println(description + "la description");
	addAll(photo, pts, nom, tel, description, btnModifier, btnModifierPassword, btnDeconnexion);

    }

}
