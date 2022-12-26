package com.anas.universty.breacktimer.validation.validators;

import com.anas.universty.breacktimer.validation.ValidationException;
import com.anas.universty.breacktimer.validation.Validator;

/**
 * Username validator, it validates the username input
 * and checks if it's valid or not
 * <li>Username must be at least 3 characters long</li>
 * <li>Username must be at most 25 characters long</li>
 * <li>Username must contain only letters and numbers, and `., -, _`</li>
 * <li>Username must start with a letter</li>
 *
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 22/12/2022
 */
public class UsernameValidator implements Validator {
    @Override
    public void validate(final String username) throws ValidationException {
        if (username == null || username.isEmpty()) {
            throw new ValidationException("Username can't be empty");
        }

        if (username.length() < 3) {
            throw new ValidationException("Username must be at least 3 characters long");
        }

        if (username.length() > 25) {
            throw new ValidationException("Username must be at most 25 characters long");
        }

        if (!username.matches("[a-zA-Z0-9_.-]+")) {
            throw new ValidationException("Username must contain only letters and numbers");
        }

        if (username.matches("[0-9]+[a-zA-Z]+")) {
            throw new ValidationException("Username must start at least one letter");
        }
    }
}
