package com.commerceteamproject.admin.controller;

import com.commerceteamproject.admin.dto.*;
import com.commerceteamproject.admin.entity.AdminRole;
import com.commerceteamproject.admin.entity.AdminStatus;
import com.commerceteamproject.admin.service.AdminService;
import com.commerceteamproject.common.dto.ApiResponse;
import com.commerceteamproject.common.dto.PageResponse;
import com.commerceteamproject.common.exception.AccessDeniedException;
import com.commerceteamproject.common.exception.LoginRequiredException;
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
    public ResponseEntity<ApiResponse<SaveAdminResponse>> saveAdmin(
            @Valid @RequestBody SaveAdminRequest request) {
        SaveAdminResponse result = adminService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(HttpStatus.CREATED, "성공적으로 회원가입 되었습니다.", result));
    }

    // 로그인
    @PostMapping("/admins/login")
    public ResponseEntity<ApiResponse<Void>> login(
            @Valid @RequestBody LoginRequest request, HttpSession session) {
        SessionAdmin sessionAdmin = adminService.login(request);
        session.setAttribute("loginAdmin", sessionAdmin);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(HttpStatus.OK, "성공적으로 로그인 되었습니다", null));
    }

    // 로그아웃
    @PostMapping("/admins/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin, HttpSession session) {
        if (sessionAdmin == null) {
            throw new LoginRequiredException("로그인이 필요합니다.");
        }
        session.invalidate();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(HttpStatus.OK, "성공적으로 로그아웃 되었습니다", null));
    }
