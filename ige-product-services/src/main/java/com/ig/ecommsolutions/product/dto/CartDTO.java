package com.ig.ecommsolutions.product.dto;

import com.ig.ecommsolutions.product.domain.Inventory;
import com.ig.ecommsolutions.product.domain.Product;

public class CartDTO {

	private String id;
	private Product product;
	private Inventory inventory;
	private Double qty = new Double(0.0);

	public CartDTO() {

	}

	public CartDTO(String id, Product product, Inventory inventory, Double qty) {
		this.id = id;
		this.product = product;
		this.inventory = inventory;
		this.qty = qty;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

}
