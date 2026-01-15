package com.commerceteamproject.admin.dto;

import com.commerceteamproject.admin.entity.AdminRole;
import lombok.Getter;

@Getter
public class AdminRoleUpdateResponse {

    private final Long adminId;
    private final AdminRole adminRole;

    public AdminRoleUpdateResponse(Long adminId, AdminRole adminRole) {
        this.adminId = adminId;
        this.adminRole = adminRole;
    }
}