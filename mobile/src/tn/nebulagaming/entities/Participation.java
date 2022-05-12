/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.entities;

import java.util.Date;

/**
 *
 * @author SuperNova
 */
public class Participation {
    private int idParticipation ;    
    private int rank ;
    private int result ; 
    private Date bookedDTM ;
    private int idUser ; 
    private int idPost ; 
    private int idPayType ;   

    
    public Participation (int idPost , int idUser , int idPayType) {
    this.idPayType = idPayType;
    this.idPost = idPost ;
    this.idUser = idUser ;
    }
    
    public Participation(int idParticipation, int rank, int result, Date bookedDTM, int idUser, int idPost, int idPayType) {
        this.idParticipation = idParticipation;
        this.rank = rank;
        this.result = result;
        this.bookedDTM = bookedDTM;
        this.idUser = idUser;
        this.idPost = idPost;
        this.idPayType = idPayType;
    }

    public int getIdParticipation() {
        return idParticipation;
    }

    public void setIdParticipation(int idParticipation) {
        this.idParticipation = idParticipation;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Date getBookedDTM() {
        return bookedDTM;
    }

    public void setBookedDTM(Date bookedDTM) {
        this.bookedDTM = bookedDTM;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdPost() {
        return idPost;
    }

    public void setIdPost(int idPost) {
        this.idPost = idPost;
    }

    public int getIdPayType() {
        return idPayType;
    }

    public void setIdPayType(int idPayType) {
        this.idPayType = idPayType;
    }

    @Override
    public String toString() {
        return "Participation{" + "idParticipation=" + idParticipation + ", rank=" + rank + ", result=" + result + ", bookedDTM=" + bookedDTM + ", idUser=" + idUser + ", idPost=" + idPost + ", idPayType=" + idPayType + '}';
    }
    
    
    
}
