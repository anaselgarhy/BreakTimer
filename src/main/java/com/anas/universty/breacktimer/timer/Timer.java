package com.anas.universty.breacktimer.timer;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 24/12/2022
 */
public enum Timer {
    INSTANCE; // Singleton enum pattern


    public enum State {
        WORKING,
        BREAKING
    }

    private TimerData timerData;
    private State state;
    private TimerListener listener;
    private Thread timerThread;

    Timer() {
        state = State.BREAKING;
    }

    public void setTimerData(final TimerData timerData) {
        this.timerData = timerData;
    }

    public void start(final State state) throws IllegalStateException {
        if (timerData == null) {
            throw new IllegalStateException("Timer data is null");
        }
        this.state = state;
        startTimer();
    }

    private void startTimer() {
        timerThread = new Thread(() -> {
            final var clock = state == State.WORKING ? timerData.getWorkTime() : timerData.getBreakTime();
            while (clock.ended()) {
                try {
                    Thread.sleep(1000);
                } catch (final InterruptedException e) {
                    e.printStackTrace();
                }
                clock.tick();
                if (listener != null) {
                    listener.onTimerUpdate(clock);
                }
            }
            if (listener != null) {
                listener.onStateSwitch(state == State.WORKING ? State.BREAKING : State.WORKING);
            }
        });
        timerThread.start();
    }

    public void stop() {
        if (timerThread != null) {
            timerThread.interrupt();
        }
        timerThread = null;
    }

    public boolean isRunning() {
        return timerThread != null;
    }

    public static boolean isWorkTime() {
        return INSTANCE.state == State.WORKING;
    }

    public void setListener(final TimerListener listener) {
        this.listener = listener;
    }

    public void removeListener() {
        this.listener = null;
    }
}
