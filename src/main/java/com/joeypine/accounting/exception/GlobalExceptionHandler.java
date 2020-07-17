package com.joeypine.accounting.exception;

import lombok.val;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ServiceException.class)
    ResponseEntity<?> handleServiceException(ServiceException e) {
        val errorResponse = ErrorResponse.builder()
                .statusCode(e.getStatusCode())
                .message(e.getMessage())
                .code(e.getErrorCode())
                .errorType(e.getErrorType())
                .build();

        return ResponseEntity.status(
                e.getStatusCode() != 0 ? e.getStatusCode() : HttpStatus.INTERNAL_SERVER_ERROR.value())
                .body(errorResponse);
    }

    @ExceptionHandler(IncorrectCredentialsException.class)
    ResponseEntity<?> handleIncorrectCredentialsException(IncorrectCredentialsException ex) {
        val errorResponse = ErrorResponse.builder()
                .statusCode(400)
                .message(ex.getMessage())
                .code("INCORRECT_CREDENTIALS")
                .errorType(ServiceException.ErrorType.Client)
                .build();

        return ResponseEntity.status(400).body(errorResponse);
    }
}
