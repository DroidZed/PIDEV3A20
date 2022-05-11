/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.List;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.tree.TreeModel;
import com.codename1.xml.Element;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;
import tn.nebulagaming.entities.Feed;

/**
 *
 * @author SuperNova
 */
public class TestRSSFeedParser implements TreeModel {

    public ArrayList<Feed> feed;

    private Element root;

    public TestRSSFeedParser(Element e) {
        root = e;
    }

    public TestRSSFeedParser() {
    }

    public Vector getChildren(Object parent) {
        if (parent == null) {
            Vector c = new Vector();
            c.addElement(root);
            return c;
        }
        Vector result = new Vector();
        Element e = (Element) parent;
        for (int iter = 0; iter < e.getNumChildren(); iter++) {
            result.addElement(e.getChildAt(iter));
        }
        return result;
    }

    public boolean isLeaf(Object node) {
        Element e = (Element) node;
        return e.getNumChildren() == 0;
    }

    int pageNumber = 1;

    public ArrayList<Feed> fetchPropertyData() {

        ConnectionRequest r = new ConnectionRequest();
        r.setPost(false);
        r.setUrl("https://news-api-cn1.herokuapp.com/");
        r.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {  
                //System.out.println("request:" + request.getResponseData());
                feed = parseRSS(new String(r.getResponseData()));
                
                r.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(r);
        return feed ; 
    }

    public ArrayList<Feed> parseRSS(String jsonText) {
        try {
            
            System.out.println("jsonText :" +jsonText);
            feed = new ArrayList<Feed>();
            JSONParser j = new JSONParser();
            Map<String, Object> eventsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            System.out.println("event:" + eventsListJson);
            java.util.List<Map<String, Object>> list = (java.util.List<Map<String, Object>>) eventsListJson.get("root");
            System.out.println("list:" + list);

 
            for (Map<String, Object> obj : list) {
   
                Feed feedObject = new Feed();
                if (obj.get("title") == null) {
                    feedObject.setTitle("null");
                } else {
                    feedObject.setTitle(obj.get("title").toString());
                }
                if (obj.get("contentSnippet") == null) {
                    feedObject.setContentSnippet("null");
                } else {
                    feedObject.setContentSnippet(obj.get("contentSnippet").toString());
                }
                if (obj.get("image") == null) {
                    feedObject.setImage("null");
                } else {
                    feedObject.setImage(obj.get("image").toString());
                }
                if (obj.get("link") == null) {
                    feedObject.setLink("null");
                } else {
                    feedObject.setLink(obj.get("link").toString());
                } 
               
                feed.add(feedObject);
            }
        } catch (IOException ex) {

        }
        
        
        return feed;
    }
   

}
