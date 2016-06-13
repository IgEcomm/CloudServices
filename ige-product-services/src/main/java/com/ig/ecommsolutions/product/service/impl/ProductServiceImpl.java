package com.ig.ecommsolutions.product.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ig.ecommsolutions.product.domain.Product;
import com.ig.ecommsolutions.product.dto.MessageDTO;
import com.ig.ecommsolutions.product.rest.ProductRepository;
import com.ig.ecommsolutions.product.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductRepository repository;

	@Override
	public ResponseEntity<?> addProduct(Product product) {

		try {
			Product persistedProduct = repository.save(product);
			return new ResponseEntity<>(persistedProduct, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("Failed to create product: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> updateProduct(String id, Product product) {

		try {
			Product existingProduct = repository.findOne(id);

			if (existingProduct == null) {
				return new ResponseEntity<>(new MessageDTO("Product not found with id: " + id), HttpStatus.NOT_FOUND);
			} else {
				product.setDocId(existingProduct.getDocId());
			}
			Product persistedProduct = repository.save(product);
			return new ResponseEntity<>(persistedProduct, HttpStatus.OK);

		} catch (Exception e) {
			log.error("Failed to update product: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> deleteProduct(String id) {

		try {
			Product existingProduct = repository.findOne(id);

			if (existingProduct == null) {
				return new ResponseEntity<>(new MessageDTO("Product not found with id: " + id), HttpStatus.NOT_FOUND);
			}
			repository.delete(id);
			return new ResponseEntity<>(new MessageDTO("Product has been deleted successsfully"), HttpStatus.OK);

		} catch (Exception e) {
			log.error("Failed to delete product: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> findProductById(String id) {

		try {
			Product existingProduct = repository.findOne(id);

			if (existingProduct == null) {
				return new ResponseEntity<>(new MessageDTO("Product not found with id: " + id), HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<>(existingProduct, HttpStatus.OK);

		} catch (Exception e) {
			log.error("Failed to find product by id: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> findAllProducts(String[] tag) {

		try {
			List<Product> products = new ArrayList<Product>();
			if (tag == null)
				products = repository.findAll();
			else
				products = repository.findByTags(tag);

			if (products == null || products.isEmpty()) {
				return new ResponseEntity<>(new MessageDTO("No record found."), HttpStatus.OK);
			}
			return new ResponseEntity<>(products, HttpStatus.OK);

		} catch (Exception e) {
			log.error("Failed to find products: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
