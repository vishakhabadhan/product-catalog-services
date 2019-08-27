package de.ms.productcatalogservices.model;

public class ProductCatalogFilterModel {
	
	private ProductCatalogSearchModel ProductCatalogSearchModel;
	private ProductCatalogSortModel ProductCatalogSortModel;
	
	public ProductCatalogSearchModel getProductCatalogSearchModel() {
		return ProductCatalogSearchModel;
	}
	public void setProductCatalogSearchModel(ProductCatalogSearchModel productCatalogSearchModel) {
		ProductCatalogSearchModel = productCatalogSearchModel;
	}
	public ProductCatalogSortModel getProductCatalogSortModel() {
		return ProductCatalogSortModel;
	}
	public void setProductCatalogSortModel(ProductCatalogSortModel productCatalogSortModel) {
		ProductCatalogSortModel = productCatalogSortModel;
	}	
}
