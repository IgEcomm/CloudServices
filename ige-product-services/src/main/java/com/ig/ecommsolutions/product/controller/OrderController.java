package com.ig.ecommsolutions.product.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ig.ecommsolutions.product.domain.Order;
import com.ig.ecommsolutions.product.service.OrderService;

@RestController()
@RequestMapping("/orderService")
public class OrderController {

	private static final Logger log = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderService service;

	@RequestMapping(value = "/orders", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getOrders(@RequestParam(value = "userId", required = true) String userId) {
		log.info("Get all orders...");
		return service.findAllOrders(userId);
	}

	@RequestMapping(value = "/orders/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getOrderID(@PathVariable("id") String id) {
		log.info("Get order by id...");
		return service.findOrderById(id);
	}

	@RequestMapping(value = "/orders", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addOrder(@RequestBody Order order) {
		log.info("Create order...");
		return service.addOrder(order);
	}

	// @RequestMapping(value = "/orders/multi", method = RequestMethod.POST,
	// consumes = MediaType.APPLICATION_JSON_VALUE, produces =
	// MediaType.APPLICATION_JSON_VALUE)
	// public ResponseEntity<?> addMultipleOrder(@RequestBody List<Order>
	// orders) {
	// log.info("Create muliple orders...");
	// return service.addMulipleOrder(orders);
	// }

	@RequestMapping(value = "/orders/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateOrder(@PathVariable("id") String id, @RequestBody Order order) {
		log.info("Update order...");
		return service.updateOrder(id, order);
	}

	@RequestMapping(value = "/orders/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteOrder(@PathVariable("id") String id) {
		log.info("Delete order...");
		return service.deleteOrder(id);
	}

}
