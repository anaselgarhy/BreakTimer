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
    private int workTime;
    private int breakTime;

    public TimerData(final int id, final String name, final String description, final String icon,
                     final int workTime, final int breakTime) {
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.workTime = workTime;
        this.breakTime = breakTime;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getWorkTime() {
        return workTime;
    }

    public void setWorkTime(int workTime) {
        this.workTime = workTime;
    }

    public int getBreakTime() {
        return breakTime;
    }

    public void setBreakTime(int breakTime) {
        this.breakTime = breakTime;
    }
}