// ============================================================ //

    // 관리자 리스트 조회(슈퍼관리자)
    @GetMapping("/admins")
    public ResponseEntity<ApiResponse<PageResponse<AdminListItemResponse>>> getAdmins(
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin loginAdmin,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) AdminRole adminRole,
            @RequestParam(required = false) AdminStatus adminStatus,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        SUPERvalidateAdmin(loginAdmin);
        PageResponse<AdminListItemResponse> adminsList = adminService.findAdmins(
                keyword, adminRole, adminStatus, page, size, sortBy, direction
        );
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "관리자 리스트 조회에 성공했습니다", adminsList));
    }

    // [관리자 상세 조회] : 특정 관리자의 상세 정보를 조회합니다.
    // `이름`, `이메일`, `전화번호`, `역할`, `상태`, `가입일`, `승인일`
    // ID 없을 경우 에러 반환
    @GetMapping("/admins/{adminId}")
    public ResponseEntity<ApiResponse<AdminGetResponse>> getOne(
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin loginAdmin,
            @PathVariable Long adminId
    ){
        SUPERvalidateAdmin(loginAdmin);
        AdminGetResponse result = adminService.findOne(adminId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(HttpStatus.OK, "관리자 상세 조회에 성공했습니다", result));
    }

    // 관리자 정보 수정(이름, 이메일, 비밀번호)
    @PutMapping("/admins/{adminId}")
    public ResponseEntity<ApiResponse<AdminUpdateResponse>> update(
            @PathVariable Long adminId,
            @Valid @RequestBody AdminUpdateRequest request
    ) {
        AdminUpdateResponse result = adminService.update(adminId, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(HttpStatus.OK, "관리자의 정보를 수정했습니다", result));
    }


    // 관리자 역할 변경
    @PutMapping("/admins/{adminId}/roles")
    public ResponseEntity<ApiResponse<AdminRoleUpdateResponse>> changeRole(
            @PathVariable Long adminId,
            @Valid @RequestBody AdminRoleUpdateRequest request
    ) {
        AdminRoleUpdateResponse result = adminService.changeRole(adminId, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(HttpStatus.OK, "관리자의 역할을 변경했습니다", result));
    }

    // 관리자 상태 변경
    @PutMapping("/admins/{adminId}/statuses")
    public ResponseEntity<ApiResponse<AdminStatusUpdateResponse>> changeStatus(
            @PathVariable Long adminId,
            @Valid @RequestBody AdminStatusUpdateRequest request,
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin loginAdmin,
            HttpSession httpSession
    ) {
        SUPERvalidateAdmin(loginAdmin);
        AdminStatusUpdateResponse result = adminService.changeStatus(adminId, request);

        // 비활성화 시 세션 무효화
        if (request.getAdminStatus() == AdminStatus.INACTIVATION) {
            httpSession.invalidate();
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(HttpStatus.OK, "관리자의 상태를 변경했습니다", result));
    }

    // 관리자 승인/거부
    //      관리자 승인
    @PutMapping("/admins/{adminId}/approve")
    public ResponseEntity<ApiResponse<AdminApproveResponse>> approve(
            @PathVariable Long adminId,
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin loginAdmin
    ) {

        // 일반 관리자(RUN, CS)는 거부 권한 없음
        SUPERvalidateAdmin(loginAdmin);
        AdminApproveResponse result = adminService.approve(adminId);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "관리자를 승인했습니다", result));
    }

    //      관리자 거부
    @PutMapping("/admins/{adminId}/rejects")
    public ResponseEntity<ApiResponse<AdminRejectResponse>> reject(
            @PathVariable Long adminId,
            @Valid @RequestBody AdminRejectRequest request,
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin loginAdmin
    ) {

        // 일반 관리자(RUN, CS)는 거부 권한 없음
        SUPERvalidateAdmin(loginAdmin);

        AdminRejectResponse result = adminService.reject(adminId, request);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "관리자를 거부했습니다", result));
    }

    // 관리자 삭제 : 특정 관리자를 탈퇴(삭제)시킵니다.
    @DeleteMapping("/admins/{adminId}/deletes")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long adminId,
            // 슈퍼 관리자 인증(비밀번호) 후 삭제
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin loginAdmin,
            HttpSession httpSession
    ) {
        SUPERvalidateAdmin(loginAdmin);
        adminService.delete(adminId);
        httpSession.invalidate(); // 삭제 후 세션 무효화
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "관리자를 삭제했습니다.", null));
    }

    // 관리자 비밀번호 수정(비밀번호)
    // 아이디, 비밀번호 인증 상태에서 비밀번호 수정
    @PutMapping("/admins/{adminId}/password")
    public ResponseEntity<ApiResponse<AdminPasswordUpdateResponse>> pwUpdate(
          @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin loginAdmin,
          @Valid @RequestBody AdminPasswordUpdateRequest request
    ) {
        if (loginAdmin == null) {
                throw new LoginRequiredException("로그인이 필요합니다.");
        }

        adminService.pwUpdate(loginAdmin, request);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK, "비밀번호를 수정했습니다.", null));
    }

    // 관리자 프로필 조회
    @GetMapping("/admins/profiles")
    public ResponseEntity<ApiResponse<FindOwnAdminResponse>> findOwnAdmin(
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin) {
        if (sessionAdmin == null) {
            throw new LoginRequiredException("로그인이 필요합니다.");
        }
        FindOwnAdminResponse result = adminService.findOwn(sessionAdmin);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(HttpStatus.OK, "관리자 프로필 조회에 성공했습니다.", result));
    }

    // 관리자 프로필 수정
    @PutMapping("/admins/profiles")
    public ResponseEntity<ApiResponse<UpdateOwnAdminResponse>> updateOwnAdmin(
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin sessionAdmin,
            @RequestBody UpdateOwnAdminRequest request) {
        if (sessionAdmin == null) {
            throw new LoginRequiredException("로그인이 필요합니다.");
        }
        UpdateOwnAdminResponse result = adminService.updateOwn(sessionAdmin, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(HttpStatus.OK, "관리자 프로필을 수정했습니다.", result));
    }

    // CS 제외 관리자 조건
    private void CSNotvalidateAdmin(SessionAdmin sessionAdmin) {
        if (sessionAdmin == null) {
            throw new LoginRequiredException("로그인이 필요합니다.");
        }
        if (sessionAdmin.getAdminRole() == AdminRole.CS) {
            throw new AccessDeniedException("권한이 없습니다.");
        }
    }
    // SUPER 관리자 조건
    private void SUPERvalidateAdmin(SessionAdmin sessionAdmin) {
        if (sessionAdmin == null) {
            throw new LoginRequiredException("로그인이 필요합니다.");
        }
        if (sessionAdmin.getAdminRole() != AdminRole.SUPER) {
            throw new AccessDeniedException("슈퍼관리자만 가능합니다..");
        }
    }

}
