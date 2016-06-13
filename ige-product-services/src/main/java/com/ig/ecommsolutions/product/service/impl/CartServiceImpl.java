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
import com.ig.ecommsolutions.product.dto.CartDTO;
import com.ig.ecommsolutions.product.dto.MessageDTO;
import com.ig.ecommsolutions.product.rest.CartRepository;
import com.ig.ecommsolutions.product.rest.InventoryRepository;
import com.ig.ecommsolutions.product.rest.ProductRepository;
import com.ig.ecommsolutions.product.service.CartService;

@Service
public class CartServiceImpl implements CartService {

	private static final Logger log = LoggerFactory.getLogger(CartServiceImpl.class);

	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private InventoryRepository inventoryRepository;
	@Autowired
	private CommonService service;

	@Override
	public ResponseEntity<?> addCart(UserCart cart) {

		try {

			if (cart == null || cart.getProductId() == null || cart.getInventoryId() == null
					|| cart.getUserId() == null)
				return new ResponseEntity<>(
						new MessageDTO("Validation Error: ProductID, InventoryID, and UserID are required."),
						HttpStatus.UNPROCESSABLE_ENTITY);

			Product product = productRepository.findOne(cart.getProductId());
			if (product == null)
				return new ResponseEntity<>(
						new MessageDTO("Invalid productId. Product not found with id: " + cart.getProductId()),
						HttpStatus.NOT_FOUND);

			Inventory inventory = inventoryRepository.findOne(cart.getInventoryId());
			if (inventory == null)
				return new ResponseEntity<>(
						new MessageDTO(
								"Invalid inventoryId. Inventory not found with id: " + cart.getInventoryId()),
						HttpStatus.NOT_FOUND);

			if (!product.getDocId().equalsIgnoreCase(inventory.getProductId()))
				return new ResponseEntity<>(
						new MessageDTO("Validation Error: ProductID and InventoryID are not compatible."),
						HttpStatus.UNPROCESSABLE_ENTITY);
			
			// Duplicate inventory in cart
			List<UserCart> existingCarts = cartRepository.findByUserIdAndInventoryId(cart.getUserId(), cart.getInventoryId());
			if(existingCarts != null && !existingCarts.isEmpty()) {
				UserCart existingCart = existingCarts.get(0);
				cart.setDocId(existingCart.getDocId());
				cart.setQty(Double.sum(cart.getQty().doubleValue(), existingCart.getQty().doubleValue()));
			}

			UserCart persistedUserCart = cartRepository.save(cart);
			return new ResponseEntity<>(persistedUserCart, HttpStatus.CREATED);

		} catch (Exception e) {
			log.error("Failed to create usercart: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> updateCart(String id, UserCart cart) {

		try {

			UserCart existingCart = cartRepository.findOne(id);
			if (existingCart == null) {
				return new ResponseEntity<>(new MessageDTO("UserCart not found with id: " + id),
						HttpStatus.NOT_FOUND);
			} else {
				cart.setDocId(existingCart.getDocId());
				cart.setUserId(existingCart.getUserId());
			}

			if (cart.getProductId() == null && cart.getInventoryId() == null) {
				cart.setProductId(existingCart.getProductId());
				cart.setInventoryId(existingCart.getInventoryId());
			} else {

				if (cart.getProductId() == null) {
					cart.setProductId(existingCart.getProductId());
				} else {
					Product product = productRepository.findOne(cart.getProductId());
					if (product == null)
						return new ResponseEntity<>(
								new MessageDTO(
										"Invalid productId. Product not found with id: " + cart.getProductId()),
								HttpStatus.NOT_FOUND);
				}

				if (cart.getInventoryId() == null)
					cart.setInventoryId(existingCart.getInventoryId());
				Inventory inventory = inventoryRepository.findOne(cart.getInventoryId());
				if (inventory == null)
					return new ResponseEntity<>(
							new MessageDTO(
									"Invalid inventoryId. Inventory not found with id: " + cart.getInventoryId()),
							HttpStatus.NOT_FOUND);

				if (!cart.getProductId().equalsIgnoreCase(inventory.getProductId()))
					return new ResponseEntity<>(
							new MessageDTO("Validation Error: ProductID and InventoryID are not compatible."),
							HttpStatus.UNPROCESSABLE_ENTITY);
			}
			UserCart persistedUserCart = cartRepository.save(cart);
			return new ResponseEntity<>(persistedUserCart, HttpStatus.CREATED);

		} catch (Exception e) {
			log.error("Failed to update usercart: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> deleteCart(String id) {

		try {

			UserCart existingCart = cartRepository.findOne(id);
			if (existingCart == null)
				return new ResponseEntity<>(new MessageDTO("UserCart not found with id: " + id),
						HttpStatus.NOT_FOUND);

			cartRepository.delete(id);
			return new ResponseEntity<>(new MessageDTO("UserCart has been deleted successsfully"), HttpStatus.OK);

		} catch (Exception e) {
			log.error("Failed to delete usercart: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> findCartByUserId(String userId) {

		try {
			List<UserCart> carts = cartRepository.findByUserId(userId);
			if (carts == null || carts.isEmpty())
				return new ResponseEntity<>(new MessageDTO("No record found."), HttpStatus.OK);

			return new ResponseEntity<>(getCartDTO(carts), HttpStatus.OK);

		} catch (Exception e) {
			log.error("Failed to find usercarts: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	private List<CartDTO> getCartDTO(List<UserCart> carts) {

		Set<String> inventoryIdSet = new HashSet<String>();
		Set<String> productIdSet = new HashSet<String>();
		getCartMap(carts, inventoryIdSet, productIdSet);

		// Fetch products by product ids array.
		List<Product> products = productRepository.findAllByIds(productIdSet.toArray());

		// Fetch products by product ids array.
		List<Inventory> inventories = inventoryRepository.findAllByIds(inventoryIdSet.toArray());

		return createCartDTO(carts, products, inventories);
	}

	private void getCartMap(List<UserCart> carts, Set<String> inventoryIdSet, Set<String> productIdSet) {

		for (UserCart cart : carts) {
			// Create Product Map
			productIdSet.add(cart.getProductId());

			// Create Inventory Map
			inventoryIdSet.add(cart.getInventoryId());
		}

	}

	private List<CartDTO> createCartDTO(List<UserCart> carts, List<Product> products, List<Inventory> inventories) {

		List<CartDTO> cartDTOs = new ArrayList<CartDTO>();
		Map<String, Product> productMap = service.getProductMap(products);
		Map<String, Inventory> inventoryMap = service.getInventoryMap(inventories);

		for (UserCart cart : carts) {
			CartDTO dto = new CartDTO();
			dto.setId(cart.getDocId());
			dto.setQty(cart.getQty());
			if (productMap.containsKey(cart.getProductId()))
				dto.setProduct(productMap.get(cart.getProductId()));
			if (inventoryMap.containsKey(cart.getInventoryId()))
				dto.setInventory(inventoryMap.get(cart.getInventoryId()));

			cartDTOs.add(dto);
		}

		return cartDTOs;
	}

}
