/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author dell
 */
public class commentaire {

    private int idCom;
    private String dateCom;
    private String descriptionCom;
    private int IdPub;

    public commentaire(int idCom, String dateCom, String descriptionCom, int IdPub) {
        this.idCom = idCom;
        this.dateCom = dateCom;
        this.descriptionCom = descriptionCom;
        this.IdPub = IdPub;
    }

    public int getIdCom() {
        return idCom;
    }

    public void setIdCom(int idCom) {
        this.idCom = idCom;
    }

    public String getDateCom() {
        return dateCom;
    }

    public void setDateCom(String dateCom) {
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
