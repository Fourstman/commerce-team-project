package com.commerceteamproject.product.exception;

import com.commerceteamproject.common.exception.ServiceException;
import org.springframework.http.HttpStatus;

public class ProductStatusNotAllowedException extends ServiceException {
    public ProductStatusNotAllowedException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
