package com.commerceteamproject.product.dto;

import lombok.Getter;

import java.util.List;

@Getter // 여기는 처음 해봐서 ai사용 하는중임 틀리면 피드백 받고 내걸로 만들어야 함.
public class ProductListResponse {
    private final List<ProductListItemResponse> items;
    private final ProductPageInfo pageInfo;

    public ProductListResponse(
            List<ProductListItemResponse> items,
            ProductPageInfo pageInfo
    ) {
        this.items = items;
        this.pageInfo = pageInfo;
    }
}
