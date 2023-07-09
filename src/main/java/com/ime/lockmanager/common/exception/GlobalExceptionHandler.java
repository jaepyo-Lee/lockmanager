package com.ime.lockmanager.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApplicationRunException.class)
    public ResponseEntity<ErrorResponse>applicationRunException(ApplicationRunException e){
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(ErrorResponse.of(e));
    }
}
