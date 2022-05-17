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
import tn.nebulagaming.entities.Wishlist;
import tn.nebulagaming.utils.SingletonUser;

/**
 *
 * @author ASUS
 */
public class WishlistService extends MotherService {

    private static List<Wishlist> wishItems;
    private CustomResponse resp;

    private static WishlistService instance = null;
    private final CustomResponseService daoService = CustomResponseService.getInstance();
    private final SingletonUser singUser = SingletonUser.getInstance();
    private static ConnectionRequest req;

    private final String WISHLIST_URL = URL + "wishlist";

    private WishlistService() {

	req = new ConnectionRequest();
    }

    public static WishlistService getInstance() {
	if (instance == null) {
	    instance = new WishlistService();
	}
	return instance;
    }

    public CustomResponse addToWishlist(int idProd) {

	String POST_URL = WISHLIST_URL + "/add";

	resp = new CustomResponse();

	System.out.println("===> " + POST_URL);

	req.setPost(true);
	req.setUrl(POST_URL);
	req.addRequestHeader("Content-Type", "application/json");
	req.setHttpMethod("POST");
	req.setRequestBody("{\"idUser\": " + singUser.getIdUser() + ",\"idProduct\": " + idProd + "}");
	req.addResponseListener(new ActionListener<NetworkEvent>() {

	    @Override
	    public void actionPerformed(NetworkEvent evt) {

		resp = daoService.parseObject(new String(req.getResponseData()));
		req.removeResponseListener(this);

	    }
	});
	NetworkManager.getInstance().addToQueueAndWait(req);

	return resp;
    }

    public List<Wishlist> getWishedItems() {

	String GET_URL = WISHLIST_URL + "/get/" + singUser.getIdUser();

	req = new ConnectionRequest();
	System.out.println("===> " + GET_URL);
	req.setUrl(GET_URL);
	req.setPost(false);
	req.addResponseListener(new ActionListener<NetworkEvent>() {
	    @Override
	    public void actionPerformed(NetworkEvent evt) {
		wishItems = parseObjects(new String(req.getResponseData()));
		req.removeResponseListener(this);
	    }
	});
	NetworkManager.getInstance().addToQueueAndWait(req);
	return wishItems;
    }

    public CustomResponse removeItem(int idWishList) {

	String REM_URL = WISHLIST_URL + "/rem?idWishlist=" + idWishList;

	resp = new CustomResponse();

	System.out.println("===> " + REM_URL);

	req.setPost(false);
	req.setUrl(REM_URL);
	req.setHttpMethod("DELETE");
	req.addResponseListener(new ActionListener<NetworkEvent>() {

	    @Override
	    public void actionPerformed(NetworkEvent evt) {

		resp = daoService.parseObject(new String(req.getResponseData()));
		req.removeResponseListener(this);

	    }
	});
	NetworkManager.getInstance().addToQueueAndWait(req);

	return resp;
    }

    @Override
    public List<Wishlist> parseObjects(String jsonText) {

	wishItems = new ArrayList<>();

	try {
	    JSONParser j = new JSONParser();
	    Map<String, Object> wishListJson
		    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

	    List<Map<String, Object>> list = (List<Map<String, Object>>) wishListJson.get("root");

	    for (Map<String, Object> obj : list) {
		wishItems.add(
			new Wishlist((int) Float.parseFloat(obj.get("idwishlist").toString()),
				parseProduct(obj)
			)
		);
	    }

	} catch (IOException ex) {

	}
	return wishItems;
    }

    private Product parseProduct(Map<String, Object> map) {

	Product product = new Product();

	Map<String, Object> object = (Map<String, Object>) map.get("idproduct");

	product.setIdproduct((int) Float.parseFloat(object.get("idproduct").toString()));
	product.setNameproduct(object.get("nameproduct").toString());
	product.setPriceproduct(Float.parseFloat(object.get("priceproduct").toString()));
	product.setQtyproduct((int) Float.parseFloat(object.get("qtyproduct").toString()));
	product.setIdCategory(parseCategory(object));

	return product;
    }

    private Category parseCategory(Map<String, Object> map) {

	Category category = new Category();

	try {
	    Map<String, String> object = (Map<String, String>) map.get("idcategory");

	    category.setNameCategory(object.get("namecategory"));
	} catch (NullPointerException ex) {

	    System.err.println("Something went wrong: " + ex.getMessage());
	}

	return category;
    }

}
