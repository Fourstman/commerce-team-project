package com.commerceteamproject.admin.dto;

import com.commerceteamproject.admin.entity.AdminRole;
import com.commerceteamproject.admin.entity.AdminStatus;
import lombok.Getter;

import java.time.LocalDateTime;

// 관리자 조회 : 이름, 이메일, 전화번호, 역할, 상태, 가입일, 승인일
@Getter
public class AdminGetResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final String phone;
    private final AdminRole adminRole;
    private final AdminStatus adminStatus;
    private final LocalDateTime createdAt;
    private final LocalDateTime approvedAt;


    public AdminGetResponse(Long id, String name, String email, String phone,
                            AdminRole adminRole, AdminStatus adminStatus,
                            LocalDateTime createdAt, LocalDateTime approvedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.adminRole = adminRole;
        this.adminStatus = adminStatus;
        this.createdAt = createdAt;
        this.approvedAt = approvedAt;
    }
}
