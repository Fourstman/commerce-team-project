package com.commerceteamproject.admin.exception;

import com.commerceteamproject.common.exception.ServiceException;
import org.springframework.http.HttpStatus;

// 유효하지 않은 접근 권한(로그인X, 상위 조건 간섭)
public class UnauthorizedException extends ServiceException {
    public UnauthorizedException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
