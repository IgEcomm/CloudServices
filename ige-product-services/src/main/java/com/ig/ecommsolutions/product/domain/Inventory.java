package com.ig.ecommsolutions.product.domain;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "inventory")
public class Inventory {

	@Id
	private String docId;
	private String productId;
	private String retailerId;
	private Long batchId = new Long(0);
	private Double totalQty = new Double(0.0);
	private Double soldQty = new Double(0.0);
	private transient Double remainingQty = new Double(0.0);
	private Double price = new Double(0.0);
	private String currency;
	private Map<String, Object> properties;
	private Map<String, Object> logs;
	private transient Double offerPrice = new Double(0.0);
	private int discountPercent = 0;
	private boolean sponsored = false;
	private WebLayout webLayout;

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getId() {
		return docId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getRetailerId() {
		return retailerId;
	}

	public void setRetailerId(String retailerId) {
		this.retailerId = retailerId;
	}

	public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	public Double getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(Double tolalQty) {
		this.totalQty = tolalQty;
	}

	public Double getSoldQty() {
		return soldQty;
	}

	public void setSoldQty(Double soldQty) {
		this.soldQty = soldQty;
	}

	public Double getRemainingQty() {
		return Double.valueOf(this.getTotalQty().doubleValue() - this.getSoldQty().doubleValue());
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	public Map<String, Object> getLogs() {
		return logs;
	}

	public void setLogs(Map<String, Object> logs) {
		this.logs = logs;
	}

	public Double getOfferPrice() {
		DecimalFormat df = new DecimalFormat("#.##");
		String offer = df.format(this.getPrice().doubleValue() - (this.getPrice().doubleValue() * this.getDiscountPercent() / 100));
		try {
			return Double.valueOf(df.parse(offer).doubleValue());
		} catch(ParseException e) {
			return Double.valueOf(this.getPrice().doubleValue() - (this.getPrice().doubleValue() * this.getDiscountPercent() / 100));
		}
		
	}

	public int getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(int discountPercent) {
		this.discountPercent = discountPercent;
	}

	public boolean isSponsored() {
		return sponsored;
	}

	public void setSponsored(boolean sponsored) {
		this.sponsored = sponsored;
	}

	public WebLayout getWebLayout() {
		return webLayout;
	}

	public void setWebLayout(WebLayout webLayout) {
		this.webLayout = webLayout;
	}

}
