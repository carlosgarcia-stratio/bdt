package com.stratio.qa.models.marathon;

public class TaskFailure {

    private String appId;

    private String host;

    private String message;

    private String state;

    private String taskId;

    private String timestamp;

    private String version;

    private String slaveId;

    public String getAppId() {
        return appId;
    }

    public String getHost() {
        return host;
    }

    public String getMessage() {
        return message;
    }

    public String getState() {
        return state;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getVersion() {
        return version;
    }

    public String getSlaveId() {
        return slaveId;
    }
}
