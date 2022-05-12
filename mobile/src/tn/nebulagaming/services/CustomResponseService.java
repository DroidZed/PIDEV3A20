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
import tn.nebulagaming.entities.CustomResponse;

/**
 *
 * @author anony
 */
public class CustomResponseService  extends MainServiceClass {

    CustomResponse resp;

    public static CustomResponseService instance = null;
    private ConnectionRequest req;

    private CustomResponseService() {

	req = new ConnectionRequest();
    }

    public static CustomResponseService getInstance() {
	if (instance == null) {
	    instance = new CustomResponseService();
	}
	return instance;
    }

    @Override
    public CustomResponse parseObject(String jsonText) {
	try {
	    resp = new CustomResponse();
	    JSONParser j = new JSONParser();
	    Map<String, Object> wishListJson
		    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

	    resp.setMessage(wishListJson.get("message").toString());

	} catch (IOException ex) {
	    System.out.println("[custom response]: something went wrong - " + ex.getMessage());

	}
	return resp;
    }
    
}
