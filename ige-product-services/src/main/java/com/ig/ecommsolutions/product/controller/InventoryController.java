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

import com.ig.ecommsolutions.product.domain.Inventory;
import com.ig.ecommsolutions.product.service.InventoryService;

@RestController()
@RequestMapping("/inventoryService")
public class InventoryController {

	private static final Logger log = LoggerFactory.getLogger(InventoryController.class);

	@Autowired
	private InventoryService service;

	@RequestMapping(value = "/products", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllProducts(@RequestParam(value = "retailerId", required = true) String retailerId) {
		log.info("Get all inventories...");
		return service.findAllInventories(retailerId);
	}
	
	@RequestMapping(value = "/products/sponsored", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllSponsoredProducts(@RequestParam(value = "retailerId", required = true) String retailerId) {
		log.info("Get all inventories...");
		return service.findAllSponsoredInventories(retailerId);
	}

	@RequestMapping(value = "/products/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getProductByID(@PathVariable("id") String id) {
		log.info("Get inventory by id...");
		return service.findInventoryById(id);
	}

	@RequestMapping(value = "/products", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addProduct(@RequestBody Inventory inventory) {
		log.info("Create inventory...");
		return service.addInventory(inventory);
	}

	@RequestMapping(value = "/products/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateProduct(@PathVariable("id") String id, @RequestBody Inventory inventory) {
		log.info("Update inventory...");
		return service.updateInventory(id, inventory);
	}

	@RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteProduct(@PathVariable("id") String id) {
		log.info("Delete inventory...");
		return service.deleteInventory(id);
	}

}
