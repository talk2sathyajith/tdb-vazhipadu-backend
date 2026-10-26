package com.temple.tdb_vazhipadu_backend.exception;

import java.time.LocalDateTime;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.converter.HttpMessageNotReadableException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public record ApiError(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path
    ) {}

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {
        return build(
            HttpStatus.NOT_FOUND,
            "NOT_FOUND",
            ex.getMessage(),
            request
        );
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ApiError> handleBusinessRule(
            BusinessRuleException ex,
            HttpServletRequest request) {
        return build(
            HttpStatus.BAD_REQUEST,
            "BUSINESS_RULE_VIOLATION",
            ex.getMessage(),
            request
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        String message = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error ->
                error.getField() + ": " + error.getDefaultMessage())
            .findFirst()
            .orElse("Request validation failed");

        return build(
            HttpStatus.BAD_REQUEST,
            "VALIDATION_ERROR",
            message,
            request
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleUnreadableRequest(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {
        return build(
            HttpStatus.BAD_REQUEST,
            "INVALID_REQUEST",
            "Request body is missing or malformed",
            request
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnexpected(
            Exception ex,
            HttpServletRequest request) {
        return build(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "INTERNAL_ERROR",
            "An unexpected error occurred",
            request
        );
    }

    private ResponseEntity<ApiError> build(
            HttpStatus status,
            String error,
            String message,
            HttpServletRequest request) {
        ApiError body = new ApiError(
            LocalDateTime.now(),
            status.value(),
            error,
            message,
            request.getRequestURI()
        );

        return ResponseEntity.status(status).body(body);
    }
}