package com.keshav.smartpay.core.user;

import com.keshav.smartpay.core.user.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(@NotBlank(message = "Enter a Email address") @Email(message = "Enter a valid email address (example: name@domain.com).") String email);
}
