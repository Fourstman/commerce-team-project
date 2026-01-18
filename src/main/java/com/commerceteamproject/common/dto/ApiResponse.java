package com.commerceteamproject.common.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {

    private boolean success;
    private String code;
    @Nullable private T data;

    // 성공
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<T>(true, "OK", data);
    }
    // 실패
    public static ApiResponse<Void> error(String code) {
        return new ApiResponse<>(false, code, null);
    }
}
