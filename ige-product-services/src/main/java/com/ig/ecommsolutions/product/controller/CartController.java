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

import com.ig.ecommsolutions.product.domain.UserCart;
import com.ig.ecommsolutions.product.service.CartService;

@RestController()
@RequestMapping("/cartService")
public class CartController {

	private static final Logger log = LoggerFactory.getLogger(CartController.class);
	
	@Autowired
	private CartService service;

	@RequestMapping(value = "/carts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getCarts(@RequestParam(value = "userId", required = true) String userId) {
		log.info("Get all user carts...");
		return service.findCartByUserId(userId);
	}

	@RequestMapping(value = "/carts", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addCart(@RequestBody UserCart cart) {
		log.info("Create user cart...");
		return service.addCart(cart);
	}

	@RequestMapping(value = "/carts/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateCart(@PathVariable("id") String id, @RequestBody UserCart cart) {
		log.info("Update user cart...");
		return service.updateCart(id, cart);
	}

	@RequestMapping(value = "/carts/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteCart(@PathVariable("id") String id) {
		log.info("Delete user cart...");
		return service.deleteCart(id);
	}

}
