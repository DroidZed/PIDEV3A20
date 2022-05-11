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
import java.util.List;
import java.util.Map;
import tn.nebulagaming.entities.Entreprise;

/**
 *
 * @author ibeno
 */
public class ServiceEntreprise {

    public ArrayList<Entreprise> entreprises;

    public static ServiceEntreprise instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceEntreprise() {
        req = new ConnectionRequest();
    }

    public static ServiceEntreprise getInstance() {
        if (instance == null) {
            instance = new ServiceEntreprise();
        }
        return instance;
    }

    public boolean modifierEntreprise(Entreprise ent) {
        String url = Statics.BASE_URL + "/Entreprise/Profil/Change_informationsM?email=" + ent.getEmail() + "&nom=" + ent.getNom() + "&tel=" + ent.getTel() + "&photo=" + ent.getPhoto();
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

    public boolean addEntreprise(Entreprise e) {
        String url = Statics.BASE_URL + "/newEntrepriseM?nom=" + e.getNom() + "&email=" + e.getEmail() + "&password=" + e.getPassword() + "&tel=" + e.getTel() +  "&photo=" + e.getPhoto(); //création de l'URL
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion  //req.setPost(true);
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

    public ArrayList<Entreprise> parseEntreprises(String jsonText) {
        try {
            entreprises = new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json            
            Map<String, Object> etudiandsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) etudiandsListJson.get("root");
            //Parcourir la liste des tâches Json
            for (Map<String, Object> obj : list) {
                //Création des tâches et récupération de leurs données
                Entreprise e = new Entreprise();
                //int idd = Integer.parseInt(obj.get("id").toString());
                e.setNom(obj.get("nom").toString());
                e.setEmail(obj.get("email").toString());
                e.setPassword(obj.get("password").toString());
                e.setTel(obj.get("tel").toString());
               e.setPhoto(obj.get("photo").toString());
                //Ajouter la tâche extraite de la réponse Json à la liste
                entreprises.add(e);
            }
        } catch (IOException ex) {

        }

        return entreprises;
    }

    
    
    

    public Entreprise getUser(String username) {
        String url = Statics.BASE_URL + "/getUser?email=" + username;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                entreprises = parseEntreprises(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
       
        return entreprises.get(0);
    }

   /* public ArrayList<Entreprise> getAllEntreprises() {
        String url = Statics.BASE_URL + "/Entreprise/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                entreprises = parseEntreprises(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return entreprises;
    }*/

}
