package de.ms.productcatalogservices.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import de.ms.productcatalogservices.model.CreateProductRequestModel;
import de.ms.productcatalogservices.model.ProductCatalogFilterModel;
import de.ms.productcatalogservices.model.ProductCatalogSearchModel;
import de.ms.productcatalogservices.model.ProductCatalogSortModel;
import de.ms.productcatalogservices.model.db.ProductModel;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
public class ProductCatalogControllerIntTest {
    
    @Autowired
    ProductCatalogController productCatalogController;
    
    @Test
    public void createProductTest() {
        
        CreateProductRequestModel createProductRequestModel = new CreateProductRequestModel();
        createProductRequestModel.setProductBrand("BRAND_1");
        createProductRequestModel.setProductColor("COLOR_1");
        createProductRequestModel.setProductDescription("DESCRIPTION_1");
        createProductRequestModel.setProductPrice(new BigDecimal(111.11));
        createProductRequestModel.setProductTitle("TITLE_1");
        createProductRequestModel.setProductId(new Long(1));
        
        ResponseEntity<Long> createdId = productCatalogController.createProduct(createProductRequestModel);
        
        assertEquals(createdId.getStatusCode(), HttpStatus.OK);
        assertNotNull(createdId);
        assertNotNull(createdId.getBody());
    }
    
    @Test
    public void createProductListTest() {
        List<CreateProductRequestModel> createProductRequestModelList = new ArrayList<>();
        
        CreateProductRequestModel createProductRequestModel = new CreateProductRequestModel();
        createProductRequestModel.setProductBrand("BRAND_1");
        createProductRequestModel.setProductColor("COLOR_1");
        createProductRequestModel.setProductDescription("DESCRIPTION_1");
        createProductRequestModel.setProductPrice(new BigDecimal(111.11));
        createProductRequestModel.setProductTitle("TITLE_1");
        createProductRequestModel.setProductId(new Long(1));
        createProductRequestModelList.add(createProductRequestModel);
        
        createProductRequestModel = new CreateProductRequestModel();
        createProductRequestModel.setProductBrand("BRAND_2");
        createProductRequestModel.setProductColor("COLOR_2");
        createProductRequestModel.setProductDescription("DESCRIPTION_2");
        createProductRequestModel.setProductPrice(new BigDecimal(11.11));
        createProductRequestModel.setProductTitle("TITLE_2");
        createProductRequestModel.setProductId(new Long(2));
        createProductRequestModelList.add(createProductRequestModel);
        
        createProductRequestModel = new CreateProductRequestModel();
        createProductRequestModel.setProductBrand("BRAND_1");
        createProductRequestModel.setProductColor("COLOR_1");
        createProductRequestModel.setProductDescription("DESCRIPTION_1");
        createProductRequestModel.setProductPrice(new BigDecimal(111.11));
        createProductRequestModel.setProductTitle("TITLE_1");
        createProductRequestModel.setProductId(new Long(1));
        createProductRequestModelList.add(createProductRequestModel);
        
        ResponseEntity<List<Long>> productModelList = productCatalogController.createProductList(createProductRequestModelList);
        
        assertNotNull(productModelList);
        assertEquals(productModelList.getStatusCode(), HttpStatus.OK);
        productModelList.getBody().stream().forEachOrdered(p -> assertNotNull(p));
    }
    
    @Test
    public void getProductDetailByProductIdTest() {
        CreateProductRequestModel createProductRequestModel = new CreateProductRequestModel();
        createProductRequestModel.setProductBrand("BRAND_1");
        createProductRequestModel.setProductColor("COLOR_1");
        createProductRequestModel.setProductDescription("DESCRIPTION_1");
        createProductRequestModel.setProductPrice(new BigDecimal(111.11));
        createProductRequestModel.setProductTitle("TITLE_1");
        createProductRequestModel.setProductId(new Long(1));
        
        productCatalogController.createProduct(createProductRequestModel);
        
        assertNotNull(productCatalogController.getProductDetailByProductId(createProductRequestModel.getProductId()));
    }
    
    @Test
    public void deleteProductByProductIdTest() {
        CreateProductRequestModel createProductRequestModel = new CreateProductRequestModel();
        createProductRequestModel.setProductBrand("BRAND_1");
        createProductRequestModel.setProductColor("COLOR_1");
        createProductRequestModel.setProductDescription("DESCRIPTION_1");
        createProductRequestModel.setProductPrice(new BigDecimal(111.11));
        createProductRequestModel.setProductTitle("TITLE_1");
        createProductRequestModel.setProductId(new Long(1));
        
        productCatalogController.createProduct(createProductRequestModel);
        
        ResponseEntity<Boolean> deleted = productCatalogController.deleteProductByProductId(createProductRequestModel.getProductId());
        assertTrue(deleted.getBody());
    }
    
