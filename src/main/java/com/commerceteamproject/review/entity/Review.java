package com.commerceteamproject.review.entity;

import com.commerceteamproject.common.entity.BaseEntity;
import com.commerceteamproject.customer.entity.Customer;
import com.commerceteamproject.order.entity.Order;
import com.commerceteamproject.product.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "reviews")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // product : [상품명] 필요
    // customer : [고객 ID(고유식별자)], [고객명], [고객 이메일] 필요
    // 이 둘 모두 Order에서 씀

    // 리뷰 : [평점, 리뷰 내용, 작성일]
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false, updatable = false)
    private Order order;

    // 평점
    private int rating;

    // 리뷰 내용
    @Column(length = 1000)
    private String content;


    // 기본 : 고유 식별자, 주문번호, 고객명, 상품명, 평점, 리뷰 내용, 작성일
    public Review(Order order, int rating, String content) {
        this.order = order;
        this.rating = rating;
        this.content = content;
    }
    // review.getOrder().getOrderNumber();           // 주문번호
    // review.getOrder().getCustomer().getId();      // 고유 식별자
    // review.getOrder().getCustomer().getName();    // 고객명
    // review.getOrder().getCustomer().getEmail();   // 고객 이메일
    // review.getOrder().getProduct().getName();     // 상품명
    // review.getRating();                           // 평점
    // review.getContent();                          // 리뷰 내용
    // review.getCreatedAt();                        // 작성일(BaseEntity)
}
