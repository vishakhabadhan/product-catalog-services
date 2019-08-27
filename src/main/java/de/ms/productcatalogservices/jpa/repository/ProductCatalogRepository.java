package de.ms.productcatalogservices.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import de.ms.productcatalogservices.model.db.ProductModel;

public interface ProductCatalogRepository extends JpaRepository<ProductModel, Long> {
    
    public ProductModel findProductByProductId(final Long productId);
    
    @Query("SELECT P FROM ProductModel P WHERE P.title = ?1 AND P.description LIKE %?2%")
    public List<ProductModel> findProductListByTitleAndDescription(String productCatalogTitle, String productCatalogDescription);
}

