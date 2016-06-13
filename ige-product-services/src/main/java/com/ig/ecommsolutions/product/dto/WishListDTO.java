package com.ig.ecommsolutions.product.dto;

import com.ig.ecommsolutions.product.domain.Inventory;
import com.ig.ecommsolutions.product.domain.Product;

public class WishListDTO {

	private String id;
	private Product product;
	private Inventory inventory;
	private Double qty = new Double(0.0);
	private boolean inCartList = false;
	private String userCartId;

	public WishListDTO() {

	}

	public WishListDTO(String id, Product product, Inventory inventory, Double qty, boolean inCartList) {
		this.id = id;
		this.product = product;
		this.inventory = inventory;
		this.qty = qty;
		this.inCartList = inCartList;
	}

	public WishListDTO(String id, Product product, Inventory inventory, Double qty, boolean inCartList,
			String userCartId) {
		this.id = id;
		this.product = product;
		this.inventory = inventory;
		this.qty = qty;
		this.inCartList = inCartList;
		this.userCartId = userCartId;
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

	public boolean isInCartList() {
		return inCartList;
	}

	public void setInCartList(boolean inCartList) {
		this.inCartList = inCartList;
	}

	public String getUserCartId() {
		return userCartId;
	}

	public void setUserCartId(String userCartId) {
		this.userCartId = userCartId;
	}
}
