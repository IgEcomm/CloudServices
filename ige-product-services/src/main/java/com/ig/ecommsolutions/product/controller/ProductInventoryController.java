package com.ig.ecommsolutions.product.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ig.ecommsolutions.product.service.InventoryService;

@RestController()
@RequestMapping("/productService")
public class ProductInventoryController {

	private static final Logger log = LoggerFactory.getLogger(ProductInventoryController.class);

	@Autowired
	private InventoryService service;

	@RequestMapping(value = "/products", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllProducts(@RequestParam(value = "userId", required = false) String userId,
			@RequestParam(value = "category", required = false) String category,
			@RequestParam(value = "subCategory", required = false) String subCategory,
			@RequestParam(value = "brand", required = false) String[] brand) {
		log.info("Get all products...");
		return service.findAllInventoryProducts(userId, category, subCategory, brand);
	}
	
	@RequestMapping(value = "/products/sponsored", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllSponsoredProducts() {
		log.info("Get all products...");
		return service.findAllSponsorInventoryProducts();
	}

	@RequestMapping(value = "/products/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getProductByID(@PathVariable("id") String id,
			@RequestParam(value = "userId", required = false) String userId) {
		log.info("Get product by id...");
		return service.findInventoryProductById(id, userId);
	}

}
