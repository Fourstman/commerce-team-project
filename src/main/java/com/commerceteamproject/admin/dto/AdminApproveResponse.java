package com.commerceteamproject.admin.dto;

import com.commerceteamproject.admin.entity.AdminStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AdminApproveResponse {
    private final Long adminId;
    private final AdminStatus adminStatus;
    private final LocalDateTime approvedAt;

    public AdminApproveResponse(Long adminId, AdminStatus adminStatus, LocalDateTime approvedAt) {
        this.adminId = adminId;
        this.adminStatus = adminStatus;
        this.approvedAt = approvedAt;
    }
}