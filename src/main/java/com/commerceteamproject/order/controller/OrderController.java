package com.commerceteamproject.order.controller;

import com.commerceteamproject.admin.dto.SessionAdmin;
import com.commerceteamproject.admin.entity.AdminRole;
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
    public ResponseEntity<CreateOrderResponse> createOrder(
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin,
            @Valid @RequestBody CreateOrderRequest request) {
        if (sessionAdmin == null) {
            throw new LoginRequiredException("로그인이 필요합니다.");
        }
        if (sessionAdmin.getAdminRole() == AdminRole.RUN) {
            throw new AccessDeniedException("권한이 없습니다.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.save(sessionAdmin, request));
    }

    // 주문 리스트 조회
    @GetMapping("/orders")
    public ResponseEntity<PageResponse<GetOrderListResponse>> findOrders(
            @RequestParam(required = false)String keyword,
            @RequestParam(required = false)OrderStatus status,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @SessionAttribute(name = "loginAdmin", required = false)SessionAdmin sessionAdmin) {
        if (sessionAdmin == null) {
            throw new LoginRequiredException("로그인이 필요합니다.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findOrders(keyword, status, pageable));
    }

    // 주문 상세 조회
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<GetOneOrderResponse> findOneOrder(
            @PathVariable Long orderId,
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin) {
        if (sessionAdmin == null) {
            throw new LoginRequiredException("로그인이 필요합니다.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findOne(orderId));
    }

    // 주문 상태 변경
    @PutMapping("/orders/{orderId}")
    public ResponseEntity<Void> updateOrderStatus(
            @PathVariable Long orderId,
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin) {
        if (sessionAdmin == null) {
            throw new LoginRequiredException("로그인이 필요합니다.");
        }
        orderService.updateStatus(orderId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 주문 취소
    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable Long orderId,
            @Valid @RequestBody DeleteOrderRequest request,
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin) {
        if (sessionAdmin == null) {
            throw new LoginRequiredException("로그인이 필요합니다.");
        }
        orderService.delete(orderId, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
