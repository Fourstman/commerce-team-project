package com.commerceteamproject.admin.dto;

import lombok.Getter;

@Getter
// 관리자 삭제할 슈퍼 관리자 비밀번호 확인용
public class AdminDeleteRequest {
    private String password;
}
