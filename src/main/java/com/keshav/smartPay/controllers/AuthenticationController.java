package com.keshav.smartPay.controllers;

import com.keshav.smartPay.domain.dtos.RegisterDTO;
import com.keshav.smartPay.services.AuthenticationService;
import com.keshav.smartPay.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDTO registerDTO){
        userService.register(registerDTO);
        return ResponseEntity.ok("Account Created Successfully");
    }

}
