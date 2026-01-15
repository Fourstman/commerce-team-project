package com.commerceteamproject.customer.exception;

import com.commerceteamproject.common.ServiceException;
import org.springframework.http.HttpStatus;

public class InvalidParameterException extends ServiceException {
    public InvalidParameterException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
