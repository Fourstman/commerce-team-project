package com.commerceteamproject.product.repository;

import com.commerceteamproject.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByCategoryOrderByModifiedAtDesc(String author);
    List<Product> findAllByOrderByModifiedAtDesc();
}
