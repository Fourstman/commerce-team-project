package com.commerceteamproject.order.dto;

import com.commerceteamproject.order.entity.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateOrderResponse {
    private final Long id;
    private final String orderNumber;
    private final Long customerId;
    private final String customerName;
    private final Long productId;
    private final String productName;
    private final int quantity;
    private final int amount;
    private final OrderStatus orderStatus;
    private final LocalDateTime createdAt;
    private final Long createdByAdminId;
    private final String createdByAdminName;

    public CreateOrderResponse(Long id, String orderNumber, Long customerId, String customerName,
                               Long productId, String productName, int quantity, int amount, OrderStatus orderStatus,
                               LocalDateTime createdAt, Long createdByAdminId, String createdByAdminName) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.customerId = customerId;
        this.customerName = customerName;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.amount = amount;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
        this.createdByAdminId = createdByAdminId;
        this.createdByAdminName = createdByAdminName;
    }
}
