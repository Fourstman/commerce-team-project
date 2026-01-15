package com.commerceteamproject.admin.dto;

import lombok.Getter;

@Getter
public class AdminPasswordUpdateResponse {
    private final String password;

    public AdminPasswordUpdateResponse(String password) {

        this.password = password;
    }
}
