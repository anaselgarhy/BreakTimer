package com.anas.universty.breacktimer.storage.db;

import com.anas.universty.breacktimer.storage.UserData;
import com.anas.universty.breacktimer.timer.TimerData;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple database helper class, it's used to connect to the database and execute queries on it
 *
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.3
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
        user.getTimers().putAll(fetchTimers(user.getId()));

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

    /**
     * Register a new user.
     *
     * @param firstName the first name of the user
     * @param lastName the last name of the user
     * @param username the username of the user
     * @param email the email of the user
     * @param password the password of the user
     * @return the user data, with the proper id
     * @throws RegistrationException if the username or email is already taken
     * @throws SQLException if there is an error in the database
     * @throws LoginException if there is an error in the database
     */
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
        statement = connection.prepareStatement("INSERT INTO users (first_name, last_name, username, email, password) " +
                "VALUES (?, ?, ?, ?, ?)");
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
     * Update the user data.
     * @param user the new user data
     * @throws SQLException if there is an error in the database
     */
    public void updateUser(final UserData user) throws SQLException {
        final var statement = connection.prepareStatement("UPDATE users SET first_name = ?, last_name = ?, " +
                "username = ?, email = ?, password = ? WHERE id = ?");
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.setString(3, user.getUsername());
        statement.setString(4, user.getEmail());
        statement.setString(5, user.getPassword());
        statement.setInt(6, user.getId());

        // Execute the query
        statement.executeUpdate();
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

    /**
     * Delete a user, and all of his timers by username.
     * @param username the username of the user to delete
     * @param password the password of the user to delete
     * @throws UserDoseNotExistException if the user does not exist
     * @throws SQLException if there is an error in the database
     */
    public void deleteUser(final String username, final String password) throws UserDoseNotExistException, SQLException {
        // Check if the user exists
        if (!usernameExists(username)) {
            throw new UserDoseNotExistException();
        }

        // Delete the user
        deleteUser(new UserData(username, password));
    }

    /**
     * Delete a user, and all of his timers.
     * @param user the user to delete
     * @throws UserDoseNotExistException if the user does not exist in the database
     * @throws SQLException if there is an error in the database
     */
    public void deleteUser(final UserData user) throws UserDoseNotExistException, SQLException {
        var userId = user.getId();
        try {
           userId = login(user.getUsername(), user.getPassword()).getId();
        } catch (final LoginException e) {
            throw new UserDoseNotExistException();
        }

        // Delete the timers
        deleteUserTimers(userId);

        // Delete the user
        var statement = connection.prepareStatement("DELETE FROM users WHERE id = ?");
        statement.setInt(1, userId);

        // Execute the query
        statement.executeUpdate();
    }

    private void deleteUserTimers(final int userId) throws SQLException {
        var statement = connection.prepareStatement("DELETE FROM timers WHERE user_id = ?");
        statement.setInt(1, userId);

        // Execute the query
        statement.executeUpdate();
    }

    public int addTimer(final TimerData timer, final int userID) throws SQLException {
        final var statement = connection.prepareStatement("INSERT INTO timers (user_id, name, description, icon, work_time, break_time) VALUES (?, ?, ?, ?, ?, ?)");
        statement.setInt(1, userID);
        statement.setString(2, timer.getName());
        statement.setString(3, timer.getDescription());
        statement.setString(4, timer.getIcon());
        statement.setInt(5, timer.getWorkTime().toMinutes());
        statement.setInt(6, timer.getBreakTime().toMinutes());

        // Execute the query
        statement.executeUpdate();

        // Get the id of the timer
        final var resultSet = connection.prepareStatement("SELECT nextval('timers_id_seq')").executeQuery();

        resultSet.next();

        return resultSet.getInt(1) - 1;
    }

    public void updateTimer(final TimerData timer) throws SQLException {
        final var statement = connection.prepareStatement("UPDATE timers SET name = ?, description = ?, icon = ?, " +
                "work_time = ?, break_time = ? WHERE id = ?");
        statement.setString(1, timer.getName());
        statement.setString(2, timer.getDescription());
        statement.setString(3, timer.getIcon());
        statement.setInt(4, timer.getWorkTime().toMinutes());
        statement.setInt(5, timer.getBreakTime().toMinutes());
        statement.setInt(6, timer.getId());

        // Execute the query
        statement.executeUpdate();
    }

    /**
     * Delete a timer by id.
     * @param timerID the id of the timer to delete
     * @throws SQLException if there is an error in the database
     */
    public void deleteTimer(final int timerID) throws SQLException {
        final var statement = connection.prepareStatement("DELETE FROM timers WHERE id = ?");
        statement.setInt(1, timerID);

        // Execute the query
        statement.executeUpdate();
    }

    public Map<Integer, TimerData> fetchTimers(final int userID) throws SQLException {
        final var statement = connection.prepareStatement("SELECT * FROM timers WHERE user_id = ?");
        statement.setInt(1, userID);

        // Execute the query
        final var resultSet = statement.executeQuery();

        final var timers = new HashMap<Integer, TimerData>();

        while (resultSet.next()) {
            final var id = resultSet.getInt("id");
            timers.put(id, new TimerData(
                    id,
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getString("icon"),
                    resultSet.getInt("work_time"),
                    resultSet.getInt("break_time")
            ));
        }

        return timers;
    }
}
