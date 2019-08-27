package de.ms.productcatalogservices.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import de.ms.productcatalogservices.jpa.repository.ProductCatalogRepository;
import de.ms.productcatalogservices.model.CreateProductRequestModel;
import de.ms.productcatalogservices.model.ProductCatalogSearchModel;
import de.ms.productcatalogservices.model.ProductCatalogSortModel;
import de.ms.productcatalogservices.model.db.ProductHistoryModel;
import de.ms.productcatalogservices.model.db.ProductModel;

@Service
public class ProductCatalogService {
    
    private ProductCatalogRepository productCatalogRepository;
    private ProductCatalogHistoryService productCatalogHistoryService;
    
    @Autowired
    public ProductCatalogService(ProductCatalogRepository productCatalogRepository, ProductCatalogHistoryService productCatalogHistoryService) {
        this.productCatalogRepository = productCatalogRepository;
        this.productCatalogHistoryService = productCatalogHistoryService;
    }
    
    public List<ProductModel> addProductListToProductCatalog(final List<CreateProductRequestModel> createProductRequestModelList) {
        List<ProductModel> productList = new ArrayList<>();
        createProductRequestModelList.stream().forEachOrdered(p -> productList.add(addProductToProductCatalog(p)));
        return productList;
    }
    
    public ProductModel addProductToProductCatalog(final CreateProductRequestModel createProductRequestModel) {
        ProductModel productModel = verifyProductModel(createProductRequestModel);
        productCatalogRepository.save(productModel);
        return productModel;
    }
    
    private Example<ProductModel> getProductExample(final ProductCatalogSearchModel productCatalogSearchModel) {
        ProductModel productModel = new ProductModel();
        productModel.setTitle(productCatalogSearchModel.getProductCatalogTitle());
        productModel.setDescription(productCatalogSearchModel.getProductCatalogDescription());
        List<String> ignoredList = new ArrayList<>();
        ignoredList.add("id");
        ExampleMatcher matcher = ExampleMatcher.matchingAny().withIgnoreCase().withIgnoreNullValues()
                .withIgnorePaths(ignoredList.toArray(new String[ignoredList.size()]));
        return Example.of(productModel, matcher);
    }
    
    private Sort getSortByProductCatelogSortRequestModel(final ProductCatalogSortModel productCatalogSortModel) {
        List<String> sortProperties = new ArrayList<>();
        
        productCatalogSortModel.getProductCatelogSortAttributes().stream()
        .forEachOrdered(p -> Arrays.asList(ProductCatalogSortModel.ProductCatelogSortByProperties.values()).forEach(q -> {
            if (p.equalsIgnoreCase(q.toString())) {
                sortProperties.add(q.toString());
            }
        }));
        
        String orderBy = productCatalogSortModel.getProductCatelogSortOrder();
        Sort.Direction sortDirection = orderBy != null && !orderBy.isEmpty()
                && !orderBy.toString().equalsIgnoreCase("ASC")
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        
        if(sortProperties.isEmpty()) {
            sortProperties.add("productId");
        }
        
        return new Sort(sortDirection, sortProperties);
    }
    
    private ProductModel verifyProductModel(final CreateProductRequestModel createProductRequestModel) {
        ProductModel productModel = getProductByProductId(createProductRequestModel.getProductId());
        if (productModel == null) {
            productModel = new ProductModel(createProductRequestModel.getProductId());
        }
        
        if (productModel.getVersionCounter() > 0L) {
            createProductHistory(productModel);
        }
        
        productModel.setVersionCounter(productModel.getVersionCounter() + 1L);
        productModel.setDescription(createProductRequestModel.getProductDescription());
        productModel.setColor(createProductRequestModel.getProductColor());
        productModel.setTitle(createProductRequestModel.getProductTitle());
        productModel.setPrice(createProductRequestModel.getProductPrice());
        productModel.setBrand(createProductRequestModel.getProductBrand());
        return productModel;
    }
    
    private void createProductHistory(final ProductModel productModel) {
        ProductHistoryModel version = ProductHistoryModel.forProduct(productModel);
        productCatalogHistoryService.saveProductHistory(version);
    }
    
    public ProductModel getProductByProductId(final Long productId) {
        return productCatalogRepository.findProductByProductId(productId);
    }
    
    public boolean deleteProductByProductId(final Long productId) {
        ProductModel productModel = getProductByProductId(productId);
        createProductHistory(productModel);
        productCatalogRepository.delete(productModel);
        return true;
    }
    
    public Page<ProductModel> searchProductByProductCatalogFilter(final ProductCatalogSearchModel productCatalogSearchModel,
            final ProductCatalogSortModel productCatalogSortModel) {
        Example<ProductModel> productExample = getProductExample(productCatalogSearchModel);
        Sort productCatalogSort = getSortByProductCatelogSortRequestModel(productCatalogSortModel);
        Pageable paging = PageRequest.of(productCatalogSortModel.getPageIndex(), productCatalogSortModel.getPageSize(), productCatalogSort);
        System.out.println(paging);
        return productCatalogRepository.findAll(productExample, paging);
    }
    
    public List<ProductModel> getProductByTitleAndDesription(final ProductCatalogSearchModel productCatalogSearchModel) {
        return productCatalogRepository.findProductListByTitleAndDescription(productCatalogSearchModel.getProductCatalogTitle(),
                productCatalogSearchModel.getProductCatalogDescription());
    }
}
