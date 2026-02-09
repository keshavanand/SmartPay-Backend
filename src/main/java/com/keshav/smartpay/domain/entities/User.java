package com.keshav.smartpay.domain.entities;

import com.keshav.smartpay.enums.FinancialInstitutionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

import static jakarta.persistence.GenerationType.SEQUENCE;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "users")
public class User extends Auditable{
    @Id
    @UuidGenerator
    @GeneratedValue(strategy = SEQUENCE, generator = "USER_SEQ")
    @SequenceGenerator(name="USER_SEQ")
    private UUID id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FinancialInstitutionType financialInstitutionType;
    @Column(nullable = false, unique = true)
    @Email(message = "Enter a valid email address (example: name@domain.com).")
    private String email;
    @Column(nullable = false)
    private String hashedPassword;
}
