package com.commerceteamproject.admin.exception;

import com.commerceteamproject.common.exception.ServiceException;
import org.springframework.http.HttpStatus;

public class RequiredFieldMissingException  extends ServiceException {
    public RequiredFieldMissingException(String message) { super(message, HttpStatus.BAD_REQUEST);}
}
