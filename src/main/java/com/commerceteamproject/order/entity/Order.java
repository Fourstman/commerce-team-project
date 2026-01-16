package com.commerceteamproject.order.entity;

import com.commerceteamproject.admin.entity.Admin;
import com.commerceteamproject.common.entity.BaseEntity;
import com.commerceteamproject.customer.entity.Customer;
import com.commerceteamproject.product.entity.Product;
import com.github.f4b6a3.tsid.TsidCreator;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at is NULL")
@SQLDelete(sql = "UPDATE orders SET deleted_at = NOW() WHERE id = ?")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NaturalId
    @Column(nullable = false, unique = true, updatable = false)
    private String orderNumber; // 주문 번호 13자리 문자열 생성
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    private int quantity;
    private int amount;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;
    private OrderStatus orderStatus;

    private LocalDateTime deletedAt;

    public Order(Customer customer, Product product, int quantity, int amount, Admin admin) {
        this.orderNumber = TsidCreator.getTsid().toString();
        this.customer= customer;
        this.product = product;
        this.quantity = quantity;
        this.amount = amount;
        this.admin = admin;
        this.orderStatus = OrderStatus.PREPARE;
    }

    public void updateOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}