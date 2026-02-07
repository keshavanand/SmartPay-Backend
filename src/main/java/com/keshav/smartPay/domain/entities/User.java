package com.keshav.smartPay.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Setter
@Getter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "USER_SEQ")
    @SequenceGenerator(name="USER_SEQ")
    private UUID id;
    private String email;
    private String hashedPassword;
}
