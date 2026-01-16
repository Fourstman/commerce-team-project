package com.commerceteamproject.product.dto;

import com.commerceteamproject.product.entity.ProductStatus;
import lombok.Getter;

@Getter
public class ProductUpdateRequest {
    private String name;
    private String category;
    private int price;
}

