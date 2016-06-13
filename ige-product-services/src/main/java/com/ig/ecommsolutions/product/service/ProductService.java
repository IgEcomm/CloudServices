package com.ig.ecommsolutions.product.service;

import org.springframework.http.ResponseEntity;

import com.ig.ecommsolutions.product.domain.Product;

public interface ProductService {

	public ResponseEntity<?> addProduct(Product product);
	public ResponseEntity<?> updateProduct(String id, Product product);
	public ResponseEntity<?> deleteProduct(String id);
	public ResponseEntity<?> findProductById(String id);
	public ResponseEntity<?> findAllProducts(String[] tag);
	
}
