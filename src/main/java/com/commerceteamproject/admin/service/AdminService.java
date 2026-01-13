package com.commerceteamproject.admin.service;

import com.commerceteamproject.admin.dto.AdminGetResponse;
import com.commerceteamproject.admin.dto.AdminUpdateRequest;
import com.commerceteamproject.admin.dto.AdminUpdateResponse;
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


}
