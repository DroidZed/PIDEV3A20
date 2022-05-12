/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.nebulagaming.screens;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;

import tn.nebulagaming.services.ServiceEntreprise;
import tn.nebulagaming.services.ServiceUser;


import java.io.IOException;
import tn.nebulagaming.entities.User;
import tn.nebulagaming.utils.Validators;

/**
 *
 * @author ibeno
 */
public class ModifierPasswordEnt extends Form {

    Form current;
    public String username;
    public User user;

    public ModifierPasswordEnt() {
    }

    public ModifierPasswordEnt(Form previous, String username) {
        this.username = username;
        System.out.println("awel" + this.username);
        this.user = ServiceEntreprise.getInstance().getUser(this.username);
        System.out.println("theni" + this.user.toString());

        current = this; //Récupération de l'interface(Form) en cours
        setTitle("Modifer vos parametres");
        Validators v = new Validators();

        TextField new_password = new TextField("", "Nouveau mot de passe");
        TextField confirmer_password = new TextField("", "Confirmer mot de passe");
        Button btnModifier = new Button("Modifier votre mot de passe");

        new_password.setConstraint(TextField.PASSWORD);
        confirmer_password.setConstraint(TextField.PASSWORD);

        btnModifier.addActionListener((e) -> {

            if (!v.test_Password(new_password.getText())) {
                Dialog.show("Attention", "Votre passe doit contenir majuscule un chiffre et une miniscule au minimum", new Command("OK"));
            } else if (!new_password.getText().equals(confirmer_password.getText())) {
                Dialog.show("Attention", "Les deux mot de passes ne correspondent pas", new Command("OK"));
            } else {


                if (ServiceUser.getInstance().modifierUserMotDePasse(this.user.getEmail(),new_password.getText())) {
                    Dialog.show("Success", "Modification effectuée", new Command("OK"));
                    try {
                        new ProfilEntreprise(this.username).show();
                    } catch (Exception ex) {
                    }

                
                } else {
                    Dialog.show("ERROR", "Server error", new Command("OK"));
                }
            }
        });

        addAll(new_password, confirmer_password, btnModifier);

        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());

    }

}
