package org.apsystem.product_service.controller;

import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.protocol.RequestContent;
import org.apsystem.product_service.dto.PageProductResponse;
import org.apsystem.product_service.dto.ProductCategory;
import org.apsystem.product_service.dto.ProductRequest;
import org.apsystem.product_service.dto.ProductResponse;
import org.apsystem.product_service.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProductControllerTest {

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getProductList() throws Exception {
        ProductResponse response=ProductResponse.builder()
                .id("id")
                .name("name")
                .category(ProductCategory.Electronics)
                .description("description")
                .price(new BigDecimal(1000))
                .build();

        PageProductResponse pageProductResponse= new PageProductResponse(List.of(response),1,0,10);

        Mockito.when(productService.getProductList(0,10)).thenReturn(pageProductResponse);
        mockMvc.perform(
                        get("/api/product/list")
                                .param("page", "0")
                                .param("size", "10")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products[0].id").value("id"));
    }

    @Test
    void findProduct() throws Exception {
        ProductResponse response=ProductResponse.builder()
                .id("id")
                .name("name")
                .category(ProductCategory.Electronics)
                .description("description")
                .price(new BigDecimal(1000))
                .build();
        Mockito.when(productService.findProduct("id")).thenReturn(response);
        mockMvc.perform(
                        get("/api/product/find/{id}", "id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("id"));
    }

    @Test
    void saveProduct() throws Exception {
        ProductRequest request=ProductRequest.builder()
                .name("name")
                .category(ProductCategory.Electronics)
                .description("description")
                .price(new BigDecimal(1000))
                .build();
        ProductResponse response=ProductResponse.builder()
                .id("id")
                .name("name")
                .category(ProductCategory.Electronics)
                .description("description")
                .price(new BigDecimal(1000))
                .build();
        Mockito.when(productService.save(request)).thenReturn(response);
        mockMvc.perform(
                        post("/api/product/save")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("id"));
    }

    @Test
    void updateProduct() throws Exception{
        ProductRequest request=ProductRequest.builder()
                .name("name")
                .category(ProductCategory.Electronics)
                .description("description")
                .price(new BigDecimal(1000))
                .build();
        ProductResponse response=ProductResponse.builder()
                .id("id")
                .name("name")
                .category(ProductCategory.Electronics)
                .description("description")
                .price(new BigDecimal(1000))
                .build();
        Mockito.when(productService.update("id",request)).thenReturn(response);
        mockMvc.perform(
                        put("/api/product/update/{id}","id")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("name"));
    }

    @Test
    void deleteProduct() throws Exception{
        Mockito.doNothing().when(productService).delete("id");
        mockMvc.perform(delete("/api/product/delete/{i  d}","id"))
                .andExpect(status().isNoContent());
    }
}