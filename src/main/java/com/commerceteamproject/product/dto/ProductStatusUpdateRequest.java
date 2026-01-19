package com.commerceteamproject.product.dto;

import com.commerceteamproject.product.entity.ProductCategory;
import com.commerceteamproject.product.entity.ProductStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class ProductStatusUpdateRequest {
    @NotNull(message = "상품 상태는 필수입니다.")
    private ProductStatus status;
}

