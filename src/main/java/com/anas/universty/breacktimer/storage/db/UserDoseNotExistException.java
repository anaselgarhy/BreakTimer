package com.anas.universty.breacktimer.storage.db;

/**
 * The exception that is thrown when the user dose not exist in the database.
 *
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 23/12/2022
 */
public class UserDoseNotExistException extends Exception {
    public UserDoseNotExistException() {
        super("User dose not exists in the database");
    }
}
