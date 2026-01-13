package com.commerceteamproject.admin.service;

import com.commerceteamproject.admin.dto.*;
import com.commerceteamproject.admin.entity.Admin;
import com.commerceteamproject.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    // 관리자 상세 조회
    // `이름`, `이메일`, `전화번호`, `역할`, `상태`, `가입일`, `승인일`
    @Transactional(readOnly = true)
    public AdminGetResponse findOne(Long userId) {
        Admin admin = adminRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("해당하는 관리자의 ID가 없음")
        );
        return new AdminGetResponse(
                admin.getId(),
                admin.getName(),
                admin.getEmail(),
                admin.getPhoneNumber(),
                admin.getRole(),
                admin.getStatus(),
                admin.getCreatedAt(),
                admin.getModifiedAt()
        );
    }

    // 관리자 정보 수정(이름, 이메일, 비밀번호)
    @Transactional
    public AdminUpdateResponse update(Long adminId, AdminUpdateRequest request) {
        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new IllegalStateException("수정할 관리자가 없음")
        );
        admin.update(request.getName(), request.getEmail(), request.getPassword());
        return new AdminUpdateResponse(
                admin.getId(),
                admin.getName(),
                admin.getEmail(),
                admin.getPassword()
        );
    }

    // 관리자 비밀번호 수정(이름, 이메일, 비밀번호)
    @Transactional
    public AdminPasswordUpdateResponse pwUpdate(Long adminId, AdminPasswordUpdateRequest request) {
        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new IllegalStateException("비밀번호 수정할 관리자가 없음?(이런 경우가 나올 수 있나)")
        );
        admin.pwUpdate(request.getPassword());
        return new AdminPasswordUpdateResponse(
                admin.getPassword()
        );
    }

    // 관리자 삭제 : 특정 관리자를 탈퇴(삭제)시킵니다.
    public void delete(Long adminId, AdminDeleteRequest request) {
        Admin schedule = adminRepository.findById(adminId).orElseThrow(
                () -> new IllegalStateException("해당 관리자 ID가 없음")
        );
        // 비밀번호 검증
        if (!schedule.getPassword().equals(request.getPassword())) {
            throw new IllegalStateException("비밀번호 불일치");
        }
        adminRepository.deleteById(adminId);
    }
}
