package com.anas.universty.breacktimer.ui;

import com.anas.universty.breacktimer.Colors;
import com.anas.universty.breacktimer.Fonts;
import com.anas.universty.breacktimer.storage.db.DatabaseHelper;
import com.anas.universty.breacktimer.validation.ValidationException;
import com.anas.universty.breacktimer.validation.validators.EmailValidator;
import com.anas.universty.breacktimer.validation.validators.FirstAndLastNameValidator;
import com.anas.universty.breacktimer.validation.validators.PasswordValidator;
import com.anas.universty.breacktimer.validation.validators.UsernameValidator;

import javax.swing.*;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 23/12/2022
 */
public class RegistrationForm extends JDialog {
    private JPanel panel1;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField usernameTextField;
    private JTextField emailTextField;
    private JPasswordField passwordPasswordField;
    private JButton registerButton;
    private JLabel firstNameLabel;
    private JLabel lsastNameLabel;
    private JLabel usernameLabel;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private LoginForm loginForm;

    public RegistrationForm(final LoginForm loginForm) {
        setContentPane(panel1);
        setModal(true);
        setupUI();
        setupTheListeners();
        pack();
        setVisible(true);
        this.loginForm = loginForm;
        loginForm.setVisible(false);
    }

    private void setupTheListeners() {
        registerButton.addActionListener(e -> {
            try {
                final var firstAndLastNameValidator = new FirstAndLastNameValidator();
                firstAndLastNameValidator.validate(firstNameTextField.getText());
                firstAndLastNameValidator.validate(lastNameTextField.getText());

                new UsernameValidator().validate(usernameTextField.getText());

                new EmailValidator().validate(emailTextField.getText());

                new PasswordValidator().validate(String.valueOf(passwordPasswordField.getPassword()));
            } catch (final ValidationException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                final var user = DatabaseHelper.INSTANCE.registerUser(firstNameTextField.getText(), lastNameTextField.getText(),
                        usernameTextField.getText(), emailTextField.getText(), String.valueOf(passwordPasswordField.getPassword()));
                loginForm.updateFields(user);
                loginForm.setVisible(true);
                dispose();
            } catch (final Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void setupUI() {
        // Setup the input fields
        Colors.INPUT_FIELD_COLOR.applyToAllAsBackground(firstNameTextField, lastNameTextField,
                usernameTextField, emailTextField, passwordPasswordField);
        Colors.INPUT_FIELD_TEXT_COLOR.applyToAllAsForeground(firstNameTextField, lastNameTextField,
                usernameTextField, emailTextField, passwordPasswordField);
        // Set the font of the text fields
        Fonts.INPUT_FIELD_FONT.applyToAll(firstNameTextField, lastNameTextField, usernameTextField,
                emailTextField, passwordPasswordField);

        // Set the font of the labels
        Fonts.MAIN_FONT.applyToAll(firstNameLabel, lsastNameLabel, usernameLabel, emailLabel, passwordLabel);

        // Setup the button
        registerButton.setBackground(Colors.SP_BUTTON_COLOR.color());
        registerButton.setForeground(Colors.SP_BUTTON_TEXT_COLOR.color());
        registerButton.setFont(Fonts.SP_BUTTON_FONT.font());
        registerButton.setFocusable(false);
        registerButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }
}
