package com.commerceteamproject.admin.controller;

import com.commerceteamproject.admin.dto.AdminGetResponse;
import com.commerceteamproject.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    // [관리자 상세 조회] : 특정 관리자의 상세 정보를 조회합니다.
    // `이름`, `이메일`, `전화번호`, `역할`, `상태`, `가입일`, `승인일`
    // ID 없을 경우 에러 반환
    @GetMapping("/admins/{adminId}")
    public ResponseEntity<AdminGetResponse> getOne(
            @PathVariable Long adminId
    ){
        return ResponseEntity.status(HttpStatus.OK).body(adminService.findOne(adminId));
    }
}
