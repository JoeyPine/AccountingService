package com.joeypine.accounting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Accounting Service ResourceNotFoundException
 */

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends ServiceException {

    public ResourceNotFoundException(String message) {
        super(message);
        this.setStatusCode(HttpStatus.NOT_FOUND.value());
        this.setErrorCode(BizErrorCode.Resourse_NOT_FOUNDE);
        this.setErrorType(ErrorType.Client );
    }
}
