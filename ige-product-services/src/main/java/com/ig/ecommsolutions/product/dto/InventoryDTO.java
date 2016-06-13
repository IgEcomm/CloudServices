package com.ig.ecommsolutions.product.dto;

import com.ig.ecommsolutions.product.domain.Inventory;
import com.ig.ecommsolutions.product.domain.Product;

public class InventoryDTO {

	private Inventory inventory;
	private Product product;
	private boolean inWishList = false;
	private boolean inCartList = false;
	private boolean ordered = false;
	private String wishListId;
	private String userCartId;

	public InventoryDTO() {

	}
	
	public InventoryDTO(Inventory inventory, Product product) {
		this.inventory = inventory;
		this.product = product;
	}

	public InventoryDTO(Inventory inventory, Product product, boolean inWishList, boolean inCartList, boolean ordered) {
		this.inventory = inventory;
		this.product = product;
		this.inWishList = inWishList;
		this.inCartList = inCartList;
		this.ordered = ordered;
	}

	public InventoryDTO(Inventory inventory, Product product, boolean inWishList, boolean inCartList, boolean ordered,
			String wishListId, String userCartId) {
		this.inventory = inventory;
		this.product = product;
		this.inWishList = inWishList;
		this.inCartList = inCartList;
		this.ordered = ordered;
		this.wishListId = wishListId;
		this.userCartId = userCartId;
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

	public boolean isInWishList() {
		return inWishList;
	}

	public void setInWishList(boolean inWishList) {
		this.inWishList = inWishList;
	}

	public boolean isInCartList() {
		return inCartList;
	}

	public void setInCartList(boolean inCartList) {
		this.inCartList = inCartList;
	}

	public boolean isOrdered() {
		return ordered;
	}

	public void setOrdered(boolean ordered) {
		this.ordered = ordered;
	}

	public String getWishListId() {
		return wishListId;
	}

	public void setWishListId(String wishListId) {
		this.wishListId = wishListId;
	}

	public String getUserCartId() {
		return userCartId;
	}

	public void setUserCartId(String userCartId) {
		this.userCartId = userCartId;
	}

}
