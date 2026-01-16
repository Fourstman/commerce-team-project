package com.commerceteamproject.product.dto;

import lombok.Getter;

@Getter
public class ProductPageInfo {
    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;

    public ProductPageInfo(int page, int size, long totalElements, int totalPages) {
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }
}
