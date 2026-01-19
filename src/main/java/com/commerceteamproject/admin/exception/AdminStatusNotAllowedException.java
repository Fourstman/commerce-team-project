package com.commerceteamproject.admin.exception;

import com.commerceteamproject.common.exception.ServiceException;
import org.springframework.http.HttpStatus;

public class AdminStatusNotAllowedException extends ServiceException {
    public AdminStatusNotAllowedException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
