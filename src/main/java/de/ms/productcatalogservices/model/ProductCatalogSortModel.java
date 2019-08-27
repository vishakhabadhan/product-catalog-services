package de.ms.productcatalogservices.model;

import java.util.List;

public class ProductCatalogSortModel {
         
	private Integer pageIndex;
	private Integer pageSize;
	private String productCatelogSortOrder;
	private List<String> productCatelogSortAttributes;
	
	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getProductCatelogSortOrder() {
		return productCatelogSortOrder;
	}

	public void setProductCatelogSortOrder(String productCatelogSortOrder) {
		this.productCatelogSortOrder = productCatelogSortOrder;
	}

	public List<String> getProductCatelogSortAttributes() {
		return productCatelogSortAttributes;
	}

	public void setProductCatelogSortAttributes(List<String> productCatelogSortAttributes) {
		this.productCatelogSortAttributes = productCatelogSortAttributes;
	}

	public enum ProductCatelogSortByProperties {
		color,
		brand,
		price;   
	}
}
