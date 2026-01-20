package com.commerceteamproject.order.exception;

import com.commerceteamproject.common.exception.ServiceException;
import org.springframework.http.HttpStatus;

public class InsufficientStockException extends ServiceException {
    public InsufficientStockException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
