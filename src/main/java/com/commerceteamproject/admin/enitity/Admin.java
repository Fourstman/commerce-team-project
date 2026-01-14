package com.commerceteamproject.admin.enitity;

import com.commerceteamproject.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "admins")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String phoneNumber;
    private AdminRole adminRole;
    private AdminStatus adminStatus;
    private LocalDateTime approvedAt;

    public Admin(String name, String email, String password,
                 String phoneNumber, AdminRole adminRole) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.adminRole = adminRole;
        this.adminStatus = AdminStatus.PENDING;
    }
}
