package com.commerceteamproject.customer.dto;

import lombok.Getter;

@Getter
public class UpdateCustomerStateResponse {

    private final String name;
    private final String state;

    public UpdateCustomerStateResponse(String name, String state) {
        this.name = name;
        this.state = state;
    }
}
