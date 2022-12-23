package com.anas.universty.breacktimer.storage.db;

/**
 * The login exception.
 *
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 22/12/2022
 */
public class LoginException extends Exception {
    public LoginException() {
        super("Invalid username or password");
    }
}
