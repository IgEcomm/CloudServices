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

import com.ig.ecommsolutions.product.domain.WishList;
import com.ig.ecommsolutions.product.service.WishListService;

@RestController()
@RequestMapping("/wishlistService")
public class WishListController {

	private static final Logger log = LoggerFactory.getLogger(WishListController.class);
	
	@Autowired
	private WishListService service;

	@RequestMapping(value = "/wishLists", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getWishLists(@RequestParam(value = "userId", required = true) String userId) {
		log.info("Get all wishlist...");
		return service.findWishListByUserId(userId);
	}

	@RequestMapping(value = "/wishLists", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addWishList(@RequestBody WishList wishList) {
		log.info("Create wishlist...");
		return service.addWishList(wishList);
	}

	@RequestMapping(value = "/wishLists/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateWishList(@PathVariable("id") String id, @RequestBody WishList wishList) {
		log.info("Update wishlist...");
		return service.updateWishList(id, wishList);
	}

	@RequestMapping(value = "/wishLists/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteWishList(@PathVariable("id") String id) {
		log.info("Delete wishlist...");
		return service.deleteWishList(id);
	}

}
