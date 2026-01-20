package com.commerceteamproject.customer.dto;

import lombok.Getter;

@Getter
public class UpdateCustomerInformationResponse {

    private final String name;
    private final String email;
    private final String phone;

    public UpdateCustomerInformationResponse(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
}
