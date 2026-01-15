package com.commerceteamproject.common.exception;

import org.springframework.http.HttpStatus;

public class InvalidParameterException extends ServiceException {
    public InvalidParameterException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
