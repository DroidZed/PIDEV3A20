/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.models;

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
    private int IdPub = 0;
    private double rating;
    private ImageView img;
    private int likes;

    public JeuVideo(int id, String nameVg, String imageVg, Double rating, int likes) {
	this.id = id;
	this.nameVg = nameVg;
	this.imageVg = imageVg;
	this.rating = rating;
	this.likes = likes;
    }

    public JeuVideo(String nameVg, String imageVg) {
	this.nameVg = nameVg;
	this.imageVg = imageVg;
    }

    public JeuVideo(int id, String nameVg, String imageVg, double rating) {
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

    public double getRating() {
	return rating;
    }

    public void setRating(double rating) {
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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
	return "JeuVideo{" + "nameVg=" + nameVg + ", imageVg=" + imageVg + '}';
    }

}
