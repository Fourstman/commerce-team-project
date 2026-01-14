package com.commerceteamproject.customer.repository;

import com.commerceteamproject.customer.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Page<Customer> findByNameContainingOrEmailContaining(String name, String email, Pageable pageable);
}
