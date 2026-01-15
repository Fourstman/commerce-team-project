package com.commerceteamproject.admin.dto;

import com.commerceteam.admin.entity.AdminRole;
import com.commerceteam.admin.entity.AdminStatus;
import lombok.Getter;
import lombok.Setter;

// 요청에서 기본값 설정
@Getter
// 객체를 기본 생성자로 사용하기에 Setter 붙임 << 이거 대체할 방법 있나?
@Setter
public class AdminListRequest {

    private String keyword;                  // 이름, 이메일 검색
    private AdminRole adminRole;             // 역할 필터
    private AdminStatus adminStatus;         // 상태 필터
    private int page = 1;                    // 기본 1페이지
    private int size = 10;                   // 기본 10개
    private String sortBy = "createdAt";     // 가입일 기준 정렬
    private String direction = "desc";       // 내림차순
}