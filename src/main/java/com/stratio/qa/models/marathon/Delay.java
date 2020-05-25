package com.stratio.qa.models.marathon;

public class Delay {

    private boolean overdue;

    private int timeLeftSeconds;

    public int getTimeLeftSeconds() {
        return timeLeftSeconds;
    }

    public boolean isOverdue() {
        return overdue;
    }
}
