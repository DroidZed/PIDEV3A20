package edu.esprit.pidev.entities;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public abstract class User {
    
    private int id;
    private String nom;
    private String email;
    private String password;
    private String tel;
    private String photo;
    private String role;
    private String etatCompte;
    private String creationDTM;

    
    public User(String nom, String email, String password, String tel, String photo, String role, String etatCompte,String date) {
        this.nom = nom;
        this.email = email;
        this.password = password;
        this.tel = tel;
        this.photo = photo;
        this.role = role;
        this.etatCompte = etatCompte;
        this.creationDTM=date;

    }

   public User(String nom, String email, String password, String tel, String photo, String role, String etatCompte) {
        this.nom = nom;
        this.email = email;
        this.password = password;
        this.tel = tel;
        this.photo = photo;
        this.role = role;


    }

    public User() {
    }

    public User(String password) {
        this.password = password;
    }

    public User(String nom, String email, String tel) {
        this.nom = nom;
        this.email = email;
        this.tel = tel;
    }

       public User(String nom, String email, String pwd,String tel, String photo) {
        this.nom = nom;
        this.email = email;
        this.password=pwd; 
        this.tel = tel;
        
        this.photo=photo;
    }
          public User(String nom, String email, String tel, String photo) {
        this.nom = nom;
        this.email = email;
       
        this.tel = tel;
        
        this.photo=photo;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * @param tel the tel to set
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @return the etatCompte
     */
    public String getEtatCompte() {
        return etatCompte;
    }

    /**
     * @param etatCompte the etatCompte to set
     */
    public void setEtatCompte(String indice) {
     this.etatCompte=indice;
    }

    /**
     * @return the photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * @param photo the photo to set
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    
    public void setDate(String date){
    this.creationDTM=date;
    }
    public String getDate(){
    return this.creationDTM;
    }
}
