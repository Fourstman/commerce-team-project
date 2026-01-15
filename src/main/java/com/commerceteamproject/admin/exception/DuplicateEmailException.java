package com.commerceteamproject.admin.exception;

import com.commerceteamproject.common.exception.ServiceException;
import org.springframework.http.HttpStatus;

public class DuplicateEmailException extends ServiceException {
    public DuplicateEmailException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}

