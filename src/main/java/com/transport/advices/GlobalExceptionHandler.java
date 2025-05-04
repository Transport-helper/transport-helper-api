package com.transport.advices;

import com.transport.exceptions.BaseException;
import com.transport.exceptions.DuplicateRouteException;
import com.transport.exceptions.LocationNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<HashMap<String,String>> handleBaseException(BaseException e) {
        return new ResponseEntity<>(
                new HashMap<>() {{
                    put("message", e.getMessage());
                }},
                e.getHttpStatus()
        );
    }

}
