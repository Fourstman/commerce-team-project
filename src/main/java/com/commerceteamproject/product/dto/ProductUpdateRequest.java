package com.commerceteamproject.product.dto;

import com.commerceteamproject.product.entity.ProductCategory;
import lombok.Getter;

@Getter
public class ProductUpdateRequest {
    private String name;
    private ProductCategory category;
    private int price;
}

