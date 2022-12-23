package com.anas.universty.breacktimer.ui;

import com.anas.universty.breacktimer.timer.TimerData;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 23/12/2022
 */
public interface UpdateListener {
    void onAddNewTimer(TimerData timerData);
    void onRemoveTimer(TimerData timerData);
    void onUpdateTimer(TimerData timerData);
}
