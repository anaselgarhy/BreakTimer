package com.anas.universty.breacktimer.ui;

import com.anas.universty.breacktimer.Colors;
import com.anas.universty.breacktimer.Fonts;
import com.anas.universty.breacktimer.timer.Clock;
import com.anas.universty.breacktimer.timer.Timer;
import com.anas.universty.breacktimer.timer.TimerData;
import com.anas.universty.breacktimer.timer.TimerListener;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 23/12/2022
 */
public class TimerDialog extends JDialog implements TimerListener {
    private JPanel mainPanel;
    private JLabel timerLabel;
    private JButton startButton;
    private JButton editButton;
    private JLabel timerNameLabel;
    private JLabel noteLabel;
    private final TimerData timerData;
    private final MainUI mainUI;

    public TimerDialog(final MainUI mainUI, final TimerData timerData) {
        this.mainUI = mainUI;
        this.timerData = timerData;

        super.setContentPane(mainPanel);
        super.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        timerNameLabel.setText(timerData.getName());
        timerLabel.setText(timerData.getWorkTime().toString());

        setupUI();
        updateNoteLabel();
        setupTheListeners(); // Setup the listeners

        super.pack(); // Set the dialog size to the preferred size
        super.setLocationRelativeTo(null);
        super.setVisible(true);
    }

    private void setupUI() {
        mainPanel.setBackground(Colors.BACKGROUND_COLOR.color());
        timerLabel.setForeground(Colors.TEXT_COLOR.color());
        timerNameLabel.setForeground(Colors.TEXT_COLOR.color());
        noteLabel.setForeground(Colors.TEXT_COLOR.color());

        timerLabel.setFont(Fonts.TIMER_FONT.font());

        startButton.setBackground(Colors.SP_BUTTON_COLOR.color());
        startButton.setForeground(Colors.SP_BUTTON_TEXT_COLOR.color());
        startButton.setFont(Fonts.SP_BUTTON_FONT.font());

        editButton.setBackground(Colors.SP_BUTTON_COLOR.color());
        editButton.setForeground(Colors.SP_BUTTON_TEXT_COLOR.color());
        editButton.setFont(Fonts.SP_BUTTON_FONT.font());
    }

    private void setupTheListeners() {
        startButton.addActionListener(e -> {
            if (!Timer.INSTANCE.isRunning()) {
                // Start the timer
                Timer.INSTANCE.setTimerData(timerData);
                Timer.INSTANCE.setListener(this);
                Timer.INSTANCE.start(Timer.State.WORKING);
                // Change the button icon
                startButton.setIcon(new ImageIcon("src/main/resources/stop-button_graid.png"));
                // Change the button text
                startButton.setText("Stop");
            } else {
                // Stop the timer
                Timer.INSTANCE.stop();
                // Change the button icon
                startButton.setIcon(new ImageIcon("src/main/resources/play.png"));
                // Change button text
                startButton.setText("Start");

                // Update the timer label
                updateTimeLabelStatic();
            }
        });

        editButton.addActionListener(e -> new EditTimerDialog(timerData, mainUI.getUserData()));

        addWindowListeners();
    }

    private void updateTimeLabelStatic() {
        if (Timer.isWorkTime()) {
            timerLabel.setText(timerData.getWorkTime().toString());
        } else {
            timerLabel.setText(timerData.getBreakTime().toString());
        }
    }

    private void addWindowListeners() {
        // Add window listener to confirm the user wants to close the dialog, if the timer is running
        super.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                close();
            }
        });

        // Add key listener to close the dialog if the user presses the escape key
        mainPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    close();
                }
            }
        });
    }

    private void close() {
        if (Timer.INSTANCE.isRunning()) {
            if (JOptionPane.showConfirmDialog(TimerDialog.this,
                    "Are you sure you want to close the timer?", "Confirm",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                Timer.INSTANCE.stop(); // Stop the timer
                Timer.INSTANCE.removeListener(); // Remove the listener
                mainUI.setVisible(true); // Show the main UI again
                super.dispose(); // Dispose the dialog
            }
        } else {
            mainUI.setVisible(true); // Show the main UI again
            super.dispose();
        }
    }

    private void updateNoteLabel() {
        noteLabel.setText("left for the " + (Timer.isWorkTime() ? "break" : "work") + " time");
    }

    @Override
    public void onTimerUpdate(final Clock clock) {
        timerLabel.setText(clock.toString());
    }

    @Override
    public void onStateSwitch(final Timer.State state) {
        updateNoteLabel();
        updateTimeLabelStatic();
    }

    private void createUIComponents() {
        mainPanel = new JPanel();
    }
}
