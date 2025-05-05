package com.transport.advices;

import com.transport.exceptions.GlobalException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

@Hidden
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
