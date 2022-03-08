/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.models;

import java.sql.Date;

/**
 *
 * @author dell
 */
public class Commentaire {

    private int idCom;
    private Date dateCom;
    private String descriptionCom;
    private int IdPub;

    public Commentaire(String descriptionCom, int IdPub) {
        this.descriptionCom = descriptionCom;
        this.IdPub = IdPub;
    }

    public Commentaire(int idCom, Date dateCom, String descriptionCom) {
        this.idCom = idCom;
        this.dateCom = dateCom;
        this.descriptionCom = descriptionCom;
    }
    

    public Commentaire(Date dateCom, String descriptionCom) {
        this.dateCom = dateCom;
        this.descriptionCom = descriptionCom;
        this.idCom = 0;
        this.IdPub = 0;
    }

    @Override
    public String toString() {
        return "commentaire{" + "dateCom=" + dateCom + ", descriptionCom=" + descriptionCom + '}';
    }

    public int getIdCom() {
        return idCom;
    }

    public void setIdCom(int idCom) {
        this.idCom = idCom;
    }

    public Date getDateCom() {
        return dateCom;
    }

    public void setDateCom(Date dateCom) {
        this.dateCom = dateCom;
    }

    public String getDescriptionCom() {
        return descriptionCom;
    }

    public void setDescriptionCom(String descriptionCom) {
        this.descriptionCom = descriptionCom;
    }

    public int getIdPub() {
        return IdPub;
    }

    public void setIdPub(int IdPub) {
        this.IdPub = IdPub;
    }
    
    

    
}
