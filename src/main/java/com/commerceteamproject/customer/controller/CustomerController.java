package com.commerceteamproject.customer.controller;

import com.commerceteamproject.admin.dto.SessionAdmin;
import com.commerceteamproject.admin.entity.AdminRole;
import com.commerceteamproject.common.dto.PageResponse;
import com.commerceteamproject.customer.dto.*;
import com.commerceteamproject.customer.entity.CustomerStatus;
import com.commerceteamproject.common.exception.AccessDeniedException;
import com.commerceteamproject.common.exception.LoginRequiredException;
import com.commerceteamproject.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/customers")
@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    // 고객 리스트 조회
    @GetMapping
    public ResponseEntity<PageResponse<GetCustomerListResponse>> findAll(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) CustomerStatus status,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin
    ) {
        if (sessionAdmin == null) {
            throw new LoginRequiredException("로그인이 필요합니다.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(customerService.findAll(
                keyword, status, pageable
        ));
    }

    // 고객 상세 조회
    @GetMapping("/{customerId}")
    public ResponseEntity<GetCustomerResponse> findOne(
            @PathVariable Long customerId,
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin
    ) {
        if (sessionAdmin == null) {
            throw new LoginRequiredException("로그인이 필요합니다.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(customerService.findOne(customerId));
    }

    // 고객 정보 수정
    @PatchMapping("/{customerId}/info")
    public ResponseEntity<UpdateCustomerInformationResponse> updateInformation(
            @PathVariable Long customerId,
            @Valid @RequestBody UpdateCustomerInformationRequest request,
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin
    ) {
        if (sessionAdmin == null) {
            throw new LoginRequiredException("로그인이 필요합니다.");
        }

        if (sessionAdmin.getAdminRole() == AdminRole.CS) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(customerService.updateInformation(customerId, request));
    }

    // 고객 상태 변경
    @PatchMapping("/{customerId}/status")
    public ResponseEntity<UpdateCustomerStatusResponse> updateStatus(
            @PathVariable Long customerId,
            @Valid @RequestBody UpdateCustomerStatusRequest request,
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin
    ) {
        if (sessionAdmin == null) {
            throw new LoginRequiredException("로그인이 필요합니다.");
        }

        if (sessionAdmin.getAdminRole() == AdminRole.CS) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(customerService.updateStatus(customerId, request));
    }
    
    // 고객 삭제
    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long customerId,
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin
    ) {
        if (sessionAdmin == null) {
            throw new LoginRequiredException("로그인이 필요합니다.");
        }

        if (sessionAdmin.getAdminRole() != AdminRole.SUPER) {
            throw new AccessDeniedException("권한이 없습니다.");
        }

        customerService.delete(customerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
