package com.stratio.qa.models.marathon;

public class HealthCheckResult {

    private boolean alive;

    private int consecutiveFailures;

    private String firstSuccess;

    private String lastFailure;

    private String lastSuccess;

    private String taskId;

    public boolean isAlive() {
        return alive;
    }

    public int getConsecutiveFailures() {
        return consecutiveFailures;
    }

    public String getFirstSuccess() {
        return firstSuccess;
    }

    public String getLastFailure() {
        return lastFailure;
    }

    public String getLastSuccess() {
        return lastSuccess;
    }

    public String getTaskId() {
        return taskId;
    }
}
