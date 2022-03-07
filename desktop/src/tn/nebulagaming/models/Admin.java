/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.nebulagaming.models;

import javafx.scene.image.ImageView;
import tn.nebulagaming.models.etatCompte.etat;

public class Admin extends User {

    public Admin(String nom, String email, String password, String tel, String photo, String role, String etatCompte, String date) {
        super(nom, email, password, tel, photo, role, etatCompte, date);
    }

    public Admin(String nom, String email, String password, String tel, String photo, String role, String etatCompte) {
        super(nom, email, password, tel, photo, role, etatCompte);
    }

    public Admin(String nom, String email, String tel) {
        super(nom, email, tel);
    }

    public Admin() {
    }

}
