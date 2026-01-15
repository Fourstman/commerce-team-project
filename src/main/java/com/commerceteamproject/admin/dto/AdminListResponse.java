package com.commerceteamproject.admin.dto;


import lombok.Getter;

import java.util.List;

// 응답 관리자 목록
// AdminListItemResponse : ID, 이름, 이메일, 전화번호, 역할, 상태, 가입일, 승인일
// AdminListResponse : 현재 페이지, 페이지당 개수, 전체 개수, 전체 페이지 수 (요청에서 기본값 설정 : 1, 10)
@Getter
public class AdminListResponse {

    private final List<AdminListItemResponse> admins;

    private final int currentPage;
    private final int pageSize;
    private final long totalElements;
    private final int totalPages;

    public AdminListResponse(
            List<AdminListItemResponse> admins,
            int currentPage,
            int pageSize,
            long totalElements,
            int totalPages
    ) {
        this.admins = admins;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }
}