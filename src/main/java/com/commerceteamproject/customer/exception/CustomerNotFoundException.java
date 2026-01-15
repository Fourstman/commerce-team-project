package com.commerceteamproject.customer.exception;

import com.commerceteamproject.common.exception.ServiceException;
import org.springframework.http.HttpStatus;

public class CustomerNotFoundException extends ServiceException {
    public CustomerNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
