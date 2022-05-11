/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import com.company.entities.domain;
import com.company.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 *
 * @author User
 */
public class ServiceDomain {
    public static ServiceDomain instance =null;
    
    private ConnectionRequest req;
    private ConnectionRequest reqq;
        public boolean resultOK;

    public static ServiceDomain getInstance ()
    { if (instance == null)
        instance=new ServiceDomain();
    return instance;
        }
    
        public ArrayList<domain> domaines;
        public ArrayList<domain> domainess;

    public ServiceDomain()
    { 
        req= new ConnectionRequest();
        reqq= new ConnectionRequest();
    }
         public void ajoutDomain (domain d) 
         {    
             String url= Statics.BASE_URL+"/domain/mobile/new?name="+d.getName()+"&description="+d.getDescription();
               req.setUrl(url);
                 
               req.addResponseListener((e)-> { 
                   String str=new String(req.getResponseData());
                   System.out.println("data== "+str);
               }
                       
                     
               );
               NetworkManager.getInstance().addToQueueAndWait(req);
         
           
         }

    
           public ArrayList<domain> afficherDomainOffre(int id){
        String url = Statics.BASE_URL+"/offer/mobile/aff?id="+id;
        req.setUrl(url);
        req.addResponseListener(new com.codename1.ui.events.ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                domaines = parseEvenements(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        com.codename1.io.NetworkManager.getInstance().addToQueueAndWait(req);
        return domaines;
    }
           public ArrayList<domain> afficherDomain(){
        String url = Statics.BASE_URL+"/domain/mobile/aff";
        req.setUrl(url);
        req.addResponseListener(new com.codename1.ui.events.ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                domaines = parsedom(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        com.codename1.io.NetworkManager.getInstance().addToQueueAndWait(req);
        return domaines;
    }

           public ArrayList<domain> parseEvenements(String jsonText){
                try {

                    System.out.println(jsonText);
            domainess=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> objj : list){
                     domain d = new domain();
                     

                domainess.add(d);


            }
        } catch (IOException ex) {
            
        }
        return domainess;
    }
           
           
           public ArrayList<domain> parsedom(String jsonText){
                try {

                    System.out.println(jsonText);
            domaines=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                     domain d = new domain();
                     String name=obj.get("name").toString();
                      String description=obj.get("description").toString();
                      d.setId((int) Float.parseFloat(obj.get("id").toString()));
                      d.setName(name);
                      d.setDescription(description);
                     

                domaines.add(d);


            }
        } catch (IOException ex) {
            
        }
        return domaines;
    }


    public boolean deleteDomain(int id) {
        String url = Statics.BASE_URL + "/domaine/mobile/del?id=" + id;
               req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
}
