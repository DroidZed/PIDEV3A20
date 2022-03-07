/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Date;

/**
 *
 * @author dell
 */
public class publication {

    private int idPub;
    private int idUser;
    private int idVG;
    private java.sql.Date DatePub;
    private String descriptionPub;
    
    public publication(String descriptionPub) {
        this.descriptionPub = descriptionPub;
    }

    public publication(int idVG, String descriptionPub) {
        this.idVG = idVG;
        this.descriptionPub = descriptionPub;
    }

    public publication(int idPub, Date DatePub, String descriptionPub) {
        this.idPub = idPub;
        this.DatePub = DatePub;
        this.descriptionPub = descriptionPub;
    }

    public publication(Date DatePub, String descriptionPub) {
        this.DatePub = DatePub;
        this.descriptionPub = descriptionPub;
    }
    
    

    public int getIdVG() {
        return idVG;
    }

    public void setIdVG(int idVG) {
        this.idVG = idVG;
    }

    @Override
    public String toString() {
        return "publication{" + "idPub=" + idPub + ", DatePub=" + DatePub + ", descriptionPub=" + descriptionPub + '}';
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

    public java.sql.Date getDatePub() {
        return DatePub;
    }

    public void setDatePub(java.sql.Date DatePub) {
        this.DatePub = DatePub;
    }

    public String getDescriptionPub() {
        return descriptionPub;
    }

    public void setDescriptionPub(String descriptionPub) {
        this.descriptionPub = descriptionPub;
    }
    
    

   
    
}
