/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Blob;
import javafx.scene.image.ImageView;

/**
 *
 * @author dell
 */
public class JeuVideo {

    private int id;
    private String nameVg;
    private String imageVg;
    private int idUser = 0;
    private int IdPub=0;
    private float rating;
    private ImageView img;

    public JeuVideo(String nameVg, String imageVg) {
        this.nameVg = nameVg;
        this.imageVg = imageVg;
    }

    public JeuVideo(int id, String nameVg, String imageVg, float rating) {
        this.id = id;
        this.nameVg = nameVg;
        this.imageVg = imageVg;
        this.rating = rating;
    }

    public JeuVideo() {
    }
    
    
    
    
    

    public JeuVideo(String nameVg, float rating) {
        this.nameVg = nameVg;
        this.rating = rating;
    }

    public JeuVideo(String nameVg) {
        this.nameVg = nameVg;
    }
    
    

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public JeuVideo(int id, String nameVg, String imageVg) {
        this.id = id;
        this.nameVg = nameVg;
        this.imageVg = imageVg;
    }

    public String getNameVg() {
        return nameVg;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setNameVg(String nameVg) {
        this.nameVg = nameVg;
    }

    public String getImageVg() {
        return imageVg;
    }

    public void setImageVg(String imageVg) {
        this.imageVg = imageVg;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdPub() {
        return IdPub;
    }

    public void setIdPub(int IdPub) {
        this.IdPub = IdPub;
    }

    @Override
    public String toString() {
        return "JeuVideo{" + "nameVg=" + nameVg + ", imageVg=" + imageVg + '}';
    }
    
    
    
    

   


}
