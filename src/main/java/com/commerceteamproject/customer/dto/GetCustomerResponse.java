package com.commerceteamproject.customer.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetCustomerResponse {

    private final String name;
    private final String email;
    private final String phone;
    private final String state;
    private final LocalDateTime createdAt;

    public GetCustomerResponse(String name, String email, String phone, String state, LocalDateTime createdAt) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.state = state;
        this.createdAt = createdAt;
    }
}
