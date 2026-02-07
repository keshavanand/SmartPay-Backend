package com.keshav.smartPay.services;

import com.keshav.smartPay.domain.dtos.RegisterDTO;

public interface UserService {

    void register(RegisterDTO registerDTO);
}
