package com.commerceteamproject.admin.exception;

import com.commerceteam.common.ServiceException;
import org.springframework.http.HttpStatus;

public class PasswordNotMatchException extends ServiceException {
    public PasswordNotMatchException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}