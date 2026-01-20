package com.commerceteamproject.review.dto;

import com.commerceteamproject.review.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewCreateResponse {
    private final Long id;
    private final Long orderId;
    private final String customerName;
    private final String productName;
    private final int rating;
    private final String content;
    private final LocalDateTime createdAt;

    public ReviewCreateResponse(Review review) {
        this.id = review.getId();
        this.orderId = review.getOrder().getId();
        this.customerName = review.getOrder().getCustomer().getName();
        this.productName = review.getOrder().getProduct().getName();
        this.rating = review.getRating();
        this.content = review.getContent();
        this.createdAt = review.getCreatedAt();
    }
}
