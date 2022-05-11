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
import tn.nebulagaming.entities.Category;
import tn.nebulagaming.entities.CustomResponse;

/**
 *
 * @author anony
 */
public class CategoryService extends MainServiceClass {

    private List<Category> categories;

    private Category category;

    private CustomResponse resp;

    private static CategoryService instance = null;

    private ConnectionRequest req;

    private CategoryService() {

	URL = URL + "category";

	req = new ConnectionRequest();
    }

    public static CategoryService getInstance() {
	if (instance == null) {
	    instance = new CategoryService();
	}
	return instance;
    }

    public List<Category> getAllCategories() {

	final String ALL_PRODUCTS_URL = URL + "/get";

	req = new ConnectionRequest();
	System.out.println("===> " + ALL_PRODUCTS_URL);
	req.setUrl(ALL_PRODUCTS_URL);
	req.setPost(false);
	req.addResponseListener(new ActionListener<NetworkEvent>() {
	    @Override
	    public void actionPerformed(NetworkEvent evt) {
		categories = parseObjects(new String(req.getResponseData()));
		req.removeResponseListener(this);
	    }
	});
	NetworkManager.getInstance().addToQueueAndWait(req);

	return categories;

    }

    @Override
    public Category parseObject(String jsonText) {

	category = new Category();

	try {

	    JSONParser j = new JSONParser();
	    Map<String, Object> productsJson
		    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

	    Map<String, Object> object = (Map<String, Object>) productsJson.get("root");

	    category.setIdcategory((int) Float.parseFloat(object.get("idcategory").toString()));
	    category.setNameCategory(object.get("nameCategory").toString());

	} catch (IOException ex) {
	    System.out.println("[Parse product]: Something went wrong: " + ex.getMessage());
	}
	return category;

    }

    @Override
    public List<Category> parseObjects(String jsonText) {

	categories = new ArrayList<>();

	try {

	    JSONParser j = new JSONParser();
	    Map<String, Object> productsJson
		    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

	    List<Map<String, Object>> objects = (List<Map<String, Object>>) productsJson.get("root");

	    for (Map<String, Object> object : objects) {

		categories.add(new Category(
			(int) Float.parseFloat(object.get("idcategory").toString()),
			object.get("nameCategory").toString()
		));
	    }

	} catch (IOException ex) {
	    System.out.println("[Parse product]: Something went wrong: " + ex.getMessage());
	}
	return categories;

    }

}
