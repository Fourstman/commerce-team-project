package com.commerceteamproject.admin.controller;

import com.commerceteamproject.admin.dto.LoginRequest;
import com.commerceteamproject.admin.dto.SaveAdminRequest;
import com.commerceteamproject.admin.dto.SaveAdminResponse;
import com.commerceteamproject.admin.dto.SessionAdmin;
import com.commerceteamproject.admin.service.AdminService;
import jakarta.servlet.http.HttpSession;
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
    public ResponseEntity<SaveAdminResponse> saveAdmin(
            @Valid @RequestBody SaveAdminRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.save(request));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Valid @RequestBody LoginRequest request, HttpSession session) {
        SessionAdmin sessionAdmin = adminService.login(request);
        session.setAttribute("loginAdmin", sessionAdmin);
        return ResponseEntity.status(HttpStatus.OK).body("성공적으로 로그인되었습니다");
    }
}
