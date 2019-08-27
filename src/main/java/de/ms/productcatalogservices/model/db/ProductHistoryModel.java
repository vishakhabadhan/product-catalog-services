package de.ms.productcatalogservices.model.db;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_MS_PRODUCT_HISTORY_MODEL")
public class ProductHistoryModel {
	
	public ProductHistoryModel() {       
    }
	
	public ProductHistoryModel(final Long productId) {
        setProductId(productId);
	}
    
    @Id
    @GeneratedValue(generator = "ProductHistoryModelSequence")
    @SequenceGenerator(name = "ProductHistoryModelSequence", sequenceName = "SEQ_TBL_MS_PRODUCT_HISTORY_MODEL", allocationSize = 1)
    @Column(name = "ID", nullable = false, updatable = false)
    private long id;
    
    @Column(name = "product_id")
    private Long productId;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "brand")
    private String brand;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "color")
    private String color;
    
    @Column(name = "price")
    private BigDecimal price;
    
    @Column(name = "version_counter")
    private Long versionCounter;
    
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Long getVersionCounter() {
		return versionCounter;
	}

	public void setVersionCounter(Long versionCounter) {
		this.versionCounter = versionCounter;
	}

	public static ProductHistoryModel forProduct(final ProductModel productModel) {
		ProductHistoryModel version = new ProductHistoryModel(productModel.getProductId());

        version.setColor(productModel.getColor());
        version.setBrand(productModel.getBrand());
        version.setDescription(productModel.getDescription());
        version.setPrice(productModel.getPrice());
        version.setTitle(productModel.getTitle());
        version.setVersionCounter(productModel.getVersionCounter());
        return version;
    }
    
	public static List<ProductHistoryModel> forProductList(final List<ProductModel> productList) {
		List<ProductHistoryModel> versionList = new ArrayList<>();
		productList.stream().forEachOrdered(q -> {
			ProductHistoryModel version = new ProductHistoryModel(q.getProductId());
	        
			version.setBrand(q.getBrand());
	        version.setColor(q.getColor());
	        version.setDescription(q.getDescription());
	        version.setPrice(q.getPrice());
	        version.setTitle(q.getTitle());
	        version.setVersionCounter(q.getVersionCounter());
	        versionList.add(version);
		});
		
        return versionList;
    }

	@Override
	public String toString() {
		return "ProductHistoryModel [id=" + id + ", productId=" + productId + ", title=" + title + ", brand="
				+ brand + ", description=" + description + ", color=" + color + ", price=" + price + ", versionCounter="
				+ versionCounter + "]";
	} 
}
