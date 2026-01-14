package com.commerceteamproject.admin.service;

import com.commerceteamproject.admin.dto.SaveAdminRequest;
import com.commerceteamproject.admin.dto.SaveAdminResponse;
import com.commerceteamproject.admin.enitity.Admin;
import com.commerceteamproject.admin.repository.AdminRepository;
import com.commerceteamproject.config.PasswordEncoder;
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
}
