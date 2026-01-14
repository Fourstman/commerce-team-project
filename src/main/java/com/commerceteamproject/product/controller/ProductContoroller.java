package com.commerceteamproject.product.controller;

import com.commerceteamproject.product.dto.*;
import com.commerceteamproject.product.entity.Product;
import com.commerceteamproject.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductContoroller {

    private final ProductService productService;

    @PostMapping("/products") // 상품 생성 > products로 생성
    public ResponseEntity<ProductCreateResponse> create (
            @RequestBody ProductCreateRquest request
    ) {
        ProductCreateResponse product = productService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);

    }

    @GetMapping("/products") // 상품 전체 조화 > products로 조회
    public ResponseEntity<List<ProductGetResponse>> getAll (
            @RequestBody(required = false) String category
    ) {
        List<ProductGetResponse> products = productService.getAll(category);
        return ResponseEntity.status(HttpStatus.OK).body(products);

    }

    @GetMapping("/products/{productsId}") // 상품 단건 조회 > products / productsId
    public ResponseEntity<ProductGetResponse> getById (
            @PathVariable Long productsId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getById(productsId));
    }

    @PutMapping("/products/{productsId}") // 상품 수정 > products / productsId
    public ResponseEntity<ProductUpdateResponse> update (
            @RequestBody ProductUpdateRequest request,
            @PathVariable Long productsId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.update(productsId, request));
    }

    @DeleteMapping("/products/{productsId}") // 상품 삭제 > products / productsId
    public void delete (@PathVariable Long productsId) {
        productService.delete(productsId);
    }

}
