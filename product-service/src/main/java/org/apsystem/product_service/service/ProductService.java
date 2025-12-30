package org.apsystem.product_service.service;

import org.apsystem.product_service.dto.PageProductResponse;
import org.apsystem.product_service.dto.ProductRequest;
import org.apsystem.product_service.dto.ProductResponse;
import org.apsystem.product_service.exception.ProductNotFoundException;
import org.apsystem.product_service.mapper.ProductMapper;
import org.apsystem.product_service.model.Product;
import org.apsystem.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public PageProductResponse getProductList(int page, int size) {
        Pageable pageable= PageRequest.of(page,size);
        Page<Product> response= productRepository.findAll(pageable);
        return ProductMapper.getPageableResponse(response);
    }

    @Transactional(readOnly = true)
    public ProductResponse findProduct(String id){
        Product product=productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product not found!"));
        return ProductMapper.getResponse(product);
    }

    @Transactional
    public ProductResponse save(ProductRequest request) {
        Product product=ProductMapper.getModel(request);
        log.info("save product information: {}",request);
        return ProductMapper.getResponse(productRepository.save(product));
    }

    @Transactional
    public ProductResponse update(String id, ProductRequest request) {
        productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product not found!"));
        Product product= ProductMapper.getModel(request);
        product.setId(id);
        return ProductMapper.getResponse(productRepository.save(product));
    }

    @Transactional
    public void delete(String id) {
        Product product=productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product not found!"));
        productRepository.delete(product);
    }
}
