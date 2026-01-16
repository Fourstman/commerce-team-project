package com.commerceteamproject.order.dto;

import com.commerceteamproject.order.entity.OrderStatus;
import lombok.Getter;

@Getter
public class UpdateOrderStatusResponse {
    private final String orderNumber;
    private final OrderStatus orderStatus;

    public UpdateOrderStatusResponse(String orderNumber, OrderStatus orderStatus) {
        this.orderNumber = orderNumber;
        this.orderStatus = orderStatus;
    }
}
