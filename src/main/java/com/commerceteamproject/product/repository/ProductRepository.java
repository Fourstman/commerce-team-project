package com.commerceteamproject.product.repository;

import com.commerceteamproject.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
