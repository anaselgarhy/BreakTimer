package com.anas.universty.breacktimer.ui;

import com.anas.universty.breacktimer.timer.Clock;
import com.anas.universty.breacktimer.timer.Timer;
import com.anas.universty.breacktimer.timer.TimerData;
import com.anas.universty.breacktimer.timer.TimerListener;

import javax.swing.*;
import java.util.Objects;

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
        super.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        super.pack();

        timerNameLabel.setText(timerData.getName());
        timerLabel.setText(timerData.getWorkTime().toString());
        updateNoteLabel();

        setupTheListeners();
    }

    private void setupTheListeners() {
        startButton.addActionListener(e -> {
            if (Timer.INSTANCE.isRunning()) {
                // Start the timer
                Timer.INSTANCE.setTimerData(timerData);
                Timer.INSTANCE.setListener(this);
                Timer.INSTANCE.start(Timer.State.WORKING);
                // Change the button icon
                startButton.setIcon(new ImageIcon(Objects.requireNonNull(
                        getClass().getResource("src/main/resources/stop-button_graid.png"))));
            } else {
                // Stop the timer
                Timer.INSTANCE.stop();
                // Change the button icon
                startButton.setIcon(new ImageIcon(Objects.requireNonNull(
                        getClass().getResource("src/main/resources/play.png"))));
            }
        });

        editButton.addActionListener(e -> {
            final var editDialog = new EditTimerDialog(timerData, mainUI.getUserData());
            editDialog.setUpdateListener(mainUI);
            editDialog.setVisible(true);
        });
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
    }

    private void createUIComponents() {
        mainPanel = new JPanel();
    }
}
