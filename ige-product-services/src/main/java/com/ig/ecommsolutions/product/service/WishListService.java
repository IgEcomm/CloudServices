package com.ig.ecommsolutions.product.service;

import org.springframework.http.ResponseEntity;

import com.ig.ecommsolutions.product.domain.WishList;

public interface WishListService {

	public ResponseEntity<?> addWishList(WishList wishList);
	public ResponseEntity<?> updateWishList(String id, WishList wishList);
	public ResponseEntity<?> deleteWishList(String id);
	public ResponseEntity<?> findWishListByUserId(String userId);
	
}
