package com.commerceteamproject.admin.exception;

import com.commerceteamproject.common.ServiceException;
import org.springframework.http.HttpStatus;

public class AdminStatusNotActivateException extends ServiceException {
    public AdminStatusNotActivateException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}