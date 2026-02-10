package com.keshav.smartpay.domain.dtos;

import com.keshav.smartpay.enums.FinancialInstitutionType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterDTO {
    @NotBlank(message = "Enter a First Name")
    private String firstName;
    @NotBlank(message = "Enter a Last Name")
    private String lastName;
    @NotNull(message = "Select a Financial Institution")
    private FinancialInstitutionType financialInstitutionType;
    @NotBlank(message = "Enter a Email address")
    @Email(message = "Enter a valid email address (example: name@domain.com).")
    private String email;
    @NotBlank(message = "Enter a password")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters long and include a mix of uppercase letters, " +
                    "lowercase letters, numbers, and symbols.")
    private String password;
    @NotBlank(message = "Enter a confirm password")
    private String confirmPassword;

    public boolean isPasswordMatch(){
        return password.equals(confirmPassword);
    }
}
