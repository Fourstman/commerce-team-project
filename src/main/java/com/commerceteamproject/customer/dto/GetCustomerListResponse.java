package com.commerceteamproject.customer.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetCustomerListResponse {

    private final Long id;
    private final String name;
    private final String email;
    private final String phone;
    private final String status;
    private final LocalDateTime createdAt;

    public GetCustomerListResponse(Long id, String name, String email, String phone, String status, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.createdAt = createdAt;
    }
}
