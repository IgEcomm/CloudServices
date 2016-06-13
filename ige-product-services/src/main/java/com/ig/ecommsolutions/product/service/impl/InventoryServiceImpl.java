package com.ig.ecommsolutions.product.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.ig.ecommsolutions.product.domain.Order;
import com.ig.ecommsolutions.product.domain.Product;
import com.ig.ecommsolutions.product.domain.UserCart;
import com.ig.ecommsolutions.product.domain.WishList;
import com.ig.ecommsolutions.product.dto.InventoryDTO;
import com.ig.ecommsolutions.product.dto.MessageDTO;
import com.ig.ecommsolutions.product.dto.ProductDTO;
import com.ig.ecommsolutions.product.rest.CartRepository;
import com.ig.ecommsolutions.product.rest.InventoryRepository;
import com.ig.ecommsolutions.product.rest.OrderRepository;
import com.ig.ecommsolutions.product.rest.ProductRepository;
import com.ig.ecommsolutions.product.rest.WishListRepository;
import com.ig.ecommsolutions.product.service.InventoryService;

@Service
public class InventoryServiceImpl implements InventoryService {

	private static final Logger log = LoggerFactory.getLogger(InventoryServiceImpl.class);

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private InventoryRepository inventoryRepository;
	@Autowired
	private WishListRepository wishListRepository;
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private CommonService service;

