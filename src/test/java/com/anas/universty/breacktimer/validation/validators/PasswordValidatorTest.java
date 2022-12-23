package com.anas.universty.breacktimer.validation.validators;

import com.anas.universty.breacktimer.validation.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 22/12/2022
 */
class PasswordValidatorTest {

    @Test
    void validate() {
        final var validator = new PasswordValidator();

        assertDoesNotThrow(() -> validator.validate("anas@1Aa"));
        assertDoesNotThrow(() -> validator.validate("anas@1Aa!"));
        assertDoesNotThrow(() -> validator.validate("2312@1Aa!"));
        assertDoesNotThrow(() -> validator.validate("2312@1Aa!@#"));
        assertDoesNotThrow(() -> validator.validate("anasAhmedElgarhy@1Aa!@#"));

        assertThrows(ValidationException.class, () -> validator.validate(null));
        assertThrows(ValidationException.class, () -> validator.validate(""));
        assertThrows(ValidationException.class, () -> validator.validate("a"));
        assertThrows(ValidationException.class, () -> validator.validate("a@"));
        assertThrows(ValidationException.class, () -> validator.validate("a@me"));
        // Test if password is can't be contain  Capital letter
        assertThrows(ValidationException.class, () -> validator.validate("a@me.uytr"));
        // Test if the password is more than 30 characters
        assertThrows(ValidationException.class,
                () -> validator.validate("tttttttttrTttttttttttt@wwwwwwwwwwwwwwwwwwyyyyyyyyyyyI"));
        // Test if the password is contain a space
        assertThrows(ValidationException.class, () -> validator.validate("anas ahmed elgarhy@1Aa!@#"));
    }
}
