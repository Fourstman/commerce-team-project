package com.commerceteamproject.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {

    private final boolean success;
    private final HttpStatus code;
    private final String message;
    private final T data;

    // 성공
    public static <T> ApiResponse<T> success (T data) {
        return new ApiResponse<>(true, HttpStatus.OK, null, data);
    }

    public static <T> ApiResponse<T> success (String message, T data) {
        return new ApiResponse<>(true, HttpStatus.OK, message, data);
    }

    // 실패
    public static ApiResponse<Void> error(HttpStatus status) {
        return new ApiResponse<>(false, status, null, null);
    }

    // 실패 + 메시지
    public static ApiResponse<Void> error(HttpStatus status, String message) {
        return new ApiResponse<>(false, status, message, null);
    }
}
