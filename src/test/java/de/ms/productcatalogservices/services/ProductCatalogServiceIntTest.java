package de.ms.productcatalogservices.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import de.ms.productcatalogservices.jpa.repository.ProductCatalogHistoryRepository;
import de.ms.productcatalogservices.model.CreateProductRequestModel;
import de.ms.productcatalogservices.model.ProductCatalogSearchModel;
import de.ms.productcatalogservices.model.ProductCatalogSortModel;
import de.ms.productcatalogservices.model.db.ProductHistoryModel;
import de.ms.productcatalogservices.model.db.ProductModel;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
public class ProductCatalogServiceIntTest {
    
    @Autowired
    ProductCatalogService productCatalogService;
    
    @Autowired
    ProductCatalogHistoryService productCatalogHistoryService;
    
    @Autowired
    ProductCatalogHistoryRepository productCatalogHistoryRepository;
    
    @Test
    public void addProductToProductCatalogTest() {
        CreateProductRequestModel createProductRequestModel = new CreateProductRequestModel();
        createProductRequestModel.setProductBrand("BRAND_1");
        createProductRequestModel.setProductColor("COLOR_1");
        createProductRequestModel.setProductDescription("DESCRIPTION_1");
        createProductRequestModel.setProductPrice(new BigDecimal(111.11));
        createProductRequestModel.setProductTitle("TITLE_1");
        createProductRequestModel.setProductId(new Long(1));
        
        ProductModel productCatelogModel = productCatalogService.addProductToProductCatalog(createProductRequestModel);
        
        assertNotNull(productCatelogModel);
        assertNotNull(productCatelogModel.getId());
        assertEquals(createProductRequestModel.getProductColor(), productCatelogModel.getColor());
        
        List<ProductHistoryModel> productHistoryModelList = productCatalogHistoryRepository.findProductVersionListByProductId(createProductRequestModel.getProductId());
        assertTrue(productHistoryModelList.isEmpty());
    }
    
    @Test
    public void addProductListToProductCatalogTest() {
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
        
        List<ProductModel> productModelList = productCatalogService.addProductListToProductCatalog(createProductRequestModelList);
        
        assertNotNull(productModelList);
        assertTrue(!productModelList.isEmpty());
        productModelList.stream().forEachOrdered(p -> assertNotNull(p.getId()));
        productModelList.stream().forEachOrdered(p -> assertNotNull(p.getProductId()));
        productModelList.stream().forEachOrdered(p -> assertNotNull(p.getPrice()));
        productModelList.stream().forEachOrdered(p -> assertNotNull(p.getVersionCounter()));
        
        List<ProductHistoryModel> productHistoryModelList = productCatalogHistoryRepository.findProductVersionListByProductId(createProductRequestModel.getProductId());
        assertFalse(productHistoryModelList.isEmpty());
        productHistoryModelList.stream().forEachOrdered(p -> assertEquals(p.getProductId(), new Long(1)));
    }
    
    @Test
    public void getProductByProductIdTest() {
        CreateProductRequestModel createProductRequestModel = new CreateProductRequestModel();
        createProductRequestModel.setProductBrand("BRAND_1");
        createProductRequestModel.setProductColor("COLOR_1");
        createProductRequestModel.setProductDescription("DESCRIPTION_1");
        createProductRequestModel.setProductPrice(new BigDecimal(111.11));
        createProductRequestModel.setProductTitle("TITLE_1");
        createProductRequestModel.setProductId(new Long(1));
        
        productCatalogService.addProductToProductCatalog(createProductRequestModel);
        
        assertNotNull(productCatalogService.getProductByProductId(createProductRequestModel.getProductId()));
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
        
        productCatalogService.addProductToProductCatalog(createProductRequestModel);
        
        assertTrue(productCatalogService.deleteProductByProductId(createProductRequestModel.getProductId()));
        assertNull(productCatalogService.getProductByProductId(createProductRequestModel.getProductId()));
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
        
        productCatalogService.addProductListToProductCatalog(createProductRequestModelList);
        
        ProductCatalogSearchModel productCatalogSearchModel = new ProductCatalogSearchModel();
        productCatalogSearchModel.setProductCatalogDescription("DESCRIPTION_1");
        
        ProductCatalogSortModel productCatalogSortModel = new ProductCatalogSortModel();
        List<String> sortProperty = new ArrayList<>();
        sortProperty.add("price");
        productCatalogSortModel.setProductCatelogSortAttributes(sortProperty);
        productCatalogSortModel.setPageIndex(0);
        productCatalogSortModel.setPageSize(1);
        Page<ProductModel> productModel = productCatalogService.searchProductByProductCatalogFilter(productCatalogSearchModel, productCatalogSortModel);
        
        assertNotNull(productModel);
        assertEquals(productModel.getTotalPages(), 1);       
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
        
        productCatalogService.addProductListToProductCatalog(createProductRequestModelList);
        
        ProductCatalogSearchModel productCatalogSearchModel = new ProductCatalogSearchModel();
        productCatalogSearchModel.setProductCatalogDescription("TION_1");
        productCatalogSearchModel.setProductCatalogTitle("TITLE_1");
        
        List<ProductModel> productModelList = productCatalogService.getProductByTitleAndDesription(productCatalogSearchModel);
        
        assertNotNull(productModelList);
        assertTrue(!productModelList.isEmpty());
        productModelList.stream().forEach(p -> assertEquals(p.getColor(), "COLOR_1"));
        productModelList.stream().forEach(p -> assertEquals(p.getBrand(), "BRAND_1"));
        productModelList.stream().forEach(p -> assertEquals(p.getTitle(), "TITLE_1"));
        productModelList.stream().forEach(p -> assertEquals(p.getDescription(), "DESCRIPTION_1"));
        
        productCatalogSearchModel = new ProductCatalogSearchModel();
        productCatalogSearchModel.setProductCatalogDescription("");
        productCatalogSearchModel.setProductCatalogTitle("TITLE_1");
        
        productModelList = productCatalogService.getProductByTitleAndDesription(productCatalogSearchModel);
        
        assertNotNull(productModelList);
        
        productCatalogSearchModel = new ProductCatalogSearchModel();
        productCatalogSearchModel.setProductCatalogDescription("DESCRIPTION_1");
        productCatalogSearchModel.setProductCatalogTitle("TIE_1");
        
        productModelList = productCatalogService.getProductByTitleAndDesription(productCatalogSearchModel);
        
        assertTrue(productModelList.isEmpty());
    }
}
