package com.anas.universty.breacktimer.ui;

import com.anas.universty.breacktimer.storage.UserData;
import com.anas.universty.breacktimer.timer.TimerData;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 22/12/2022
 */
public class MainUI extends JFrame implements UpdateListener {

    private JPanel mainPanel;
    private JList<TimerWidget> timersWidgetsList;
    private JButton addNewTimerButton;
    private DefaultListModel<TimerWidget> timersWidgetsListModel;
    private final UserData userData;

    public MainUI(final UserData userData) {
        this.userData = userData;

        super.setContentPane(mainPanel);
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        super.pack();

        addTheListeners();

        updateUI(userData);
    }

    private void updateUI(final UserData userData) {
        for (final TimerData timerData : userData.getTimers().values()) {
            timersWidgetsListModel.addElement(new TimerWidget(timerData));
        }
    }

    private void addTheListeners() {
        addNewTimerButton.addActionListener(e -> {
            final var addDialog = new EditTimerDialog(null, userData);
            addDialog.setUpdateListener(this);
            addDialog.setVisible(true);
        });

        // Open the timer when the user clicks on it
        timersWidgetsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    final var timerWidget = timersWidgetsList.getSelectedValue();
                    final var timerData = userData.getTimers().get(timerWidget.getId());
                    final var timerDialog = new TimerDialog(timerData);
                    timerDialog.setStateListener(timerWidget);
                    timerDialog.setVisible(true);
                }
            }
        });
    }

    private void createUIComponents() {
        timersWidgetsListModel = new DefaultListModel<>();
        timersWidgetsList = new JList<>(timersWidgetsListModel);
    }

    @Override
    public void onAddNewTimer(final TimerData timerData) {
        timersWidgetsListModel.addElement(new TimerWidget(timerData));
    }

    @Override
    public void onRemoveTimer(final TimerData timerData) {
        for (var i = 0; i < timersWidgetsListModel.size(); i++) {
            if (timersWidgetsListModel.get(i).getId() == timerData.getId()) {
                timersWidgetsListModel.remove(i);
                break;
            }
        }
    }

    @Override
    public void onUpdateTimer(final TimerData timerData) {
        for (var i = 0; i < timersWidgetsListModel.size(); i++) {
            if (timersWidgetsListModel.get(i).getId() == timerData.getId()) {
                timersWidgetsListModel.set(i, new TimerWidget(timerData));
                break;
            }
        }
    }
}
