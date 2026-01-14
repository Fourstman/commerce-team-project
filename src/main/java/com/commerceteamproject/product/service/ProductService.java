package com.commerceteamproject.product.service;

import com.commerceteamproject.product.dto.ProductCreateResponse;
import com.commerceteamproject.product.dto.ProductCreateRquest;
import com.commerceteamproject.product.entity.Product;
import com.commerceteamproject.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductCreateResponse save(ProductCreateRquest request) {
        Product product = new Product(
                request.getName(),
                request.getCategory(),
                request.getPrice(),
                request.getStock(),
                request.getDescription(),
                request.getStatus());

        Product saveproduct = productRepository.save(product);
        return new ProductCreateResponse(
                saveproduct.getId(),
                saveproduct.getName(),
                saveproduct.getCategory(),
                saveproduct.getPrice(),
                saveproduct.getStock(),
                saveproduct.getDescription(),
                saveproduct.getStatus()
        );

    }
}
