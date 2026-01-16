package com.commerceteamproject.admin.dto;

import lombok.Getter;

@Getter
public class UpdateOwnAdminResponse {
    private final String name;
    private final String email;
    private final String phoneNumber;

    public UpdateOwnAdminResponse(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
