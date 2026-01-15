package com.commerceteamproject.admin.repository;

import com.commerceteamproject.admin.entity.Admin;
import com.commerceteamproject.admin.entity.AdminRole;
import com.commerceteamproject.admin.entity.AdminStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long>{
    boolean existsByEmail(String email);

    Optional<Admin> findByEmail(String email);

    // 슈퍼관리자용
    boolean existsByAdminRole(AdminRole adminRole);

    // 관리자 리스트 조회용
    @Query("""
        SELECT a
        FROM Admin a
        WHERE 1=1
          AND (:keyword IS NULL
               OR a.name LIKE %:keyword%
               OR a.email LIKE %:keyword%)
          AND (:role IS NULL OR a.adminRole = :role)
          AND (:status IS NULL OR a.adminStatus = :status)
    """)
    Page<Admin> findAdminList(
            @Param("keyword") String keyword,
            @Param("role") AdminRole role,
            @Param("status") AdminStatus status,
            Pageable pageable
    );
}

