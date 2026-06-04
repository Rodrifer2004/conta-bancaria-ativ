package com.example.conta_bancaria.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<String> handleOptimisticLock(
        ObjectOptimisticLockingFailureException ex)
        {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Conflito de atualização. Tente novamente");
        }
}
