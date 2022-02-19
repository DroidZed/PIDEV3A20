/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Blob;

/**
 *
 * @author dell
 */
public class JeuVideo {

    private int statusVg;
    private String nameVg;
    private Blob imageVg;
    private int idUser;
    private int IdPub;

    public JeuVideo(int statusVg, String nameVg, Blob imageVg, int idUser, int IdPub) {
        this.statusVg = statusVg;
        this.nameVg = nameVg;
        this.imageVg = imageVg;
        this.idUser = idUser;
        this.IdPub = IdPub;
    }

    public int getStatusVg() {
        return statusVg;
    }

    public void setStatusVg(int statusVg) {
        this.statusVg = statusVg;
    }

    public String getNameVg() {
        return nameVg;
    }

    public void setNameVg(String nameVg) {
        this.nameVg = nameVg;
    }

    public Blob getImageVg() {
        return imageVg;
    }

    public void setImageVg(Blob imageVg) {
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


}
