package com.commerceteamproject.product.service;

import com.commerceteamproject.product.dto.*;
import com.commerceteamproject.product.entity.Product;
import com.commerceteamproject.product.entity.ProductStatus;
import com.commerceteamproject.product.repository.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductCreateResponse save(ProductCreateRequest request) {
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
    public List<ProductGetResponse> getAll(String category, String sort, String keyword, int page, int size, String sortBy, String order ) {

        Sort sortCondition = Sort.by("modifiedAt").descending();

        if ("price".equals(sort)) {
            sortCondition = Sort.by("price").descending();
        } else if ("stock".equals(sort)) {
            sortCondition = Sort.by("stock").descending();
        }

        List<Product> products;
        if (category != null && !category.isEmpty()) {
            products = productRepository.findAllByCategory(category, sortCondition);
        } else {
            products = productRepository.findAll(sortCondition);
        }

        List<ProductGetResponse> dtos = new ArrayList<>();
        for (Product product : products) {
            dtos.add(new ProductGetResponse(
                    product.getId(),
                    product.getName(),
                    product.getCategory(),
                    product.getPrice(),
                    product.getStock(),
                    product.getDescription(),
                    product.getStatus()
            ));
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
    public ProductUpdateResponse updateProductInfo(
            Long productId,
            @Valid ProductUpdateRequest request
    ) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new IllegalArgumentException("없는 상품입니다.")
        );

        product.updateInfo(
                request.getName(),
                request.getCategory(),
                request.getPrice()
        );

        return new ProductUpdateResponse(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice());
    }

    @Transactional
    public ProductStockUpdateResponse updateStock(Long productId, ProductStockUpdateRequest request
    ) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("없는 상품입니다."));

        product.updateStock(request.getStock());

        if (product.getStatus() != ProductStatus.DISCONTINUED) {
            if (request.getStock() <= 0) {
                product.changeStatus(ProductStatus.SOLD_OUT);
            } else {
                product.changeStatus(ProductStatus.ON_SALE);
            }
        }

        return new ProductStockUpdateResponse(
                product.getId(),
                product.getStock(),
                product.getStatus()
        );
    }

    @Transactional(readOnly = true)
    public ProductListResponse getProducts(
            int page,
            int size
    ) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());

        Page<Product> productPage = productRepository.findAll(pageRequest);

        List<ProductListItemResponse> items = productPage.getContent()
                .stream()
                .map(product -> new ProductListItemResponse(
                        product.getId(),
                        product.getName(),
                        product.getCategory(),
                        product.getPrice(),
                        product.getStock(),
                        product.getStatus(),
                        product.getCreatedAt()))
                        .toList();

        ProductPageInfo pageInfo = new ProductPageInfo(
                page,
                size,
                productPage.getTotalElements(),
                productPage.getTotalPages()
        );

        return new ProductListResponse(items, pageInfo);
    }

    public void delete(Long productsId) {
        boolean existence = productRepository.existsById(productsId);
        if (!existence) {
            throw new IllegalArgumentException("없는 상품 입니다.");
        }
        productRepository.deleteById(productsId);
    }
}
