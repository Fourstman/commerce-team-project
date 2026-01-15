package com.commerceteamproject.product.repository;

import com.commerceteamproject.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {


    List<Product> findAllByCategory(String category, Sort sortCondition);
}
