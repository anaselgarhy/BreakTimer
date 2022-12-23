package com.anas.universty.breacktimer.validation.validators;

import com.anas.universty.breacktimer.validation.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 22/12/2022
 */
class UsernameValidatorTest {
    @Test
    void validate() {
        final var validator = new UsernameValidator();

        assertDoesNotThrow(() -> validator.validate("anas"));
        assertDoesNotThrow(() -> validator.validate("anas123"));
        assertDoesNotThrow(() -> validator.validate("anas-elgarhy"));

        assertThrows(ValidationException.class, () -> validator.validate(null));
        assertThrows(ValidationException.class, () -> validator.validate(""));
        assertThrows(ValidationException.class, () -> validator.validate("an"));
        assertThrows(ValidationException.class, () -> validator.validate("1an"));
        assertThrows(ValidationException.class, () -> validator.validate("anas1234567890123456789012345"));
    }
}
