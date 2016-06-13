package com.ig.ecommsolutions.product.service;

import org.springframework.http.ResponseEntity;

import com.ig.ecommsolutions.product.domain.RecentlyViewed;

public interface RecentlyViewedService {

	public ResponseEntity<?> addRecentlyViewed(RecentlyViewed viewed);
	public ResponseEntity<?> deleteRecentlyViewed(String id);
	public ResponseEntity<?> findRecentlyViewedByUserId(String userId);
	public ResponseEntity<?> updateRecentlyViewed(String id, RecentlyViewed viewed);
	
}
