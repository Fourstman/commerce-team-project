package com.commerceteamproject.admin.exception;

import com.commerceteam.common.ServiceException;
import org.springframework.http.HttpStatus;

public class AdminRoleIsNullException extends ServiceException {
    public AdminRoleIsNullException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
