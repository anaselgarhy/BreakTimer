package com.anas.universty.breacktimer.validation.validators;

import com.anas.universty.breacktimer.validation.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 22/12/2022
 */
class FirstAndLastNameValidatorTest {

    @Test
    void validate() {
        final var validator = new FirstAndLastNameValidator();

        assertDoesNotThrow(() -> validator.validate("Anas"));
        assertDoesNotThrow(() -> validator.validate("Elgarhy"));
        assertDoesNotThrow(() -> validator.validate("Anas Elgarhy"));
        assertDoesNotThrow(() -> validator.validate("Anas Elgarhy Dev"));

        assertThrows(ValidationException.class, () -> validator.validate(null));
        assertThrows(ValidationException.class, () -> validator.validate(""));
        assertThrows(ValidationException.class, () -> validator.validate("a"));
        assertThrows(ValidationException.class, () -> validator.validate("a "));
        assertThrows(ValidationException.class, () -> validator.validate(" a"));
        assertThrows(ValidationException.class, () -> validator.validate("1"));
    }
}
