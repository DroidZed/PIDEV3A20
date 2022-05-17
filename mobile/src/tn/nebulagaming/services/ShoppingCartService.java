/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.services;

import java.util.ArrayList;
import java.util.Collections;
import tn.nebulagaming.entities.CartItem;
import tn.nebulagaming.entities.CustomResponse;
import tn.nebulagaming.entities.Product;
import tn.nebulagaming.utils.ShoppingCartManager;
import tn.nebulagaming.utils.SingletonUser;

/**
 *
 * @author ASUS
 */
public class ShoppingCartService {

    private int currentUserId = 0;
    private static ShoppingCartService instance = null;
    private SingletonUser singUser = SingletonUser.getInstance();
    private final ShoppingCartManager shoppingCart = ShoppingCartManager.getInstance();

    private ShoppingCartService() {
	currentUserId = singUser.getIdUser();
    }

    public float getSum() { return shoppingCart.getSum();  }

    public static ShoppingCartService getInstance() {

	if (instance == null) {
	    instance = new ShoppingCartService();
	}
	return instance;

    }

    public ArrayList<CartItem> collectCartItems() {

	return shoppingCart.getProdsWithQuantity();
    }

    public CustomResponse addToCart(Product prod) {

// problem 1: if the cart is empty
	if (shoppingCart == null) {

	    ArrayList<CartItem> items = new ArrayList<>();

	    items.add(new CartItem(prod, 1));

	    shoppingCart.setSum(shoppingCart.getSum() + prod.getPriceproduct());

	    shoppingCart.setIdUser(currentUserId);
	    shoppingCart.setProdsWithQuantity(items);

	}
// problem 2: if the item doesn't exist in the cart, we simply add it:

	boolean found = false;

	for (CartItem i : shoppingCart.getProdsWithQuantity()) {
	    found = i.getProduct().getNameproduct().equals(prod.getNameproduct());
	}

	System.out.println("before else: " + found);

	if (found == false) {
	    shoppingCart.getProdsWithQuantity().add(new CartItem(prod, 1));
	    shoppingCart.setSum(shoppingCart.getSum() + prod.getPriceproduct());
	} // problem 3: if the same object exists already
	else {

	    for (CartItem item : shoppingCart.getProdsWithQuantity()) {
		if (item.getProduct().getNameproduct().equals(prod.getNameproduct())) {

		    System.out.println(prod);

		    //shoppingCart.getProdsWithQuantity().remove(item);
		    item.setQuantity(item.getQuantity() + 1);
		    shoppingCart.setSum(shoppingCart.getSum() + prod.getPriceproduct());
		    //shoppingCart.getProdsWithQuantity().add(item);
		}
	    }

	    return new CustomResponse("Quantity updated !");
	}

	return new CustomResponse("Product added to cart !");
    }

    public CustomResponse takeFromCart(CartItem item) {

	int idx = Collections.binarySearch(shoppingCart.getProdsWithQuantity(), item);

	if ((idx != -1) && (item.getQuantity() - 1 > 0)) {
	    item.setQuantity(item.getQuantity() - 1);
	    shoppingCart.setSum(shoppingCart.getSum() - item.getProduct().getPriceproduct());
	    return new CustomResponse("Quantity updated !");
	}

	shoppingCart.getProdsWithQuantity().remove(item);
	shoppingCart.setSum(shoppingCart.getSum() - item.getProduct().getPriceproduct());

	return new CustomResponse("Product removed from cart !");
    }

    public void clearCart() {
	singUser = null;
    }
}
