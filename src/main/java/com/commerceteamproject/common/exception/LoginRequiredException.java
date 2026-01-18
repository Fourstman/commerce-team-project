package com.commerceteamproject.common.exception;

import org.springframework.http.HttpStatus;

public class LoginRequiredException extends ServiceException {
    public LoginRequiredException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
