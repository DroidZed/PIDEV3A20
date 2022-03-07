/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Date;

/**
 *
 * @author hp
 */
public class offer {
    
    
  private int idOffer;
  private String titleOffer;
  private String Typeoffer;
  private String descOffer;
  private java.sql.Date startDTM;
  private java.sql.Date endDTM;
  private float salaryOffer;
  private String regionOffer;
  private String addressOffer;
  private int idUser;
  private int idDomain;
  private String Domain;

    public String getDomain() {
        return Domain;
    }

    public void setDomain(String Domain) {
        this.Domain = Domain;
    }

    public offer(int idOffer, String titleOffer, String Typeoffer, String descOffer, Date startDTM, Date endDTM, float salaryOffer, String regionOffer, String addressOffer, int idUser, int idDomain) {
        this.idOffer = idOffer;
        this.titleOffer = titleOffer;
        this.Typeoffer = Typeoffer;
        this.descOffer = descOffer;
        this.startDTM = startDTM;
        this.endDTM = endDTM;
        this.salaryOffer = salaryOffer;
        this.regionOffer = regionOffer;
        this.addressOffer = addressOffer;
        this.idUser = idUser;
        this.idDomain = idDomain;
    }
    

  
    public offer(String titleOffer, String Typeoffer, String descOffer, java.sql.Date startDTM, java.sql.Date endDTM, float salaryOffer, String regionOffer, String addressOffer,int idDomain) {
        this.titleOffer = titleOffer;
        this.Typeoffer = Typeoffer;
        this.descOffer = descOffer;
        this.startDTM = startDTM;
        this.endDTM = endDTM;
        this.salaryOffer = salaryOffer;
        this.regionOffer = regionOffer;
        this.addressOffer = addressOffer;
        this.idDomain = idDomain;
    }
    public offer(int idOffer,String titleOffer, String Typeoffer, String descOffer, java.sql.Date startDTM, java.sql.Date endDTM, float salaryOffer, String regionOffer, String addressOffer,int idDomain) {
        
        this.idOffer = idOffer;
        this.titleOffer = titleOffer;
        this.Typeoffer = Typeoffer;
        this.descOffer = descOffer;
        this.startDTM = startDTM;
        this.endDTM = endDTM;
        this.salaryOffer = salaryOffer;
        this.regionOffer = regionOffer;
        this.addressOffer = addressOffer;
        this.idDomain = idDomain;
    }

    public String getTypeoffer() {
        return Typeoffer;
    }

    public void setTypeoffer(String Typeoffer) {
        this.Typeoffer = Typeoffer;
    }

    public int getIdOffer() {
        return idOffer;
    }

    public void setIdOffer(int idOffer) {
        this.idOffer = idOffer;
    }

    public String getTitleOffer() {
        return titleOffer;
    }

    public void setTitleOffer(String titleOffer) {
        this.titleOffer = titleOffer;
    }


    public String getDescOffer() {
        return descOffer;
    }

    public void setDescOffer(String descOffer) {
        this.descOffer = descOffer;
    }

    public java.sql.Date getStartDTM() {
        return startDTM;
    }

    public void setStartDTM(java.sql.Date startDTM) {
        this.startDTM = startDTM;
    }

    public java.sql.Date getEndDTM() {
        return endDTM;
    }

    public void setEndDTM(java.sql.Date endDTM) {
        this.endDTM = endDTM;
    }

    public float getSalaryOffer() {
        return salaryOffer;
    }

    public void setSalaryOffer(float salaryOffer) {
        this.salaryOffer = salaryOffer;
    }

    public String getRegionOffer() {
        return regionOffer;
    }

    public void setRegionOffer(String regionOffer) {
        this.regionOffer = regionOffer;
    }

    public String getAddressOffer() {
        return addressOffer;
    }

    public void setAddressOffer(String addressOffer) {
        this.addressOffer = addressOffer;
    }


    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdDomain() {
        return idDomain;
    }

    public void setIdDomain(int idDomain) {
        this.idDomain = idDomain;
    }

  @Override
    public String toString() {
      System.out.println("----");
        return "offre{" + ", \ntitleOffer=" + this.titleOffer + ", \ndescOffer=" + this.descOffer + ", \nstartDTM=" + this.startDTM +", \nendDTM=" + this.endDTM +", \nsalaryOffer=" + this.salaryOffer +", \nregionOffer=" + this.regionOffer +", \naddressOffer=" + this.addressOffer + ",\n----\n"+'}';
    }
  
}
