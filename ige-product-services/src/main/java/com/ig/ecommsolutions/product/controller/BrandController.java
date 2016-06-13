/**
 * 
 */
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
import org.springframework.web.bind.annotation.RestController;

import com.ig.ecommsolutions.product.domain.Brand;
import com.ig.ecommsolutions.product.service.BrandService;

/**
 * @author yashpal.singh
 *
 */
@RestController()
@RequestMapping("/brandService")
public class BrandController {

	private static final Logger log = LoggerFactory.getLogger(BrandController.class);
	
	@Autowired
	private BrandService service;
	
	@RequestMapping(value = "/brands", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllBrands() {
		log.info("Get all brands...");
		return service.findAllBrands();
	}
	
	@RequestMapping(value = "/topbrands", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getTopBrands() {
		log.info("Get top brands...");
		return service.findTopBrands();
	}
	
	@RequestMapping(value = "/brands/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getBrandByID(@PathVariable("id") String id) {
		log.info("Get brand by id...");
		return service.findBrandById(id);
	}
	
	@RequestMapping(value = "/brands", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addBrand(@RequestBody Brand brand) {
		log.info("Create brand...");
		return service.addBrand(brand);
	}
	
	@RequestMapping(value = "/brands/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateProduct(@PathVariable("id") String id, @RequestBody Brand brand) {
		log.info("Update brand...");
		return service.updateBrand(id, brand);
	}

	@RequestMapping(value = "/brands/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteBrand(@PathVariable("id") String id) {
		log.info("Delete brand...");
		return service.deleteBrand(id);
	}
	
}
