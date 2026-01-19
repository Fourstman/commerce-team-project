package com.commerceteamproject.review.service;

import com.commerceteamproject.admin.dto.SessionAdmin;
import com.commerceteamproject.admin.entity.AdminRole;
import com.commerceteamproject.common.dto.PageResponse;
import com.commerceteamproject.common.exception.AccessDeniedException;
import com.commerceteamproject.common.exception.LoginRequiredException;
import com.commerceteamproject.order.entity.Order;
import com.commerceteamproject.order.repository.OrderRepository;
import com.commerceteamproject.review.dto.ReviewCreateRequest;
import com.commerceteamproject.review.dto.ReviewCreateResponse;
import com.commerceteamproject.review.dto.ReviewGetResponse;
import com.commerceteamproject.review.entity.Review;
import com.commerceteamproject.review.exception.ReviewNotFoundException;
import com.commerceteamproject.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    // 리뷰 리스트 조회
    @Transactional(readOnly = true)
    public PageResponse<ReviewGetResponse> getReviews(
            String keyword,
            Integer rating,
            int page,
            int size,
            String sortBy,
            String direction
    ) {
        Pageable pageable = PageRequest.of(
                page - 1,  // 1-based -> 0-based
                size,
                Sort.by(Sort.Direction.fromString(direction), sortBy)
        );

        Page<Review> reviews = reviewRepository.findByKeywordAndRating(keyword, rating, pageable);
        Page<ReviewGetResponse> dtoPage = reviews.map(ReviewGetResponse::new);

        return new PageResponse<>(dtoPage);
    }

    // 리뷰 상세 조회
    @Transactional
    public ReviewGetResponse getReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("존재하지 않는 리뷰입니다."));

        return new ReviewGetResponse(review);
    }

    // 리뷰 삭제
    @Transactional
    public void delete(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("존재하지 않는 리뷰입니다."));
        reviewRepository.delete(review);
    }

    // 테스트용 리뷰 생성 코드
    private final OrderRepository orderRepository;
    @Transactional
    public ReviewCreateResponse createReview(ReviewCreateRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new ReviewNotFoundException("존재하지 않는 주문입니다."));

        // 이미 리뷰가 있는 주문이면 에러
        if (reviewRepository.findAll().stream().anyMatch(r -> r.getOrder().getId().equals(order.getId()))) {
            throw new IllegalStateException("이미 리뷰가 등록된 주문입니다.");
        }

        Review review = new Review(order, request.getRating(), request.getContent());
        reviewRepository.save(review);

        return new ReviewCreateResponse(review);
    }
}
