package com.commerceteamproject.admin.controller;

import com.commerceteamproject.admin.dto.SaveAdminRequest;
import com.commerceteamproject.admin.enitity.Admin;
import com.commerceteamproject.admin.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> saveAdmin(
            @Valid @RequestBody SaveAdminRequest request) {
        Admin admin = adminService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다.");
    }
}
