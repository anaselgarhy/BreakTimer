package com.universty.anas.breacktimer;

import com.universty.anas.breacktimer.db.DatabaseHelper;
import com.universty.anas.breacktimer.validation.ValidationException;
import com.universty.anas.breacktimer.validation.validators.FirstAndLastNameValidator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 21/12/2022
 */
public class RegistrationForm extends JFrame implements ActionListener {
    private final JTextField firstNameField;
    private final JTextField lastNameField;
    private final JTextField usernameField;
    private final JTextField emailField;
    private final JPasswordField passwordField;
    private final JButton registerButton;

    public RegistrationForm() {
        setTitle("Registration Form");
        setSize(520, 420);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        firstNameField = new JTextField();
        lastNameField = new JTextField();
        usernameField = new JTextField();
        emailField = new JTextField();
        passwordField = new JPasswordField();
        registerButton = new JButton("Register");

        setupUI();  // Setup the UI (Fonts, Colors, etc.)

        registerButton.addActionListener(this);

        add(setupInputFields(), BorderLayout.CENTER);
        add(registerButton, BorderLayout.SOUTH);

        setVisible(true); // Show the form
    }

    private void setupUI() {
        // Setup the input fields
        Colors.INPUT_FIELD_COLOR.applyToAllAsBackground(firstNameField, lastNameField,
                usernameField, emailField, passwordField);
        Colors.INPUT_FIELD_TEXT_COLOR.applyToAllAsForeground(firstNameField, lastNameField,
                usernameField, emailField, passwordField);
        // Set the font of the text fields
        Fonts.INPUT_FIELD_FONT.applyToAll(firstNameField, lastNameField, usernameField, emailField, passwordField);

       // Setup the button
        registerButton.setBackground(Colors.SP_BUTTON_COLOR.color());
        registerButton.setForeground(Colors.SP_BUTTON_TEXT_COLOR.color());
        registerButton.setFont(Fonts.SP_BUTTON_FONT.font());
        registerButton.setFocusable(false);
        registerButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private JPanel setupInputFields() {
        final var fieldsPanel = new JPanel(new GridLayout(0, 2));
        // Set the background color of the panel
        fieldsPanel.setBackground(Colors.BACKGROUND_COLOR.color());
        // Set the font of the panel
        fieldsPanel.setFont(Fonts.MAIN_FONT.font());
        // Set the font color of the panel
        fieldsPanel.setForeground(Colors.TEXT_COLOR.color());

        final var firstNameLabel = new JLabel("First Name: ");
        fieldsPanel.add(firstNameLabel);
        fieldsPanel.add(firstNameField);
        final var lastNameLabel = new JLabel("Last Name: ");
        fieldsPanel.add(lastNameLabel);
        fieldsPanel.add(lastNameField);
        final var usernameLabel = new JLabel("Username: ");
        fieldsPanel.add(usernameLabel);
        fieldsPanel.add(usernameField);
        final var emailLabel = new JLabel("Email: ");
        fieldsPanel.add(emailLabel);
        fieldsPanel.add(emailField);
        final var passwordLabel = new JLabel("Password: ");
        fieldsPanel.add(passwordLabel);
        fieldsPanel.add(passwordField);

        // Set the font of the labels
        Fonts.MAIN_FONT.applyToAll(firstNameLabel, lastNameLabel, usernameLabel, emailLabel, passwordLabel);

        return fieldsPanel;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        try {
            validateTheInputFields();
        } catch (final Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DatabaseHelper.registerUser(firstNameField.getText(), lastNameField.getText(), usernameField.getText(),
                emailField.getText(), passwordField.getPassword());

    }

    private void validateTheInputFields() throws ValidationException {
        if (usernameField.getText().isEmpty()) {
            throw new Exception("Username is required");
        }
        if (emailField.getText().isEmpty()) {
            throw new Exception("Email is required");
        }
        if (passwordField.getPassword().length == 0) {
            throw new Exception("Password is required");
        }

        final var firstAndLastNameValidator = new FirstAndLastNameValidator();
        firstAndLastNameValidator.validate(firstNameField.getText());
        firstAndLastNameValidator.validate(lastNameField.getText());

    }
}
