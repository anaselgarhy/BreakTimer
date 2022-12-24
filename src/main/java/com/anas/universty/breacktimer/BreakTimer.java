package com.anas.universty.breacktimer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 17/12/2022
 */
public class BreakTimer {

    private JFrame frame;
    private JTextField frequencyField;
    private JTextField durationField;
    private JLabel statusLabel;
    private boolean running;
    private long frequency;
    private long duration;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    BreakTimer window = new BreakTimer();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public BreakTimer() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel frequencyLabel = new JLabel("Frequency (minutes):");
        frequencyLabel.setBounds(40, 40, 150, 16);
        frame.getContentPane().add(frequencyLabel);

        frequencyField = new JTextField();
        frequencyField.setBounds(200, 37, 134, 28);
        frame.getContentPane().add(frequencyField);
        frequencyField.setColumns(10);

        JLabel durationLabel = new JLabel("Duration (minutes):");
        durationLabel.setBounds(40, 80, 150, 16);
        frame.getContentPane().add(durationLabel);

        durationField = new JTextField();
        durationField.setColumns(10);
        durationField.setBounds(200, 77, 134, 28);
        frame.getContentPane().add(durationField);

        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> start());
        startButton.setBounds(40, 120, 117, 29);
        frame.getContentPane().add(startButton);

        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stop();
            }
        });
        stopButton.setBounds(200, 120, 117, 29);
        frame.getContentPane().add(stopButton);

        statusLabel = new JLabel("");
        statusLabel.setBounds(40, 160, 300, 16);
        frame.getContentPane().add(statusLabel);
    }

    private void start() {
        // Parse frequency and duration
        try {
            frequency = Long.parseLong(frequencyField.getText());
            duration = Long.parseLong(durationField.getText());
        } catch (NumberFormatException e) {
            statusLabel.setText("Invalid frequency or duration");
            return;
        }

        // Start timer
        running = true;
        statusLabel.setText("Running");
        new Thread(new Runnable() {
            public void run() {
                while (running) {
                    try {
                        TimeUnit.MINUTES.sleep(frequency);
                        if (running) {
                            statusLabel.setText("Break time!");
                            TimeUnit.MINUTES.sleep(duration);
                            if (running) {
                                statusLabel.setText("Back to work!");
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void stop() {
        running = false;
        statusLabel.setText("Stopped");
    }

}

