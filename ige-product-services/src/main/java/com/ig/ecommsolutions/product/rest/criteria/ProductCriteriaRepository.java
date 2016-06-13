package com.ig.ecommsolutions.product.rest.criteria;

import java.util.List;

import com.ig.ecommsolutions.product.domain.Product;

public interface ProductCriteriaRepository {

	public List<Product> findByCriteria(String name, String description, String brand, String category);
	
}
