package com.anas.universty.breacktimer.validation.validators;

import com.anas.universty.breacktimer.validation.ValidationException;
import com.anas.universty.breacktimer.validation.Validator;

/**
 * Email validator, it validates the email input and checks if it's valid or not
 * <li>Email must be at least 3 characters long</li>
 * <li>Email must be start with a letter</li>
 * <li>Email must contain only letters, numbers, and the following characters: . _ - @</li>
 * <li>Email must contain at least one @</li>
 * <li>Email must contain at least one .</li>
 *
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 22/12/2022
 */
public class EmailValidator implements Validator {
    @Override
    public void validate(final String input) throws ValidationException {
        if (input == null || input.isEmpty()) {
            throw new ValidationException("Email can't be empty");
        }

        if (!input.matches("^[A-Za-z0-9+_.-]+@.+$")) {
            throw new ValidationException("Invalid email");
        }
    }
}
