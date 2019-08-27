package de.ms.productcatalogservices.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.ms.productcatalogservices.model.CreateProductRequestModel;
import de.ms.productcatalogservices.model.ProductCatalogFilterModel;
import de.ms.productcatalogservices.model.ProductCatalogSearchModel;
import de.ms.productcatalogservices.model.db.ProductModel;
import de.ms.productcatalogservices.services.ProductCatalogService;

@RestController
@RequestMapping("/productCatalog")
public class ProductCatalogController {
    
    private ProductCatalogService service;
    
    private static final Logger log = LoggerFactory.getLogger(ProductCatalogController.class);
    
    @Autowired
    public ProductCatalogController(ProductCatalogService service) {
        this.service = service;
    }
    
    @RequestMapping(path = "createProduct", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> createProduct(@RequestBody CreateProductRequestModel requestModel) {
        return ResponseEntity.ok().body(service.addProductToProductCatalog(requestModel).getId());
    }
    
    @RequestMapping(path = "searchProduct", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ProductModel>> searchProductByProductCatalogFilter(@RequestBody ProductCatalogFilterModel productCatalogFilterModel) {
        log.info("searchProductByProductCatalogFilter");
        Page<ProductModel> productModels = service.searchProductByProductCatalogFilter(productCatalogFilterModel.getProductCatalogSearchModel(),
                productCatalogFilterModel.getProductCatalogSortModel());
        return ResponseEntity.ok().body(productModels);
    }
    
    @RequestMapping(path = "productDetail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductModel> getProductDetailByProductId(@RequestParam Long productId) {
        log.info("getProductDetailByProductId " + productId);
        ProductModel productCatelogModel = service.getProductByProductId(productId);
        return ResponseEntity.ok().body(productCatelogModel);
    }
    
    @RequestMapping(path = "deleteProduct", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> deleteProductByProductId(@RequestParam Long productId) {
        log.info("deleteProductByProductId " + productId);
        boolean result = service.deleteProductByProductId(productId);
        return ResponseEntity.ok().body(result);
    }
    
    @RequestMapping(path = "createProductList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Long>> createProductList(@RequestBody List<CreateProductRequestModel> createProductRequestModel) {
        log.info("createProductList " + createProductRequestModel);
        List<Long> idList = service.addProductListToProductCatalog(createProductRequestModel).stream().map(ProductModel::getId)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(idList);
    }
    
    @RequestMapping(path = "productListByTitleAndDescription", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductModel>> findProductListByTitleAndDescription(
            @RequestBody ProductCatalogSearchModel productCatalogSearchModel) {
        log.info("findProductDetailByTitleAndDescription " + productCatalogSearchModel);
        List<ProductModel> modelList = service.getProductByTitleAndDesription(productCatalogSearchModel);
        return ResponseEntity.ok().body(modelList);
    }
}
