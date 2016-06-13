package com.ig.ecommsolutions.product.rest.criteria.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.ig.ecommsolutions.product.domain.Product;
import com.ig.ecommsolutions.product.rest.criteria.ProductCriteriaRepository;

@Repository
public class ProductCriteriaRepositoryImpl implements ProductCriteriaRepository {

	@Autowired
    protected MongoTemplate mongoTemplate;
	
	@Override
	public List<Product> findByCriteria(String name, String description, String brand, String category) {

		Criteria criteria = null;
		
		if(name != null) {
			criteria = Criteria.where("name").regex(name);
		}
		
		if(description != null) {
			if(criteria == null)
				criteria = Criteria.where("description").regex(description);
			else
				criteria.and("description").regex(description);
		}
		
		if(brand != null) {
			if(criteria == null)
				criteria = Criteria.where("brand").is(brand);
			else
				criteria.and("brand").is(brand);
		}
		
		if(category != null) {
			if(criteria == null)
				criteria = Criteria.where("category").is(category);
			else
				criteria.and("category").is(category);
		}
		
		if(criteria != null) 
			return mongoTemplate.find(new Query(criteria), Product.class);
		else		
			return mongoTemplate.findAll(Product.class);
		
	}

}
