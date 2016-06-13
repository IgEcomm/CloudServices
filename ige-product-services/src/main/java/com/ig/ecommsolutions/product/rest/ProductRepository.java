package com.ig.ecommsolutions.product.rest;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.ig.ecommsolutions.product.domain.Product;

public interface ProductRepository extends MongoRepository<Product, String> {

	@Query("{'docId' : {'$in' : ?0}}")
	public List<Product> findAllByIds(Object[] ids);

	public List<Product> findByCategory(String category);

	public List<Product> findBySubCategory(String subcategory);

	@Query("{'brand' : {'$in' : ?0}}")
	public List<Product> findByBrand(String[] brand);

	public List<Product> findByCategoryAndSubCategory(String category, String subCategory);

	@Query("{'category' : ?0, 'brand' : {'$in' : ?1}}")
	public List<Product> findByCategoryAndBrand(String category, String[] brand);

	@Query("{'subCategory' : ?0, 'brand' : {'$in' : ?1}}")
	public List<Product> findBySubCategoryAndBrand(String subCategory, String[] brand);

	@Query("{'category' : ?0, 'subCategory' : ?1, 'brand' : {'$in' : ?2}}")
	public List<Product> findByCategoryAndSubCategoryAndBrand(String category, String subCategory, String[] brand);

	@Query("{'tags' : {'$in' : ?0}}")
	public List<Product> findByTags(String[] Tag);

}
