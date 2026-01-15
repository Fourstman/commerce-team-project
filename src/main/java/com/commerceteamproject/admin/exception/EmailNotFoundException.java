package com.commerceteamproject.admin.exception;

import com.commerceteam.common.ServiceException;
import org.springframework.http.HttpStatus;

public class EmailNotFoundException extends ServiceException {
    public EmailNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}