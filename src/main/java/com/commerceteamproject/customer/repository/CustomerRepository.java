package com.commerceteamproject.customer.repository;

import com.commerceteamproject.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
