package com.commerceteamproject.admin.exception;

import com.commerceteamproject.common.exception.ServiceException;
import org.springframework.http.HttpStatus;

// 권한 외 조회
public class ForbiddenException extends ServiceException {
    public ForbiddenException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
