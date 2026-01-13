package com.commerceteamproject.admin.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "admins")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // `이름`, `이메일`, '전화번호', `전화번호`, `역할`, `상태`
    // `가입일`/`승인일` << BaseEntit
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String role;
    private String status;

    public Admin(String name, String email, String password, String phoneNumber, String role, String status) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.status = status;
    }

    // 수정 : 이름, 이메일, 비밀번호
    public void update(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // 비밀번호 수정
    public void pwUpdate(String password){
        this.password = password;
    }
}
