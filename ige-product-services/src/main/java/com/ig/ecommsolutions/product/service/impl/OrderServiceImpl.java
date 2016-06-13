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
import com.ig.ecommsolutions.product.domain.Order;
import com.ig.ecommsolutions.product.domain.OrderItem;
import com.ig.ecommsolutions.product.domain.Product;
import com.ig.ecommsolutions.product.dto.MessageDTO;
import com.ig.ecommsolutions.product.dto.OrderDTO;
import com.ig.ecommsolutions.product.dto.OrderItemDTO;
import com.ig.ecommsolutions.product.rest.InventoryRepository;
import com.ig.ecommsolutions.product.rest.OrderRepository;
import com.ig.ecommsolutions.product.rest.ProductRepository;
import com.ig.ecommsolutions.product.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private InventoryRepository inventoryRepository;
	@Autowired
	private CommonService service;

	@Override
	public ResponseEntity<?> addOrder(Order order) {

		try {

			if (order == null || order.getOrderItems().isEmpty() || order.getUserId() == null)
				return new ResponseEntity<>(
						new MessageDTO("Validation Error: ProductID, InventoryID, and UserId are required."),
						HttpStatus.UNPROCESSABLE_ENTITY);

			Order persistedOrder = orderRepository.save(order);
			return new ResponseEntity<>(persistedOrder, HttpStatus.CREATED);

		} catch (Exception e) {
			log.error("Failed to create order: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// @Override
	// public ResponseEntity<?> addMulipleOrder(List<Order> orders) {
	//
	// try {
	//
	// if (orders == null || orders.isEmpty())
	// return new ResponseEntity<>(new MessageDTO("Invalid Request........ Order
	// not found to add."),
	// HttpStatus.UNPROCESSABLE_ENTITY);
	//
	// List<Order> persistedOrders = orderRepository.save(orders);
	// return new ResponseEntity<>(persistedOrders, HttpStatus.CREATED);
	//
	// } catch (Exception e) {
	// log.error("Failed to create multiple order: " + e.getMessage());
	// return new ResponseEntity<>(new MessageDTO(e.getMessage()),
	// HttpStatus.INTERNAL_SERVER_ERROR);
	// }
	// }

	@Override
	public ResponseEntity<?> updateOrder(String id, Order order) {

		try {

			Order existingOrder = orderRepository.findOne(id);
			if (existingOrder == null) {
				return new ResponseEntity<>(new MessageDTO("Order not found with id: " + id), HttpStatus.NOT_FOUND);
			} else {
				order.setDocId(existingOrder.getDocId());
				order.setUserId(existingOrder.getUserId());
			}

			if (order.getOrderItems().isEmpty())
				return new ResponseEntity<>(new MessageDTO("Validation Error: ProductID and InventoryID are required."),
						HttpStatus.UNPROCESSABLE_ENTITY);

			Order persistedOrder = orderRepository.save(order);
			return new ResponseEntity<>(persistedOrder, HttpStatus.CREATED);

		} catch (Exception e) {
			log.error("Failed to update order: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> deleteOrder(String id) {

		try {

			Order existingOrder = orderRepository.findOne(id);
			if (existingOrder == null)
				return new ResponseEntity<>(new MessageDTO("Order not found with id: " + id), HttpStatus.NOT_FOUND);

			orderRepository.delete(id);
			return new ResponseEntity<>(new MessageDTO("Order has been deleted successsfully"), HttpStatus.OK);

		} catch (Exception e) {
			log.error("Failed to delete order: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> findOrderById(String id) {

		try {
			Order existingOrder = orderRepository.findOne(id);

			if (existingOrder == null)
				return new ResponseEntity<>(new MessageDTO("Order not found with id: " + id), HttpStatus.NOT_FOUND);

			OrderDTO orderDTO = new OrderDTO(existingOrder.getDocId(), existingOrder.getUserId(),
					existingOrder.getReplacementOrders(), existingOrder.getQty(), existingOrder.getPrice(),
					existingOrder.getStatus(), existingOrder.getPaymentType(), existingOrder.getLog(),
					existingOrder.getCreatedOn());

			for (OrderItem item : existingOrder.getOrderItems()) {

				Product product = productRepository.findOne(item.getProductId());
				if (product == null)
					return new ResponseEntity<>(new MessageDTO("Associated Product is not found for order id: " + id),
							HttpStatus.NOT_FOUND);

				Inventory inventory = inventoryRepository.findOne(item.getInventoryId());
				if (inventory == null)
					return new ResponseEntity<>(new MessageDTO("Inventory is not found for order id: " + id),
							HttpStatus.NOT_FOUND);

				orderDTO.getOrderItems().add(new OrderItemDTO(item.getRetailerId(), inventory, product));

			}

			return new ResponseEntity<>(orderDTO, HttpStatus.OK);

		} catch (Exception e) {
			log.error("Failed to find order by id: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> findAllOrders(String userId) {

		try {
			List<Order> orders = orderRepository.findByUserId(userId);

			if (orders == null || orders.isEmpty()) {
				return new ResponseEntity<>(new MessageDTO("No record found."), HttpStatus.OK);
			}

			return new ResponseEntity<>(getOrderDTO(orders), HttpStatus.OK);

		} catch (Exception e) {
			log.error("Failed to find orders: " + e.getMessage());
			return new ResponseEntity<>(new MessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	private List<OrderDTO> getOrderDTO(List<Order> orders) {

		Set<String> inventoryIdSet = new HashSet<String>();
		Set<String> productIdSet = new HashSet<String>();
		getOrderMap(orders, inventoryIdSet, productIdSet);

		// Fetch products by product ids array.
		List<Product> products = productRepository.findAllByIds(productIdSet.toArray());

		// Fetch products by product ids array.
		List<Inventory> inventories = inventoryRepository.findAllByIds(inventoryIdSet.toArray());

		return createOrderDTO(orders, products, inventories);

	}

	private void getOrderMap(List<Order> orders, Set<String> inventoryIdSet, Set<String> productIdSet) {

		for (Order order : orders) {
			for (OrderItem item : order.getOrderItems()) {
				// Create Product Map
				productIdSet.add(item.getProductId());
				// Create Inventory Map
				inventoryIdSet.add(item.getInventoryId());
			}
		}

	}

	private List<OrderDTO> createOrderDTO(List<Order> orders, List<Product> products, List<Inventory> inventories) {

		List<OrderDTO> orderDTOs = new ArrayList<OrderDTO>();
		Map<String, Product> productMap = service.getProductMap(products);
		Map<String, Inventory> inventoryMap = service.getInventoryMap(inventories);

		for (Order order : orders) {
			OrderDTO dto = new OrderDTO();
			dto.setId(order.getDocId());
			dto.setUserId(order.getUserId());
			dto.setReplacementOrders(order.getReplacementOrders());
			dto.setQty(order.getQty());
			dto.setPrice(order.getPrice());
			dto.setStatus(order.getStatus());
			dto.setPaymentType(order.getPaymentType());
			dto.setLog(order.getLog());
			dto.setCreatedOn(order.getCreatedOn());

			for (OrderItem item : order.getOrderItems()) {
				OrderItemDTO itemDTO = new OrderItemDTO();
				itemDTO.setRetailerId(item.getRetailerId());
				if (productMap.containsKey(item.getProductId()))
					itemDTO.setProduct(productMap.get(item.getProductId()));
				if (inventoryMap.containsKey(item.getInventoryId()))
					itemDTO.setInventory(inventoryMap.get(item.getInventoryId()));
				dto.getOrderItems().add(itemDTO);
			}

			orderDTOs.add(dto);
		}

		return orderDTOs;
	}

}
