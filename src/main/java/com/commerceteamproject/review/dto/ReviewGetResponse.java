package com.commerceteamproject.review.dto;

import com.commerceteamproject.review.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewGetResponse {

    private final Long reviewId;
    private final String productName;
    private final String customerName;
    private final String customerEmail;
    private final int rating;
    private final String content;
    private final LocalDateTime createdAt;

    public ReviewGetResponse(Review review) {
        this.reviewId = review.getId();
        this.productName = review.getOrder().getProduct().getName();
        this.customerName = review.getOrder().getCustomer().getName();
        this.customerEmail = review.getOrder().getCustomer().getEmail();
        this.rating = review.getRating();
        this.content = review.getContent();
        this.createdAt = review.getCreatedAt();
    }
}
