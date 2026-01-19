package com.commerceteamproject.product.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter // 재고 관리
public class ProductStockUpdateRequest {
    @NotNull(message = "상품 재고는 필수입니다.")
    private int stock;
}
