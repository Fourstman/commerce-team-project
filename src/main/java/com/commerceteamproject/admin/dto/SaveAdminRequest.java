package com.commerceteamproject.admin.dto;

import com.commerceteamproject.admin.enitity.AdminRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SaveAdminRequest {
    @NotBlank
    private String name;
    @NotBlank @Email(message = "이메일 형식이 아닙니다.")
    private String email;
    @NotBlank @Size(min = 8, max = 20, message = "비밀번호는 8~20자 사이여야 합니다.")
    private String password;
    @NotBlank @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "전화번호 형식이 아닙니다.")
    private String phoneNumber;
    private AdminRole adminRole;
}
