package com.anas.universty.breacktimer.validation;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 22/12/2022
 */
public interface Validator {
    /**
     * Validate the input, if it's not valid then throw an exception, otherwise do nothing
     * @param input The input to validate
     * @throws ValidationException If the input is not valid
     */
    void validate(final String input) throws ValidationException;
}
