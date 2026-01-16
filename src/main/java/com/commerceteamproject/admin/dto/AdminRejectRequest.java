package com.commerceteamproject.admin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminRejectRequest {
    // 거부 사유도 적어야해서 거절사유
    private String reason;
}