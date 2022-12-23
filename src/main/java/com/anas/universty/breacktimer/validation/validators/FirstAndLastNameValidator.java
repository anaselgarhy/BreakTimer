package com.anas.universty.breacktimer.validation.validators;

import com.anas.universty.breacktimer.validation.ValidationException;
import com.anas.universty.breacktimer.validation.Validator;

/**
 * First and last name validator, it validates the first and last name input
 * and checks if it's valid or not
 * <li>First and last name must be at least 3 characters long</li>
 * <li>First and last name must be at most 25 characters long</li>
 * <li>First and last name must contain only letters</li>
 *
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 22/12/2022
 */
public class FirstAndLastNameValidator implements Validator {
    @Override
    public void validate(final String input) throws ValidationException {
        if (input == null || input.isEmpty()) {
            throw new ValidationException("First and last name can't be empty");
        }

        if (input.length() < 3) {
            throw new ValidationException("First and last name must be at least 3 characters long");
        }

        if (input.length() > 25) {
            throw new ValidationException("First and last name must be at most 20 characters long");
        }

        if (!input.matches("[a-zA-Z ]+")) {
            throw new ValidationException("First and last name must contain only letters");
        }
    }
}
