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
import java.util.Map;
import tn.nebulagaming.entities.FidelityCard;
import tn.nebulagaming.utils.SingletonUser;

/**
 *
 * @author ASUS
 */
public class FidelityCardService extends MotherService {

    private static FidelityCardService instance = null;
    private static ConnectionRequest req;
    private final SingletonUser singUser = SingletonUser.getInstance();
    private final String FIDCARD_URL = URL + "fidcard";

    private FidelityCard fidCard;

    private FidelityCardService() {

	req = new ConnectionRequest();
    }

    public static FidelityCardService getInstance() {
	if (instance == null) {
	    instance = new FidelityCardService();
	}
	return instance;
    }

    public FidelityCard getUserFidCard() {

	String GET_URL = FIDCARD_URL + "/get?idUser=" + singUser.getIdUser();

	req = new ConnectionRequest();
	System.out.println("===> " + GET_URL);
	req.setUrl(GET_URL);
	req.setPost(false);
	req.addResponseListener(new ActionListener<NetworkEvent>() {
	    @Override
	    public void actionPerformed(NetworkEvent evt) {
		fidCard = parseObject(new String(req.getResponseData()));
		req.removeResponseListener(this);
	    }
	});
	NetworkManager.getInstance().addToQueueAndWait(req);

	return fidCard;

    }

    @Override
    protected FidelityCard parseObject(String jsonText) {

	fidCard = new FidelityCard();

	try {

	    JSONParser j = new JSONParser();
	    Map<String, Object> fidCardJson
		    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

	    System.out.println(fidCardJson);

	    fidCard.setPoints((int) Float.parseFloat(fidCardJson.get("nbpointsfid").toString()));
	    fidCard.setType(parseType(fidCardJson));

	} catch (IOException ex) {
	    System.err.println("[Parse fidcard]: Something went wrong: " + ex.getMessage());
	}

	return fidCard;

    }

    private String parseType(Map<String, Object> map) {

	Map<String, Object> object = (Map<String, Object>) map.get("idcardtype");

	return object.get("cardtype").toString();

    }

}
