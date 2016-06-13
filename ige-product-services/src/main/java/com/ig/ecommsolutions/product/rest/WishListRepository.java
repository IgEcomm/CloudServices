package com.ig.ecommsolutions.product.rest;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ig.ecommsolutions.product.domain.WishList;

public interface WishListRepository extends MongoRepository<WishList, String> {
	
	public List<WishList> findByUserId(String userId);
	
	public List<WishList> findByUserIdAndInventoryId(String userId, String inventoryId);
	
	@Query("{'userId' : ?0, 'productId' : {'$in' : ?1}}")
	public List<WishList> findAllByUIdAndProIdIn(String userId, Object[] proIds);

}