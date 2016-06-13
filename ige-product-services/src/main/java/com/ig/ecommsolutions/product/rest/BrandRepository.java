/**
 * 
 */
package com.ig.ecommsolutions.product.rest;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ig.ecommsolutions.product.domain.Brand;

/**
 * @author yashpal.singh
 *
 */
public interface BrandRepository extends MongoRepository<Brand, String> {

	public List<Brand> findByRatingGreaterThan(int rating);
	
	public List<Brand> findByName(String name);
	
}
