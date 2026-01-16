package com.commerceteamproject.order.dto;

import com.commerceteamproject.order.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateOrderStatusRequest {
    @NotNull
    private OrderStatus orderStatus;
}
