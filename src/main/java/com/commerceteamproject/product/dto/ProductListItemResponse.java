package com.commerceteamproject.product.dto;

import com.commerceteamproject.product.entity.Product;
import com.commerceteamproject.product.entity.ProductStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProductListItemResponse {

    private final Long id;
    private final String name;
    private final String category;
    private final int price;
    private final int stock;
    private final ProductStatus status;

    public ProductListItemResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.category = product.getCategory();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.status = product.getStatus();
    }
}
