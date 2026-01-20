package com.commerceteamproject.product.dto;

import com.commerceteamproject.product.entity.ProductStatus;
import lombok.Getter;

@Getter
public class ProductStatusUpdateResponse {
    private final Long id;
    private final ProductStatus status;

    public ProductStatusUpdateResponse(Long id, ProductStatus status) {
        this.id = id;
        this.status = status;
    }
}
