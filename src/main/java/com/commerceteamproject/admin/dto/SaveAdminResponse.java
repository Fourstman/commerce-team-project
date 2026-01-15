package com.commerceteamproject.admin.dto;


import com.commerceteamproject.admin.entity.AdminRole;
import com.commerceteamproject.admin.entity.AdminStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SaveAdminResponse {
    private final String name;
    private final String email;
    private final String phoneNumber;
    private final AdminRole adminRole;
    private final AdminStatus adminStatus;
    private final LocalDateTime createdAt;

    public SaveAdminResponse(String name, String email, String phoneNumber,
                             AdminRole adminRole, AdminStatus adminStatus, LocalDateTime createdAt) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.adminRole = adminRole;
        this.adminStatus = adminStatus;
        this.createdAt = createdAt;
    }
}