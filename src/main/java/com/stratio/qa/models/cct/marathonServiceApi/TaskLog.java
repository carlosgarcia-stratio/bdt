package com.stratio.qa.models.cct.marathonServiceApi;

public class TaskLog {

    private String name;

    private LogAction action;

    private String path;

    private Boolean isRotated;

    public String getName() {
        return name;
    }

    public LogAction getAction() {
        return action;
    }

    public String getPath() {
        return path;
    }

    public Boolean getIsRotated() {
        return isRotated;
    }
}
