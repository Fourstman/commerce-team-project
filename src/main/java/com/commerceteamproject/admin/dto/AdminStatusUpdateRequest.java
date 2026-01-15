package com.commerceteamproject.admin.dto;

import com.commerceteam.admin.entity.AdminStatus;
import lombok.Getter;

@Getter
public class AdminStatusUpdateRequest {
    private AdminStatus adminStatus;
}