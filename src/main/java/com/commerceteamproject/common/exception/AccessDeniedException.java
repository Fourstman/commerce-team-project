package com.commerceteamproject.common.exception;

import org.springframework.http.HttpStatus;

public class AccessDeniedException extends ServiceException {
    public AccessDeniedException(String message) {
        super("ACCESS_DENIED", HttpStatus.FORBIDDEN, message);
    }
}
