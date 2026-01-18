package com.commerceteamproject.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ServiceException extends RuntimeException {

    private final HttpStatus status;
    private final String code;

    public ServiceException(String message, HttpStatus status) {
        this("SERVICE_ERROR", status, message);
    }

    public ServiceException(String code, HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.code = code;
    }
}
