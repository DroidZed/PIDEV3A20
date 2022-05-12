/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import tn.nebulagaming.entities.Event;
import tn.nebulagaming.utils.Statics;
/**
 *
 * @author SuperNova
 */
public class ServiceEvent {
    
    private ConnectionRequest request ;
    public ArrayList<Event> events;
    
    public static ServiceEvent instance=null;
    public boolean resultOK;
    
    
    public ServiceEvent() {
         request = new ConnectionRequest();
    }
    
     public ArrayList<Event> parseEvents (String jsonText){
        try {
            
            System.out.println("jsonTextEvent :" +jsonText);
            events =new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> eventsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
    
            List<Map<String,Object>> list = (List<Map<String,Object>>)eventsListJson.get("root");
            
            System.out.println(list);
            
            for(Map<String,Object> obj : list){
                Event event = new Event();
                
                float id = Float.parseFloat(obj.get("idpost").toString());
                event.setIdPost((int)id);
                
                   if(obj.get("titlepost") == null) {
                       event.setTitlePost("null");
                   }
                   else {
                       event.setTitlePost(obj.get("titlepost").toString());
                   }
                   
                   if(obj.get("descpost") == null) {
                    event.setDescPost("null");
                   } else {
                     event.setDescPost(obj.get("descpost").toString());
                   }
                   
                   if(obj.get("addressevent") == null) {
                    event.setAddressEvent("null");
                   } else {
                     event.setAddressEvent(obj.get("addressevent").toString());
                   }
                   
                    float nbTickets = Float.parseFloat(obj.get("nbticketavailable").toString());
                    event.setNbTicketAvailable((int)nbTickets);
                
      
                   if(obj.get("photopost") == null) {
                    event.setPhotoPost("null");
                   } else {
                     event.setPhotoPost(obj.get("photopost").toString());
                   }
                
                
                
                float status = Float.parseFloat(obj.get("statuspost").toString());
                event.setStatusPost((int)status);
                
                float latitude = Float.parseFloat(obj.get("latitude").toString());  
                event.setLatitude(latitude);
                
                float longitude = Float.parseFloat(obj.get("longitude").toString());  
                event.setLongitude(longitude);

                
               
                events.add(event);
                
            }    
        } catch (IOException ex) {
            
        }
        return events;
    }
    
    
    
    public ArrayList<Event> getAllEvents(){
        request = new ConnectionRequest();
        String url = Statics.BASE_URL+"wsevents/getEvents";
        System.out.println("===>"+url);
        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                //System.out.println("request:" + request.getResponseData());
                events = parseEvents(new String(request.getResponseData()));
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
        return events ;
    }
    
   
}
