package de.ms.productcatalogservices.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.ms.productcatalogservices.model.db.ProductHistoryModel;

public interface ProductCatalogHistoryRepository extends JpaRepository<ProductHistoryModel, Long> {
    
    public List<ProductHistoryModel> findProductVersionListByProductId(final Long productId);
    
    public ProductHistoryModel findProductVersionByProductIdAndVersionCounter(final Long productId, final Long versionCounter);
    
}