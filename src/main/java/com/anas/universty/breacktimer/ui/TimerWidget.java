package com.anas.universty.breacktimer.ui;

import com.anas.universty.breacktimer.timer.TimerData;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 22/12/2022
 */
public class TimerWidget extends JPanel {
    private JPanel panel1;
    private JLabel timerNameLabel;
    private JLabel workTimeLabel;
    private JLabel breakTimeLabel;
    private JButton viewButton;
    private final int id;

    /**
     * Create a new timer widget
     *
     * @param timerData the timer data
     */
    public TimerWidget(final TimerData timerData) {
        this.id = timerData.getId();
        super.add(panel1);

        // Set up the widget data
        timerNameLabel.setText(timerData.getName());
        timerNameLabel.setIcon(new ImageIcon(timerData.getIcon()));
        workTimeLabel.setText(timerData.getWorkTime().toString());
        breakTimeLabel.setText(timerData.getBreakTime().toString());
    }

    public void addViewButtonListener(final ActionListener listener) {
        viewButton.addActionListener(listener);
    }

    /**
     * Get the timer id
     *
     * @return the timer id
     */
    public int getId() {
        return id;
    }
}
