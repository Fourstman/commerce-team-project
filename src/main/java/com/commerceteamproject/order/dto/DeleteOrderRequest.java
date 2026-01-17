package com.commerceteamproject.order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DeleteOrderRequest {
    @NotBlank
    public String canceledReason;
}
