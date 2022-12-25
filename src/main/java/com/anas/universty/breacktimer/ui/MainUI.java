package com.anas.universty.breacktimer.ui;

import com.anas.universty.breacktimer.storage.UserData;
import com.anas.universty.breacktimer.timer.TimerData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 22/12/2022
 */
public class MainUI extends JFrame implements UpdateListener, MouseListener {

    private JPanel mainPanel;
    private JLabel addTimerLabel;
    private JPanel timersPanel;
    private JLabel therNoTimersYetLabel;
    private final UserData userData;

    public MainUI(final UserData userData) {
        this.userData = userData;

        mainPanel = new JPanel();
        super.setContentPane(mainPanel);
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.pack();
        super.setLocationRelativeTo(null); // center the form

        addTheListeners();

       updateUI(userData);

        super.setVisible(true);
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
                // Add the mouse listener to the timer widget
                timerWidget.addMouseListener(this);
                timersPanel.add(new TimerWidget(timerData));
            }
        } else {
            // Change the timers panel to be a border layout
            timersPanel.setLayout(new BorderLayout());
            // Add the "There are no timers yet" label
            timersPanel.add(therNoTimersYetLabel, BorderLayout.CENTER);
        }
    }

    private void addTheListeners() {
        addTimerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                final var addDialog = new EditTimerDialog(null, userData);
                addDialog.setUpdateListener(MainUI.this);
                addDialog.setVisible(true);
            }
        });
    }

    @Override
    public void onAddNewTimer(final TimerData timerData) {
        timersPanel.add(new TimerWidget(timerData));
    }

    @Override
    public void onRemoveTimer(final TimerData timerData) {
        // Update the timers panel
        updateUI(userData);
    }

    @Override
    public void onUpdateTimer(final TimerData timerData) {
        // Update the timers panel
        updateUI(userData);
    }

    public UserData getUserData() {
        return userData;
    }

    @Override
    public void mouseClicked(final MouseEvent e) {
        final var timerData = userData.getTimers().get(((TimerWidget) e.getSource()).getId());
        final var editDialog = new EditTimerDialog(timerData, userData);
        editDialog.setUpdateListener(this);
        editDialog.setVisible(true);
    }

    @Override
    public void mousePressed(final MouseEvent e) {

    }

    @Override
    public void mouseReleased(final MouseEvent e) {

    }

    @Override
    public void mouseEntered(final MouseEvent e) {

    }

    @Override
    public void mouseExited(final MouseEvent e) {

    }
}
