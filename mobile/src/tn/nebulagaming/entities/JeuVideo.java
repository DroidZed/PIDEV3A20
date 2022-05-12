/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.nebulagaming.entities;

/**
 *
 * @author dell
 */
public class JeuVideo {
    
    private int id;
    private boolean statusvg;
    private String namevg;
    private String imagevg;
    private int rating;
    private int likes;

    public JeuVideo(int id, boolean statusvg, String namevg, String imagevg, int rating, int likes) {
        this.id = id;
        this.statusvg = statusvg;
        this.namevg = namevg;
        this.imagevg = imagevg;
        this.rating = rating;
        this.likes = likes;
    }

    public JeuVideo(boolean statusvg, String namevg, String imagevg, int rating, int likes) {
        this.statusvg = statusvg;
        this.namevg = namevg;
        this.imagevg = imagevg;
        this.rating = rating;
        this.likes = likes;
    }

    public JeuVideo(String namevg, String imagevg) {
        this.namevg = namevg;
        this.imagevg = imagevg;
    }

    @Override
    public String toString() {
        return "JeuVideo{" + "namevg=" + namevg + ", imagevg=" + imagevg + '}';
    }

    
    
    public JeuVideo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isStatusvg() {
        return statusvg;
    }

    public void setStatusvg(boolean statusvg) {
        this.statusvg = statusvg;
    }

    public String getNamevg() {
        return namevg;
    }

    public void setNamevg(String namevg) {
        this.namevg = namevg;
    }

    public String getImagevg() {
        return imagevg;
    }

    public void setImagevg(String imagevg) {
        this.imagevg = imagevg;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
    
    
    
}
