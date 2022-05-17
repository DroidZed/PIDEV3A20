/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.nebulagaming.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;

import tn.nebulagaming.services.ServiceMembre;
import tn.nebulagaming.services.ServiceEntreprise;
import tn.nebulagaming.services.ServiceUser;
import java.io.IOException;
import tn.nebulagaming.entities.Entreprise;
import tn.nebulagaming.entities.Membre;
import tn.nebulagaming.utils.RoleEnum;
import tn.nebulagaming.utils.SingletonUser;

/**
 *
 * @author ibeno
 */
public class Login1 extends BaseForm {

    private SingletonUser singUser = SingletonUser.getInstance();

    Form current;

    public static String username = "";

    public Login1(Resources res) {
	current = this; //Récupération de l'interface(Form) en cours
	setTitle("Acceuil");
	setLayout(BoxLayout.y());

	Button InscritMembre = new Button("Inscription entreprise");
	Button InscritEnt = new Button("Inscription membre");
	FontImage.setMaterialIcon(InscritMembre, FontImage.MATERIAL_CREATE);
	FontImage.setMaterialIcon(InscritEnt, FontImage.MATERIAL_CREATE);

	Button seConnecter = new Button("Se connecter");
	FontImage.setMaterialIcon(seConnecter, FontImage.MATERIAL_LOGIN);

	TextField email = new TextField("", "Adresse Email");
	TextField password = new TextField("", "Mot de passe");

	Button forgetPassword = new Button("mot de passe oublié");

	password.setConstraint(TextField.PASSWORD);

	seConnecter.addActionListener((e) -> {
	    String result = ServiceUser.getInstance().loginCheck(email.getText(), password.getText());

	    if ("1".equals(result)) {
		Entreprise etu = new Entreprise();
		this.username = email.getText();
		etu = ServiceEntreprise.getInstance().getUser(this.username);

		singUser.setIdUser(etu.getId());
		singUser.setUsername(etu.getNom());
		singUser.setRole(RoleEnum.ENTTERPRISE);
		singUser.setTel(etu.getTel());
		singUser.setEmail(etu.getEmail());
		System.out.println("login entreprise singleton: "+singUser);

		Dialog d = new Dialog("Bienvenue");

		TextArea popupBody = new TextArea("Salut " + etu.getNom().toUpperCase() + " L'equipe est heureuse de te souhaiter la bienvenue.", 3, 20);
		popupBody.setUIID("PopupBody");
		popupBody.setEditable(false);
		d.setLayout(new BorderLayout());
		d.add(BorderLayout.CENTER, popupBody);
		try {
		    new ProfilEntreprise(res, this.username).show();
		} catch (IOException ex) {
		}
	    } else if ("2".equals(result)) {
		Membre ent = new Membre();
		this.username = email.getText();
		ent = ServiceMembre.getInstance().getMem(this.username);

		singUser.setIdUser(ent.getId());
		singUser.setUsername(ent.getNom());
		singUser.setRole(RoleEnum.MEMBER);
		singUser.setTel(ent.getTel());
		singUser.setEmail(ent.getEmail());
		System.out.println("login user singleton: "+singUser);

		Dialog d = new Dialog("Bienvenue");

		TextArea popupBody = new TextArea("Salut " + ent.getNom().toUpperCase() + " L'equipe  est heureuse de te souhaiter la bienvenue.", 3, 20);
		popupBody.setUIID("PopupBody");
		popupBody.setEditable(false);
		d.setLayout(new BorderLayout());
		d.add(BorderLayout.CENTER, popupBody);
		System.out.println(ent.getDescription());
		try {
		    new ProfilMembre(this.username, res).show();
		} catch (IOException ex) {
		}
	    } else {
		Dialog.show("Attention", "Veuillez verifier vos données ", new Command("OK"));

	    }

	}
	);

	forgetPassword.addActionListener((e) -> {

	    new ForgetPasswordCheck(res, current).show();

	});

	InscritMembre.addActionListener((e) -> {

	    new InscriEntreprise(current).show();

	});

	InscritEnt.addActionListener((e) -> {

	    new InscriMembre(current).show();

	});

	addAll(email, password, seConnecter, forgetPassword, InscritMembre, InscritEnt);

    }

}
