package com.commerceteamproject.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateOrderRequest {
    @NotNull
    private Long customerId;
    @NotNull
    private Long productId;
    @NotNull @Min(value = 1, message = "수량은 1 이상이어야 합니다.")
    private int quantity;
}
