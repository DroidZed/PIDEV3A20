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
import tn.nebulagaming.utils.Validators;

import java.io.IOException;


/**
 *
 * @author SeifBS
 */
public class VerificationCodeForgetPassword extends Form {

    Form current;
    public int tentative=0;

    public VerificationCodeForgetPassword() {
    }

    public VerificationCodeForgetPassword(Resources res, Form previous) {

        current = this;
        setTitle("Code de verification");
        Validators v = new Validators();
        

        TextField verificationCode = new TextField("", "Code de verification");
        Button verifier = new Button("verifier");

        verifier.addActionListener((e) -> {
            System.out.println(ForgetPasswordCheck.verificationCode + "test");
            StringBuilder test=new StringBuilder(ForgetPasswordCheck.verificationCode);
            test.deleteCharAt(0);
            test.deleteCharAt(32);
            

            if (! verificationCode.getText().equals(test.toString())) {
                tentative++;System.out.println(verificationCode.getText());
                Dialog.show("Attention", "Code incorrect", new Command("OK"));
                
                if(tentative>2)
                {
                Dialog.show("Erreur", "Vous avez atteint toutes les tentatives ", new Command("OK"));
                new Login1(res).show();
                }

            } 
            
            else {
                Dialog.show("Succees", "Code correct", new Command("OK"));
                new ForgetPassword(res, current).show();
            }

        });

        addAll(verificationCode, verifier);

        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());

    }

}
