package com.ig.ecommsolutions.product.service;

import org.springframework.http.ResponseEntity;

import com.ig.ecommsolutions.product.domain.Order;

public interface OrderService {

	public ResponseEntity<?> addOrder(Order order);
//	public ResponseEntity<?> addMulipleOrder(List<Order> orders);
	public ResponseEntity<?> updateOrder(String id, Order order);
	public ResponseEntity<?> deleteOrder(String id);
	public ResponseEntity<?> findOrderById(String id);
	public ResponseEntity<?> findAllOrders(String userId);

}
