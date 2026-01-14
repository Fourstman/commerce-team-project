package com.commerceteamproject.admin.repository;

import com.commerceteamproject.admin.enitity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    boolean existsByEmail(String email);
}
