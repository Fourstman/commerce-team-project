package com.commerceteamproject.admin.controller;

import com.commerceteamproject.admin.dto.*;
import com.commerceteamproject.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // 관리자 정보 수정(이름, 이메일, 비밀번호)
    @PutMapping("/admins/{adminId}")
    public ResponseEntity<AdminUpdateResponse> update(
            @PathVariable Long adminId,
            @RequestBody AdminUpdateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.update(adminId, request));
    }

    // 관리자 비밀번호 수정(비밀번호)
    // 아이디, 비밀번호 인증 상태에서 비밀번호 수정
    @PutMapping("/admins/{adminId}/password")
    public ResponseEntity<AdminPasswordUpdateResponse> pwUpdate(
            @PathVariable Long adminId,
            @RequestBody AdminPasswordUpdateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.pwUpdate(adminId, request));
    }

}
