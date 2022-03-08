/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.nebulagaming.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Matcher;
import tn.nebulagaming.services.ServiceUser;

public class UserUtiles {

    Connection cnx;

    public UserUtiles() {
	cnx = GlobalConfig.getInstance().getCONNECTION();
    }

    public String crypterPassword(String password) {
	String hashValue = "";
	try {

	    MessageDigest messageDigest = MessageDigest.getInstance("MD5");
	    messageDigest.update(password.getBytes());
	    byte[] digestedBytes = messageDigest.digest();
	    hashValue = DatatypeConverter.printHexBinary(digestedBytes).toLowerCase();

	} catch (NoSuchAlgorithmException e) {
	}

	return hashValue;
    }

    public boolean testNom(String nom) {

	Matcher matcher = Constantes.VALID_NAME_REGEX.matcher(nom);
	return matcher.find();

    }

    public boolean testNumTelephonique(String tel) {
	int i;
	String[] tab = {"0", "1", "4", "6", "8"};
	for (i = 0; i < tab.length; i++) {
	    if (tel.charAt(0) == tab[i].charAt(0)) {
		return false;
	    }
	}
	return true;
    }

    public boolean testTel(String tel) {

	int i, length;
	length = tel.length();
	if (length != 8) {
	    return false;
	}
	for (i = 0; i < length; i++) {

	    if ((!(tel.charAt(i) >= '0' && tel.charAt(i) <= '9')) || (testNumTelephonique(tel) == false)) {
		return false;
	    }
	}
	return true;

    }

    public boolean testEmail(String mail) {
	Matcher matcher = Constantes.VALID_EMAIL_ADDRESS_REGEX.matcher(mail);
	return matcher.find();
    }

    public boolean testPassword(String password) {
	Matcher matcher = Constantes.VALID_PASSWORD_REGEX.matcher(password);
	return matcher.find();
    }

    public void information_Box(String title, String message) {
	Alert dg = new Alert(Alert.AlertType.INFORMATION);
	dg.setTitle(title);
	dg.setContentText(message);
	dg.show();
    }

    public void alert_Box(String title, String message) {
	Alert dg = new Alert(Alert.AlertType.WARNING);
	dg.setTitle(title);
	dg.setContentText(message);
	dg.show();
    }

    public boolean check_Box(String title, String message) {
	boolean sortie = false;
	Alert.AlertType Type = Alert.AlertType.CONFIRMATION;
	Alert alert = new Alert(Type, "");
	alert.initModality(Modality.APPLICATION_MODAL);
	alert.setTitle(title);
	alert.setContentText(message);
	Optional<ButtonType> result = alert.showAndWait();
	if (result.get() == ButtonType.OK) {
	    sortie = true;
	} else if (result.get() == ButtonType.CANCEL) {
	    sortie = false;
	}

	return sortie;

    }

    public String randomString() {
	int leftLimit = 97; // letter 'a'
	int rightLimit = 122; // letter 'z'
	int targetStringLength = 10;
	Random random = new Random();

	String generatedString = random.ints(leftLimit, rightLimit + 1).limit(targetStringLength).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
	return generatedString;
    }

    /* public String[] loadSession() {
        StringBuffer sb = new StringBuffer();
        String logs[] = new String[2];
        try {
            String fileName = fast_login;
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            for (int i = 0; i < 2; i++) {
                logs[i] = br.readLine();
//System.out.println(s[i]);

            }
            br.close();
            return logs;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return logs;
    }*/

 /* public void saveSession(String mail, String pwd) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fast_login));
            bw.write(mail);
            bw.write("\n");
            bw.write(pwd);
            bw.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }*/
    public void codeConfirmation(String code, String email) {

	ServiceUser sU = new ServiceUser();
	JavaMail uMail = new JavaMail();
	UserUtiles uUtiles = new UserUtiles();

	if (!uUtiles.testEmail(email) || !sU.verifierEmailBd(email)) {
	    System.out.println("Verifier adresse Veillez saisir une adresse mail valide");
	} else {

	    uMail.sendTextMail("code modification :", email, "voici votre code  " + code + "  ");

	}

    }

    public boolean verifCode(String codeSent, String codeEntre) {
	UserUtiles uUtiles = new UserUtiles();

	if (!codeSent.equals(codeEntre)) {
	    System.out.println("Verifier Veillez verifier le code ");
	    return false;
	} else {
	    System.out.println("VCode verifier Vous pouvez desoermer changer votre mot de passe");
	    return true;
	}

    }
}
