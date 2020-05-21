package com.stratio.qa.models.cct.deployApi;

import java.util.List;

public class DeployedTask {

    private String id;

    private String name;

    private String state;

    private int healthy;

    private long timestamp;

    private TaskResources resources;

    private String host;

    private String calicoIP;

    private String marathonServiceName;

    private List<SandboxItem> logs;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public int getHealthy() {
        return healthy;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public TaskResources getResources() {
        return resources;
    }

    public String getHost() {
        return host;
    }

    public String getCalicoIP() {
        return calicoIP;
    }

    public String getMarathonServiceName() {
        return marathonServiceName;
    }

    public List<SandboxItem> getLogs() {
        return logs;
    }
}
