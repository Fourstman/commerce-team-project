package com.commerceteamproject.common.exception;

import org.springframework.http.HttpStatus;

public class AdminStatusNotAllowedException extends ServiceException {
    public AdminStatusNotAllowedException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
