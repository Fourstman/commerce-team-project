package com.commerceteamproject.customer.dto;

import com.commerceteamproject.customer.entity.CustomerStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateCustomerStatusRequest {

    @NotNull
    private CustomerStatus status;
}
