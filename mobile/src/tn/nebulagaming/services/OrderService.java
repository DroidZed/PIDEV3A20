/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.services;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import tn.nebulagaming.entities.CartItem;
import tn.nebulagaming.entities.CustomResponse;
import tn.nebulagaming.entities.Order;
import tn.nebulagaming.utils.ShoppingCartManager;
import tn.nebulagaming.utils.SingletonUser;

/**
 *
 * @author ASUS
 */
public class OrderService extends MotherService {

    private CustomResponse resp;

    private static OrderService instance = null;
    private final CustomResponseService respService = CustomResponseService.getInstance();
    private final SingletonUser singUser = SingletonUser.getInstance();
    private final ShoppingCartService shoppingCartService = ShoppingCartService.getInstance();
    private static ConnectionRequest req;

    private final String ORDER_URL = URL + "checkout";

    private OrderService() {

	req = new ConnectionRequest();
    }

    public static OrderService getInstance() {
	if (instance == null) {
	    instance = new OrderService();
	}
	return instance;
    }

    public CustomResponse placeOrder(Order o) {

	String body = "{ \"idUser\": " + singUser.getIdUser() + ","
		+ " \"address\": \"" + o.getAddr() + "\","
		+ " \"payType\": \"" + o.getPayType() + "\","
		+ " \"cartItems\": [ ";
	for (CartItem i : shoppingCartService.collectCartItems()) {
	    body = body + "{ \"idProd\":" + i.getProduct().getIdproduct() + ",\"qt\": " + i.getQuantity() + " },";
	}

	body = removeLastChar(body);

	body = body + " ] }";

	System.out.println(body);

	System.out.println("===> " + ORDER_URL);
	
	req.setPost(true);
	req.setUrl(ORDER_URL);
	req.addRequestHeader("Content-Type", "application/json");
	req.setHttpMethod("POST");
	req.setRequestBody(body);

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

    private static String removeLastChar(String str) {
	return removeLastChars(str, 1);
    }

    private static String removeLastChars(String str, int chars) {
	return str.substring(0, str.length() - chars);
    }

}
