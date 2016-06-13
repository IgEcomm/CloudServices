/**
 * 
 */
package com.ig.ecommsolutions.product.domain;

/**
 * @author Yashpal.Singh
 *
 */
public class OrderItem {

	private String productId;
	private String retailerId;
	private String inventoryId;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(String retailerId) {
		this.retailerId = retailerId;
	}

	public String getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(String inventoryId) {
		this.inventoryId = inventoryId;
	}

}
