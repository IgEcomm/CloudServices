package com.ig.ecommsolutions.product.service;

import org.springframework.http.ResponseEntity;

import com.ig.ecommsolutions.product.domain.Inventory;

public interface InventoryService {

	public ResponseEntity<?> addInventory(Inventory inventory);

	public ResponseEntity<?> updateInventory(String id, Inventory inventory);

	public ResponseEntity<?> deleteInventory(String id);

	public ResponseEntity<?> findInventoryById(String id);

	public ResponseEntity<?> findAllInventories(String retailerId);
	
	public ResponseEntity<?> findAllSponsoredInventories(String retailerId);

	public ResponseEntity<?> findInventoryProductById(String id, String userId);

	public ResponseEntity<?> findAllInventoryProducts(String userId, String category, String subCategory,
			String[] brand);
	
	public ResponseEntity<?> findAllSponsorInventoryProducts();

}
