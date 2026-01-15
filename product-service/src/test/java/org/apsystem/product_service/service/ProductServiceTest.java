package org.apsystem.product_service.service;

import org.apsystem.product_service.dto.PageProductResponse;
import org.apsystem.product_service.dto.ProductCategory;
import org.apsystem.product_service.dto.ProductRequest;
import org.apsystem.product_service.dto.ProductResponse;
import org.apsystem.product_service.model.Product;
import org.apsystem.product_service.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.bson.assertions.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class ProductServiceTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    Product productTestModel=Product.builder()
            .name("IPone 16 Pro-max")
            .category(ProductCategory.Electronics)
            .description("Iphone flagship")
            .price(new BigDecimal(12000))
            .build();

    @BeforeEach
    public void cleanRepository(){
        productRepository.deleteAll();
        productRepository.save(productTestModel);
    }

    @Test
    void getProductList() {
        PageProductResponse response=productService.getProductList(0,10);
        assertNotNull(response);
        Assertions.assertEquals(1,response.getProducts().size());
    }

    @Test
    void findProduct() {
        PageProductResponse productList=productService.getProductList(0,10);
        assertNotNull(productList);
        assertNotNull(productList.getProducts());
        Assertions.assertEquals(1,productList.getProducts().size());
        ProductResponse exist=productList.getProducts().get(0);
        String id = exist.getId();
        ProductResponse response=productService.findProduct(id);
        Assertions.assertEquals(response.getName(),exist.getName());
        Assertions.assertEquals(response.getPrice(),exist.getPrice());
    }

    @Test
    void save() {
        ProductRequest request=ProductRequest.builder()
                .name("productName")
                .category(ProductCategory.Electronics)
                .price(new BigDecimal(1000))
                .description("description")
                .build();
        ProductResponse response=productService.save(request);
        assertNotNull(response);
    }

    @Test
    void update() {
        PageProductResponse productList=productService.getProductList(0,10);
        assertNotNull(productList);
        assertNotNull(productList.getProducts());
        Assertions.assertEquals(1,productList.getProducts().size());
        ProductResponse old=productList.getProducts().get(0);
        ProductRequest request=ProductRequest.builder()
                .name("productName")
                .category(ProductCategory.Electronics)
                .price(new BigDecimal(1000))
                .description("description")
                .build();
        ProductResponse response = productService.update(old.getId(),request);
        Assertions.assertEquals(request.getName(),response.getName());
    }

    @Test
    void delete() {
        PageProductResponse productList=productService.getProductList(0,10);
        assertNotNull(productList);
        assertNotNull(productList.getProducts());
        Assertions.assertEquals(1,productList.getProducts().size());
        ProductResponse old=productList.getProducts().get(0);
        productService.delete(old.getId());
        productList=productService.getProductList(0,10);
        assertNotNull(productList);
        assertNotNull(productList.getProducts());
        Assertions.assertEquals(0,productList.getProducts().size());
    }
}