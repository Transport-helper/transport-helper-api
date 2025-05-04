package com.transport.exceptions;


import org.springframework.http.HttpStatus;

public class LocationNotFoundException extends BaseException {
    public LocationNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
