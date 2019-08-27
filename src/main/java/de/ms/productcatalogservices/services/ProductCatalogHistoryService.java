package de.ms.productcatalogservices.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import de.ms.productcatalogservices.jpa.repository.ProductCatalogHistoryRepository;
import de.ms.productcatalogservices.model.db.ProductHistoryModel;


@Service
public class ProductCatalogHistoryService {
    ProductCatalogHistoryRepository productCatalogHistoryRepository;
    
    @Autowired
    public ProductCatalogHistoryService(ProductCatalogHistoryRepository productCatalogHistoryRepository) {
        this.productCatalogHistoryRepository = productCatalogHistoryRepository;
    }
    
    public ProductHistoryModel saveProductHistory(final ProductHistoryModel productHistoryModel) {
        return productCatalogHistoryRepository.save(productHistoryModel);
    }
}