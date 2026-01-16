package com.commerceteamproject.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateOrderRequest {
    @NotBlank
    private Long customerId;
    @NotBlank
    private Long productId;
    @NotBlank @Min(value = 1, message = "수량은 1 이상이어야 합니다.")
    private int quantity;
}
