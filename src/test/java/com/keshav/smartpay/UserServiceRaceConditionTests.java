package com.keshav.smartpay;

import com.keshav.smartpay.domain.dtos.RegisterDTO;
import com.keshav.smartpay.enums.FinancialInstitutionType;
import com.keshav.smartpay.repositories.UserRepository;
import com.keshav.smartpay.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@SpringBootTest
class UserServiceRaceConditionTests {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void clearDatabase(){
        userRepository.deleteAll();
    }

    @Test
    void testRaceConditionOnSave() throws InterruptedException {
        RegisterDTO registerDTO = RegisterDTO.builder()
                .firstName("keshav")
                .lastName("singh")
                .email("keshav@gmail.com")
                .financialInstitutionType(FinancialInstitutionType.OTHER)
                .password("Keshav@123")
                .confirmPassword("Keshav@123")
                .build();
        int numOfThreads = 10;

        try (ExecutorService executorService = Executors.newFixedThreadPool(numOfThreads)) {
            CountDownLatch countDownLatch = new CountDownLatch(numOfThreads);

            IntStream.range(0, numOfThreads).forEach(i -> executorService.execute(() -> {
                try{
                    userService.register(registerDTO);
                }
                finally {
                    countDownLatch.countDown();
                }
            }));
            countDownLatch.await(5, TimeUnit.SECONDS);
        }catch (Exception e){
            System.err.println("Error during save: " + e.getMessage());
        }

        assert 1==userRepository.findAll().size();

    }
}
