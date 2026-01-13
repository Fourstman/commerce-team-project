package com.commerceteamproject.admin.dto;

import lombok.Getter;

@Getter
public class AdminUpdateRequest {
    private String name;
    private String email;
    private String password;
}
