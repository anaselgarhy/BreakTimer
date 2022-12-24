package com.anas.universty.breacktimer.timer;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @version 1.0
 * @since 22/12/2022
 */
public class TimerData {
    private int id;
    private String name;
    private String description;
    private String icon;
    private Clock workClock;
    private Clock breakClock;

    public TimerData(final int id, final String name, final String description, final String icon,
                     final int workTime, final int breakTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.workClock = Clock.fromMinutes(workTime);
        this.breakClock = Clock.fromMinutes(breakTime);
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(final String icon) {
        this.icon = icon;
    }

    public Clock getWorkTime() {
        return workClock;
    }

    public void setWorkTime(final Clock workTime) {
        this.workClock = workTime;
    }

    public Clock getBreakTime() {
        return breakClock;
    }

    public void setBreakTime(final Clock breakTime) {
        this.breakClock = breakTime;
    }
}
