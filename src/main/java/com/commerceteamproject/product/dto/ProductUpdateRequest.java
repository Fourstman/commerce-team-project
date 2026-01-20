package com.commerceteamproject.product.dto;

import com.commerceteamproject.product.entity.ProductCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class ProductUpdateRequest {
    @NotBlank(message = "상품명은 필수입니다.")
    private String name;
    @NotNull(message = "상품 카테고리는 필수입니다.")
    private ProductCategory category;
    @Positive(message = "상품 가격은 1 이상이어야 합니다.")
    private int price;
}

