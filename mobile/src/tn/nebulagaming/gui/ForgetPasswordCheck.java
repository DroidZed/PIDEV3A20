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
import com.codename1.ui.TextField;
import com.codename1.ui.util.Resources;

import tn.nebulagaming.services.ServiceUser;

import static java.lang.String.valueOf;
import java.util.Random;
import tn.nebulagaming.utils.Validators;

/**
 *
 * @author ibeno
 */
public class ForgetPasswordCheck extends BaseForm {

    Form current;
    public static String username;
    public static String verificationCode;

    public ForgetPasswordCheck() {
    }

    public ForgetPasswordCheck(Resources res, Form previous) {

	// n.setAlertSound("/notification_sound_bells.mp3"); //file name must begin with notification_sound
//        Message m = new Message("Body of message");
//        Display.getInstance().sendMessage(new String[] {"seifeddine.bensalah@gmail.com"}, "Subject of message", m);
	current = this;
	setTitle("Mot de passe oublié");
	Validators v = new Validators();
	Random random = new Random();

	TextField email = new TextField("", "Adresse Email");

	//cnt.add(show).add(showPopup);
	Button verifier = new Button("verifier");

	verifier.addActionListener((e) -> {

	    if (email.getText().length() == 0) {
		Dialog.show("Attention", "Veuillez verifier vos parametres ", new Command("OK"));
	    } else {
		this.username = email.getText();
		this.verificationCode = ServiceUser.getInstance().forgetPasswordCheck(username).toString();
		System.err.println(this.verificationCode);

		Dialog.show("Code de Verification", "Veuillez verifier votre courrier", new Command("OK"));

		new VerificationCodeForgetPassword(res, current).show();
	    }

	});

	addAll(email, verifier);

	getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());

    }
}
