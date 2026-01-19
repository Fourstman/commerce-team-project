package com.commerceteamproject.order.controller;

import com.commerceteamproject.admin.dto.SessionAdmin;
import com.commerceteamproject.admin.entity.AdminRole;
import com.commerceteamproject.common.dto.ApiResponse;
import com.commerceteamproject.common.dto.PageResponse;
import com.commerceteamproject.common.exception.AccessDeniedException;
import com.commerceteamproject.common.exception.LoginRequiredException;
import com.commerceteamproject.order.dto.*;
import com.commerceteamproject.order.entity.OrderStatus;
import com.commerceteamproject.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    // CS 주문 생성
    @PostMapping("/orders")
    public ResponseEntity<ApiResponse<CreateOrderResponse>> createOrder(
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin,
            @Valid @RequestBody CreateOrderRequest request) {
        if (sessionAdmin == null) {
            throw new LoginRequiredException("로그인이 필요합니다.");
        }
        if (sessionAdmin.getAdminRole() == AdminRole.RUN) {
            throw new AccessDeniedException("권한이 없습니다.");
        }
        CreateOrderResponse result = orderService.save(sessionAdmin, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(HttpStatus.CREATED, "CS 주문이 생성되었습니다.", result));
    }

    // 주문 리스트 조회
    @GetMapping("/orders")
    public ResponseEntity<ApiResponse<PageResponse<GetOrderListResponse>>> findOrders(
            @RequestParam(required = false)String keyword,
            @RequestParam(required = false)OrderStatus status,
            @PageableDefault(page = 1, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @SessionAttribute(name = "loginAdmin", required = false)SessionAdmin sessionAdmin) {
        if (sessionAdmin == null) {
            throw new LoginRequiredException("로그인이 필요합니다.");
        }
        PageResponse<GetOrderListResponse> result = orderService.findOrders(keyword, status, pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(HttpStatus.OK, "주문 리스트 조회에 성공했습니다.", result));
    }

    // 주문 상세 조회
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponse<GetOneOrderResponse>> findOneOrder(
            @PathVariable Long orderId,
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin) {
        if (sessionAdmin == null) {
            throw new LoginRequiredException("로그인이 필요합니다.");
        }
        GetOneOrderResponse result = orderService.findOne(orderId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(HttpStatus.OK, "주문 상세 조회에 성공했습니다.", result));
    }

    // 주문 상태 변경
    @PutMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponse<Void>> updateOrderStatus(
            @PathVariable Long orderId,
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin) {
        if (sessionAdmin == null) {
            throw new LoginRequiredException("로그인이 필요합니다.");
        }
        orderService.updateStatus(orderId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(HttpStatus.OK, "주문의 상태를 변경했습니다.", null));
    }

    // 주문 취소
    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<ApiResponse<Void>> deleteOrder(
            @PathVariable Long orderId,
            @Valid @RequestBody DeleteOrderRequest request,
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin) {
        if (sessionAdmin == null) {
            throw new LoginRequiredException("로그인이 필요합니다.");
        }
        orderService.delete(orderId, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(HttpStatus.OK, "주문을 취소했습니다.", null));
    }
}
