/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.nebulagaming.models;
public class Entreprise extends User {

    private String cv;

    public Entreprise(String nom, String email, String password, String tel, String photo, String role, String cv, String etatCompte, String date) {
        super(nom, email, password, tel, photo, role, etatCompte, date);
        this.cv = cv;
    }

    public Entreprise(String nom, String email, String password, String tel, String photo, String role, String cv, String etatCompte) {
        super(nom, email, password, tel, photo, role, etatCompte);
        this.cv = cv;
    }

    public Entreprise(String nom, String email, String tel, String cv) {
        super(nom, email, tel);
        this.cv = cv;
    }

    public Entreprise() {
    }

    /**
     * @return the cv
     */
    public String getCv() {
        return cv;
    }

    /**
     * @param cv the cv to set
     */
    public void setCv(String cv) {
        this.cv = cv;
    }

    @Override
    public String toString() {
        return this.getNom() + " " + this.getEmail() + " " + this.getTel() + " " + this.getEtatCompte() + " " + this.getDate();
    }
}
