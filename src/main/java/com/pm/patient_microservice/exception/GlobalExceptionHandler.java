package com.pm.patient_microservice.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleConstraintViolation(MethodArgumentNotValidException ex){
        HashMap<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        ErrorDetails errorDetails  = new ErrorDetails(
                LocalDateTime.now(), "Validation Failed",
                "Check the 'validationErrors' field for details",
                 errors
                );

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDetails> handleConstraintViolation(DataIntegrityViolationException ex) {
        ErrorDetails error = new ErrorDetails(
                LocalDateTime.now(),
                "Database Constraint Violation",
                ex.getMostSpecificCause().getMessage(),
                null
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex) {
        ErrorDetails error = new ErrorDetails(
                LocalDateTime.now(),
                "An unexpected error occurred",
                ex.getMessage(), // In production, consider hiding the raw message
                null
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity <ErrorDetails> handlePatientNotFoundException(PatientNotFoundException ex){
        ErrorDetails error = new ErrorDetails(
                LocalDateTime.now(), ex.getMessage(), null, null
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
