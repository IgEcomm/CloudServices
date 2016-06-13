package com.ig.ecommsolutions.product.rest;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ig.ecommsolutions.product.domain.RecentlyViewed;

public interface RecentlyViewedRepository extends MongoRepository<RecentlyViewed, String>{

	public List<RecentlyViewed> findByUserId(String userId);
	
	public List<RecentlyViewed> findByUserIdAndInventoryId(String userId, String inventoryId);
	
}
