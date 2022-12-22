package com.universty.anas.breacktimer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 20/12/2022
 */

public class LoginForm extends JFrame implements ActionListener {

    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final JButton cancelButton;

    public LoginForm() {
        // Set the title and layout of the frame
        setTitle("Login");
        setLayout(new BorderLayout());

        // Create the username label and field
        final var usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);

        // Create the password label and field
        final var passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);

        // Create the login and cancel buttons
        JLabel registerLabel = new JLabel("Don't have an account? Register here");
        loginButton = new JButton("Login");
        cancelButton = new JButton("Cancel");

        // Add action listeners to the buttons
        loginButton.addActionListener(this);
        cancelButton.addActionListener(this);
        // Add action listener to the register label
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                super.mouseClicked(e);
                // Open the register form
                new RegistrationForm();
                // Close the login form
                dispose();
            }
        });

        // Style the register label
        registerLabel.setForeground(Colors.LINK_COLOR.color());
        registerLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        // Create a panel for the labels and fields
        final var fieldPanel = new JPanel(new GridLayout(2, 2));
        fieldPanel.add(usernameLabel);
        fieldPanel.add(usernameField);
        fieldPanel.add(passwordLabel);
        fieldPanel.add(passwordField);

        // Create a panel for the buttons
        final var buttonPanel = new JPanel();
        buttonPanel.add(registerLabel);
        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);

        // Add the panels to the frame
        add(fieldPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Set the size of the frame and make it visible
        setSize(300, 150);
        setVisible(true);
    }

    public void actionPerformed(final ActionEvent e) {
        // If the login button was clicked, check the username and password
        if (e.getSource() == loginButton) {
            // Check the username and password
            if (checkLogin(usernameField.getText(), passwordField.getPassword())) {
                // Login successful, do something here
            } else {
                // Login failed, show an error message
            }
        } else if (e.getSource() == cancelButton) { // If the cancel button was clicked, close the frame
            dispose();
        }
    }

    private boolean checkLogin(final String username, final char[] password) {
        // Check the username and password and return true if they are correct
        return true;
    }
}
