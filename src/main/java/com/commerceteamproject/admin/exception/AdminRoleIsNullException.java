package com.commerceteamproject.admin.exception;

import com.commerceteamproject.common.exception.ServiceException;
import org.springframework.http.HttpStatus;

public class AdminRoleIsNullException extends ServiceException {
    public AdminRoleIsNullException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
