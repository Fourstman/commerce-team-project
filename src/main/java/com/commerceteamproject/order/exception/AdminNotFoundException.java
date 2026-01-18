package com.commerceteamproject.order.exception;

import com.commerceteamproject.common.exception.ServiceException;
import org.springframework.http.HttpStatus;

public class AdminNotFoundException extends ServiceException {
    public AdminNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
