package com.anas.universty.breacktimer.storage.db;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 23/12/2022
 */
class DatabaseHelperTest {
    private static final Logger LOGGER = Logger.getLogger(DatabaseHelperTest.class.getName());

    @BeforeEach
    void setupTestUser() {
        // Create new user for testing
        LOGGER.info("Creating new user (anas) for testing...");
        try {
            DatabaseHelper.INSTANCE.registerUser("Anas", "Elgarhy",
                    "anas", "anas.elgarhy.dev@gmail.com", "anasA#3");
        } catch (final RegistrationException | SQLException | LoginException e) {
            fail("Registration failed");
        }
    }

    @AfterEach
    void deleteTestUser() {
        // Delete the user
        LOGGER.info("Deleting the user (anas)...");
        try {
            DatabaseHelper.INSTANCE.deleteUser("anas", "anasA#3");
        } catch (final SQLException | UserDoseNotExistException e) {
            LOGGER.warning("Failed to delete test user");
        }
    }

    @Test
    void login() {
        LOGGER.info("Trying to login with correct credentials");
        assertDoesNotThrow(() -> DatabaseHelper.INSTANCE.login("anas", "anasA#3"));

        LOGGER.info("Trying to login with wrong password");
        assertThrows(LoginException.class,
                () -> DatabaseHelper.INSTANCE.login("anas", "anasA#34"));

        LOGGER.info("Trying to login with wrong username");
        assertThrows(LoginException.class,
                () -> DatabaseHelper.INSTANCE.login("anas1", "anasA#3"));

    }

    @Test
    void usernameExists() throws SQLException {
        assertTrue(DatabaseHelper.INSTANCE.usernameExists("anas"));
        assertFalse(DatabaseHelper.INSTANCE.usernameExists("anas1"));

        assertDoesNotThrow(() -> DatabaseHelper.INSTANCE.usernameExists("anas"));
    }

    @Test
    void emailExists() throws SQLException {
        assertDoesNotThrow(() -> DatabaseHelper.INSTANCE.emailExists("anas.elgarhy.dev@gmail.com"));
        
        assertTrue(DatabaseHelper.INSTANCE.emailExists("anas.elgarhy.dev@gmail.com"));
        assertFalse(DatabaseHelper.INSTANCE.emailExists("anaselgarhy@email.com"));
    }

    @Test
    void registerUser() {
        assertThrows(RegistrationException.class, () -> DatabaseHelper.INSTANCE.registerUser("Anas", "Elgarhy",
                "anas", "anuther.email@anas.com", "anasA#3"));

        assertDoesNotThrow(() -> DatabaseHelper.INSTANCE.registerUser("Abdallah", "Madeh",
                "abdallah", "abdulh@mail.com", "abdallahA#3"));

        // Delete the user
        try {
            DatabaseHelper.INSTANCE.deleteUser("abdallah", "abdallahA#3");
        } catch (final SQLException | UserDoseNotExistException e) {
            fail("Failed to delete user");
        }
    }
    @Test
    void deleteUser() {
        LOGGER.info("Trying to delete user (anas) with correct credentials");
        assertDoesNotThrow(() -> DatabaseHelper.INSTANCE.deleteUser("anas", "anasA#3"));

        LOGGER.info("Trying to delete user (anas) with wrong password");
        assertThrows(UserDoseNotExistException.class,
                () -> DatabaseHelper.INSTANCE.deleteUser("anas", "anasA#34"));

        LOGGER.info("Trying to delete user (anas) with wrong username");
        assertThrows(UserDoseNotExistException.class,
                () -> DatabaseHelper.INSTANCE.deleteUser("anas1", "anasA#3"));
    }

    @AfterAll
    static void closeConnection() {
        LOGGER.info("Closing connection...");
        DatabaseHelper.INSTANCE.close();
        LOGGER.info("Connection closed");
    }

    @Test
    void updateUser() throws SQLException, LoginException {
        final var newUserData = DatabaseHelper.INSTANCE.login("anas", "anasA#3");
        newUserData.setFirstName("Abdallah");
        newUserData.setLastName("Madeh");
        newUserData.setEmail("abdallah@email.com");
        assertDoesNotThrow(() -> DatabaseHelper.INSTANCE.updateUser(newUserData));

        final var updatedUserData = DatabaseHelper.INSTANCE.login("anas", "anasA#3");
        assertEquals("Abdallah", updatedUserData.getFirstName());
        assertEquals("Madeh", updatedUserData.getLastName());
        assertEquals("abdallah@email.com", updatedUserData.getEmail());
    }

    @Test
    void addTimer() {
    }

    @Test
    void updateTimer() {
    }

    @Test
    void deleteTimer() {
    }

    @Test
    void fetchTimers() {
    }
}
