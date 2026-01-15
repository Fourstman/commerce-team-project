package com.commerceteamproject.customer.dto;

import lombok.Getter;

@Getter
public class UpdateCustomerStatusResponse {

    private final String name;
    private final String status;

    public UpdateCustomerStatusResponse(String name, String status) {
        this.name = name;
        this.status = status;
    }
}
