package com.commerceteamproject.admin.dto;

import lombok.Getter;

@Getter
public class FindOwnAdminResponse {
    private final String name;
    private final String email;
    private final String phoneNumber;

    public FindOwnAdminResponse(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
