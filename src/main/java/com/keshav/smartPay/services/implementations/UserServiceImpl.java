package com.keshav.smartPay.services.implementations;

import com.keshav.smartPay.domain.dtos.RegisterDTO;
import com.keshav.smartPay.repositories.UserRepository;
import com.keshav.smartPay.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(RegisterDTO registerDTO) {
        if(userRepository.findByEmail(registerDTO.getEmail()).isPresent()){
            throw new UserAlreadyExistException("We can’t create an account with that email. " +
                    "Please sign in, or reset your password if you already have an account.");
        }
        userRepository.save(registerDTO);
    }
}
