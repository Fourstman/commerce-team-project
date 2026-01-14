package com.commerceteamproject.product.controller;

import com.commerceteamproject.product.dto.ProductCreateResponse;
import com.commerceteamproject.product.dto.ProductCreateRquest;
import com.commerceteamproject.product.dto.ProductGetResponse;
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

    @PostMapping("/products")
    public ResponseEntity<ProductCreateResponse> create (
            @RequestBody ProductCreateRquest request
    ) {
        ProductCreateResponse product = productService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);

    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductGetResponse>> getAll (
            @RequestBody(required = false) String category
    ) {
        List<ProductGetResponse> products = productService.getAll(category);
        return ResponseEntity.status(HttpStatus.OK).body(products);

    }

    @GetMapping("/products/{productsId}")
    public ResponseEntity<ProductGetResponse> getById (
            @PathVariable Long productsId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getById(productsId));
    }

}
