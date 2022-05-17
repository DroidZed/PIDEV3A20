/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.services;

import com.codename1.io.ConnectionRequest;
import tn.nebulagaming.entities.CustomResponse;
import tn.nebulagaming.utils.SingletonUser;

/**
 *
 * @author ASUS
 */
public class CheckoutService extends MotherService {

    private final String CHECKOUT_URL = URL + "checkout";
    private CheckoutService instance = null;
    private static ConnectionRequest req;
    private CustomResponse resp;

    private static CustomResponseService daoService = CustomResponseService.getInstance();
    private ShoppingCartService shoppingCartService = ShoppingCartService.getInstance();
    private final SingletonUser singUser = SingletonUser.getInstance();

    private CheckoutService() {
	req = new ConnectionRequest();
    }

    @Override
    protected CustomResponse parseObject(String jsonText) {
	return null;
    }

}
