package com.anas.universty.breacktimer.storage.db;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 23/12/2022
 */
public class RegistrationException extends Exception {
    public RegistrationException(final String message) {
        super("Registration failed: " + message);
    }
}
