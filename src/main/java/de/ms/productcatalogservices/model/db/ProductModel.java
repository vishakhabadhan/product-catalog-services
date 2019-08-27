package de.ms.productcatalogservices.model.db;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TBL_MS_PRODUCT_MODEL")
public class ProductModel {
	
	@Id
    @GeneratedValue(generator = "ProductModelSequence")
    @SequenceGenerator(name = "ProductModelSequence", sequenceName = "SEQ_TBL_MS_PRODUCT_MODEL", allocationSize = 1)
    @Column(name = "ID", nullable = false, updatable = false)
    private long id;
    
    @Column(name = "product_id")
    private Long productId;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "brand")
    private String brand;
    
    @Column(name = "color")
    private String color;
    
    @Column(name = "price")
    private BigDecimal price;
    
    @JsonIgnore
    @Column(name="version_counter")
    private Long versionCounter;
    
    public ProductModel() {
	}
    
	public ProductModel(final Long productId) {
    	setProductId(productId);
    	setVersionCounter(0L);
	}

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

	@Override
	public String toString() {
		return "ProductModel [id=" + id + ", productId=" + productId + ", title=" + title + ", description="
				+ description + ", brand=" + brand + ", color=" + color + ", price=" + price + ", versionCounter="
				+ versionCounter + "]";
	}	
}
