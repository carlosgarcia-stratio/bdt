package com.stratio.qa.models.marathon;

public class HealthCheckResults {

    private Boolean alive;

    private Integer consecutiveFailures;

    private String firstSuccess;

    private String lastFailure;

    private String lastSuccess;

    private String lastFailureCause;

    private String taskId;

    private String instanceId;

    public String getInstanceId() {
        return instanceId;
    }

    public Boolean getAlive() {
        return alive;
    }

    public Integer getConsecutiveFailures() {
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

    public String getLastFailureCause() {
        return lastFailureCause;
    }

    public String getTaskId() {
        return taskId;
    }
}
