package com.commerceteamproject.customer.dto;

import com.commerceteamproject.customer.entity.CustomerState;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateCustomerStateRequest {

    @NotNull
    private CustomerState state;
}
