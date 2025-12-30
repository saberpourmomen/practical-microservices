package org.apsystem.product_service.mapper;

import org.apsystem.product_service.dto.PageProductResponse;
import org.apsystem.product_service.dto.ProductRequest;
import org.apsystem.product_service.dto.ProductResponse;
import org.apsystem.product_service.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public class ProductMapper {
    public static ProductResponse getResponse(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .category(product.getCategory())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

    public static Product getModel(ProductRequest request){

        return Product.builder()
                .name(request.getName())
                .category(request.getCategory())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();

    }

    public static PageProductResponse getPageableResponse(Page<Product> response){
        List<ProductResponse> productResponseList=response.getContent().stream()
                .map(ProductMapper::getResponse).toList();
        return PageProductResponse.builder()
                .page(response.getNumber())
                .size(response.getSize())
                .totalCount(response.getTotalElements())
                .products(productResponseList)
                .build();
    }

}
