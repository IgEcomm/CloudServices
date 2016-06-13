package com.ig.ecommsolutions.product.domain;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "product")
public class Product {

	@Id
	private String docId;
	private String name;
	private String description;
	private String brand;
	private String category;
	private String subCategory;
	private String webLogoURL;
	private String mobileLogoURL;
	private Map<String, Object> properties;
	private WebLayout webLayout;
	private String[] tags;
	private String type;
	private transient int rating = 0;

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getId() {
		return docId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getWebLogoURL() {
		return webLogoURL;
	}

	public void setWebLogoURL(String webLogoURL) {
		this.webLogoURL = webLogoURL;
	}

	public String getMobileLogoURL() {
		return mobileLogoURL;
	}

	public void setMobileLogoURL(String mobileLogoURL) {
		this.mobileLogoURL = mobileLogoURL;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	public WebLayout getWebLayout() {
		return webLayout;
	}

	public void setWebLayout(WebLayout webLayout) {
		this.webLayout = webLayout;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getRating() {
		return rating;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

}
