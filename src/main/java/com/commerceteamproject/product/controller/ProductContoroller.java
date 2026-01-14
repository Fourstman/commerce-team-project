package com.commerceteamproject.product.controller;

import com.commerceteamproject.product.dto.ProductCreateResponse;
import com.commerceteamproject.product.dto.ProductCreateRquest;
import com.commerceteamproject.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
