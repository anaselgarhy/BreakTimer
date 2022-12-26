package com.anas.universty.breacktimer.ui;

import com.anas.universty.breacktimer.storage.UserData;
import com.anas.universty.breacktimer.timer.TimerData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 22/12/2022
 */
public class MainUI extends JFrame {

    private JPanel mainPanel;
    private JLabel addTimerLabel;
    private JPanel timersPanel;
    private JLabel theirNoTimersYetLabel;
    private JLabel reloadIcon;
    private final UserData userData;

    public MainUI(final UserData userData) {
        this.userData = userData;

        super.setContentPane(mainPanel);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.pack();
        super.setLocationRelativeTo(null); // center the form

        addTheListeners();
        setupUI();
        updateUI(userData);

        super.setVisible(true);
    }

    private void setupUI() {
        // Change the cursor to hand cursor when the mouse is over the reload icon
        reloadIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void updateUI(final UserData userData) {
        // Clear the timers panel
        timersPanel.removeAll();
        if (!userData.getTimers().isEmpty()) {
            // Change the timers panel to be a grid layout
            timersPanel.setLayout(new GridLayout(userData.getTimers().size(), 1, 0, 10));
            // Add the timers widgets
            for (final TimerData timerData : userData.getTimers().values()) {
                final var timerWidget = new TimerWidget(timerData);
                timerWidget.addViewButtonListener(e -> {
                    // Open the timer form
                    new TimerDialog(this, timerData);
                    dispose();
                });
                timersPanel.add(timerWidget);
            }
        } else {
            // Change the timers panel to be a border layout
            timersPanel.setLayout(new BorderLayout());
            // Add the "There are no timers yet" label
            timersPanel.add(theirNoTimersYetLabel, BorderLayout.CENTER);
        }
        // Refresh the timers panel
        timersPanel.revalidate();
    }

    private void addTheListeners() {
        addTimerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                new EditTimerDialog(null, userData);
            }
        });
        // Add the mouse listener to the reload icon
        reloadIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                updateUI(userData);
            }
        });
    }

    public UserData getUserData() {
        return userData;
    }

    private void createUIComponents() {
        mainPanel = new JPanel();
    }
}
