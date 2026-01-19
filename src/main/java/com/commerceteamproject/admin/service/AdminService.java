package com.commerceteamproject.admin.service;

import com.commerceteamproject.admin.dto.*;
import com.commerceteamproject.admin.entity.Admin;
import com.commerceteamproject.admin.entity.AdminRole;
import com.commerceteamproject.admin.entity.AdminStatus;
import com.commerceteamproject.admin.exception.*;
import com.commerceteamproject.admin.repository.AdminRepository;
import com.commerceteamproject.common.dto.PageResponse;
import com.commerceteamproject.admin.exception.AdminStatusNotAllowedException;
import com.commerceteamproject.common.exception.LoginRequiredException;
import com.commerceteamproject.config.PasswordEncoder;
import com.commerceteamproject.review.dto.ReviewGetResponse;
import com.commerceteamproject.review.entity.Review;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Transactional
    public SaveAdminResponse save(SaveAdminRequest request) {
        boolean existence = adminRepository.existsByEmail(request.getEmail());
        if (existence) {
            throw new DuplicateEmailException("중복된 이메일이 존재합니다.");
        } else if (request.getAdminRole() == null) {
            throw new AdminRoleIsNullException("관리자 역할을 선택해주세요.");
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

    // 로그인
    @Transactional
    public SessionAdmin login(@Valid LoginRequest request) {
        Admin admin = adminRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new EmailNotFoundException("등록되지 않은 이메일입니다.")
        );
        boolean isPasswordMatch = passwordEncoder.matches(request.getPassword(), admin.getPassword());
        if (!isPasswordMatch) {
            throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
        }
        switch (admin.getAdminStatus()) {
            case INACTIVATION -> throw new AdminStatusNotAllowedException("비활성화된 계정입니다.");
            case SUSPENSION -> throw new AdminStatusNotAllowedException("정지된 계정입니다.");
            case PENDING -> throw new AdminStatusNotAllowedException("승인대기 상태입니다. 슈퍼관리자의 승인이 필요합니다.");
            case REJECTION -> throw new AdminStatusNotAllowedException("승인거부된 계정입니다.");
        }
        return new SessionAdmin(
                admin.getId(),
                admin.getEmail(),
                admin.getAdminRole()
        );
    }

    // 관리자 리스트 조회(쿼리 파라미터 사용)
    @Transactional(readOnly = true)
    public PageResponse<AdminListItemResponse> findAdmins(
            String keyword,
            AdminRole adminRole,
            AdminStatus adminStatus,
            int page,
            int size,
            String sortBy,
            String direction
    ) {
        Pageable pageable = PageRequest.of(
                page - 1,  // 1-based -> 0-based
                size,
                Sort.by(Sort.Direction.fromString(direction), sortBy)
        );

        Page<Admin> reviews = adminRepository.findAdminList(keyword, adminRole, adminStatus, pageable);
        Page<AdminListItemResponse> dtoPage = reviews.map(AdminListItemResponse::new);

        return new PageResponse<>(dtoPage);
    }

    // 관리자 상세 조회
    // `이름`, `이메일`, `전화번호`, `역할`, `상태`, `가입일`, `승인일`
    @Transactional(readOnly = true)
    public AdminGetResponse findOne(Long userId) {
        Admin admin = adminRepository.findById(userId).orElseThrow(
                () -> new AdminNotFoundException("해당하는 관리자의 ID가 없음")
        );
        return new AdminGetResponse(
                admin.getId(),
                admin.getName(),
                admin.getEmail(),
                admin.getPhoneNumber(),
                admin.getAdminRole(),
                admin.getAdminStatus(),
                admin.getCreatedAt(),
                admin.getApprovedAt()
        );
    }

    // 관리자 정보 수정(이름, 이메일, 비밀번호 아님. 전화번호)
    @Transactional
    public AdminUpdateResponse update(Long adminId, AdminUpdateRequest request) {
        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new AdminNotFoundException("수정할 관리자가 없음")
        );
        admin.update(request.getName(), request.getEmail(), request.getPhoneNumber());
        return new AdminUpdateResponse(
                admin.getId(),
                admin.getName(),
                admin.getEmail(),
                admin.getPhoneNumber()
        );
    }

    // 관리자 역할 변경
    @Transactional
    public AdminRoleUpdateResponse changeRole(Long adminId, AdminRoleUpdateRequest request) {
        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new AdminNotFoundException("역할을 변경할 관리자가 없습니다.")
        );
        admin.changeRole(request.getAdminRole());
        return new AdminRoleUpdateResponse(
                admin.getId(),
                admin.getAdminRole()
        );
    }

    // 관리자 상태 변경
    @Transactional
    public AdminStatusUpdateResponse changeStatus(Long adminId, AdminStatusUpdateRequest request) {
        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new AdminNotFoundException("상태를 변경할 관리자가 없습니다.")
        );
        admin.changeStatus(request.getAdminStatus());
        return new AdminStatusUpdateResponse(
                admin.getId(),
                admin.getAdminStatus()
        );
    }

    // 관리자 승인/거부
    @Transactional
    public AdminApproveResponse approve(Long adminId) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new AdminNotFoundException("승인할 관리자가 없습니다."));

        admin.approve();

        return new AdminApproveResponse(
                admin.getId(),
                admin.getAdminStatus(),
                admin.getApprovedAt()
        );
    }


    // 관리자 거부
    @Transactional
    public AdminRejectResponse reject(Long adminId, AdminRejectRequest request) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new AdminNotFoundException("거부할 관리자가 없습니다."));

        admin.reject(request.getReason());

        return new AdminRejectResponse(
                admin.getId(),
                admin.getAdminStatus(),
                admin.getRejectReason(),
                admin.getRejectedAt()
        );
    }

    // 관리자 삭제 : 특정 관리자를 탈퇴(삭제)시킵니다.
    @Transactional
    public void delete(Long adminId) {
        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new AdminNotFoundException("해당 관리자 ID가 없음")
        );
        adminRepository.deleteById(adminId);
    }

    // 관리자 비밀번호 수정(이름, 이메일, 비밀번호)
    @Transactional
    public void pwUpdate(SessionAdmin sessionAdmin, AdminPasswordUpdateRequest request) {
        Admin admin = adminRepository.findById(sessionAdmin.getId())
                .orElseThrow(() -> new AdminNotFoundException("존재하지 않는 유저입니다."));

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        admin.pwUpdate(encodedPassword);
    }


    @Transactional
    public FindOwnAdminResponse findOwn(SessionAdmin sessionAdmin) {
        Admin admin = adminRepository.findById(sessionAdmin.getId()).orElseThrow(
                () -> new AdminNotFoundException("존재하지 않는 유저입니다.")
        );
        return new FindOwnAdminResponse(
                admin.getName(),
                admin.getEmail(),
                admin.getPhoneNumber()
        );
    }

    @Transactional
    public UpdateOwnAdminResponse updateOwn(SessionAdmin sessionAdmin, UpdateOwnAdminRequest request) {
        Admin admin = adminRepository.findById(sessionAdmin.getId()).orElseThrow(
                () -> new AdminNotFoundException("존재하지 않는 유저입니다.")
        );
        admin.updateOwn(
                request.getName(),
                request.getEmail(),
                request.getPhoneNumber());
        return new UpdateOwnAdminResponse(
                admin.getName(),
                admin.getEmail(),
                admin.getPhoneNumber());
    }
}
