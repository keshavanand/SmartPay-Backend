package com.keshav.smartpay.core.user;

import com.keshav.smartpay.api.auth.RegisterRequest;

public interface UserService {

    void register(RegisterRequest registerDTO);
}
