/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import java.io.IOException;
import java.util.Map;
import tn.nebulagaming.entities.JsonResponseDAO;


/**
 *
 * @author ASUS
 */
public class JsonResponseDAOService extends MotherService {

    JsonResponseDAO resp;

    public static JsonResponseDAOService instance = null;
    private ConnectionRequest req;

    private JsonResponseDAOService() {

	req = new ConnectionRequest();
    }

    public static JsonResponseDAOService getInstance() {
	if (instance == null) {
	    instance = new JsonResponseDAOService();
	}
	return instance;
    }

    @Override
    public JsonResponseDAO parseObject(String jsonText) {
	try {
	    resp = new JsonResponseDAO();
	    JSONParser j = new JSONParser();
	    Map<String, Object> wishListJson
		    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

	    resp.setMessage(wishListJson.get("message").toString());

	} catch (IOException ex) {

	}
	return resp;
    }
}
