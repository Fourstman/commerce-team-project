package com.commerceteamproject.admin.entity;

import com.commerceteamproject.common.exception.AdminStatusNotAllowedException;
import com.commerceteamproject.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import com.commerceteamproject.admin.exception.UnauthorizedException;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "admins")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at is NULL")
@SQLDelete(sql = "UPDATE admins SET deleted_at = NOW() WHERE id = ?")
public class Admin extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // `이름`, `이메일`, '전화번호', `전화번호`, `역할`, `상태`, '승인일'
    // `가입일(createdAt)` << BaseEntit
    @Column(nullable = false)   // 공백 금지
    private String name;
    @Column(nullable = false, unique = true) // 중복 체크
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)    // Enum 타입 저장 시 문자열 그대로 저장
    private AdminRole adminRole;
    @Enumerated(EnumType.STRING)
    private AdminStatus adminStatus;

    private LocalDateTime approvedAt; // 승인일

    private  LocalDateTime rejectedAt;  // 거부 일시
    private String rejectReason;    // 거부 사유

    private LocalDateTime deletedAt;


    public Admin(String name, String email, String password, String phoneNumber,
                 AdminRole adminrole) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.adminRole = adminrole;
        this.adminStatus = AdminStatus.PENDING;
    }

    // 수정 : 이름, 이메일, 비밀번호
    public void update(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // 관리자 역할 변경
    public void changeRole(AdminRole adminRole) {
        this.adminRole = adminRole;
        this.adminStatus = AdminStatus.ACTIVATION;
    }

    public void updateOwn(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // 관리자 상태 변경
    public void changeStatus(AdminStatus adminStatus) {
        this.adminStatus = adminStatus;
    }

    // 관리자 승인
    public void approve() {
        if (this.adminStatus != AdminStatus.PENDING) {
            throw new UnauthorizedException("승인 대기 상태의 관리자만 승인할 수 있습니다.");
        }

        this.adminStatus = AdminStatus.ACTIVATION;
        this.approvedAt = LocalDateTime.now();
    }

    // 관리자 거부
    public void reject(String reason) {
        if (this.adminStatus != AdminStatus.PENDING) {
            throw new UnauthorizedException("승인 대기 상태의 관리자만 거부할 수 있습니다.");
        }

        if (reason == null || reason.trim().isEmpty()) {
            throw new AdminStatusNotAllowedException("거부 사유는 필수입니다.");
        }

        this.adminStatus = AdminStatus.REJECTION;
        this.rejectReason = reason;
        this.rejectedAt = LocalDateTime.now();
    }

    // 비밀번호 수정
    public void pwUpdate(String password){
        this.password = password;
    }
}
