package com.keshav.smartpay.services.implementations;

import com.keshav.smartpay.domain.dtos.RegisterDTO;
import com.keshav.smartpay.domain.entities.User;
import com.keshav.smartpay.domain.mappers.RegisterDTOMapper;
import com.keshav.smartpay.exceptions.PasswordMisMatchException;
import com.keshav.smartpay.exceptions.UserAlreadyExistException;
import com.keshav.smartpay.repositories.UserRepository;
import com.keshav.smartpay.services.Encoder;
import com.keshav.smartpay.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Encoder encoder;

    @Override
    public void register(RegisterDTO registerDTO) {
        if(userRepository.findByEmail(registerDTO.getEmail()).isPresent()){
            throw new UserAlreadyExistException("We can’t create an account with that email. " +
                    "Please sign in, or reset your password if you already have an account."
                    ,"UNPROCESSABLE_CONTENT", HttpStatus.UNPROCESSABLE_CONTENT);
        }
        if(!registerDTO.isPasswordMatch()){
            throw new PasswordMisMatchException("Passwords do not match. Please re-enter and try again.,"
                    ,"UNPROCESSABLE_CONTENT", HttpStatus.UNPROCESSABLE_CONTENT);
        }
        User user = RegisterDTOMapper.INSTANCE.registerDtoToUser(registerDTO);
        log.info("User: {}", user.toString());
        user.setHashedPassword(encoder.encode(registerDTO.getPassword()));
        userRepository.save(user);
    }
}
