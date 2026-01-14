package com.commerceteamproject.customer.exception;

import com.commerceteamproject.common.ServiceException;
import org.springframework.http.HttpStatus;

public class LoginRequiredException extends ServiceException {
    public LoginRequiredException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
