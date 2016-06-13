package com.ig.ecommsolutions.product.rest;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ig.ecommsolutions.product.domain.UserCart;

public interface CartRepository extends MongoRepository<UserCart, String> {

	public List<UserCart> findByUserId(String userId);
	
	public List<UserCart> findByUserIdAndInventoryId(String userId, String inventoryId);
	
	@Query("{'userId' : ?0, 'productId' : {'$in' : ?1}}")
	public List<UserCart> findAllByUIdAndProIdIn(String userId, Object[] proIds);
	
}