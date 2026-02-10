package com.keshav.smartpay.core.user;

import com.keshav.smartpay.api.auth.RegisterRequest;
import com.keshav.smartpay.common.exceptions.PasswordMisMatchException;
import com.keshav.smartpay.common.exceptions.UserAlreadyExistException;
import com.keshav.smartpay.infrastructure.security.Encoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Encoder encoder;

    @Override
    @Transactional
    public void register(RegisterRequest registerDTO) {
        if(userRepository.findByEmail(registerDTO.getEmail().trim().toLowerCase()).isPresent()){
            throw new UserAlreadyExistException("We can’t create an account with that email. " +
                    "Please sign in, or reset your password if you already have an account."
                    ,"UNPROCESSABLE_CONTENT", HttpStatus.UNPROCESSABLE_CONTENT);
        }
        if(!registerDTO.isPasswordMatch()){
            throw new PasswordMisMatchException("Passwords do not match. Please re-enter and try again.,"
                    ,"UNPROCESSABLE_CONTENT", HttpStatus.UNPROCESSABLE_CONTENT);
        }
        User user = RegisterRequestMapper.INSTANCE.registerRequestToUser(registerDTO);
        log.info("User: {}", user.toString());
        user.setHashedPassword(encoder.encode(registerDTO.getPassword()));
        userRepository.save(user);
    }
}
