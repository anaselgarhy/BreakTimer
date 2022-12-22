package com.universty.anas.breacktimer.validation;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 22/12/2022
 */
public class ValidationException extends Exception {
    public ValidationException(final String message) {
        super(message);
    }
}
