/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.pidev.gui;

import com.codename1.capture.Capture;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.util.ImageIO;
import edu.esprit.pidev.entities.Membre;
import edu.esprit.pidev.service.ServiceMembre;
import edu.esprit.pidev.utils.Statics;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.String.valueOf;
import static edu.esprit.pidev.utils.Statics.*;
import java.util.Random;
import static java.util.concurrent.ThreadLocalRandom.current;

/**
 *
 * @author ibeno
 */
public class ProfilMembre extends Form {

    Form current;

    public static String username;
    public Membre membre;

    public ProfilMembre(Form previous) {
        current = this; //Récupération de l'interface(Form) en cours
        setTitle("Profil Membre");

    }

    public ProfilMembre(String username) throws IOException {

        this.username = username;
        this.membre = ServiceMembre.getInstance().getMembre(this.username);

        Dialog d = new Dialog("Bienvenue");
        TextArea popupBody = new TextArea("Salut " + this.membre.getNom().toUpperCase() + " Bienvenu dans votre profil.", 3, 20);
        popupBody.setUIID("PopupBody");
        popupBody.setEditable(false);
        d.setLayout(new BorderLayout());
        d.add(BorderLayout.CENTER, popupBody);
        current = this; //Récupération de l'interface(Form) en cours
        setTitle("Modifer vos parametres");

       Image image = Image.createImage(PATH + this.membre.getPhoto());
        int w = image.getWidth();
        int h = image.getHeight();
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
        Button uploadPicture = new Button("Modifier Votre photo");

        uploadPicture.addActionListener((ActionEvent e) -> {
            
            try {
                String imgPath = Capture.capturePhoto();
                String link = imgPath.toString();
                int pod = link.indexOf("/", 4);
                String news = link.substring(pod + 36, link.length());
Membre ent = new Membre( nom.getText(),this.membre.getEmail(), tel.getText(), description.getText());
                ent.setPhoto(news);
                
if (ServiceMembre.getInstance().modifierMembre(ent)) {
                Dialog.show("Success", "Modification effectuée", new Command("OK"));
            } else {
                Dialog.show("ERROR", "Server error", new Command("OK"));
            }
//              
            } catch (Exception ex) {
                ex.printStackTrace();
            }

     try {
                    new ProfilMembre(this.username).show();
                } catch (IOException ex) {
                }
            
        }
                
        );

        btnModifier.addActionListener((e) -> {

            Membre ent = new Membre( nom.getText(),this.membre.getEmail(), tel.getText(), description.getText(),this.membre.getPhoto());
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
        Button btnDeconnexion= new Button("Deconnexion");
        btnDeconnexion.addActionListener((e) -> {
        Login1 login = new Login1();
        this.membre=null;
        this.username=null;
        login.show();
        });
        Button capture = new Button();
        FontImage.setMaterialIcon(capture, FontImage.MATERIAL_PHOTO_CAMERA);

        capture.addActionListener((e) -> {
            Image screenshot = Image.createImage(this.getWidth(), this.getHeight());
            this.revalidate();
            this.setVisible(true);
            this.paintComponent(screenshot.getGraphics(), true);
            String imageFile = PATH + "screenshot" + valueOf(new Random().nextInt()).substring(1, 6) + ".png";
            FileSystemStorage.getInstance().getAppHomePath();
            try (OutputStream os = FileSystemStorage.getInstance().openOutputStream(imageFile)) {
                ImageIO.getImageIO().save(screenshot, os, ImageIO.FORMAT_PNG, 1);
            } catch (IOException err) {
                Log.e(err);
            }
        });
        System.out.println(description + "la description");
        addAll(photo,nom, tel,description, btnModifier, capture,btnModifierPassword,btnDeconnexion,uploadPicture);

    }

}
