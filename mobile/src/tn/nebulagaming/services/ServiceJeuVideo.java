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
import tn.nebulagaming.entities.JeuVideo;
import tn.nebulagaming.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dell
 */
public class ServiceJeuVideo {
    public ArrayList<JeuVideo>List1;
        public boolean resultOK;

    public static ServiceJeuVideo instance = null;
    
    private ConnectionRequest req;
    
    public static ServiceJeuVideo getInstance()
    {
        if(instance == null)
            instance = new ServiceJeuVideo();
        return instance;
    }
    
    public ServiceJeuVideo()
    {
        req = new ConnectionRequest();
    }
    
    public void ajoutJeuVideo(JeuVideo JV)
    {
        String url = Statics.BASE_URL+"/jeu/mobile/new?name="+JV.getNamevg()+"&image="+JV.getImagevg()+"&rating="+JV.getRating();
        
        req.setUrl(url);
        req.addResponseListener((e)-> {
            String str = new String(req.getResponseData());
            System.out.println("data=="+str);
        }
            );
        
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    
    public ArrayList<JeuVideo>afficherJeuVideo()
    {
        ArrayList<JeuVideo> result = new ArrayList<>();
        
        String url = Statics.BASE_URL;
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent> ()
        {
            @Override
            public void actionPerformed(NetworkEvent evt)
            {
                JSONParser jsonp;
                jsonp = new JSONParser();
                
                try{
                    Map<String, Object>mapJeuVideo = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    List<Map<String, Object>> listOfMaps = (List<Map<String, Object>>) mapJeuVideo.get("root");
                    
                    for(Map<String, Object> obj : listOfMaps)
                    {
                        JeuVideo jv = new JeuVideo();
                        int id = (int) Float.parseFloat(obj.get("id").toString());
                        jv.setId(id);
                        String name = obj.get("namevg").toString();
                        String image = obj.get("imagevg").toString();
                        jv.setRating((int) Float.parseFloat(obj.get("rating").toString()));
                        jv.setLikes((int) Float.parseFloat(obj.get("likes").toString()));
                        jv.setNamevg(name);
                        jv.setImagevg(image);
                        
                        result.add(jv);
                        
                    }
                }catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });
            
        NetworkManager.getInstance().addToQueueAndWait(req);
                
        return result;

    }
    
    public ArrayList<JeuVideo> parseJeu(String jsonText)
    {
        try {
            List1=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = 
               j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                        JeuVideo jv = new JeuVideo();
                        int id = (int) Float.parseFloat(obj.get("id").toString());
                        jv.setId(id);
                        String name = obj.get("namevg").toString();
                        String image = obj.get("imagevg").toString();
                        jv.setRating((int) Float.parseFloat(obj.get("rating").toString()));
                        jv.setLikes((int) Float.parseFloat(obj.get("likes").toString()));
                        jv.setNamevg(name);
                        jv.setImagevg(image);
                List1.add(jv);
            }
            
            
        } catch (IOException ex) {
            
        }
        return List1;
    }
    
    public ArrayList<JeuVideo> getAllJeu(){
        req = new ConnectionRequest();
        //String url = Statics.BASE_URL+"/tasks/";
        String url = Statics.BASE_URL+"/Jeu/mobile/aff";
        System.out.println("===>"+url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                List1 = parseJeu(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return List1;
    }
    
        public boolean deleteJV(int id) {
        String url = Statics.BASE_URL + "/jeu/mobile/del?id=" + id;
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
