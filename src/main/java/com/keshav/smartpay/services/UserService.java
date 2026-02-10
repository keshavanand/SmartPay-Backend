package com.keshav.smartpay.services;

import com.keshav.smartpay.domain.dtos.RegisterDTO;

public interface UserService {

    void register(RegisterDTO registerDTO);
}
