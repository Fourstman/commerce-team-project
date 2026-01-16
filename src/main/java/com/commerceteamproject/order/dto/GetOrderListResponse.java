package com.commerceteamproject.order.dto;

import com.commerceteamproject.order.entity.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetOrderListResponse {
    private final Long id;
    private final String orderNumber;
    private final String customerName;
    private final String productName;
    private final int quantity;
    private final int amount;
    private final LocalDateTime createdAt;
    private final OrderStatus orderStatus;
    private final String createdByAdminName;

    public GetOrderListResponse(Long id, String orderNumber, String customerName, String productName,
                                int quantity, int amount, LocalDateTime createdAt,
                                OrderStatus orderStatus, String createdByAdminName) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.productName = productName;
        this.quantity = quantity;
        this.amount = amount;
        this.createdAt = createdAt;
        this.orderStatus = orderStatus;
        this.createdByAdminName = createdByAdminName;
    }
}
