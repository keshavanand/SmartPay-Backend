package com.keshav.smartpay.exceptions;

import org.springframework.http.HttpStatus;

public class PasswordMisMatchException extends BaseException {
    public PasswordMisMatchException(String message, String errorCode, HttpStatus status) {
        super(message, errorCode, status);
    }
}
