package com.anas.universty.breacktimer.timer;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 24/12/2022
 */
public interface TimerListener {
    void onTimerUpdate(Clock clock);
    void onStateSwitch(Timer.State state);
}
