package com.commerceteamproject.order.dto;

import com.commerceteamproject.admin.entity.AdminRole;
import com.commerceteamproject.order.entity.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetOneOrderResponse {
    //    주문번호, 고객명, 고객 이메일, 상품명, 수량, 금액, 주문일, 상태, 등록 관리자명, 등록 관리자 이메일, 등록 관리자 역할
    private final String orderNumber;
    private final String customerName;
    private final String customerEmail;
    private final String productName;
    private final int quantity;
    private final int amount;
    private final LocalDateTime createdAt;
    private final OrderStatus orderStatus;
    private final String createdByAdminName;
    private final String createdByAdminEmail;
    private final AdminRole adminRole;

    public GetOneOrderResponse(String orderNumber, String customerName, String customerEmail, String productName,
                               int quantity, int amount, LocalDateTime createdAt, OrderStatus orderStatus,
                               String createdByAdminName, String createdByAdminEmail, AdminRole adminRole) {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.productName = productName;
        this.quantity = quantity;
        this.amount = amount;
        this.createdAt = createdAt;
        this.orderStatus = orderStatus;
        this.createdByAdminName = createdByAdminName;
        this.createdByAdminEmail = createdByAdminEmail;
        this.adminRole = adminRole;
    }
}
