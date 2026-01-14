package com.commerceteamproject.admin.service;

import com.commerceteamproject.admin.dto.LoginRequest;
import com.commerceteamproject.admin.dto.SaveAdminRequest;
import com.commerceteamproject.admin.dto.SaveAdminResponse;
import com.commerceteamproject.admin.dto.SessionAdmin;
import com.commerceteamproject.admin.enitity.Admin;
import com.commerceteamproject.admin.enitity.AdminStatus;
import com.commerceteamproject.admin.repository.AdminRepository;
import com.commerceteamproject.config.PasswordEncoder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SaveAdminResponse save(SaveAdminRequest request) {
        boolean existence = adminRepository.existsByEmail(request.getEmail());
        if (existence) {
            throw new IllegalStateException("중복된 이메일이 존재합니다.");
        } else if (request.getAdminRole() == null) {
            throw new IllegalStateException("관리자 역할을 선택해주세요.");
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Admin admin = new Admin(
                request.getName(),
                request.getEmail(),
                encodedPassword,
                request.getPhoneNumber(),
                request.getAdminRole()
        );
        Admin savedAdmin = adminRepository.save(admin);
        return new SaveAdminResponse(
                savedAdmin.getName(),
                savedAdmin.getEmail(),
                savedAdmin.getPhoneNumber(),
                savedAdmin.getAdminRole(),
                savedAdmin.getAdminStatus(),
                savedAdmin.getCreatedAt()
        );
    }

    @Transactional
    public SessionAdmin login(@Valid LoginRequest request) {
        Admin admin = adminRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new IllegalStateException("등록되지 않은 이메일입니다.")
        );
        boolean isPasswordMatch = passwordEncoder.matches(request.getPassword(), admin.getPassword());
        if (!isPasswordMatch) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
        switch (admin.getAdminStatus()) {
            case INACTIVATION -> throw new IllegalStateException("비활성화된 계정입니다.");
            case SUSPENSION -> throw new IllegalStateException("정지된 계정입니다.");
            case PENDING -> throw new IllegalStateException("승인대기 상태입니다. 슈퍼관리자의 승인이 필요합니다.");
            case REJECTION -> throw new IllegalStateException("승인거부된 계정입니다.");
        }
        return new SessionAdmin(
                admin.getId(),
                admin.getEmail(),
                admin.getAdminRole()
        );
    }
}
