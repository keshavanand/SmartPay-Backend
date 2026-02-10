package com.keshav.smartpay.common.exceptions;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistException extends BaseException {

    public UserAlreadyExistException(String message, String errorCode, HttpStatus status) {
        super(message, errorCode, status);
    }
}
