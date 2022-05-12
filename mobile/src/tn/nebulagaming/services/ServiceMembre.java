/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.nebulagaming.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;

import tn.nebulagaming.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import tn.nebulagaming.entities.Membre;

/**
 *
 * @author ibeno
 */
public class ServiceMembre {

    public ArrayList<Membre> entreprises;

    public static ServiceMembre instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    ServiceMembre() {
        req = new ConnectionRequest();
    }

    public static ServiceMembre getInstance() {
        if (instance == null) {
            instance = new ServiceMembre();
        }
        return instance;
    }
    

    public boolean addMembre(Membre e) {
        String url = Statics.BASE_URL + "/newMembreM?nom=" + e.getNom() + "&email=" + e.getEmail() + "&password=" + e.getPassword() + "&tel=" + e.getTel() + "&descuser=" + e.getDescription()+ "&photo=" + e.getPhoto(); //création de l'URL
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion        
        //req.setPost(true);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); //Supprimer cet actionListener

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public boolean modifEnt(Membre ent) {
        return false;
    }

    
    
    public ArrayList<Membre> parseMembre(String jsonText) {
        
        try {
            entreprises = new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json            
            Map<String, Object> entreprisesListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) entreprisesListJson.get("root");
            
            //Parcourir la liste des tâches Json
            for (Map<String, Object> obj : list) {
                //Création des tâches et récupération de leurs données
                Membre e = new Membre();
                e.setId((int) Float.parseFloat(obj.get("id").toString()));
                e.setNom(obj.get("nom").toString());
                e.setEmail(obj.get("email").toString());
                e.setPassword(obj.get("password").toString());
                e.setTel(obj.get("tel").toString());
              e.setDescription(obj.get("descuser").toString());
              System.out.println(e.getDescription() + " test");
                e.setPhoto(obj.get("photo").toString());
                //Ajouter la tâche extraite de la réponse Json à la list
                entreprises.add(e);
            }
        } catch (IOException ex) {

        }
        return entreprises;
    }
    
    public boolean modifierUserMotDePasse(Membre ent) {

        String url = Statics.BASE_URL + "/modifyPasswordMembre?mail=" + ent.getEmail() + "&password=" + ent.getPassword();
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
    

    public boolean modifierMembre(Membre ent) {
        String url = Statics.BASE_URL + "/modifyMembre?email=" + ent.getEmail() + "&nom=" + ent.getNom() + "&tel=" + ent.getTel() + "&descuser=" + ent.getDescription() + "&photo=" + ent.getPhoto();
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
    

    public Membre getMembre(String username) {
        String url = Statics.BASE_URL + "/getUser?email=" + username;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
              
                entreprises = parseMembre(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return entreprises.get(0);
    }

    
    
    public Membre getMem(String username) {
        String url = Statics.BASE_URL + "/getUser?email=" + username;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                  System.out.println("listen");
                System.out.println(Arrays.toString(req.getResponseData()));
                entreprises = parseMembre(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return entreprises.get(0);
    }

}
