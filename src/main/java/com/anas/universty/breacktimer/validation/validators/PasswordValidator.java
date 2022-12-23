package com.anas.universty.breacktimer.validation.validators;

import com.anas.universty.breacktimer.validation.ValidationException;
import com.anas.universty.breacktimer.validation.Validator;

/**
 * Password validator, it validates the password
 * and checks if it's valid or not
 * <li>Password must be at least 8 characters long</li>
 * <li>Password must be at most 30 characters long</li>
 * <li>Password must contain at least one letter</li>
 * <li>Password must contain at least one number</li>
 * <li>Password must contain at least one special character</li>
 * <li>Password must contain at least one uppercase letter</li>
 * <li>Password must contain at least one lowercase letter</li>
 * <li>Password must not contain any spaces</li>
 *
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 22/12/2022
 */
public class PasswordValidator implements Validator {
    @Override
    public void validate(final String input) throws ValidationException {
        if (input == null || input.isEmpty()) {
            throw new ValidationException("Password can't be empty");
        }

        if (input.length() < 8) {
            throw new ValidationException("Password must be at least 8 characters long");
        }

        if (input.length() > 30) {
            throw new ValidationException("Password must be at most 30 characters long");
        }

        if (!input.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}([ ]*)$")) {
            throw new ValidationException("Password must contain at least one digit, one lower case letter, " +
                    "one upper case letter, one special character and no white spaces");
        }
    }
}
