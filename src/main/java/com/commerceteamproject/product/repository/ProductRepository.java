package com.commerceteamproject.product.repository;

import com.commerceteamproject.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // 기본 최신순
    List<Product> findAllByCategoryOrderByModifiedAtDesc(String category);
    List<Product> findAllByOrderByModifiedAtDesc();

    // 가격 순서
    List<Product> findAllByOrderByPriceDesc();
    List<Product> findAllByCategoryOrderByPriceDesc(String category);
}
