package com.anas.universty.breacktimer.storage.db;

import com.anas.universty.breacktimer.storage.UserData;
import com.anas.universty.breacktimer.timer.TimerData;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A simple database helper class, it's used to connect to the database and execute queries on it
 *
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 21/12/2022
 */
public enum DatabaseHelper {
    INSTANCE("break_timer", "anas", "");

    private Connection connection;

    DatabaseHelper(final String databaseName, final String user, final String password) {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + databaseName, user, password);
        } catch (final SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage() + " - Open the console for more details!",
                    "Error in connecting to database",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    /**
     * Login the user, and return the user data.
     *
     * @param username the username
     * @param password the password
     * @return the user data
     * @throws LoginException if the username or password is wrong
     * @throws SQLException   if there is an error in the database
     */
    public UserData login(final String username, final String password) throws LoginException, SQLException {
        var statement = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
        statement.setString(1, username);
        statement.setString(2, password);

        // Execute the query
        var resultSet = statement.executeQuery();

        // Check if the result set is empty
        if (!resultSet.next()) {
            throw new LoginException();
        }

        final var user = new UserData(resultSet.getInt("id"), resultSet.getString("first_name"),
                resultSet.getString("last_name"), resultSet.getString("username"),
                resultSet.getString("email"), resultSet.getString("password"));

        // Fetch the timers
        statement = connection.prepareStatement("SELECT * FROM timers WHERE user_id = ?");
        statement.setInt(1, user.getId());

        resultSet = statement.executeQuery();

        while (resultSet.next()) {
            user.addTimer(new TimerData(resultSet.getInt("id"), resultSet.getString("name"),
                    resultSet.getString("description"), resultSet.getString("icon"),
                    resultSet.getInt("work_time"), resultSet.getInt("break_time")));
        }

        return user;
    }

    /**
     * Check if the username is already taken.
     * @param username the username to check
     * @return true if the username is already taken, false otherwise
     * @throws SQLException if there is an error in the database
     */
    public boolean usernameExists(final String username) throws SQLException {
        final var statement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
        statement.setString(1, username);

        // Execute the query and check if the result set is empty (if not, the user exists)
        return statement.executeQuery().next();
    }

    /**
     * Check if the email is already taken.
     * @param email the email to check
     * @return true if the email is already taken, false otherwise
     * @throws SQLException if there is an error in the database
     */
    public boolean emailExists(final String email) throws SQLException {
        final var statement = connection.prepareStatement("SELECT * FROM users WHERE email = ?");
        statement.setString(1, email);

        // Execute the query and check if the result set is empty (if not, the user exists)
        return statement.executeQuery().next();
    }

    public UserData registerUser(final String firstName, final String lastName, final String username,
                                 final String email, final String password)
            throws RegistrationException, SQLException, LoginException {
        // Double check if the username or email exists, if so, throw an exception
        var statement = connection.prepareStatement("SELECT * FROM users WHERE username = ? OR email = ?");
        statement.setString(1, username);
        statement.setString(2, email);

        // Execute the query
        var resultSet = statement.executeQuery();

        // Check if the result set is empty
        if (resultSet.next()) {
            throw new RegistrationException("Username or email already exists!");
        }

        // Insert the user
        statement = connection.prepareStatement("INSERT INTO users (first_name, last_name, username, email, password) VALUES (?, ?, ?, ?, ?)");
        statement.setString(1, firstName);
        statement.setString(2, lastName);
        statement.setString(3, username);
        statement.setString(4, email);
        statement.setString(5, password);

        // Execute the query
        statement.executeUpdate();

        return login(username, password);
    }

    /**
     * Close connection.
     * <p>Please call this method when you are done with the database</p>
     */
    public void close() {
        try {
            connection.close();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }
}
