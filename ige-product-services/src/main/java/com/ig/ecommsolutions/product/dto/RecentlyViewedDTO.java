package com.ig.ecommsolutions.product.dto;

import java.util.Date;

import com.ig.ecommsolutions.product.domain.Inventory;
import com.ig.ecommsolutions.product.domain.Product;

public class RecentlyViewedDTO {

	private String id;
	private Product product;
	private Inventory inventory;
	private Date reviewedDate;

	public RecentlyViewedDTO() {

	}

	public RecentlyViewedDTO(String id, Product product, Inventory inventory, Date reviewedDate) {
		this.id = id;
		this.product = product;
		this.inventory = inventory;
		this.reviewedDate = reviewedDate;
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

	public Date getReviewedDate() {
		return reviewedDate;
	}

	public void setReviewedDate(Date reviewedDate) {
		this.reviewedDate = reviewedDate;
	}

}
