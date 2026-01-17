package com.commerceteamproject.customer.dto;

import com.commerceteamproject.customer.entity.CustomerStatus;
import lombok.Getter;

@Getter
public class UpdateCustomerStatusResponse {

    private final String name;
    private final CustomerStatus status;

    public UpdateCustomerStatusResponse(String name, CustomerStatus status) {
        this.name = name;
        this.status = status;
    }
}
