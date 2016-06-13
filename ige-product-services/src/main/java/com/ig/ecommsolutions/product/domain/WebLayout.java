package com.ig.ecommsolutions.product.domain;

import java.util.Set;

public class WebLayout {
	private Page pages[];
	private String style;

	public Page[] getPages() {
		return pages;
	}

	public void setPages(Page[] pages) {
		this.pages = pages;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

}

class Page {
	private String title;
	private int order;
	private String style;
	private Section[] sections;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public Section[] getSections() {
		return sections;
	}

	public void setSections(Section[] sections) {
		this.sections = sections;
	}

}

class Section {
	private String title;
	private int order;
	private String style;
	private Row[] rows;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public Row[] getRows() {
		return rows;
	}

	public void setRows(Row[] rows) {
		this.rows = rows;
	}

}

class Row {
	private String style;
	private int order;
	private Column[] cols;

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public Column[] getCols() {
		return cols;
	}

	public void setCols(Column[] cols) {
		this.cols = cols;
	}

}

class Column {
	private String labelRef;
	private String lableStyle;
	private String fieldType;
	private String fieldStyle;
	private Set<String> validations;

	public String getLabelRef() {
		return labelRef;
	}

	public void setLabelRef(String labelRef) {
		this.labelRef = labelRef;
	}

	public String getLableStyle() {
		return lableStyle;
	}

	public void setLableStyle(String lableStyle) {
		this.lableStyle = lableStyle;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldStyle() {
		return fieldStyle;
	}

	public void setFieldStyle(String fieldStyle) {
		this.fieldStyle = fieldStyle;
	}

	public Set<String> getValidations() {
		return validations;
	}

	public void setValidations(Set<String> validations) {
		this.validations = validations;
	}

}
