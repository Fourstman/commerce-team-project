package com.commerceteamproject.product.dto;

import com.commerceteamproject.product.entity.ProductCategory;
import lombok.Getter;

@Getter
public class ProductUpdateResponse {
    private final Long id;
    private final String name;
    private final ProductCategory category;
    private final int price;

    public ProductUpdateResponse(Long id, String name, ProductCategory category, int price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;

    }

}
