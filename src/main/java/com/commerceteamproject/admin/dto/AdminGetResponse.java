package com.commerceteamproject.admin.dto;

import lombok.Getter;

import java.time.LocalDateTime;

// 관리자 조회 : 이름, 이메일, 전화번호, 역할, 상태, 가입일, 승인일
@Getter
public class AdminGetResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final String phone;
    private final String role;
    private final String status;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;


    public AdminGetResponse(Long id, String name, String email, String phone, String role, String status, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
