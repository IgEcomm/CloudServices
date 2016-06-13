package com.ig.ecommsolutions.product.service;

import org.springframework.http.ResponseEntity;

import com.ig.ecommsolutions.product.domain.UserCart;

public interface CartService {

	public ResponseEntity<?> addCart(UserCart cart);
	public ResponseEntity<?> updateCart(String id, UserCart cart);
	public ResponseEntity<?> deleteCart(String id);
	public ResponseEntity<?> findCartByUserId(String userId);
	
}
