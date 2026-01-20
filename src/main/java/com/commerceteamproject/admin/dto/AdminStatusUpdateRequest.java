package com.commerceteamproject.admin.dto;

import com.commerceteamproject.admin.entity.AdminStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AdminStatusUpdateRequest {
    @NotBlank(message = "관리자 상태 필수")
    private AdminStatus adminStatus;
}