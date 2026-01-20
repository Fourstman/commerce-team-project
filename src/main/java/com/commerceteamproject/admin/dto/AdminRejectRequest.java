package com.commerceteamproject.admin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminRejectRequest {
    // 거부 사유도 적어야해서 거절사유
    @NotBlank(message = "거부 사유는 반드시 필요합니다.")
    private String reason;
}