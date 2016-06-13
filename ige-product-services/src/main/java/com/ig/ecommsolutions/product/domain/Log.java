package com.ig.ecommsolutions.product.domain;

public class Log {

	private Long byUserId;
	private Long onDate;
	private String operation;
	private String description;

	public Long getByUserId() {
		return byUserId;
	}

	public void setByUserId(Long byUserId) {
		this.byUserId = byUserId;
	}

	public Long getOnDate() {
		return onDate;
	}

	public void setOnDate(Long onDate) {
		this.onDate = onDate;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
