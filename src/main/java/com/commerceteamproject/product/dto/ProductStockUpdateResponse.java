package com.commerceteamproject.product.dto;

import com.commerceteamproject.product.entity.ProductStatus;
import lombok.Getter;

@Getter
public class ProductStockUpdateResponse {
    private final Long id;
    private final int stock;
    private final ProductStatus status;

    public ProductStockUpdateResponse(Long id, int stock, ProductStatus status) {
        this.id = id;
        this.stock = stock;
        this.status = status;
    }
}
