package com.commerceteamproject.admin.dto;

import com.commerceteam.admin.entity.AdminStatus;
import lombok.Getter;

@Getter
public class AdminStatusUpdateResponse {

    private final Long adminId;
    private final AdminStatus adminStatus;

    public AdminStatusUpdateResponse(Long adminId, AdminStatus adminStatus) {
        this.adminId = adminId;
        this.adminStatus = adminStatus;
    }
}