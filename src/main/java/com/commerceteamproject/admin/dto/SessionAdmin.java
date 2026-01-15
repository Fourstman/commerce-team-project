package com.commerceteamproject.admin.dto;

import com.commerceteamproject.admin.entity.AdminRole;
import lombok.Getter;

@Getter
public class SessionAdmin {
    private final Long id;
    private final String email;
    private final AdminRole adminRole;

    public SessionAdmin(Long id, String email, AdminRole adminRole) {
        this.id = id;
        this.email = email;
        this.adminRole = adminRole;
    }
}
