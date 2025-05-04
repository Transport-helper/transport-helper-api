package com.transport.exceptions;

import org.springframework.http.HttpStatus;

public class DuplicateRouteException extends BaseException {
    public DuplicateRouteException(String message, HttpStatus status) {
        super(message, status);
    }
}
