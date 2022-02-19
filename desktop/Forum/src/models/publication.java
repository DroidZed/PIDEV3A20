/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author dell
 */
public class publication {

    private int idPub;
    private int idUser;
    private String DatePub;
    private int TypePub;
    private String descriptionPub;
    private int idVg;

    public publication() {
    }

    public publication(int idPub, int idUser, String DatePub, int TypePub, String descriptionPub, int idVg) {
        this.idPub = idPub;
        this.idUser = idUser;
        this.DatePub = DatePub;
        this.TypePub = TypePub;
        this.descriptionPub = descriptionPub;
        this.idVg = idVg;
    }

    public int getIdPub() {
        return idPub;
    }

    public void setIdPub(int idPub) {
        this.idPub = idPub;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getDatePub() {
        return DatePub;
    }

    public void setDatePub(String DatePub) {
        this.DatePub = DatePub;
    }

    public int getTypePub() {
        return TypePub;
    }

    public void setTypePub(int TypePub) {
        this.TypePub = TypePub;
    }

    public String getDescriptionPub() {
        return descriptionPub;
    }

    public void setDescriptionPub(String descriptionPub) {
        this.descriptionPub = descriptionPub;
    }

    public int getIdVg() {
        return idVg;
    }

    public void setIdVg(int idVg) {
        this.idVg = idVg;
    }


    
}
