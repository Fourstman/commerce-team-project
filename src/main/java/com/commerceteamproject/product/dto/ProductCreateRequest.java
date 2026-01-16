package com.commerceteamproject.product.dto;

import com.commerceteamproject.product.entity.ProductCategory;
import com.commerceteamproject.product.entity.ProductStatus;
import lombok.Getter;

@Getter
public class ProductCreateRequest {
    private String name;
    private ProductCategory category;
    private int price;
    private int stock;
    private String description;
    private ProductStatus status;
}
