package com.anas.universty.breacktimer.ui;

import com.anas.universty.breacktimer.Colors;
import com.anas.universty.breacktimer.storage.UserData;
import com.anas.universty.breacktimer.timer.TimerData;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.sql.SQLException;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 23/12/2022
 */
public class EditTimerDialog extends JDialog {
    private JPanel mainPanel;
    private JLabel timerIcon;
    private JTextField timerNameTextField;
    private JTextArea descriptionTextArea;
    private JSpinner workTimeSpinner;
    private JSpinner breakTimeSpinner;
    private JButton saveButton;
    private JButton removeButton;
    private String iconPath;
    private final UserData userData;
    private int timerId;

    public EditTimerDialog(final TimerData timerData, final UserData userData) {
        this.userData = userData;
        timerId = -1;
        super.setContentPane(mainPanel);
        super.setModal(true);
        super.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        if (timerData != null) {
            iconPath = timerData.getIcon();
            timerId = timerData.getId();
            timerIcon.setIcon(new ImageIcon(iconPath));
            timerNameTextField.setText(timerData.getName());
            descriptionTextArea.setText(timerData.getDescription());
            workTimeSpinner.setValue(timerData.getWorkTime().toMinutes());
            breakTimeSpinner.setValue(timerData.getBreakTime().toMinutes());

            // Enable the remove and save buttons
            saveButton.setEnabled(true);
            removeButton.setEnabled(true);
        }

        setupUI();
        setupTheListeners();
        super.pack();
        super.setLocationRelativeTo(null); // center the dialog

        super.setVisible(true);
    }

    private void setupUI() {
        mainPanel.setBackground(Colors.BACKGROUND_COLOR.color());
        timerIcon.setBackground(Colors.BACKGROUND_COLOR.color());
        timerNameTextField.setBackground(Colors.BACKGROUND_COLOR.color());
        timerNameTextField.setForeground(Colors.TEXT_COLOR.color());
        descriptionTextArea.setBackground(Colors.INPUT_FIELD_COLOR.color());
        descriptionTextArea.setForeground(Colors.INPUT_FIELD_TEXT_COLOR.color());

        timerIcon.setCursor(new Cursor(Cursor.HAND_CURSOR)); // change the cursor to hand cursor

        // set the spinner model
        workTimeSpinner.setModel(new SpinnerNumberModel(15, 1, Integer.MAX_VALUE, 1));
        breakTimeSpinner.setModel(new SpinnerNumberModel(5, 1, Integer.MAX_VALUE, 1));

        workTimeSpinner.setEditor(new JSpinner.NumberEditor(workTimeSpinner, "#"));
        breakTimeSpinner.setEditor(new JSpinner.NumberEditor(breakTimeSpinner, "#"));
    }

    private void setupTheListeners() {
        timerIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                super.mouseClicked(e);
                final var fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg"));
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.showOpenDialog(null);
                final var selectedFile = fileChooser.getSelectedFile();
                if (selectedFile != null) {
                    iconPath = selectedFile.getAbsolutePath();
                    timerIcon.setIcon(new ImageIcon(iconPath));
                    saveButton.setEnabled(true);
                } else {
                    saveButton.setEnabled(false);
                }
            }
        });

        timerNameTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(final KeyEvent e) {
                super.keyReleased(e);
                // Enable the save button if the timer name is not empty, otherwise disable it
                saveButton.setEnabled(!timerNameTextField.getText().isEmpty());
            }
        });

        saveButton.addActionListener(e -> {
            final var name = timerNameTextField.getText();
            final var description = descriptionTextArea.getText();
            final var workTime = (int) workTimeSpinner.getValue();
            final var breakTime = (int) breakTimeSpinner.getValue();

            // check if the user entered the data correctly
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Please enter a name for the timer", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (iconPath == null) {
                JOptionPane.showMessageDialog(null, "Please select an icon for the timer",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Add the timer to the user data and save it to the database
            try {
                userData.addTimer(new TimerData(timerId, name, description, iconPath, workTime, breakTime));
                super.dispose();
            } catch (final SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "An error occurred while saving the timer",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        removeButton.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this, "Are you sure you want to remove this timer?",
                    "Remove Timer", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                // Remove the timer from the database and close the dialog
                try {
                    userData.removeTimer(timerId);
                    super.dispose();
                } catch (final SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "An error occurred while removing the timer",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add a listener directly to the dialog to close it when the user press the escape key
        super.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    if (JOptionPane.showConfirmDialog(null,
                            "Are you sure you want to close the dialog, without saving?",
                            "Close Dialog", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        EditTimerDialog.super.dispose();
                    }
                }
            }
        });
    }

    private void createUIComponents() {
        mainPanel = new JPanel();
    }
}
