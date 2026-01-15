package com.commerceteamproject.admin.dto;

import com.commerceteam.admin.entity.Admin;
import com.commerceteam.admin.entity.AdminRole;
import com.commerceteam.admin.repository.AdminRepository;
import com.commerceteam.config.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements ApplicationRunner {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {

        // 이미 SUPER가 있으면 아무것도 안 함
        boolean existsSuper = adminRepository.existsByAdminRole(AdminRole.SUPER);
        if (existsSuper) {
            return;
        }

        Admin superAdmin = new Admin(
                "슈퍼관리자",
                "super@admin.com",
                passwordEncoder.encode("12345678"),
                "010-1111-2222",
                AdminRole.SUPER
        );

        // 슈퍼관리자는 즉시 활성화.
        // 다만, approve보다 위에 있으면 활성화 -> 승인대기 순서 꼬임. 아래로 내리기
        superAdmin.approve(); // approvedAt까지 세팅
        // superAdmin.changeStatus(AdminStatus.ACTIVATION); // 됨

        adminRepository.save(superAdmin);
    }
}