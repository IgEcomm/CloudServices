package com.ig.ecommsolutions.product.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ig.ecommsolutions.product.domain.Inventory;
import com.ig.ecommsolutions.product.domain.Product;
import com.ig.ecommsolutions.product.domain.RecentlyViewed;
import com.ig.ecommsolutions.product.dto.MessageDTO;
import com.ig.ecommsolutions.product.dto.RecentlyViewedDTO;
import com.ig.ecommsolutions.product.rest.InventoryRepository;
import com.ig.ecommsolutions.product.rest.ProductRepository;
import com.ig.ecommsolutions.product.rest.RecentlyViewedRepository;
import com.ig.ecommsolutions.product.service.RecentlyViewedService;

@Service
public class RecentlyViewedServiceImpl implements RecentlyViewedService {

	private static final Logger log = LoggerFactory.getLogger(RecentlyViewedServiceImpl.class);

	@Autowired
	private RecentlyViewedRepository repository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private InventoryRepository inventoryRepository;
	@Autowired
	private CommonService service;

	@Override
	public ResponseEntity<?> addRecentlyViewed(RecentlyViewed viewed) {

		try {

			if (viewed == null || viewed.getProductId() == null || viewed.getInventoryId() == null
					|| viewed.getUserId() == null)
				return new ResponseEntity<>(
						new MessageDTO("Validation Error: ProductID, InventoryID, and UserID are required."),
						HttpStatus.UNPROCESSABLE_ENTITY);

			Product product = productRepository.findOne(viewed.getProductId());
			if (product == null)
				return new ResponseEntity<>(
						new MessageDTO("Invalid productId. Product not found with id: " + viewed.getProductId()),
						HttpStatus.NOT_FOUND);

			Inventory inventory = inventoryRepository.findOne(viewed.getInventoryId());
			if (inventory == null)
				return new ResponseEntity<>(
						new MessageDTO("Invalid inventoryId. Inventory not found with id: " + viewed.getInventoryId()),
						HttpStatus.NOT_FOUND);

			if (!product.getDocId().equalsIgnoreCase(inventory.getProductId()))
				return new ResponseEntity<>(
						new MessageDTO("Validation Error: ProductID and InventoryID are not compatible."),
						HttpStatus.UNPROCESSABLE_ENTITY);

			// Validate duplicate recentlyViewed
			boolean isDuplicate = isDuplicate(viewed.getUserId(), viewed.getInventoryId());
			if (isDuplicate)
				return new ResponseEntity<>(
						new MessageDTO("Validation Error: Product is already available in recently viewed list."),
						HttpStatus.UNPROCESSABLE_ENTITY);

			RecentlyViewed persistedRecentlyViewed = repository.save(viewed);
			return new ResponseEntity<>(persistedRecentlyViewed, HttpStatus.CREATED);

		} catch (Exception e) {
			log.error("Failed to create recently view product: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> updateRecentlyViewed(String id, RecentlyViewed viewed) {

		try {

			RecentlyViewed existingRecentlyViewed = repository.findOne(id);
			if (existingRecentlyViewed == null) {
				return new ResponseEntity<>(new MessageDTO("Recently viewed product not found with id: " + id),
						HttpStatus.NOT_FOUND);
			} else {
				viewed.setDocId(existingRecentlyViewed.getDocId());
				viewed.setUserId(existingRecentlyViewed.getUserId());
			}

			if (viewed.getProductId() == null && viewed.getInventoryId() == null) {
				viewed.setProductId(existingRecentlyViewed.getProductId());
				viewed.setInventoryId(existingRecentlyViewed.getInventoryId());
			} else {

				if (viewed.getProductId() == null) {
					viewed.setProductId(existingRecentlyViewed.getProductId());
				} else {
					Product product = productRepository.findOne(viewed.getProductId());
					if (product == null)
						return new ResponseEntity<>(
								new MessageDTO(
										"Invalid productId. Product not found with id: " + viewed.getProductId()),
								HttpStatus.NOT_FOUND);
				}

				if (viewed.getInventoryId() == null)
					viewed.setInventoryId(existingRecentlyViewed.getInventoryId());
				Inventory inventory = inventoryRepository.findOne(viewed.getInventoryId());
				if (inventory == null)
					return new ResponseEntity<>(
							new MessageDTO(
									"Invalid inventoryId. Inventory not found with id: " + viewed.getInventoryId()),
							HttpStatus.NOT_FOUND);

				if (!viewed.getProductId().equalsIgnoreCase(inventory.getProductId()))
					return new ResponseEntity<>(
							new MessageDTO("Validation Error: ProductID and InventoryID are not compatible."),
							HttpStatus.UNPROCESSABLE_ENTITY);
			}

			// Validate if duplicate entry
			List<RecentlyViewed> recentlyViewedProducts = repository.findByUserIdAndInventoryId(viewed.getUserId(),
					viewed.getInventoryId());
			for (RecentlyViewed recentView : recentlyViewedProducts) {
				if (recentView.getDocId() != viewed.getDocId())
					return new ResponseEntity<>(
							new MessageDTO("Validation Error: Product is already available in recently viewed list."),
							HttpStatus.UNPROCESSABLE_ENTITY);
			}

			RecentlyViewed persistedRecentlyViewed = repository.save(viewed);
			return new ResponseEntity<>(persistedRecentlyViewed, HttpStatus.CREATED);

		} catch (Exception e) {
			log.error("Failed to update recently viewed product: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> deleteRecentlyViewed(String id) {

		try {

			RecentlyViewed existingRecentlyViewed = repository.findOne(id);
			if (existingRecentlyViewed == null)
				return new ResponseEntity<>(new MessageDTO("Recently Viewed Product not found with id: " + id),
						HttpStatus.NOT_FOUND);

			repository.delete(id);
			return new ResponseEntity<>(new MessageDTO("Recently Viewed Product has been deleted successsfully"),
					HttpStatus.OK);

		} catch (Exception e) {
			log.error("Failed to delete recently viewed product: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> findRecentlyViewedByUserId(String userId) {

		try {
			List<RecentlyViewed> viewed = repository.findByUserId(userId);
			if (viewed == null || viewed.isEmpty())
				return new ResponseEntity<>(new MessageDTO("No record found."), HttpStatus.OK);

			return new ResponseEntity<>(getRecentlyViewedDTO(viewed), HttpStatus.OK);

		} catch (Exception e) {
			log.error("Failed to find recently viewed product: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	private List<RecentlyViewedDTO> getRecentlyViewedDTO(List<RecentlyViewed> vieweds) {

		Set<String> inventoryIdSet = new HashSet<String>();
		Set<String> productIdSet = new HashSet<String>();
		getRecentlyViewedMap(vieweds, inventoryIdSet, productIdSet);

		// Fetch products by product ids array.
		List<Product> products = productRepository.findAllByIds(productIdSet.toArray());

		// Fetch products by product ids array.
		List<Inventory> inventories = inventoryRepository.findAllByIds(inventoryIdSet.toArray());

		return createRecentlyViewedDTO(vieweds, products, inventories);
	}

	private void getRecentlyViewedMap(List<RecentlyViewed> vieweds, Set<String> inventoryIdSet,
			Set<String> productIdSet) {

		for (RecentlyViewed viewed : vieweds) {
			// Create Product Map
			productIdSet.add(viewed.getProductId());

			// Create Inventory Map
			inventoryIdSet.add(viewed.getInventoryId());
		}

	}

	private List<RecentlyViewedDTO> createRecentlyViewedDTO(List<RecentlyViewed> vieweds, List<Product> products,
			List<Inventory> inventories) {

		List<RecentlyViewedDTO> viewedDTOs = new ArrayList<RecentlyViewedDTO>();
		Map<String, Product> productMap = service.getProductMap(products);
		Map<String, Inventory> inventoryMap = service.getInventoryMap(inventories);

		for (RecentlyViewed viewed : vieweds) {
			RecentlyViewedDTO dto = new RecentlyViewedDTO();
			dto.setId(viewed.getDocId());
			dto.setReviewedDate(viewed.getReviewedDate());
			if (productMap.containsKey(viewed.getProductId()))
				dto.setProduct(productMap.get(viewed.getProductId()));
			if (inventoryMap.containsKey(viewed.getInventoryId()))
				dto.setInventory(inventoryMap.get(viewed.getInventoryId()));

			viewedDTOs.add(dto);
		}

		return viewedDTOs;
	}

	private boolean isDuplicate(String userId, String inventoryId) {

		List<RecentlyViewed> recentlyVieweds = repository.findByUserIdAndInventoryId(userId, inventoryId);
		if (recentlyVieweds != null && !recentlyVieweds.isEmpty() && recentlyVieweds.size() > 0)
			return true;

		return false;

	}

}