    @Test
    public void searchProductByProductCatalogFilterTest() {
        List<CreateProductRequestModel> createProductRequestModelList = new ArrayList<>();
        
        CreateProductRequestModel createProductRequestModel = new CreateProductRequestModel();
        createProductRequestModel.setProductBrand("BRAND_1");
        createProductRequestModel.setProductColor("COLOR_1");
        createProductRequestModel.setProductDescription("DESCRIPTION_1");
        createProductRequestModel.setProductPrice(new BigDecimal(111.11));
        createProductRequestModel.setProductTitle("TITLE_1");
        createProductRequestModel.setProductId(new Long(1));
        createProductRequestModelList.add(createProductRequestModel);
        
        createProductRequestModel = new CreateProductRequestModel();
        createProductRequestModel.setProductBrand("BRAND_2");
        createProductRequestModel.setProductColor("COLOR_2");
        createProductRequestModel.setProductDescription("DESCRIPTION_2");
        createProductRequestModel.setProductPrice(new BigDecimal(11.11));
        createProductRequestModel.setProductTitle("TITLE_2");
        createProductRequestModel.setProductId(new Long(2));
        createProductRequestModelList.add(createProductRequestModel);
        
        productCatalogController.createProductList(createProductRequestModelList);
        
        ProductCatalogFilterModel productCatalogFilterModel = new ProductCatalogFilterModel();
        
        ProductCatalogSearchModel productCatalogSearchModel = new ProductCatalogSearchModel();
        productCatalogSearchModel.setProductCatalogDescription("DESCRIPTION_1");
        
        ProductCatalogSortModel productCatalogSortModel = new ProductCatalogSortModel();
        List<String> sortProperty = new ArrayList<>();
        sortProperty.add("price");
        productCatalogSortModel.setProductCatelogSortAttributes(sortProperty);
        productCatalogSortModel.setPageIndex(0);
        productCatalogSortModel.setPageSize(1);
        
        productCatalogFilterModel.setProductCatalogSearchModel(productCatalogSearchModel);
        productCatalogFilterModel.setProductCatalogSortModel(productCatalogSortModel);
        
        ResponseEntity<Page<ProductModel>> productModelList = productCatalogController.searchProductByProductCatalogFilter(productCatalogFilterModel);
        
        assertNotNull(productModelList);
        assertEquals(productModelList.getStatusCode(), HttpStatus.OK);
        productModelList.getBody().stream().forEachOrdered(p -> assertNotNull(p));
        
    }
    
    @Test
    public void getProductByTitleAndDesriptionTest() {
        List<CreateProductRequestModel> createProductRequestModelList = new ArrayList<>();
        
        CreateProductRequestModel createProductRequestModel = new CreateProductRequestModel();
        createProductRequestModel.setProductBrand("BRAND_1");
        createProductRequestModel.setProductColor("COLOR_1");
        createProductRequestModel.setProductDescription("DESCRIPTION_1");
        createProductRequestModel.setProductPrice(new BigDecimal(111.11));
        createProductRequestModel.setProductTitle("TITLE_1");
        createProductRequestModel.setProductId(new Long(1));
        createProductRequestModelList.add(createProductRequestModel);
        
        createProductRequestModel = new CreateProductRequestModel();
        createProductRequestModel.setProductBrand("BRAND_2");
        createProductRequestModel.setProductColor("COLOR_2");
        createProductRequestModel.setProductDescription("DESCRIPTION_2");
        createProductRequestModel.setProductPrice(new BigDecimal(11.11));
        createProductRequestModel.setProductTitle("TITLE_2");
        createProductRequestModel.setProductId(new Long(2));
        createProductRequestModelList.add(createProductRequestModel);
        
        productCatalogController.createProductList(createProductRequestModelList);
        
        ProductCatalogSearchModel productCatalogSearchModel = new ProductCatalogSearchModel();
        productCatalogSearchModel.setProductCatalogDescription("TION_1");
        productCatalogSearchModel.setProductCatalogTitle("TITLE_1");
        
        ResponseEntity<List<ProductModel>> productModelList = productCatalogController
                .findProductListByTitleAndDescription(productCatalogSearchModel);
        
        assertNotNull(productModelList);
        assertEquals(productModelList.getStatusCode(), HttpStatus.OK);
        productModelList.getBody().forEach(p -> assertEquals(p.getColor(), "COLOR_1"));
        productModelList.getBody().forEach(p -> assertEquals(p.getBrand(), "BRAND_1"));
        productModelList.getBody().forEach(p -> assertEquals(p.getTitle(), "TITLE_1"));
        productModelList.getBody().forEach(p -> assertEquals(p.getDescription(), "DESCRIPTION_1"));
        
        productCatalogSearchModel = new ProductCatalogSearchModel();
        productCatalogSearchModel.setProductCatalogDescription("");
        productCatalogSearchModel.setProductCatalogTitle("TITLE_1");
        
        productModelList = productCatalogController.findProductListByTitleAndDescription(productCatalogSearchModel);
        
        assertNotNull(productModelList.getBody());
        
        productCatalogSearchModel = new ProductCatalogSearchModel();
        productCatalogSearchModel.setProductCatalogDescription("DESCRIPTION_1");
        productCatalogSearchModel.setProductCatalogTitle("TIE_1");
        
        productModelList = productCatalogController.findProductListByTitleAndDescription(productCatalogSearchModel);
        
        assertTrue(productModelList.getBody().isEmpty());
    }
}
