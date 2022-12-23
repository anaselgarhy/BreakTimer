package com.anas.universty.breacktimer.ui;

import com.anas.universty.breacktimer.storage.UserData;
import com.anas.universty.breacktimer.timer.TimerData;

import javax.swing.*;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 22/12/2022
 */
public class MainUI {

    private JList<TimerWidget> timersWidgetsList;
    private JButton addNewTimerButton;
    private DefaultListModel<TimerWidget> timersWidgetsListModel;

    public MainUI(final UserData userData) {
        for (final TimerData timerData : userData.getTimers()) {
            timersWidgetsListModel.addElement(new TimerWidget(timerData));
        }
    }

    private void createUIComponents() {
        timersWidgetsListModel = new DefaultListModel<>();
        timersWidgetsList = new JList<>(timersWidgetsListModel);
    }
}
