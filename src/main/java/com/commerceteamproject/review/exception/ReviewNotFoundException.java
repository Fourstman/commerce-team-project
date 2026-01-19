package com.commerceteamproject.review.exception;

import com.commerceteamproject.common.exception.ServiceException;
import org.springframework.http.HttpStatus;

public class ReviewNotFoundException extends ServiceException {
    public ReviewNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
