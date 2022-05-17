/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.entities;

import java.util.Comparator;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author ASUS
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItem implements Comparable<CartItem>, Comparator<CartItem> {

    private Product product;
    private int quantity;

    @Override
    public int hashCode() {

	return Objects.hashCode(this.product.getNameproduct());
    }

    @Override
    public boolean equals(Object obj) {

	if (getClass() != obj.getClass()) {
	    return false;
	}
	final CartItem other = (CartItem) obj;

	if (!other.getProduct().getNameproduct().equals(product.getNameproduct())) {
	    return false;
	}
	return true;
    }

    @Override
    public int compareTo(CartItem o) {
	return product.getNameproduct().compareTo( o.getProduct().getNameproduct());
    }

    @Override
    public int compare(CartItem o1, CartItem o2) {

	return o1.getProduct().getIdproduct() - o2.getProduct().getIdproduct();
	
    }

}
