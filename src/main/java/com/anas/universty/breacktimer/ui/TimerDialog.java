package com.anas.universty.breacktimer.ui;

import com.anas.universty.breacktimer.timer.TimerData;

import javax.swing.*;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 23/12/2022
 */
public class TimerDialog extends JDialog {
    private JPanel mainPanel;
    private JLabel timerLabel;
    private JButton startButton;
    private JButton editButton;
    private JLabel timerNameLabel;
    private JLabel noteLabel;
    private TimerData timerData;

    public TimerDialog(final TimerData timerData) {
        this.timerData = timerData;

        super.setContentPane(mainPanel);
        super.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        super.pack();

        timerNameLabel.setText(timerData.getName());
        noteLabel.setText(timerData.getNote());
    }

}
