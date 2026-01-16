package com.commerceteamproject.product.controller;

import com.commerceteamproject.admin.dto.SessionAdmin;
import com.commerceteamproject.admin.entity.AdminRole;
import com.commerceteamproject.common.dto.PageResponse;
import com.commerceteamproject.common.exception.AccessDeniedException;
import com.commerceteamproject.common.exception.LoginRequiredException;
import com.commerceteamproject.product.dto.*;
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
    public ResponseEntity<ProductCreateResponse> create(
            @Valid @RequestBody ProductCreateRequest request,
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin
    ) {
        validateAdmin(sessionAdmin);
        ProductCreateResponse product = productService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @GetMapping
    public ResponseEntity<PageResponse<ProductListItemResponse>> getProducts(
            @RequestParam(required = false)String keyword,
            @RequestParam(required = false)ProductStatus productStatus,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin
    ) {
        if (sessionAdmin == null) {
            throw new LoginRequiredException("로그인이 필요합니다.");
        }
        return ResponseEntity.ok(productService.getProducts(keyword, productStatus, pageable));
    }

    @GetMapping("/{productId}") // 상품 단건 조회
    public ResponseEntity<ProductGetResponse> getById(
            @PathVariable Long productId,
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin
    ) {
        if (sessionAdmin == null) {
            throw new LoginRequiredException("로그인이 필요합니다.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(productService.getById(productId));
    }

    @PutMapping("/{productId}") // 상품 수정
    public ResponseEntity<ProductUpdateResponse> updateProductInfo(
            @Valid @RequestBody ProductUpdateRequest request,
            @PathVariable Long productId,
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin
    ) {
        validateAdmin(sessionAdmin);
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateProductInfo(productId, request));
    }

    @PatchMapping("/{productId}/stock")
    public ResponseEntity<ProductStockUpdateResponse> updateStock(
            @PathVariable Long productId,
            @RequestBody ProductStockUpdateRequest request,
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin
    ) {
        validateAdmin(sessionAdmin);
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateStock(productId, request));
    }

    @DeleteMapping("/{productId}") // 상품 삭제
    public ResponseEntity<Void> delete(
            @PathVariable Long productId,
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin
    ) {
        validateAdmin(sessionAdmin);
        productService.delete(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
