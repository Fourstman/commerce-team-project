package com.commerceteamproject.order.repository;

import com.commerceteamproject.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
