/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Blob;
import java.sql.Date;
import javafx.scene.image.ImageView;

/**
 *
 * @author hp
 */
public class candidacy {

 private int id;
 private int idOffer;
 private int idUser;
 private java.sql.Date candidacyDTM ;  
 private String etat;
 private String imageCV;
 private ImageView img;
 private String title;


    public candidacy(int id,java.sql.Date candidacyDTM, int idOffer, int idUser,  String etat, String imageCV) {
        this.id = id;
        this.idOffer = idOffer;
        this.idUser = idUser;
        this.candidacyDTM = candidacyDTM;
        this.etat = etat;
        this.imageCV = imageCV;
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public candidacy(int idOffer, int idUser, String imageCV,String etat) {
        this.idOffer = idOffer;
        this.idUser = idUser;
        this.etat = etat;
        this.imageCV = imageCV;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

 
    public int getIdOffer() {
        return idOffer;
    }

    public void setIdOffer(int idOffer) {
        this.idOffer = idOffer;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public java.sql.Date getCandidacyDTM() {
        return candidacyDTM;
    }

    public void setCandidacyDTM(java.sql.Date candidacyDTM) {
        this.candidacyDTM = candidacyDTM;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getImageCV() {
        return imageCV;
    }

    public void setImageCV(String imageCV) {
        this.imageCV = imageCV;
    }

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "candidacy{" + "id=" + id + ", idOffer=" + idOffer + ", idUser=" + idUser + ", candidacyDTM=" + candidacyDTM + ", etat=" + etat + ", imageCV=" + imageCV + ", img=" + img + ", title=" + title + '}';
    }

    
}
