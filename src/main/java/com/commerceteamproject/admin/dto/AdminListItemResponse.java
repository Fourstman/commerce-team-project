package com.commerceteamproject.admin.dto;


import com.commerceteamproject.admin.entity.Admin;
import com.commerceteamproject.admin.entity.AdminRole;
import com.commerceteamproject.admin.entity.AdminStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
// 응답 관리자 목록
// AdminListItemResponse : ID, 이름, 이메일, 전화번호, 역할, 상태, 가입일, 승인일
// AdminListResponse : 현재 페이지, 페이지당 개수, 전체 개수, 전체 페이지 수
public class AdminListItemResponse {

    private final Long id;
    private final String name;
    private final String email;
    private final String phoneNumber;

    private final AdminRole adminRole;
    private final AdminStatus adminStatus;
    
    private final LocalDateTime createdAt;
    private final LocalDateTime approvedAt;

    public AdminListItemResponse(Admin admin) {
        this.id = admin.getId();
        this.name = admin.getName();
        this.email = admin.getEmail();
        this.phoneNumber = admin.getPhoneNumber();
        this.adminRole = admin.getAdminRole();
        this.adminStatus = admin.getAdminStatus();
        this.createdAt = admin.getCreatedAt();
        this.approvedAt = admin.getApprovedAt();
    }
}