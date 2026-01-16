package com.commerceteamproject.admin.controller;

import com.commerceteamproject.admin.dto.*;
import com.commerceteamproject.admin.entity.AdminRole;
import com.commerceteamproject.admin.service.AdminService;
import com.commerceteamproject.common.dto.PageResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

// ================================================== //
    // 회원가입
    @PostMapping("/admins/signup")
    public ResponseEntity<SaveAdminResponse> saveAdmin(
            @Valid @RequestBody SaveAdminRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.save(request));
    }

    // 로그인
    @PostMapping("/admins/login")
    public ResponseEntity<Void> login(
            @Valid @RequestBody LoginRequest request, HttpSession session) {
        SessionAdmin sessionAdmin = adminService.login(request);
        session.setAttribute("loginAdmin", sessionAdmin);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 로그아웃
    @PostMapping("/admins/logout")
    public ResponseEntity<Void> logout(
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin, HttpSession session) {
        if (sessionAdmin == null) {
            return ResponseEntity.badRequest().build();
        }
        session.invalidate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
// ============================================================ //

    // 관리자 리스트 조회
    @GetMapping("/admins")
    public ResponseEntity<PageResponse<AdminListItemResponse>> getAdmins(
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin loginAdmin,
            AdminListRequest request
    ) {
        return ResponseEntity.ok(adminService.findAdmins(loginAdmin, request));
    }

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
            @Valid @RequestBody AdminUpdateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.update(adminId, request));
    }


    // 관리자 역할 변경
    @PutMapping("/admins/{adminId}/roles")
    public ResponseEntity<AdminRoleUpdateResponse> changeRole(
            @PathVariable Long adminId,
            @Valid @RequestBody AdminRoleUpdateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.changeRole(adminId, request));
    }

    // 관리자 상태 변경
    @PutMapping("/admins/{adminId}/statuses")
    public ResponseEntity<AdminStatusUpdateResponse> changeStatus(
            @PathVariable Long adminId,
            @Valid @RequestBody AdminStatusUpdateRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(adminService.changeStatus(adminId, request));
    }

    // 관리자 승인/거부
    //      관리자 승인
    @PutMapping("/admins/{adminId}/approve")
    public ResponseEntity<AdminApproveResponse> approve(
            @PathVariable Long adminId,
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin loginAdmin
    ) {
        // 로그인 안 함
        if (loginAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    
        // 일반관리자(RUN, CS)는 권한 없음
        if (loginAdmin.getAdminRole() != AdminRole.SUPER) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(null); // "접근 권한이 없습니다"
        }
    
        return ResponseEntity.ok(adminService.approve(adminId));
    }

    //      관리자 거부
    @PutMapping("/admins/{adminId}/rejects")
    public ResponseEntity<AdminRejectResponse> reject(
            @PathVariable Long adminId,
            @Valid @RequestBody AdminRejectRequest request,
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin loginAdmin
    ) {
        // 로그인 안 함
        if (loginAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 일반 관리자(RUN, CS)는 거부 권한 없음
        if (loginAdmin.getAdminRole() != AdminRole.SUPER) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(adminService.reject(adminId, request));
    }






    // 관리자 삭제 : 특정 관리자를 탈퇴(삭제)시킵니다.
    @DeleteMapping("/admins/{adminId}/deletes")
    public ResponseEntity<Void> delete(
            @PathVariable Long adminId,
            // 슈퍼 관리자 인증(비밀번호) 후 삭제
            @Valid @RequestBody AdminDeleteRequest request
    ) {
        adminService.delete(adminId, request);
        return ResponseEntity.noContent().build();
    }

    // 관리자 비밀번호 수정(비밀번호)
    // 아이디, 비밀번호 인증 상태에서 비밀번호 수정
    @PutMapping("/admins/{adminId}/password")
    public ResponseEntity<AdminPasswordUpdateResponse> pwUpdate(
            @PathVariable Long adminId,
            @Valid @RequestBody AdminPasswordUpdateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(adminService.pwUpdate(adminId, request));
    }
}
