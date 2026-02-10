package com.keshav.smartpay.common.exceptions;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
class ErrorResponse {
    private String errorCode;
    private String message;
    private int status;
    private Instant timestamp;
    private String path;
}
