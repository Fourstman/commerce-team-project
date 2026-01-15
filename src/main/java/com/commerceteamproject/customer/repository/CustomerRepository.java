package com.commerceteamproject.customer.repository;

import com.commerceteamproject.customer.entity.Customer;
import com.commerceteamproject.customer.entity.CustomerState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT c FROM Customer c " +
            "WHERE (:keyword IS NULL OR :keyword = '' OR c.name LIKE %:keyword% OR c.email LIKE %:keyword%) " +
            "AND (:state IS NULL OR c.state = :state)")
    Page<Customer> findByKeywordAndState(@Param("keyword") String keyword,
                                         @Param("state") CustomerState state,
                                         Pageable pageable);
}
