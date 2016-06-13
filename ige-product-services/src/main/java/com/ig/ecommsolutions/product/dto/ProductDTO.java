package com.ig.ecommsolutions.product.dto;

import com.ig.ecommsolutions.product.domain.Inventory;
import com.ig.ecommsolutions.product.domain.Product;

public class ProductDTO {

	private Inventory inventory;
	private Product product;

	public ProductDTO() {

	}

	public ProductDTO(Inventory inventory, Product product) {
		this.inventory = inventory;
		this.product = product;
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
