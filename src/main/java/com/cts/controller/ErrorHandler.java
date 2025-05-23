package com.cts.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cts.dto.ErrorResponce;
import com.cts.exceptions.BikeNotFoundException;

import java.util.HashMap;
import java.util.Map;

// Handles errors in the application and provides user-friendly responses
@ControllerAdvice
public class ErrorHandler {

	// Handles errors when a requested bike is not found
    @ExceptionHandler(BikeNotFoundException.class)
    public ResponseEntity<ErrorResponce> handleBikeNotFound(BikeNotFoundException ex) {
        ErrorResponce response = new ErrorResponce(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // Handles validation errors when input data is incorrect
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    // Handles errors when an unsupported HTTP method is used
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponce> handleInvalidHttpRequest(HttpRequestMethodNotSupportedException ex) {
        ErrorResponce response = new ErrorResponce(ex.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
    }

    // Handles any unexpected errors in the system
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponce> handleGenericException(Exception ex) {
        ErrorResponce response = new ErrorResponce("Unexpected error: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
