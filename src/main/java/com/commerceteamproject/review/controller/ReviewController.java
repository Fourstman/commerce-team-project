package com.commerceteamproject.review.controller;

import com.commerceteamproject.admin.dto.AdminDeleteRequest;
import com.commerceteamproject.admin.dto.SessionAdmin;
import com.commerceteamproject.admin.entity.AdminRole;
import com.commerceteamproject.common.dto.ApiResponse;
import com.commerceteamproject.common.dto.PageResponse;
import com.commerceteamproject.common.exception.AccessDeniedException;
import com.commerceteamproject.common.exception.LoginRequiredException;
import com.commerceteamproject.review.dto.ReviewCreateRequest;
import com.commerceteamproject.review.dto.ReviewCreateResponse;
import com.commerceteamproject.review.dto.ReviewGetResponse;
import com.commerceteamproject.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admins/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    // 리뷰 리스트 조회
    @GetMapping
    public ApiResponse<PageResponse<ReviewGetResponse>> getReviews(
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer rating,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        validateAdmin(sessionAdmin);
        PageResponse<ReviewGetResponse> reviews =
                reviewService.getReviews(keyword, rating, page, size, sortBy, direction);
        return ApiResponse.success(reviews);
    }

    // 리뷰 상세 조회(현재 : dto/TestReviewOne)
    // 상위 경로 지정 : 관리자 -> 리뷰
    @GetMapping("/{reviewId}")
    public ApiResponse<ReviewGetResponse> getReview(
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin,
            @PathVariable Long reviewId
    ) {
        validateAdmin(sessionAdmin);
        return ApiResponse.success(reviewService.getReview(reviewId));
    }

    // 리뷰 삭제
    @DeleteMapping("/{reviewId}")
    public ApiResponse<Void> delete(
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin,
            @PathVariable Long reviewId
    ) {
        validateAdmin(sessionAdmin);
        reviewService.delete(reviewId);
        return ApiResponse.success(null);
    }

    // 테스트용 리뷰 생성 코드
    @PostMapping
    public ApiResponse<ReviewCreateResponse> createReview(@Valid @RequestBody ReviewCreateRequest request) {
        return ApiResponse.success(reviewService.createReview(request));
    }

    // 관리자 조건
    private void validateAdmin(SessionAdmin sessionAdmin) {
        if (sessionAdmin == null) {
            throw new LoginRequiredException("로그인이 필요합니다.");
        }
        if (sessionAdmin.getAdminRole() == AdminRole.CS) {
            throw new AccessDeniedException("권한이 없습니다.");
        }
    }
}

