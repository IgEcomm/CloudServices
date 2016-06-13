package com.ig.ecommsolutions.product.rest;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ig.ecommsolutions.product.domain.Inventory;

public interface InventoryRepository extends MongoRepository<Inventory, String> {
	
	public List<Inventory> findByRetailerId(String retailerId);
	
	@Query("{'retailerId' : ?0, 'sponsored' : true}")
	public List<Inventory> findByRetailerAndSponsored(String retailerId);
	
	@Query("{'sponsored' : true}")
	public List<Inventory> findBySponsored();
	
	@Query("{'docId' : {'$in' : ?0}}")
	public List<Inventory> findAllByIds(Object[] ids);
	
	@Query("{'productId' : {'$in' : ?0}}")
	public List<Inventory> findAllByProductId(Object[] ids);

}
