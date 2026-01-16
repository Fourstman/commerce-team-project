package com.commerceteamproject.product.repository;

import com.commerceteamproject.product.entity.Product;
import com.commerceteamproject.product.entity.ProductCategory;
import com.commerceteamproject.product.entity.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p " +
            "WHERE (:keyword IS NULL OR :keyword = '' OR p.name LIKE %:keyword%) " +
            "AND (:category IS NULL OR p.category = :category) " +
            "AND (:status IS NULL OR p.status = :status) ")
    Page<Product> findByKeywordAndStatus(@Param("keyword") String keyword,
                                         @Param("category") ProductCategory category,
                                         @Param("status") ProductStatus status,
                                         Pageable pageable);
}
