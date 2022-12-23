package com.anas.universty.breacktimer.storage;

import com.anas.universty.breacktimer.timer.TimerData;

import java.util.ArrayList;

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
    private ArrayList<TimerData> timers;

    public UserData(final String firstName, final String lastName, final String username,
                    final String email, final String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserData(final int id, final String firstName, final String lastName, final String username,
                    final String email, final String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     * Add new timer to user timers
     *
     * @param timerData the timer data
     */
    public void addTimer(final TimerData timerData) {
        timers.add(timerData);
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
    public ArrayList<TimerData> getTimers() {
        return timers;
    }

    /**
     * Set the timers
     *
     * @param timers the timers to set
     */
    public void setTimers(ArrayList<TimerData> timers) {
        this.timers = timers;
    }
}
