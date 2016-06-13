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

import com.ig.ecommsolutions.product.domain.RecentlyViewed;
import com.ig.ecommsolutions.product.service.RecentlyViewedService;

@RestController()
@RequestMapping("/recentlyViewedService")
public class RecentlyViewedController {

	private static final Logger log = LoggerFactory.getLogger(CartController.class);

	@Autowired
	private RecentlyViewedService service;

	@RequestMapping(value = "/vieweds", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getRecentlyViewed(@RequestParam(value = "userId", required = true) String userId) {
		log.info("Get all user recently viewed products...");
		return service.findRecentlyViewedByUserId(userId);
	}

	@RequestMapping(value = "/vieweds", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addRecentlyViewed(@RequestBody RecentlyViewed viewed) {
		log.info("Create user recently viewed product...");
		return service.addRecentlyViewed(viewed);
	}

	@RequestMapping(value = "/vieweds/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateRecentlyViewed(@PathVariable("id") String id, @RequestBody RecentlyViewed viewed) {
		log.info("Update user recently viewed product...");
		return service.updateRecentlyViewed(id, viewed);
	}

	@RequestMapping(value = "/vieweds/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteRecentlyViewed(@PathVariable("id") String id) {
		log.info("Delete user recently viewed product...");
		return service.deleteRecentlyViewed(id);
	}

}
