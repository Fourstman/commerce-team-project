package com.commerceteamproject.review.dto;

import com.commerceteamproject.review.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewCreateResponse {
    private Long id;
    private Long orderId;
    private String customerName;
    private String productName;
    private int rating;
    private String content;
    private LocalDateTime createdAt;

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
