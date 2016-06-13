/**
 * 
 */
package com.ig.ecommsolutions.product.service;

import org.springframework.http.ResponseEntity;

import com.ig.ecommsolutions.product.domain.Brand;

/**
 * @author yashpal.singh
 *
 */
public interface BrandService {
	
	public ResponseEntity<?> addBrand(Brand brand);
	public ResponseEntity<?> updateBrand(String id, Brand brand);
	public ResponseEntity<?> deleteBrand(String id);
	public ResponseEntity<?> findBrandById(String id);
	public ResponseEntity<?> findAllBrands();
	public ResponseEntity<?> findTopBrands();
	
}
