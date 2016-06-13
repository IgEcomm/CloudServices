package com.ig.ecommsolutions.product.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ig.ecommsolutions.product.domain.Inventory;
import com.ig.ecommsolutions.product.domain.Order;
import com.ig.ecommsolutions.product.domain.OrderItem;
import com.ig.ecommsolutions.product.domain.Product;
import com.ig.ecommsolutions.product.domain.UserCart;
import com.ig.ecommsolutions.product.domain.WishList;

@Service
public class CommonService {

	public Map<String, Product> getProductMap(List<Product> products) {

		Map<String, Product> productMap = new HashMap<String, Product>();
		for (Product product : products) {
			if (!productMap.containsKey(product.getDocId()))
				productMap.put(product.getDocId(), product);
		}
		return productMap;
	}

	public Map<String, Inventory> getInventoryMap(List<Inventory> inventories) {

		Map<String, Inventory> inventoryMap = new HashMap<String, Inventory>();
		for (Inventory inventory : inventories) {
			if (!inventoryMap.containsKey(inventory.getDocId()))
				inventoryMap.put(inventory.getDocId(), inventory);
		}
		return inventoryMap;
	}
	
	public Map<String, String> isInWishListMap(List<WishList> wishLists) {

		Map<String, String> wishListMap = new HashMap<String, String>();
		for (WishList wish : wishLists)
			wishListMap.put(wish.getInventoryId(), wish.getDocId());

		return wishListMap;
	}

	public Map<String, String> isInUserCartMap(List<UserCart> userCarts) {

		Map<String, String> userCartMap = new HashMap<String, String>();
		for (UserCart cart : userCarts)
			userCartMap.put(cart.getInventoryId(), cart.getDocId());

		return userCartMap;
	}

	public Map<String, String> isOrderedMap(List<Order> orders) {

		Map<String, String> orderedMap = new HashMap<String, String>();
		for (Order order : orders)
			for(OrderItem item : order.getOrderItems()) {
				orderedMap.put(item.getInventoryId(), order.getDocId());
			}
		return orderedMap;
	}

}
