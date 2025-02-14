package com.getyourguide.demo;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // Handle 400 - Bad Request
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
        log.warn("Bad Request: {} - {}", request.getRequestURI(), e.getMessage());
        return buildErrorResponse(e, HttpStatus.BAD_REQUEST, request);
    }

    // Handle 404 - Not Found
    @ExceptionHandler({NoSuchElementException.class, ResponseStatusException.class})
    public ResponseEntity<Map<String, Object>> handleNotFoundException(Exception e, HttpServletRequest request) {
        log.warn("Not Found: {} - {}", request.getRequestURI(), e.getMessage());
        return buildErrorResponse(e, HttpStatus.NOT_FOUND, request);
    }

    // Handle 500 - Internal Server Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception e, HttpServletRequest request) {
        log.error("Internal Server Error at {}: {}", request.getRequestURI(), e.getMessage(), e);
        return buildErrorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    // Utility method for consistent error response formatting
    private ResponseEntity<Map<String, Object>> buildErrorResponse(Exception e, HttpStatus status, HttpServletRequest request) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", Instant.now());
        errorResponse.put("status", status.value());
        errorResponse.put("error", status.getReasonPhrase());
        errorResponse.put("message", e.getMessage() != null ? e.getMessage() : "Unexpected error");
        errorResponse.put("path", request.getRequestURI());

        return ResponseEntity.status(status).body(errorResponse);
    }
}
