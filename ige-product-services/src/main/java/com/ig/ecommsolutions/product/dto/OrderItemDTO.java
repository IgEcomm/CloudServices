/**
 * 
 */
package com.ig.ecommsolutions.product.dto;

import com.ig.ecommsolutions.product.domain.Inventory;
import com.ig.ecommsolutions.product.domain.Product;

/**
 * @author Yashpal.Singh
 *
 */
public class OrderItemDTO {

	private String retailerId;
	private Inventory inventory;
	private Product product;
	
	public OrderItemDTO() {
		
	}

	public OrderItemDTO(String retailerId, Inventory inventory, Product product) {
		this.retailerId = retailerId;
		this.product = product;
		this.inventory = inventory;
	}
	
	public String getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(String retailerId) {
		this.retailerId = retailerId;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
