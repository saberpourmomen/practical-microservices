package org.apsystem.product_service.controller;

import org.apsystem.product_service.dto.PageProductResponse;
import org.apsystem.product_service.dto.ProductRequest;
import org.apsystem.product_service.dto.ProductResponse;
import org.apsystem.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/list")
    public ResponseEntity<PageProductResponse> getProductList(
            @RequestParam(name = "page",defaultValue ="0") int page,
            @RequestParam(name = "size",defaultValue ="10") int size){
    return ResponseEntity.ok(productService.getProductList(page,size));
    }

    @GetMapping("find/{id}")
    public ResponseEntity<ProductResponse> findProduct(@PathVariable(name = "id")String id){
        return ResponseEntity.ok(productService.findProduct(id));
    }

    @PostMapping("save")
    public ResponseEntity<ProductResponse> saveProduct(@RequestBody ProductRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(request));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable(name = "id")String id,@RequestBody ProductRequest request){
        return ResponseEntity.ok(productService.update(id,request));
    }


    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(name = "id")String id){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