	@Override
	public ResponseEntity<?> addInventory(Inventory inventory) {

		try {
			// Get product by id.
			if (inventory.getProductId() == null)
				return new ResponseEntity<>(new MessageDTO("Invalid request. ProductID is required."),
						HttpStatus.BAD_REQUEST);
			Product product = productRepository.findOne(inventory.getProductId());
			if (product == null)
				return new ResponseEntity<>(
						new MessageDTO("Invalid productId. Product not found with id: " + inventory.getProductId()),
						HttpStatus.NOT_FOUND);

			// Save inventory
			Inventory persistedInventory = inventoryRepository.save(inventory);
			return new ResponseEntity<>(persistedInventory, HttpStatus.CREATED);

		} catch (Exception e) {
			log.error("Failed to create inventory-product: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> updateInventory(String id, Inventory inventory) {

		try {

			Inventory existingInventory = inventoryRepository.findOne(id);
			if (existingInventory == null) {
				return new ResponseEntity<>(new MessageDTO("Inventory not found with id: " + id), HttpStatus.NOT_FOUND);
			} else {
				inventory.setDocId(existingInventory.getDocId());
				inventory.setRetailerId(existingInventory.getRetailerId());
			}

			// Get product by id.
			if (inventory.getProductId() == null) {
				inventory.setProductId(existingInventory.getProductId());
			} else {
				Product product = productRepository.findOne(inventory.getProductId());
				if (product == null)
					return new ResponseEntity<>(
							new MessageDTO("Invalid productId. Product not found with id: " + inventory.getProductId()),
							HttpStatus.NOT_FOUND);
			}

			// Update inventory
			Inventory persistedInventory = inventoryRepository.save(inventory);
			return new ResponseEntity<>(persistedInventory, HttpStatus.OK);

		} catch (Exception e) {
			log.error("Failed to update inventory-product: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> deleteInventory(String id) {

		try {
			Inventory existingInventory = inventoryRepository.findOne(id);
			if (existingInventory == null) {
				return new ResponseEntity<>(new MessageDTO("Inventory not found with id: " + id), HttpStatus.NOT_FOUND);
			}

			inventoryRepository.delete(id);
			return new ResponseEntity<>(new MessageDTO("Inventory has been deleted successsfully"), HttpStatus.OK);

		} catch (Exception e) {
			log.error("Failed to delete Inventory: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> findInventoryById(String id) {

		try {
			Inventory existingInventory = inventoryRepository.findOne(id);

			if (existingInventory == null) {
				return new ResponseEntity<>(new MessageDTO("Inventory not found with id: " + id), HttpStatus.NOT_FOUND);
			}

			Product product = productRepository.findOne(existingInventory.getProductId());
			if (product == null) {
				return new ResponseEntity<>(new MessageDTO("Inventory-product not found for inventory-id: " + id),
						HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<>(new ProductDTO(existingInventory, product), HttpStatus.OK);

		} catch (Exception e) {
			log.error("Failed to find inventory by id: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> findAllInventories(String retailerId) {

		try {
			List<Inventory> inventories = inventoryRepository.findByRetailerId(retailerId);

			if (inventories == null || inventories.isEmpty()) {
				return new ResponseEntity<>(new MessageDTO("No record found."), HttpStatus.OK);
			}

			// Get a Map which contains product id as key and list of
			// inventories as value.
			Map<String, List<Inventory>> inventoryMap = getInventoryMap(inventories);
			Set<String> keys = inventoryMap.keySet();

			// Fetch products by product ids array.
			List<Product> products = productRepository.findAllByIds(keys.toArray());
			if (products == null || products.isEmpty()) {
				return new ResponseEntity<>(new MessageDTO("No inventory-product found."), HttpStatus.OK);
			}

			return new ResponseEntity<>(createProductDTO(inventoryMap, products), HttpStatus.OK);

		} catch (Exception e) {
			log.error("Failed to find inventories: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> findAllSponsoredInventories(String retailerId) {

		try {
			List<Inventory> inventories = inventoryRepository.findByRetailerAndSponsored(retailerId);

			if (inventories == null || inventories.isEmpty()) {
				return new ResponseEntity<>(new MessageDTO("No record found."), HttpStatus.OK);
			}

			// Get a Map which contains product id as key and list of
			// inventories as value.
			Map<String, List<Inventory>> inventoryMap = getInventoryMap(inventories);
			Set<String> keys = inventoryMap.keySet();

			// Fetch products by product ids array.
			List<Product> products = productRepository.findAllByIds(keys.toArray());
			if (products == null || products.isEmpty()) {
				return new ResponseEntity<>(new MessageDTO("No inventory-product found."), HttpStatus.OK);
			}

			return new ResponseEntity<>(createProductDTO(inventoryMap, products), HttpStatus.OK);

		} catch (Exception e) {
			log.error("Failed to find inventories: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> findInventoryProductById(String id, String userId) {

		try {
			Inventory existingInventory = inventoryRepository.findOne(id);
			if (existingInventory == null) {
				return new ResponseEntity<>(new MessageDTO("Inventory not found with id: " + id), HttpStatus.NOT_FOUND);
			}

			Product product = productRepository.findOne(existingInventory.getProductId());
			if (product == null) {
				return new ResponseEntity<>(new MessageDTO("Inventory-product not found for inventory-id: " + id),
						HttpStatus.NOT_FOUND);
			}

			if(userId != null) {
				// Is in wish list
				List<WishList> wishList = wishListRepository.findByUserIdAndInventoryId(userId,
						existingInventory.getDocId());
				boolean isInWishList = false;
				String wishListId = null;
				if (wishList != null && !wishList.isEmpty() && wishList.size() >= 1) {
					isInWishList = true;
					wishListId = wishList.get(0).getDocId();
				}

				// Is in cart list
				List<UserCart> userCart = cartRepository.findByUserIdAndInventoryId(userId, existingInventory.getDocId());
				boolean isInCartList = false;
				String userCartId = null;
				if (userCart != null && !userCart.isEmpty() && userCart.size() >= 1) {
					isInCartList = true;
					userCartId = userCart.get(0).getDocId();
				}

				// Is ordered product
				List<Order> orders = orderRepository.findByUserId(userId);
				Map<String, String> orderMap = service.isOrderedMap(orders);
				boolean isOrdered = false;
				if (orderMap.containsKey(existingInventory.getDocId()))
					isOrdered = true;

				return new ResponseEntity<>(new InventoryDTO(existingInventory, product, isInWishList, isInCartList,
						isOrdered, wishListId, userCartId), HttpStatus.OK);								
			} else {
				return new ResponseEntity<>(new InventoryDTO(existingInventory, product), HttpStatus.OK);
			}
			

		} catch (Exception e) {
			log.error("Failed to find inventory by id: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> findAllInventoryProducts(String userId, String category, String subCategory,
			String[] brand) {

		try {
			List<Product> products = null;
			List<Inventory> inventories = null;
			Map<String, List<Inventory>> inventoryMap = new HashMap<String, List<Inventory>>();

			if (category != null || subCategory != null || brand != null) {

				if (category != null && subCategory != null && brand != null) {
					products = productRepository.findByCategoryAndSubCategoryAndBrand(category, subCategory, brand);
				} else if (category != null && subCategory != null) {
					products = productRepository.findByCategoryAndSubCategory(category, subCategory);
				} else if (category != null && brand != null) {
					products = productRepository.findByCategoryAndBrand(category, brand);
				} else if (subCategory != null && brand != null) {
					products = productRepository.findBySubCategoryAndBrand(subCategory, brand);
				} else if (category != null) {
					products = productRepository.findByCategory(category);
				} else if (subCategory != null) {
					products = productRepository.findBySubCategory(subCategory);
				} else {
					products = productRepository.findByBrand(brand);
				}

				if (products == null || products.isEmpty()) {
					return new ResponseEntity<>(new MessageDTO("No record found."), HttpStatus.OK);
				}
				List<String> ids = getProductIds(products);
				inventories = inventoryRepository.findAllByProductId(ids.toArray());

				if (inventories == null || inventories.isEmpty()) {
					return new ResponseEntity<>(new MessageDTO("No inventory-product found."), HttpStatus.OK);
				}

				// Get a Map which contains product id as key and list of
				// inventories as value.
				inventoryMap = getInventoryMap(inventories);

			} else {

				inventories = inventoryRepository.findAll();
				if (inventories == null || inventories.isEmpty()) {
					return new ResponseEntity<>(new MessageDTO("No record found."), HttpStatus.OK);
				}

				// Get a Map which contains product id as key and list of
				// inventories as value.
				inventoryMap = getInventoryMap(inventories);

				// Fetch products by product ids array.
				products = productRepository.findAllByIds(inventoryMap.keySet().toArray());
				if (products == null || products.isEmpty()) {
					return new ResponseEntity<>(new MessageDTO("No inventory-product found."), HttpStatus.OK);
				}
			}

			Object[] proIds = inventoryMap.keySet().toArray();

			List<InventoryDTO> inventoryDTOs = null;
			if (userId != null) {
				/*
				 * Get user WishList and create a map with inventory id as key
				 * and wishlist id as value.
				 */
				List<WishList> wishList = wishListRepository.findAllByUIdAndProIdIn(userId, proIds);
				Map<String, String> wishListMap = service.isInWishListMap(wishList);

				/*
				 * Get user carts and create a map with inventory id as key and
				 * usercart id as value.
				 */
				List<UserCart> userCart = cartRepository.findAllByUIdAndProIdIn(userId, proIds);
				Map<String, String> userCartMap = service.isInUserCartMap(userCart);

				/*
				 * Get user orders and create a map with inventory id as key and
				 * order id as value.
				 */
				List<Order> orders = orderRepository.findByUserId(userId);
				Map<String, String> orderMap = service.isOrderedMap(orders);

				inventoryDTOs = createInventoryDTO(inventoryMap, products, wishListMap, userCartMap, orderMap);
			} else {
				inventoryDTOs = createInventoryDTO(inventoryMap, products);
			}

			return new ResponseEntity<>(inventoryDTOs, HttpStatus.OK);

		} catch (Exception e) {
			log.error("Failed to find inventory-products: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> findAllSponsorInventoryProducts() {

		try {
			List<Inventory> inventories = inventoryRepository.findBySponsored();

			if (inventories == null || inventories.isEmpty()) {
				return new ResponseEntity<>(new MessageDTO("No record found."), HttpStatus.OK);
			}

			// Get a Map which contains product id as key and list of
			// inventories as value.
			Map<String, List<Inventory>> inventoryMap = getInventoryMap(inventories);
			Set<String> keys = inventoryMap.keySet();

			// Fetch products by product ids array.
			List<Product> products = productRepository.findAllByIds(keys.toArray());
			if (products == null || products.isEmpty()) {
				return new ResponseEntity<>(new MessageDTO("No inventory-product found."), HttpStatus.OK);
			}

			return new ResponseEntity<>(createProductDTO(inventoryMap, products), HttpStatus.OK);

		} catch (Exception e) {
			log.error("Failed to find sponsored inventory products: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	private List<String> getProductIds(List<Product> products) {
		List<String> pids = new ArrayList<String>();
		for (Product product : products)
			pids.add(product.getDocId());
		return pids;
	}

	private Map<String, List<Inventory>> getInventoryMap(List<Inventory> inventories) {

		Map<String, List<Inventory>> inventoryMap = new HashMap<String, List<Inventory>>();

		for (Inventory inventory : inventories) {
			if (inventoryMap.containsKey(inventory.getProductId())) {
				inventoryMap.get(inventory.getProductId()).add(inventory);
			} else {
				List<Inventory> list = new ArrayList<Inventory>();
				list.add(inventory);
				inventoryMap.put(inventory.getProductId(), list);
			}
		}
		return inventoryMap;

	}

	private List<ProductDTO> createProductDTO(Map<String, List<Inventory>> inventoryMap, List<Product> products) {

		List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
		for (Product product : products) {
			if (inventoryMap.containsKey(product.getDocId())) {
				for (Inventory inventory : inventoryMap.get(product.getDocId())) {
					ProductDTO dto = new ProductDTO(inventory, product);
					productDTOs.add(dto);
				}
			}
		}
		return productDTOs;
	}

	private List<InventoryDTO> createInventoryDTO(Map<String, List<Inventory>> inventoryMap, List<Product> products,
			Map<String, String> wishListMap, Map<String, String> userCartMap, Map<String, String> orderMap) {

		List<InventoryDTO> inventoryDTOs = new ArrayList<InventoryDTO>();
		for (Product product : products) {
			if (inventoryMap.containsKey(product.getDocId())) {
				for (Inventory inventory : inventoryMap.get(product.getDocId())) {
					InventoryDTO dto = new InventoryDTO(inventory, product,
							wishListMap.containsKey(inventory.getDocId()),
							userCartMap.containsKey(inventory.getDocId()), orderMap.containsKey(inventory.getDocId()));
					if (wishListMap.containsKey(inventory.getDocId()))
						dto.setWishListId(wishListMap.get(inventory.getDocId()));
					if (userCartMap.containsKey(inventory.getDocId()))
						dto.setUserCartId(userCartMap.get(inventory.getDocId()));
					inventoryDTOs.add(dto);
				}
			}
		}
		return inventoryDTOs;
	}
	
	private List<InventoryDTO> createInventoryDTO(Map<String, List<Inventory>> inventoryMap, List<Product> products) {

		List<InventoryDTO> inventoryDTOs = new ArrayList<InventoryDTO>();
		for (Product product : products) {
			if (inventoryMap.containsKey(product.getDocId())) {
				for (Inventory inventory : inventoryMap.get(product.getDocId())) {
					InventoryDTO dto = new InventoryDTO(inventory, product);					
					inventoryDTOs.add(dto);
				}
			}
		}
		return inventoryDTOs;
	}

}
