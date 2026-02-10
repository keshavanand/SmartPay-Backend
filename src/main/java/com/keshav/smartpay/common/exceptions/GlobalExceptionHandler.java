package com.keshav.smartpay.common.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    ResponseEntity<ErrorResponse> baseExceptionHandler(BaseException ex, HttpServletRequest request){
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .errorCode(ex.getErrorCode())
                        .message(ex.getMessage())
                        .status(ex.getStatus().value())
                        .timestamp(Instant.now())
                        .path(request.getRequestURI())
                        .build(),
                ex.getStatus());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<ErrorResponse> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException ex, HttpServletRequest request){
        HttpStatus status= HttpStatus.BAD_REQUEST;
        String message = (ex.getCause() == null)
                ? "Request body is missing"
                : "Invalid JSON format";

        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .errorCode(status.toString())
                        .message(message)
                        .status((status.value()))
                        .timestamp(Instant.now())
                        .path(request.getRequestURI())
                        .build(),
                status
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex, HttpServletRequest request){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(err->errors.put(err.getField(),err.getDefaultMessage()));
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .errorCode(ex.getStatusCode().toString())
                        .message(errors.toString())
                        .status(ex.getStatusCode().value())
                        .timestamp(Instant.now())
                        .path(request.getRequestURI())
                        .build(),
                ex.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorResponse> exceptionHandler(Exception ex, HttpServletRequest request){
        log.error(ex.getMessage());
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                        .message("INTERNAL SERVER ERROR")
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .timestamp(Instant.now())
                        .path(request.getRequestURI())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
