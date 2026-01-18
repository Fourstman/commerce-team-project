package com.commerceteamproject.product.controller;

import com.commerceteamproject.admin.dto.SessionAdmin;
import com.commerceteamproject.admin.entity.AdminRole;
import com.commerceteamproject.common.dto.ApiResponse;
import com.commerceteamproject.common.dto.PageResponse;
import com.commerceteamproject.common.exception.AccessDeniedException;
import com.commerceteamproject.common.exception.LoginRequiredException;
import com.commerceteamproject.product.dto.*;
import com.commerceteamproject.product.entity.ProductCategory;
import com.commerceteamproject.product.entity.ProductStatus;
import com.commerceteamproject.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/products")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private void validateAdmin(SessionAdmin sessionAdmin) {

        if (sessionAdmin == null) {
            throw new LoginRequiredException("로그인이 필요합니다.");
        }
        if (sessionAdmin.getAdminRole() == AdminRole.CS) {
            throw new AccessDeniedException("권한이 없습니다.");
        }
    }

    @PostMapping // 상품 생성
    public ResponseEntity<ApiResponse<ProductCreateResponse>> create(
            @Valid @RequestBody ProductCreateRequest request,
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin
    ) {
        validateAdmin(sessionAdmin);
        ProductCreateResponse product = productService.save(sessionAdmin, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(HttpStatus.CREATED, "상품이 생성되었습니다.", product));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ProductListItemResponse>>> getProducts(
            @RequestParam(required = false)String keyword,
            @RequestParam(required = false) ProductCategory productCategory,
            @RequestParam(required = false)ProductStatus productStatus,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin
    ) {
        if (sessionAdmin == null) {
            throw new LoginRequiredException("로그인이 필요합니다.");
        }
        PageResponse<ProductListItemResponse> result = productService.getProducts(keyword, productCategory, productStatus, pageable);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "상품 목록 조회에 성공했습니다.", result));
    }

    @GetMapping("/{productId}") // 상품 단건 조회
    public ResponseEntity<ApiResponse<ProductGetResponse>> getById(
            @PathVariable Long productId,
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin
    ) {
        if (sessionAdmin == null) {
            throw new LoginRequiredException("로그인이 필요합니다.");
        }
        ProductGetResponse result = productService.getById(productId);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "상품 조회에 성공했습니다.", result));
    }


    @PutMapping("/{productId}") // 상품 수정
    public ResponseEntity<ApiResponse<ProductUpdateResponse>> updateProductInfo(
            @Valid @RequestBody ProductUpdateRequest request,
            @PathVariable Long productId,
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin
    ) {
        validateAdmin(sessionAdmin);
        ProductUpdateResponse result = productService.updateProductInfo(productId, request);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "상품 정보가 수정되었습니다.", result));
    }

    @PatchMapping("/{productId}/stock")
    public ResponseEntity<ApiResponse<ProductStockUpdateResponse>> updateStock(
            @PathVariable Long productId,
            @RequestBody ProductStockUpdateRequest request,
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin
    ) {
        validateAdmin(sessionAdmin);
        ProductStockUpdateResponse result = productService.updateStock(productId, request);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "상품 재고가 수정되었습니다.", result));
    }

    @DeleteMapping("/{productId}") // 상품 삭제
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long productId,
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin
    ) {
        validateAdmin(sessionAdmin);
        productService.delete(productId);

        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "상품이 삭제 되었습니다", null));
    }

}
