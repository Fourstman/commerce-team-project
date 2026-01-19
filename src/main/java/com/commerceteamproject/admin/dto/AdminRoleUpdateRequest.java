package com.commerceteamproject.admin.dto;

import com.commerceteamproject.admin.entity.AdminRole;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AdminRoleUpdateRequest {
    @NotBlank(message = "관리자 역할은 필수")
    private AdminRole adminRole;
}