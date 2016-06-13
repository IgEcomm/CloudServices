package com.ig.ecommsolutions.product.rest;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ig.ecommsolutions.product.domain.Order;

public interface OrderRepository extends MongoRepository<Order, String> {

	public List<Order> findByUserId(String userId);
	
}
