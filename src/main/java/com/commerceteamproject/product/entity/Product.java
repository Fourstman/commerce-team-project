package com.commerceteamproject.product.entity;

import com.commerceteamproject.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "products")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category;
    private int price;
    private int stock;
    private String description;
    private String status;

    public Product(String name, String category, int price, int stock, String description, String status) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.status = status;
    }

}
