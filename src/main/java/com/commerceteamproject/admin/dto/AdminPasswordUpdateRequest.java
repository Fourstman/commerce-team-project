package com.commerceteamproject.admin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AdminPasswordUpdateRequest {
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;
}
