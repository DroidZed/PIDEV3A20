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
import tn.nebulagaming.entities.Product;
import tn.nebulagaming.entities.User;

/**
 *
 * @author anony
 */
public class ServiceProduct extends MainServiceClass {

    private List<Product> products;

    private Product product;

    private CustomResponse resp;

    private CustomResponseService respService = CustomResponseService.getInstance();

    private static ServiceProduct instance = null;

    private ConnectionRequest req;

    private ServiceProduct() {

	URL = URL + "product";

	req = new ConnectionRequest();
    }

    public static ServiceProduct getInstance() {
	if (instance == null) {
	    instance = new ServiceProduct();
	}
	return instance;
    }

    public List<Product> getAllProducts() {

	final String ALL_PRODUCTS_URL = URL + "/get";

	req = new ConnectionRequest();
	System.out.println("===> " + ALL_PRODUCTS_URL);
	req.setUrl(ALL_PRODUCTS_URL);
	req.setPost(false);
	req.addResponseListener(new ActionListener<NetworkEvent>() {
	    @Override
	    public void actionPerformed(NetworkEvent evt) {
		products = parseObjects(new String(req.getResponseData()));
		req.removeResponseListener(this);
	    }
	});
	NetworkManager.getInstance().addToQueueAndWait(req);

	return products;
    }

    public CustomResponse addToWishlist(Product p) {

	String POST_URL = URL + "/add";

	resp = new CustomResponse();

	System.out.println("===> " + POST_URL);

	req.setPost(true);
	req.setUrl(POST_URL);
	req.addRequestHeader("Content-Type", "application/json");
	req.setHttpMethod("POST");
	req.setRequestBody(
		"{\"nameProduct\":\"" + p.getNameproduct()
		+ "\",\"qtyProd\":" + p.getQtyproduct()
		+ ",\"priceProduct\":" + p.getPriceproduct()
		+ ",\"idCategory\":" + p.getIdCategory().getIdcategory()
		+ ",\"idUser\":" + p.getIdUser().getIduser() + "}"
	);
	req.addResponseListener(new ActionListener<NetworkEvent>() {

	    @Override
	    public void actionPerformed(NetworkEvent evt) {

		resp = respService.parseObject(new String(req.getResponseData()));
		req.removeResponseListener(this);

	    }
	});
	NetworkManager.getInstance().addToQueueAndWait(req);

	return resp;
    }

    public Product getById(int idProd) {

	for (Product p : products) {
	    if (p.getIdproduct() == idProd) {
		product = p;
	    }
	}

	return product;
    }

    @Override
    public Product parseObject(String jsonText) {

	product = new Product();

	try {

	    JSONParser j = new JSONParser();
	    Map<String, Object> productsJson
		    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

	    Map<String, Object> object = (Map<String, Object>) productsJson.get("root");

	    product.setIdproduct((int) Float.parseFloat(object.get("idproduct").toString()));
	    product.setNameproduct(object.get("nameproduct").toString());
	    product.setPriceproduct(Float.parseFloat(object.get("priceproduct").toString()));
	    product.setQtyproduct((int) Float.parseFloat(object.get("qtyproduct").toString()));
	    product.setIdCategory(parseCategory(object));
	    product.setIdUser(parseUser(object));

	} catch (IOException ex) {
	    System.out.println("[Parse product]: Something went wrong: " + ex.getMessage());
	}
	return product;

    }

    @Override
    public List<Product> parseObjects(String jsonText) {

	products = new ArrayList<>();

	try {

	    JSONParser j = new JSONParser();
	    Map<String, Object> productsJson
		    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

	    List<Map<String, Object>> objects = (List<Map<String, Object>>) productsJson.get("root");

	    for (Map<String, Object> object : objects) {

		products.add(new Product(
			(int) Float.parseFloat(object.get("idproduct").toString()),
			object.get("nameproduct").toString(),
			Float.parseFloat(object.get("priceproduct").toString()),
			(int) Float.parseFloat(object.get("qtyproduct").toString()),
			parseCategory(object),
			parseUser(object)
		));
	    }

	} catch (IOException ex) {
	    System.out.println("[Parse product]: Something went wrong: " + ex.getMessage());
	}
	return products;

    }

    private Category parseCategory(Map<String, Object> map) {

	Category category = new Category();

	try {

	    Map<String, Object> object = (Map<String, Object>) map.get("idcategory");

	    category.setNameCategory(object.get("nameCategory").toString());
	    category.setIdcategory((int) Float.parseFloat(object.get("idcategory").toString()));
	} catch (NullPointerException ex) {

	    System.out.println("[Parse category]: Something went wrong: " + ex.getMessage());
	}

	return category;
    }

    private User parseUser(Map<String, Object> map) {

	User user = new User();

	try {

	    Map<String, Object> object = (Map<String, Object>) map.get("iduser");

	    user.setNameuser(object.get("nom").toString());
	} catch (NullPointerException ex) {

	    System.out.println("[Parse user]: Something went wrong: " + ex.getMessage());
	}

	return user;
    }

}
