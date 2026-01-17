package com.commerceteamproject.customer.dto;

import com.commerceteamproject.customer.entity.CustomerStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetCustomerResponse {

    private final String name;
    private final String email;
    private final String phone;
    private final CustomerStatus status;
    private final int totalOrderCount;
    private final int totalOrderAmount;
    private final LocalDateTime createdAt;

    public GetCustomerResponse(String name, String email, String phone, CustomerStatus status, int totalOrderCount, int totalOrderAmount, LocalDateTime createdAt) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.totalOrderCount = totalOrderCount;
        this.totalOrderAmount = totalOrderAmount;
        this.createdAt = createdAt;
    }
}
