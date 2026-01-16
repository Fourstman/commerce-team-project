package com.commerceteamproject.product.dto;

import com.commerceteamproject.product.entity.ProductStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter // 재고 아이템 관리
public class ProductListItemResponse {

    private final Long id;
    private final String name;
    private final String category;
    private final int price;
    private final int stock;
    private final ProductStatus status;
    private final LocalDateTime createdAt;

    public ProductListItemResponse(Long id, String name, String category, int price, int stock, ProductStatus status, LocalDateTime createdAt) {

        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.status = status;
        this.createdAt = createdAt;

    }

}
