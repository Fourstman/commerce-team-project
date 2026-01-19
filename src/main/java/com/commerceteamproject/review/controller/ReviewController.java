package com.commerceteamproject.review.controller;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    // 리뷰 리스트 조회
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ReviewGetResponse>>> getReviews(
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
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "리뷰 조회 성공", reviews));
    }

    // 리뷰 상세 조회(현재 : dto/TestReviewOne)
    // 상위 경로 지정 : 관리자 -> 리뷰
    @GetMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewGetResponse>> getReview(
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin,
            @PathVariable Long reviewId
    ) {
        validateAdmin(sessionAdmin);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "리뷰 상세 조회 성공", reviewService.getReview(reviewId)));

    }

    // 리뷰 삭제
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin,
            @PathVariable Long reviewId
    ) {
        validateAdmin(sessionAdmin);
        reviewService.delete(reviewId);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "리뷰 삭제 성공", null));
    }

    // 테스트용 리뷰 생성 코드
    @PostMapping
    public ResponseEntity<ApiResponse<ReviewCreateResponse>> createReview(@Valid @RequestBody ReviewCreateRequest request) {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.CREATED, "리뷰 생성 성공", reviewService.createReview(request)));
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

