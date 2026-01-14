package com.commerceteamproject.customer.controller;

import com.commerceteamproject.admin.dto.SessionAdmin;
import com.commerceteamproject.admin.enitity.AdminRole;
import com.commerceteamproject.customer.dto.*;
import com.commerceteamproject.customer.entity.CustomerSortBy;
import com.commerceteamproject.customer.entity.CustomerSortOrder;
import com.commerceteamproject.customer.entity.CustomerState;
import com.commerceteamproject.customer.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/customers")
@RestController
@RequiredArgsConstructor
@Validated
public class CustomerController {

    private final CustomerService customerService;

    // 고객 리스트 조회
    @GetMapping
    public ResponseEntity<PageResponse<GetCustomerListResponse>> findAll(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") @Positive int pageNum,
            @RequestParam(defaultValue = "10") @Positive int pageSize,
            @RequestParam(defaultValue = "createdAt") CustomerSortBy sortBy,
            @RequestParam(required = false) CustomerSortOrder sortOrder,
            @RequestParam(required = false) CustomerState state,
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin,
            HttpSession session
    ) {
        if (sessionAdmin == null) {
            return ResponseEntity.badRequest().build();
        }

        AdminRole role = sessionAdmin.getAdminRole();
        if (role != AdminRole.SUPER && role != AdminRole.RUN) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(customerService.findAll(
                keyword, pageNum, pageSize, sortBy, sortOrder, state
        ));
    }

    // 고객 상세 조회
    @GetMapping("/{customerId}")
    public ResponseEntity<GetCustomerResponse> findOne(
            @PathVariable Long customerId,
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin,
            HttpSession session
    ) {
        if (sessionAdmin == null) {
            return ResponseEntity.badRequest().build();
        }

        AdminRole role = sessionAdmin.getAdminRole();
        if (role != AdminRole.SUPER && role != AdminRole.RUN) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(customerService.findOne(customerId));
    }

    // 고객 정보 수정
    @PatchMapping("/{customerId}/info")
    public ResponseEntity<UpdateCustomerInformationResponse> updateInformation(
            @PathVariable Long customerId,
            @Valid @RequestBody UpdateCustomerInformationRequest request,
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin,
            HttpSession session
    ) {
        if (sessionAdmin == null) {
            return ResponseEntity.badRequest().build();
        }

        AdminRole role = sessionAdmin.getAdminRole();
        if (role != AdminRole.SUPER && role != AdminRole.RUN) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(customerService.updateInformation(customerId, request));
    }

    // 고객 상태 변경
    @PatchMapping("/{customerId}/state")
    public ResponseEntity<UpdateCustomerStateResponse> updateState(
            @PathVariable Long customerId,
            @Valid @RequestBody UpdateCustomerStateRequest request,
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin,
            HttpSession session
    ) {
        if (sessionAdmin == null) {
            return ResponseEntity.badRequest().build();
        }

        AdminRole role = sessionAdmin.getAdminRole();
        if (role != AdminRole.SUPER && role != AdminRole.RUN) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(customerService.updateState(customerId, request));
    }
    
    // 고객 삭제
    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long customerId,
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin,
            HttpSession session
    ) {
        if (sessionAdmin == null) {
            return ResponseEntity.badRequest().build();
        }

        AdminRole role = sessionAdmin.getAdminRole();
        if (role != AdminRole.SUPER) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        customerService.delete(customerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
