package com.commerceteamproject.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateCustomerInformationRequest {

    private String name;
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;
    @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "전화번호 형식이 아닙니다.")
    private String phone;
}
