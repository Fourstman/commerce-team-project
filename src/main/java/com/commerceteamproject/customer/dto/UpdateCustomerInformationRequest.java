package com.commerceteamproject.customer.dto;

import lombok.Getter;

@Getter
public class UpdateCustomerInformationRequest {

    private String name;
    private String email;
    private String phone;
}
