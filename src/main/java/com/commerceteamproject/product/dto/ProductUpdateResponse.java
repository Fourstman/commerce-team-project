package com.commerceteamproject.product.dto;

import com.commerceteamproject.product.entity.ProductStatus;
import lombok.Getter;

@Getter
public class ProductUpdateResponse {
    private final Long id;
    private final String name;
    private final String category;
    private final int price;
    private final int stock;
    private final String description;
    private final ProductStatus status;

    public ProductUpdateResponse(Long id, String name, String category, int price, int stock, String description, ProductStatus status) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.status = status;
    }
}
