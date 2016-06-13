package com.ig.ecommsolutions.product.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ig.ecommsolutions.product.domain.Log;

public class OrderDTO {

	private String id;
	private String userId;
	private List<OrderItemDTO> orderItems = new ArrayList<OrderItemDTO>();
	private String replacementOrders[];
	private Double qty;
	private Double price;
	private String status;
	private String paymentType;	
	private Log log;
	private Date createdOn;

	public OrderDTO() {

	}

	public OrderDTO(String id, String userId, String replacementOrders[], Double qty,
			Double price, String status, String paymentType, Log log, Date createdOn) {
		this.id = id;
		this.userId = userId;
		this.replacementOrders = replacementOrders;
		this.qty = qty;
		this.price = price;
		this.status = status;
		this.paymentType = paymentType;
		this.log = log;
		this.createdOn = createdOn;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String[] getReplacementOrders() {
		return replacementOrders;
	}

	public void setReplacementOrders(String[] replacementOrders) {
		this.replacementOrders = replacementOrders;
	}

	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public List<OrderItemDTO> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemDTO> orderItems) {
		this.orderItems = orderItems;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

}
