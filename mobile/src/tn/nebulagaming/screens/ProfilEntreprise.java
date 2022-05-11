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
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.util.ImageIO;
import edu.esprit.pidev.entities.Entreprise;
import edu.esprit.pidev.service.ServiceEntreprise;
import static edu.esprit.pidev.utils.Statics.PATH;

import java.io.IOException;

import java.io.OutputStream;
import static java.lang.String.valueOf;

import java.util.Random;

/**
 *
 * @author ibeno
 */
public class ProfilEntreprise extends Form {

    Form current;

    public static String username;
    public Entreprise entreprise;

    public ProfilEntreprise(Form previous) {
        current = this; //Récupération de l'interface(Form) en cours
        setTitle("Profil Entreprise");

    }

    public ProfilEntreprise(String username) throws IOException {

        this.username = username;
        this.entreprise = ServiceEntreprise.getInstance().getUser(this.username);

        Dialog d = new Dialog("Bienvenue");
        TextArea popupBody = new TextArea("Salut " + this.entreprise.getNom().toUpperCase() + " Bienvenu dans votre profil", 3, 20);
        popupBody.setUIID("PopupBody");
        popupBody.setEditable(false);
        d.setLayout(new BorderLayout());
        d.add(BorderLayout.CENTER, popupBody);
        current = this; //Récupération de l'interface(Form) en cours
        setTitle("Modifer vos parametres");

      Image image = Image.createImage(PATH + this.entreprise.getPhoto());
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
        TextField nom = new TextField(this.entreprise.getNom(), "Nom");
        TextField tel = new TextField(this.entreprise.getTel(), "tel");
        
         TextField status = new TextField(this.entreprise.getEmail(), "Email");

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
Entreprise ent = new Entreprise( nom.getText(),this.entreprise.getEmail(), tel.getText());
                ent.setPhoto(news);
                
if (ServiceEntreprise.getInstance().modifierEntreprise(ent)) {
                Dialog.show("Success", "Modification effectuée", new Command("OK"));
            } else {
                Dialog.show("ERROR", "Server error", new Command("OK"));
            }
//              
            } catch (Exception ex) {
                ex.printStackTrace();
            }

     try {
                    new ProfilEntreprise(this.username).show();
                } catch (IOException ex) {
                }
            
        }
                
        );

        btnModifier.addActionListener((e) -> {

            Entreprise ent = new Entreprise(nom.getText(), this.entreprise.getEmail(), tel.getText());
            ent.setPhoto(this.entreprise.getPhoto());

            if (ServiceEntreprise.getInstance().modifierEntreprise(ent)) {
                Dialog.show("Success", "Modification effectuée", new Command("OK"));
            } else {
                Dialog.show("ERROR", "Server error", new Command("OK"));
            }
        });

        btnModifierPassword.addActionListener((e) -> {
            ModifierPasswordEnt modiferPassword = new ModifierPasswordEnt(current, this.username);
            // homeClient(current).show();
            modiferPassword.show();
        });
        Button capture = new Button();
        FontImage.setMaterialIcon(capture, FontImage.MATERIAL_PHOTO_CAMERA);

        capture.addActionListener((k) -> {
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
        addAll( photo,nom, tel,status, btnModifier, capture, btnModifierPassword,uploadPicture);
        

    }

}
