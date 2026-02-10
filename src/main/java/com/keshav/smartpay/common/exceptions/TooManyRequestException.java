package com.keshav.smartpay.common.exceptions;

import org.springframework.http.HttpStatus;

public class TooManyRequestException extends BaseException {
    public TooManyRequestException(String message, String errorCode, HttpStatus status) {
        super(message, errorCode, status);
    }
}
