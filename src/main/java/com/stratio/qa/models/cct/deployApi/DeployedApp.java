package com.stratio.qa.models.cct.deployApi;

import com.stratio.qa.models.BaseResponse;

import java.util.List;

public class DeployedApp extends BaseResponse {

    private String serviceName;

    private String service;

    private String model;

    private String version;

    private TaskResources resources;

    private int status;

    private int healthy;

    private List<String> actions;

    private List<DeployedTask> tasks;

    private int totalTasks;

    private int totalHealthyTasks;

    private List<External> external;

    public String getServiceName() {
        return serviceName;
    }

    public String getService() {
        return service;
    }

    public String getModel() {
        return model;
    }

    public String getVersion() {
        return version;
    }

    public TaskResources getResources() {
        return resources;
    }

    public int getStatus() {
        return status;
    }

    public int getHealthy() {
        return healthy;
    }

    public List<String> getActions() {
        return actions;
    }

    public List<DeployedTask> getTasks() {
        return tasks;
    }

    public int getTotalTasks() {
        return totalTasks;
    }

    public int getTotalHealthyTasks() {
        return totalHealthyTasks;
    }
}