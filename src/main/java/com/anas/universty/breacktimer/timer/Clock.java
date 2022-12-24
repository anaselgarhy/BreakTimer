package com.anas.universty.breacktimer.timer;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 24/12/2022
 */
public class Clock {
    private int hours;
    private byte minutes;
    private byte seconds;

    public Clock(final int hours, final byte minutes, final byte seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public static Clock fromMinutes(final int minutes) {
        return new Clock(minutes / 60, (byte) (minutes % 60), (byte) 0);
    }

    public static Clock clone(final Clock otherClock) {
        return new Clock(otherClock.hours, otherClock.minutes, otherClock.seconds);
    }

    public int toMinutes() {
        return hours * 60 + minutes;
    }

    public void tick() {
        if (seconds > 0) {
            seconds--;
        } else if (minutes > 0) {
            minutes--;
            seconds = 59;
        } else if (hours > 0) {
            hours--;
            minutes = 59;
            seconds = 59;
        }
    }


    @Override
    public String toString() {
        if (hours == 0) {
            return String.format("%02d:%02d", minutes, seconds);
        } else {
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        }
    }

    public boolean ended() {
        return hours == 0 && minutes == 0 && seconds == 0;
    }
}
