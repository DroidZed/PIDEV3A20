/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.nebulagaming.screens;

import com.codename1.capture.Capture;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;
import tn.nebulagaming.entities.Entreprise;

import tn.nebulagaming.services.ServiceEntreprise;
import tn.nebulagaming.services.ServiceUser;
import tn.nebulagaming.utils.Validators;


/**
 *
 * @author ibeno
 */
public class InscriEntreprise extends Form {

    String photo = "default.jpg";

    public InscriEntreprise(Form previous) {
        /*
        Le paramètre previous définit l'interface(Form) précédente.
        Quelque soit l'interface faisant appel à AddTask, on peut y revenir
        en utilisant le bouton back
         */
        setTitle("inscription entreprise");
        setLayout(BoxLayout.y());

        TextField nomEnt = new TextField("", "nom");
        TextField mail = new TextField("", "mail");
        TextField password = new TextField("", "password");
        TextField confirmer_password = new TextField("", "Confirmer mot de passe");
        TextField tel = new TextField("", "tel");
        Button btnAjoutPhoto = new Button("ajouter une photo");

        Button btnValider = new Button("s'inscrire");

        password.setConstraint(TextField.PASSWORD);
        confirmer_password.setConstraint(TextField.PASSWORD);

        btnAjoutPhoto.addActionListener((ActionEvent e) -> {
            try {
                String imgPath = Capture.capturePhoto();
                System.out.println(imgPath);


                int pod = imgPath.indexOf("/", 4);
                System.out.println(pod);

                String news = imgPath.substring(pod + 36, imgPath.length());
                System.out.println(news);

                this.photo = news;
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });

        btnValider.addActionListener((ko) -> {
         Validators v= new Validators();
    if(v.test_Email(mail.getText()) && v.test_num_telephonique(tel.getText()) && v.test_Password(password.getText()) && !nomEnt.getText().equals("")){

            Entreprise ent = new Entreprise(nomEnt.getText(), mail.getText(), password.getText(), tel.getText(), this.photo,"[\"ROLE_ENTREPRISE\"]","0");
            String result = ServiceUser.getInstance().checkUserUnique(ent);
            System.out.println(result);
            if(result.equals("1")) {
            ServiceEntreprise.getInstance().addEntreprise(ent);
            Dialog.show("Success", "Entreprise ajoutée avec succees", new Command("OK"));}
             else
                Dialog.show("failed", "Email existant", new Command("OK"));
                }
    else
     
Dialog.show("failed", "Vérifiez vos paramètres", new Command("OK"));
        });

        addAll(nomEnt, mail, password, confirmer_password, tel, btnValider,btnAjoutPhoto);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK,
        ko -> previous.showBack()); // Revenir vers l'interface précédente

    }
}
