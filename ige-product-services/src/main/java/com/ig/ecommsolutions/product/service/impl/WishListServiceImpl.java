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
import com.ig.ecommsolutions.product.domain.UserCart;
import com.ig.ecommsolutions.product.domain.WishList;
import com.ig.ecommsolutions.product.dto.MessageDTO;
import com.ig.ecommsolutions.product.dto.WishListDTO;
import com.ig.ecommsolutions.product.rest.CartRepository;
import com.ig.ecommsolutions.product.rest.InventoryRepository;
import com.ig.ecommsolutions.product.rest.ProductRepository;
import com.ig.ecommsolutions.product.rest.WishListRepository;
import com.ig.ecommsolutions.product.service.WishListService;

@Service
public class WishListServiceImpl implements WishListService {

	private static final Logger log = LoggerFactory.getLogger(WishListServiceImpl.class);

	@Autowired
	private WishListRepository wishListRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private InventoryRepository inventoryRepository;
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private CommonService service;

	@Override
	public ResponseEntity<?> addWishList(WishList wishList) {

		try {

			if (wishList == null || wishList.getProductId() == null || wishList.getInventoryId() == null
					|| wishList.getUserId() == null)
				return new ResponseEntity<>(
						new MessageDTO("Validation Error: ProductID, InventoryID, and UserID are required."),
						HttpStatus.UNPROCESSABLE_ENTITY);

			Product product = productRepository.findOne(wishList.getProductId());
			if (product == null)
				return new ResponseEntity<>(
						new MessageDTO("Invalid productId. Product not found with id: " + wishList.getProductId()),
						HttpStatus.NOT_FOUND);

			Inventory inventory = inventoryRepository.findOne(wishList.getInventoryId());
			if (inventory == null)
				return new ResponseEntity<>(
						new MessageDTO(
								"Invalid inventoryId. Inventory not found with id: " + wishList.getInventoryId()),
						HttpStatus.NOT_FOUND);

			if (!product.getDocId().equalsIgnoreCase(inventory.getProductId()))
				return new ResponseEntity<>(
						new MessageDTO("Validation Error: ProductID and InventoryID are not compatible."),
						HttpStatus.UNPROCESSABLE_ENTITY);

			// Validate duplicate wishlist
			boolean isDuplicate = isDuplicate(wishList.getUserId(), wishList.getInventoryId());
			if (isDuplicate)
				return new ResponseEntity<>(
						new MessageDTO("Validation Error: Product is already available in WishList."),
						HttpStatus.UNPROCESSABLE_ENTITY);

			WishList persistedWishList = wishListRepository.save(wishList);
			return new ResponseEntity<>(persistedWishList, HttpStatus.CREATED);

		} catch (Exception e) {
			log.error("Failed to create wishlist: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> updateWishList(String id, WishList wishList) {

		try {

			WishList existingWishList = wishListRepository.findOne(id);
			if (existingWishList == null) {
				return new ResponseEntity<>(new MessageDTO("WishList not found with id: " + id), HttpStatus.NOT_FOUND);
			} else {
				wishList.setDocId(existingWishList.getDocId());
				wishList.setUserId(existingWishList.getUserId());
			}

			if (wishList.getProductId() == null && wishList.getInventoryId() == null) {
				wishList.setProductId(existingWishList.getProductId());
				wishList.setInventoryId(existingWishList.getInventoryId());
			} else {

				if (wishList.getProductId() == null) {
					wishList.setProductId(existingWishList.getProductId());
				} else {
					Product product = productRepository.findOne(wishList.getProductId());
					if (product == null)
						return new ResponseEntity<>(
								new MessageDTO(
										"Invalid productId. Product not found with id: " + wishList.getProductId()),
								HttpStatus.NOT_FOUND);
				}

				if (wishList.getInventoryId() == null)
					wishList.setInventoryId(existingWishList.getInventoryId());
				Inventory inventory = inventoryRepository.findOne(wishList.getInventoryId());
				if (inventory == null)
					return new ResponseEntity<>(
							new MessageDTO(
									"Invalid inventoryId. Inventory not found with id: " + wishList.getInventoryId()),
							HttpStatus.NOT_FOUND);

				if (!wishList.getProductId().equalsIgnoreCase(inventory.getProductId()))
					return new ResponseEntity<>(
							new MessageDTO("Validation Error: ProductID and InventoryID are not compatible."),
							HttpStatus.UNPROCESSABLE_ENTITY);
			}
			
			// Validate if duplicate entry
			List<WishList> wishLists = wishListRepository.findByUserIdAndInventoryId(wishList.getUserId(), wishList.getInventoryId());
			for(WishList wish : wishLists) {
				if(wish.getDocId() != wishList.getDocId())
					return new ResponseEntity<>(
							new MessageDTO("Validation Error: Product is already available in WishList."),
							HttpStatus.UNPROCESSABLE_ENTITY);					
			}
			
			WishList persistedWishList = wishListRepository.save(wishList);
			return new ResponseEntity<>(persistedWishList, HttpStatus.CREATED);

		} catch (Exception e) {
			log.error("Failed to update wishlist: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> deleteWishList(String id) {

		try {

			WishList existingWishList = wishListRepository.findOne(id);
			if (existingWishList == null)
				return new ResponseEntity<>(new MessageDTO("WishList not found with id: " + id), HttpStatus.NOT_FOUND);

			wishListRepository.delete(id);
			return new ResponseEntity<>(new MessageDTO("WishList has been deleted successsfully"), HttpStatus.OK);

		} catch (Exception e) {
			log.error("Failed to delete wishlist: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> findWishListByUserId(String userId) {

		try {
			List<WishList> wishLists = wishListRepository.findByUserId(userId);

			if (wishLists == null || wishLists.isEmpty()) {
				return new ResponseEntity<>(new MessageDTO("No record found."), HttpStatus.OK);
			}
			return new ResponseEntity<>(getWishListDTO(wishLists, userId), HttpStatus.OK);

		} catch (Exception e) {
			log.error("Failed to find wishlists: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	private List<WishListDTO> getWishListDTO(List<WishList> wishLists, String userId) {

		Set<String> inventoryIdSet = new HashSet<String>();
		Set<String> productIdSet = new HashSet<String>();
		getWishListMap(wishLists, inventoryIdSet, productIdSet);

		// Fetch products by product ids array.
		List<Product> products = productRepository.findAllByIds(productIdSet.toArray());

		// Fetch products by product ids array.
		List<Inventory> inventories = inventoryRepository.findAllByIds(inventoryIdSet.toArray());

		/*
		 * Get user carts and create a map with inventory id as key and
		 * isInUserCart property as value.
		 */
		List<UserCart> userCart = cartRepository.findByUserId(userId);
		Map<String, String> userCartMap = service.isInUserCartMap(userCart);

		return createWishListDTO(wishLists, products, inventories, userCartMap);
	}

	private void getWishListMap(List<WishList> wishLists, Set<String> inventoryIdSet, Set<String> productIdSet) {

		for (WishList wishList : wishLists) {
			// Create Product Map
			productIdSet.add(wishList.getProductId());

			// Create Inventory Map
			inventoryIdSet.add(wishList.getInventoryId());
		}

	}

	private List<WishListDTO> createWishListDTO(List<WishList> wishLists, List<Product> products,
			List<Inventory> inventories, Map<String, String> userCartMap) {

		List<WishListDTO> wishListDTOs = new ArrayList<WishListDTO>();
		Map<String, Product> productMap = service.getProductMap(products);
		Map<String, Inventory> inventoryMap = service.getInventoryMap(inventories);

		for (WishList wishList : wishLists) {
			WishListDTO dto = new WishListDTO();
			dto.setId(wishList.getDocId());
			dto.setQty(wishList.getQty());
			
			if (productMap.containsKey(wishList.getProductId()))
				dto.setProduct(productMap.get(wishList.getProductId()));
			
			if (inventoryMap.containsKey(wishList.getInventoryId()))
				dto.setInventory(inventoryMap.get(wishList.getInventoryId()));
			
			dto.setInCartList(userCartMap.containsKey(wishList.getInventoryId()));
			
			if(userCartMap.containsKey(wishList.getInventoryId()))
				dto.setUserCartId(userCartMap.get(wishList.getInventoryId()));

			wishListDTOs.add(dto);
		}

		return wishListDTOs;
	}

	private boolean isDuplicate(String userId, String inventoryId) {

		List<WishList> wishLists = wishListRepository.findByUserIdAndInventoryId(userId, inventoryId);
		if (wishLists != null && !wishLists.isEmpty() && wishLists.size() > 0)
			return true;

		return false;

	}

}
