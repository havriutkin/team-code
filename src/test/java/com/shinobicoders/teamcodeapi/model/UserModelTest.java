package com.shinobicoders.teamcodeapi.model;

import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Set;

public class UserModelTest {
    private final LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();

    @Test
    @DisplayName("Creating user with invalid email should throw exception")
    void testInvalidEmail(){
        // Prepare
        String badEmail = "BadEmail";
        User user = new User(badEmail);

        // Perform action
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // Assert
        Assertions.assertEquals(1, violations.size());
        ConstraintViolation<User> violation = violations.iterator().next();
        Assertions.assertEquals("Email should be valid", violation.getMessage());
    }
}
