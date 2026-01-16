package com.commerceteamproject.order.controller;

import com.commerceteamproject.admin.dto.SessionAdmin;
import com.commerceteamproject.admin.entity.AdminRole;
import com.commerceteamproject.common.exception.AccessDeniedException;
import com.commerceteamproject.common.exception.LoginRequiredException;
import com.commerceteamproject.order.dto.CreateOrderRequest;
import com.commerceteamproject.order.dto.CreateOrderResponse;
import com.commerceteamproject.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    // CS 주문 생성
    @PostMapping("/orders")
    public ResponseEntity<CreateOrderResponse> createOrder(
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin,
            @RequestBody CreateOrderRequest request) {
        if (sessionAdmin == null) {
            throw new LoginRequiredException("로그인이 필요합니다.");
        }
        if (sessionAdmin.getAdminRole() == AdminRole.RUN) {
            throw new AccessDeniedException("권한이 없습니다.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.save(sessionAdmin, request));
    }
}
