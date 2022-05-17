/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.nebulagaming.gui;

import com.codename1.capture.Capture;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;
import tn.nebulagaming.entities.Membre;
import tn.nebulagaming.services.ServiceMembre;
import tn.nebulagaming.services.ServiceUser;
import tn.nebulagaming.utils.Validators;

/**
 *
 * @author ibeno
 */
public class InscriMembre extends Form {

    String photo = "default.jpg";

    public InscriMembre(Form previous) {
	/*
        Le paramètre previous définit l'interface(Form) précédente.
        Quelque soit l'interface faisant appel à AddTask, on peut y revenir
        en utilisant le bouton back
	 */
	setTitle("Inscription Membre");
	setLayout(BoxLayout.y());

	TextField nomMembre = new TextField("", "nom");
	TextField mail = new TextField("", "mail");
	TextField password = new TextField("", "password");
	TextField confirmer_password = new TextField("", "Confirmer mot de passe");
	TextField tel = new TextField("", "tel");
	TextField description = new TextField("", "description");
	Button btnAjoutPhoto = new Button("ajouter une photo");

	Button btnValider = new Button("s'inscrire");

	password.setConstraint(TextField.PASSWORD);
	confirmer_password.setConstraint(TextField.PASSWORD);

	btnAjoutPhoto.addActionListener((ActionEvent e) -> {

	    String imgPath = Capture.capturePhoto();
	    System.out.println(imgPath);

	    int pod = imgPath.indexOf("/", 4);
	    System.out.println(pod);

	    String news = imgPath.substring(pod + 35, imgPath.length());
	    System.out.println(news);

	    btnValider.addActionListener((et) -> {
		Validators v = new Validators();
		System.out.println(tel.getText());
		System.out.println(v.test_Email(mail.getText()) + " " + v.test_num_telephonique(tel.getText()) + " " + v.test_Password(password.getText()) + " " + !nomMembre.getText().equals(""));
		if (v.test_Email(mail.getText()) && v.test_num_telephonique(tel.getText()) && v.test_Password(password.getText()) && !nomMembre.getText().equals("")) {

		    Membre ent = new Membre(nomMembre.getText(), mail.getText(), password.getText(), tel.getText(), this.photo, "[\"ROLE_MEMBRE\"]", description.getText(), "0");
		    String result = ServiceUser.getInstance().checkUserUnique(ent);
		    System.out.println(result);
		    if (result.equals("1")) {
			ServiceMembre.getInstance().addMembre(ent);
			Dialog.show("Success", "Membre ajouté avec succees", new Command("OK"));
		    } else {
			Dialog.show("failed", "Email existant", new Command("OK"));
		    }
		} else {
		    Dialog.show("failed", "vérifiez vos paramètres", new Command("OK"));
		}

	    });

	    addAll(nomMembre, mail, password, confirmer_password, tel, description, btnValider, btnAjoutPhoto);
	    getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK,
		    et -> previous.showBack()); // Revenir vers l'interface précédente
	});
    }
}
