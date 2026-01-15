package com.commerceteamproject.admin.dto;

import com.commerceteam.admin.entity.AdminStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AdminRejectResponse {

    private final Long adminId;
    private final AdminStatus adminStatus;
    private final String rejectReason;
    private final LocalDateTime rejectedAt;

    public AdminRejectResponse(Long adminId, AdminStatus adminStatus,
            String rejectReason, LocalDateTime rejectedAt) {
        this.adminId = adminId;
        this.adminStatus = adminStatus;
        this.rejectReason = rejectReason; // 거부 사유
        this.rejectedAt = rejectedAt;
    }
}