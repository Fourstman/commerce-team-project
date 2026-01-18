package com.commerceteamproject.order.exception;

import com.commerceteamproject.common.exception.ServiceException;
import org.springframework.http.HttpStatus;

public class OrderStatusNotAllowedException extends ServiceException {
    public OrderStatusNotAllowedException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
