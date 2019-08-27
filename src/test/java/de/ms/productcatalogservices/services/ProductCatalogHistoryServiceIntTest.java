package de.ms.productcatalogservices.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import de.ms.productcatalogservices.model.db.ProductHistoryModel;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
public class ProductCatalogHistoryServiceIntTest {
    
    @Autowired
    ProductCatalogHistoryService productCatalogHistoryService;
    
    @Test
    public void saveProductHistoryTest() {
        ProductHistoryModel productHistoryModel = new ProductHistoryModel();
        productHistoryModel.setBrand("BRAND_1");
        productHistoryModel.setColor("COLOR_1");
        productHistoryModel.setDescription("DESCRIPTION_1");
        productHistoryModel.setPrice(new BigDecimal(111.232));
        productHistoryModel.setProductId(new Long(111));
        productHistoryModel.setTitle("TITLE_1");
        
        ProductHistoryModel storedProductHistoryModel = productCatalogHistoryService.saveProductHistory(productHistoryModel);
        
        assertNotNull(storedProductHistoryModel);
        assertEquals(storedProductHistoryModel.getBrand(), productHistoryModel.getBrand());
        assertEquals(storedProductHistoryModel.getPrice(), productHistoryModel.getPrice());
        assertTrue(storedProductHistoryModel.getVersionCounter() == null);
    }
    
}
