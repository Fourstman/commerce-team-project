package com.commerceteamproject.admin.repository;

import com.commerceteamproject.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long>{
}
