package com.commerceteamproject.common.exception;

import org.springframework.http.HttpStatus;

public class LoginRequiredException extends ServiceException {
    public LoginRequiredException(String message) {
        super("LOGIN_REQUIRED", HttpStatus.UNAUTHORIZED, message);
    }
}
