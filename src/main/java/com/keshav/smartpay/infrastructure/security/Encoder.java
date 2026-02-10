package com.keshav.smartpay.infrastructure.security;

import jakarta.validation.constraints.NotBlank;

public interface Encoder {
    String encode(@NotBlank(message = "Enter a password") String password);
}
