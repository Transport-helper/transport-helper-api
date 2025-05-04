package com.transport.advices;

import com.transport.exceptions.GlobalException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<HashMap<String,String>> handleBaseException(GlobalException e) {
        return new ResponseEntity<>(
                new HashMap<>() {{
                    put("message", e.getMessage());
                }},
                e.getHttpStatus()
        );
    }

}
