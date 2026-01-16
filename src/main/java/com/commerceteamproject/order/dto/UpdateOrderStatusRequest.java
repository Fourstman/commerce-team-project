package com.commerceteamproject.order.dto;

import com.commerceteamproject.order.entity.OrderStatus;
import lombok.Getter;

@Getter
public class UpdateOrderStatusRequest {
    private OrderStatus orderStatus;
}
