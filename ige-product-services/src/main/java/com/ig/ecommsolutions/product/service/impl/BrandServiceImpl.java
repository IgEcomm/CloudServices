/**
 * 
 */
package com.ig.ecommsolutions.product.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ig.ecommsolutions.product.domain.Brand;
import com.ig.ecommsolutions.product.dto.MessageDTO;
import com.ig.ecommsolutions.product.rest.BrandRepository;
import com.ig.ecommsolutions.product.service.BrandService;

/**
 * @author yashpal.singh
 *
 */
@Service
public class BrandServiceImpl implements BrandService {

	private static final Logger log = LoggerFactory.getLogger(BrandServiceImpl.class);

	@Autowired
	private BrandRepository repository;

	@Override
	public ResponseEntity<?> addBrand(Brand brand) {

		try {
			Brand persistedBrand = repository.save(brand);
			return new ResponseEntity<>(persistedBrand, HttpStatus.CREATED);
		} catch (DuplicateKeyException me) {
			log.error("Database Exception: " + me.getMessage());
			return new ResponseEntity<>(new MessageDTO("Duplicate brand name"), HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (Exception e) {
			log.error("Failed to create product: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> updateBrand(String id, Brand brand) {

		try {
			Brand existingBrand = repository.findOne(id);

			if (existingBrand == null) {
				return new ResponseEntity<>(new MessageDTO("Brand not found with id: " + id), HttpStatus.NOT_FOUND);
			} else {
				brand.setDocId(existingBrand.getDocId());
			}

			Brand persistedBrand = repository.save(brand);
			return new ResponseEntity<>(persistedBrand, HttpStatus.OK);

		} catch (DuplicateKeyException me) {
			log.error("Database Exception: " + me.getMessage());
			return new ResponseEntity<>(new MessageDTO("Duplicate brand name"), HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (Exception e) {
			log.error("Failed to update brand: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> deleteBrand(String id) {

		try {
			Brand existingBrand = repository.findOne(id);

			if (existingBrand == null) {
				return new ResponseEntity<>(new MessageDTO("Brand not found with id: " + id), HttpStatus.NOT_FOUND);
			}
			repository.delete(id);
			return new ResponseEntity<>(new MessageDTO("Brand has been deleted successsfully"), HttpStatus.OK);

		} catch (Exception e) {
			log.error("Failed to delete brand: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> findBrandById(String id) {

		try {
			Brand existingBrand = repository.findOne(id);

			if (existingBrand == null) {
				return new ResponseEntity<>(new MessageDTO("Brand not found with id: " + id), HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<>(existingBrand, HttpStatus.OK);

		} catch (Exception e) {
			log.error("Failed to find brand by id: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> findAllBrands() {

		try {
			List<Brand> brands = repository.findAll();

			if (brands == null || brands.isEmpty()) {
				return new ResponseEntity<>(new MessageDTO("No record found."), HttpStatus.OK);
			}
			return new ResponseEntity<>(brands, HttpStatus.OK);

		} catch (Exception e) {
			log.error("Failed to find brands: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> findTopBrands() {

		try {
			List<Brand> brands = repository.findByRatingGreaterThan(2);

			if (brands == null || brands.isEmpty()) {
				return new ResponseEntity<>(new MessageDTO("No record found."), HttpStatus.OK);
			}
			return new ResponseEntity<>(brands, HttpStatus.OK);

		} catch (Exception e) {
			log.error("Failed to find brands: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
