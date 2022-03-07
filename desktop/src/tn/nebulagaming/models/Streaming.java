/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.models;

/**
 *
 * @author houba
 */
public class Streaming {
    private int idUser;
    private String description;
    private int nbVu;
    private String link;
    private int idStream;
    
    public Streaming(int idUser, String descr,String link)
    {
        this.idUser=idUser;
        this.description=descr;
        this.link=link;
    }
    public Streaming()
    {}
   public void setDescription(String descr)
   {
     this.description=descr;   
   }
   public void setLink(String link)
   {
       this.link=link;
   }
   public void setNbVu(int nb)
   {
       this.nbVu=nb;
   }
   public String getDescription() {
       return this.description;
   }
   public String getLink()
   {
       return this.link;
   }
   public int getNbVu()
   {
       return this.nbVu;
   }
   public void setIdStream(int id)
   {
       this.idStream=id;
   }
   public int getId(){
   return this.idStream;}
   
   public int getIdUser(){
   return this.idUser;}
   public void setIdUser(int id){
   this.idUser=id;}
}
