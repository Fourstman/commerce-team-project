package com.commerceteamproject.product.entity;

import com.commerceteamproject.admin.entity.Admin;
import com.commerceteamproject.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;


@Entity
@Getter
@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at is NULL")
@SQLDelete(sql = "UPDATE products SET deleted_at = NOW() WHERE id = ?")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;
    private String name;
    @Enumerated(EnumType.STRING)
    private ProductCategory category;
    private int price;
    private int stock;
    private String description;
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    private LocalDateTime deletedAt;

    public Product(Admin admin, String name, ProductCategory category, int price, int stock,
                   String description, ProductStatus status ) {
        this.admin = admin;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.status = status;
    }

    public void updateInfo(String name, ProductCategory category, int price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public void updateStock(int stock) {
        this.stock = stock;
    }

    public void changeStatus(ProductStatus status) {
        this.status = status;
    }

}
