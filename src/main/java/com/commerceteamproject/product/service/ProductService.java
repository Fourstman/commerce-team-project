package com.commerceteamproject.product.service;

import com.commerceteamproject.admin.dto.SessionAdmin;
import com.commerceteamproject.admin.entity.Admin;
import com.commerceteamproject.admin.repository.AdminRepository;
import com.commerceteamproject.common.dto.PageResponse;
import com.commerceteamproject.common.exception.InvalidParameterException;
import com.commerceteamproject.common.exception.LoginRequiredException;
import com.commerceteamproject.product.dto.*;
import com.commerceteamproject.product.entity.Product;
import com.commerceteamproject.product.entity.ProductCategory;
import com.commerceteamproject.product.entity.ProductStatus;
import com.commerceteamproject.product.exception.ProductNotFoundException;
import com.commerceteamproject.product.exception.ProductStatusNotAllowedException;
import com.commerceteamproject.product.repository.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final AdminRepository adminRepository;

    // 상품 생성
    @Transactional
    public ProductCreateResponse save(SessionAdmin sessionAdmin, ProductCreateRequest request) {
        Admin admin = adminRepository.findById(sessionAdmin.getId()).orElseThrow(
                () -> new LoginRequiredException("유효하지 않은 세션입니다.")
        );
        Product product = new Product(
                admin,
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

    // 상품 리스트 조회
    @Transactional(readOnly = true)
    public PageResponse<ProductListItemResponse> getProducts(
            String keyword, ProductCategory productCategory, ProductStatus productStatus, Pageable pageable) {
        List<String> allowedProperties = List.of("price", "stock", "createdAt");
        pageable.getSort().forEach(order -> {
            if (!allowedProperties.contains(order.getProperty())) {
                throw new InvalidParameterException("잘못된 정렬 기준입니다.");
            }
        });
        Pageable basePageable = PageRequest.of(
                pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        Page<Product> products = productRepository.findByKeywordAndStatus(
                keyword, productCategory, productStatus, basePageable);
        Page<ProductListItemResponse> page = products.map(p -> new ProductListItemResponse(
                p.getId(),
                p.getName(),
                p.getCategory(),
                p.getPrice(),
                p.getStock(),
                p.getStatus(),
                p.getCreatedAt(),
                p.getAdmin().getName()
        ));
        return new PageResponse<>(page);
    }

    // 상품 상세 조회
    @Transactional(readOnly = true)
    public ProductGetResponse getById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProductNotFoundException("없는 상품 입니다.")
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

    // 상품 정보 수정
    @Transactional
    public ProductUpdateResponse updateProductInfo(
            Long productId,
            @Valid ProductUpdateRequest request
    ) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProductNotFoundException("없는 상품입니다.")
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

    // 상품 재고 변경
    @Transactional
    public ProductStockUpdateResponse updateStock(Long productId, ProductStockUpdateRequest request
    ) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("없는 상품입니다."));

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

    // 상품 상태 변경
    @Transactional
    public ProductStatusUpdateResponse updateStatus(Long productId, ProductStatusUpdateRequest request
    ) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("없는 상품입니다."));

        if (request.getStatus() == ProductStatus.ON_SALE && product.getStock() == 0) {
            throw new ProductStatusNotAllowedException("상품의 재고가 없습니다.");
        }

        product.changeStatus(request.getStatus());

        return new ProductStatusUpdateResponse(
                product.getId(),
                product.getStatus()
        );
    }

    // 상품 삭제
    @Transactional
    public void delete(Long productsId) {
        boolean existence = productRepository.existsById(productsId);
        if (!existence) {
            throw new ProductNotFoundException("없는 상품 입니다.");
        }
        productRepository.deleteById(productsId);
    }
}
