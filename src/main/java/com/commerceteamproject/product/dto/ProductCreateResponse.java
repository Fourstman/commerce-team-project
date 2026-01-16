package com.commerceteamproject.product.dto;

import com.commerceteamproject.product.entity.ProductCategory;
import lombok.Getter;
import com.commerceteamproject.product.entity.ProductStatus;

@Getter
public class ProductCreateResponse {

    private final Long id;
    private final String name;
    private final ProductCategory category;
    private final int price;
    private final int stock;
    private final String description;
    private final ProductStatus status;

    public ProductCreateResponse(Long id, String name, ProductCategory category, int price, int stock,
                                 String description, ProductStatus status ) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.status = status;
    }
}
