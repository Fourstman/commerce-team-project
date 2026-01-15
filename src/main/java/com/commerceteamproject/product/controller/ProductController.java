package com.commerceteamproject.product.controller;

import com.commerceteamproject.admin.dto.SessionAdmin;
import com.commerceteamproject.admin.enitity.AdminRole;
import com.commerceteamproject.common.exception.AccessDeniedException;
import com.commerceteamproject.common.exception.LoginRequiredException;
import com.commerceteamproject.product.dto.*;
import com.commerceteamproject.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/products")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/products") // 상품 생성 > products로 생성
    public ResponseEntity<ProductCreateResponse> create(
            @RequestBody ProductCreateRequest request,
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin
    ) {
        if (sessionAdmin == null) {
            throw new LoginRequiredException("로그인이 필요합니다.");
        }

        if (sessionAdmin.getAdminRole() == AdminRole.CS) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        ProductCreateResponse product = productService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);

    }

    @GetMapping("/products") // 상품 전체 조회 > products로 조회
    public ResponseEntity<List<ProductGetResponse>> getAll(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String Keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String order,

            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin

    ) {
        if (sessionAdmin == null) {
            throw new LoginRequiredException("로그인이 필요합니다.");
        }

        if (sessionAdmin.getAdminRole() == AdminRole.CS) {
            throw new AccessDeniedException("권한이 없습니다.");
        }
        List<ProductGetResponse> products = productService.getAll(category, sort);
        return ResponseEntity.status(HttpStatus.OK).body(products);

    }

    @GetMapping("/products/{productsId}") // 상품 단건 조회 > products / productsId
    public ResponseEntity<ProductGetResponse> getById(
            @PathVariable Long productsId,
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin
    ) {
        if (sessionAdmin == null) {
            throw new LoginRequiredException("로그인이 필요합니다.");
        }

        if (sessionAdmin.getAdminRole() == AdminRole.CS) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(productService.getById(productsId));
    }

    @PutMapping("/products/{productsId}") // 상품 수정 > products / productsId
    public ResponseEntity<ProductUpdateResponse> update(
            @RequestBody ProductUpdateRequest request,
            @PathVariable Long productsId,
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin
    ) {
        if (sessionAdmin == null) {
            throw new LoginRequiredException("로그인이 필요합니다.");
        }

        if (sessionAdmin.getAdminRole() == AdminRole.CS) {
            throw new AccessDeniedException("권한이 없습니다.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(productService.update(productsId, request));
    }

    @DeleteMapping("/products/{productsId}") // 상품 삭제 > products / productsId
    public ResponseEntity<Void> delete(
            @PathVariable Long productsId,
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin
    ) {
        if (sessionAdmin == null) {
            throw new LoginRequiredException("로그인이 필요합니다.");
        }

        if (sessionAdmin.getAdminRole() == AdminRole.CS) {
            throw new AccessDeniedException("권한이 없습니다.");
        }
        productService.delete(productsId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
