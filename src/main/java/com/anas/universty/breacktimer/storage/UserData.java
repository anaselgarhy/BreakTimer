package com.anas.universty.breacktimer.storage;

import com.anas.universty.breacktimer.storage.db.DatabaseHelper;
import com.anas.universty.breacktimer.timer.TimerData;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 22/12/2022
 */
public class UserData {
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private Map<Integer, TimerData> timers;

    public UserData(final String firstName, final String lastName, final String username,
                    final String email, final String password) {
        this(-1, firstName, lastName, username, email, password, new HashMap<>());
    }

    public UserData(final int id, final String fistName, final String lastName, final String username,
                    final String email, final String password) {
        this(id, fistName, lastName, username, email, password, new HashMap<>());
    }

    public UserData(final int id, final String firstName, final String lastName, final String username,
                    final String email, final String password, final HashMap<Integer, TimerData> timers) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.timers = timers;
    }

    public UserData(final String username, final String password) {
        this(-1, null, null, username, null, password);
    }

    /**
     * Add new timer to user timers, or update the timer if it already exists
     * <p>This method updates the database too</p>
     *
     * @param timerData the timer data
     * @throws SQLException if there is an error while updating the database
     */
    public void addTimer(final TimerData timerData) throws SQLException {
        if (timerData.getId() == -1) {
            timerData.setId(DatabaseHelper.INSTANCE.addTimer(timerData, this.id));
            timers.put(timerData.getId(), timerData);
        } else {
            DatabaseHelper.INSTANCE.updateTimer(timerData);
            timers.replace(timerData.getId(), timerData);
        }

    }

    /*----------------------- Setters and Getters -----------------------*/

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set the firstName
     *
     * @param firstName the firstName to set
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set the lastName
     *
     * @param lastName the lastName to set
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username
     *
     * @param username the username to set
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the email
     *
     * @param email the email to set
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password
     *
     * @param password the password to set
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * @return the timers
     */
    public Map<Integer, TimerData> getTimers() {
        return timers;
    }

    /**
     * Set the timers
     *
     * @param timers the timers to set
     */
    public void setTimers(HashMap<Integer, TimerData> timers) {
        this.timers = timers;
    }

    /**
     * Remove timer from user timers, and delete it from the database
     *
     * @param timerId the timer id to remove
     * @throws SQLException if there is an error while deleting the timer from the database.
     */
    public TimerData removeTimer(final int timerId) throws SQLException {
        if (timers.containsKey(timerId)) {
            DatabaseHelper.INSTANCE.deleteTimer(timerId);
            return timers.remove(timerId);
        }
        return null;
    }
}
