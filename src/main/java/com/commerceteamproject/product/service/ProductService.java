package com.commerceteamproject.product.service;

import com.commerceteamproject.common.dto.PageResponse;
import com.commerceteamproject.common.exception.InvalidParameterException;
import com.commerceteamproject.product.dto.*;
import com.commerceteamproject.product.entity.Product;
import com.commerceteamproject.product.entity.ProductStatus;
import com.commerceteamproject.product.repository.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
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
    public PageResponse<ProductListItemResponse> getProducts(
            String keyword, ProductStatus productStatus, Pageable pageable) {
        List<String> allowedProperties = List.of("price", "stock", "createdAt");
        pageable.getSort().forEach(order -> {
            if (!allowedProperties.contains(order.getProperty())) {
                throw new InvalidParameterException("잘못된 정렬 기준입니다.");
            }
        });
        Page<Product> products = productRepository.findByKeywordAndStatus(keyword, productStatus, pageable);
        Page<ProductListItemResponse> page = products.map(p -> new ProductListItemResponse(
                p.getId(),
                p.getName(),
                p.getCategory(),
                p.getPrice(),
                p.getStock(),
                p.getStatus(),
                p.getCreatedAt()
        ));
        return new PageResponse<>(page);
    }

    @Transactional(readOnly = true)
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

    @Transactional
    public void delete(Long productsId) {
        boolean existence = productRepository.existsById(productsId);
        if (!existence) {
            throw new IllegalArgumentException("없는 상품 입니다.");
        }
        productRepository.deleteById(productsId);
    }
}
