/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.models;

import java.util.Objects;

/**
 *
 * @author houba
 */
public class Reclamation {
private int idComplaint;//

private String message;//
private String typeComplaint;//
private String statusComplaint="NON TRAITEE";
private String answerComplaint;
private int rate;
private int idUser; //
private String nomUser;

public Reclamation() {
};

public Reclamation( String typeComplaint, String message,  String statusComplaint, String answerComplaint, int rate,int idUser,String nomUser)
{
this.message=message;
this.typeComplaint=typeComplaint;
this.statusComplaint=statusComplaint;
this.rate=rate;
this.answerComplaint=answerComplaint;
this.idUser=idUser;
this.nomUser=nomUser;
}

    public Reclamation(int id, String toString, String Message) {
        this.idUser=id;
        this.message=Message;
        this.typeComplaint=toString;
    }
@Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Reclamation other = (Reclamation) obj;
        if (this.idComplaint!= other.idComplaint) {
            return false;
        }
        if (this.idUser != other.idUser) {
            return false;
        }
       
       
        if (!Objects.equals(this.message, other.message)) {
            return false;
        }
        if (!Objects.equals(this.typeComplaint, other.typeComplaint)) {
            return false;
        }
        return true;
    }
 
    public int getIdRec() {
        return idComplaint;
    }
    public String getNomUser()
    {
        return this.nomUser;
    }

   public void setIdComplaint(int id){
this.idComplaint=id;
}

 
 
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTypeComplaint() {
        return typeComplaint;
    }

    public Reclamation(String response) {
        this.answerComplaint = response;
    }

    public int getUserId() {
        return idUser;
    }


    public void setIdUser(int id) {
this.idUser=id;
}
    public String getAnswerComplaint() {
        return answerComplaint;
    }
public void setReponse(String rp) {
this.answerComplaint=rp;
}
    public void setType(String type) {
        this.typeComplaint = type;
    }
    public String getStatusComplaint()
    {
        return statusComplaint;
    }
    public void setStatus(String status)
    {
      this.statusComplaint=status;
     }
    public void setNameUser(String nom)
    {
        this.nomUser=nom;
    }
    
    @Override
    public String toString() {
        return this.getNomUser()+ " "+this.getStatusComplaint()  +  " " + this.getTypeComplaint() ;
    }

}
