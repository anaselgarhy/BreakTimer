package com.anas.universty.breacktimer.validation.validators;

import com.anas.universty.breacktimer.validation.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 22/12/2022
 */
class EmailValidatorTest {
    @Test
    void validate() {
        final var validator = new EmailValidator();

        assertDoesNotThrow(() -> validator.validate("anas.elgarhy.dev@bmail.com"));
        assertDoesNotThrow(() -> validator.validate("a@me.com"));
        assertDoesNotThrow(() -> validator.validate("anas20-00864@student.eelu.edu.eg"));

        assertThrows(ValidationException.class, () -> validator.validate(null));
        assertThrows(ValidationException.class, () -> validator.validate(""));
        assertThrows(ValidationException.class, () -> validator.validate("a"));
        assertThrows(ValidationException.class, () -> validator.validate("a@"));
        // assertThrows(ValidationException.class, () -> validator.validate("a@me"));
        // assertThrows(ValidationException.class, () -> validator.validate("a@me."));
    }
}
