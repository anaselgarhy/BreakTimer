package com.anas.universty.breacktimer.ui;

import com.anas.universty.breacktimer.Colors;
import com.anas.universty.breacktimer.storage.UserData;
import com.anas.universty.breacktimer.storage.db.DatabaseHelper;
import com.anas.universty.breacktimer.validation.ValidationException;
import com.anas.universty.breacktimer.validation.validators.UsernameValidator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The login form.
 *
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 22/12/2022
 */
public class LoginForm extends JFrame {
    private JPanel panel1;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel registerLabel;

    public LoginForm() {
        super.setTitle("Login Form");
        super.setContentPane(panel1);

        setupRegisterLabel();
        setupLoginButton();

        super.pack();
        super.setVisible(true);
    }

    private void setupLoginButton() {
        loginButton.addActionListener(e -> {
            try {
                new UsernameValidator().validate(usernameField.getText());
                new UsernameValidator().validate(String.valueOf(passwordField.getPassword()));
            } catch (final ValidationException exception) {
                JOptionPane.showMessageDialog(this, exception.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                final var userData = DatabaseHelper.INSTANCE.login(usernameField.getText(),
                        String.valueOf(passwordField.getPassword()));

                JOptionPane.showMessageDialog(this, "Login successful", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new MainUI(userData);
            } catch (final Exception exception) {
                JOptionPane.showMessageDialog(this, "Invalid username or password", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void setupRegisterLabel() {
        // Add action listener to the register label
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                // Open the register form
                new RegistrationForm(LoginForm.this);
            }
        });

        // Style the register label
        registerLabel.setForeground(Colors.LINK_COLOR.color());
        registerLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
    }

    /**
     * Fill the username field with the given username, and the password field with the given password,
     * in the registration form.
     * @param user the user data
     */
    public void updateFields(final UserData user) {
        usernameField.setText(user.getUsername());
        passwordField.setText(user.getPassword());
    }
}
