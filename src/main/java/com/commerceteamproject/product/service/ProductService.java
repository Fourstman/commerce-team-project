package com.commerceteamproject.product.service;

import com.commerceteamproject.product.dto.*;
import com.commerceteamproject.product.entity.Product;
import com.commerceteamproject.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    @Transactional(readOnly = true)
    public List<ProductGetResponse> getAll(String category) {
        List<Product> products;
        List<ProductGetResponse> dtos = new ArrayList<>();

        if (category != null && !category.isEmpty()) {
            products = productRepository.findAllByCategoryOrderByModifiedAtDesc(category);
        } else {
            products = productRepository.findAllByOrderByModifiedAtDesc();
        }

        for(Product product : products) {
            ProductGetResponse productGetResponse = new ProductGetResponse(
                    product.getId(),
                    product.getName(),
                    product.getCategory(),
                    product.getPrice(),
                    product.getStock(),
                    product.getDescription(),
                    product.getStatus()
            );
            dtos.add(productGetResponse);
        }
        return dtos;
    }

    @Transactional
    public ProductGetResponse getById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new IllegalArgumentException("없는 상품 입니다.")
        );

        return new ProductGetResponse(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getStock(),
                product.getDescription(),
                product.getStatus()
        );
    }

    @Transactional
    public ProductUpdateResponse update(Long productsId, ProductUpdateRequest request) { //
        Product product = productRepository.findById(productsId).orElseThrow(
                () -> new IllegalArgumentException(" 없는 상품 입니다.")
        );
        product.update(request.getName(), request.getCategory(), request.getPrice(), request.getStock(), request.getDescription(),request.getStatus());
        return new ProductUpdateResponse(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getStock(),
                product.getDescription(),
                product.getStatus()
        );
    }

    public void delete(Long productsId) {
        boolean existence = productRepository.existsById(productsId);
        if (!existence) {
            throw new IllegalArgumentException("없는 상품 입니다.");
        }
        productRepository.deleteById(productsId);
    }
}
