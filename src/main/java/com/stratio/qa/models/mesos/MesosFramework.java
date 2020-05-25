package com.stratio.qa.models.mesos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MesosFramework {

    private String id;

    private String name;

    private String pid;

    @JsonProperty("used_resources")
    private TaskResources usedResources;

    @JsonProperty("offered_resources")
    private TaskResources offeredResouces;

    private String hostname;

    @JsonProperty("webui_url")
    private String webuiUrl;

    private boolean active;

    private boolean connected;

    private boolean recovered;

    private List<String> capabilities;

    @JsonProperty("TASK_STAGING")
    private int taskStaging;

    @JsonProperty("TASK_STARTING")
    private int taskStarting;

    @JsonProperty("TASK_RUNNING")
    private int taskRunning;

    @JsonProperty("TASK_KILLING")
    private int taskKilling;

    @JsonProperty("TASK_FINISHED")
    private int taskFinished;

    @JsonProperty("TASK_KILLED")
    private int taskKilled;

    @JsonProperty("TASK_FAILED")
    private int taskFailed;

    @JsonProperty("TASK_LOST")
    private int taskLost;

    @JsonProperty("TASK_ERROR")
    private int taskError;

    @JsonProperty("TASK_UNREACHABLE")
    private int taskUnreachable;

    @JsonProperty("slave_ids")
    private List<String> slaveIds;

    public List<String> getSlaveIds() {
        return slaveIds;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPid() {
        return pid;
    }

    public TaskResources getUsedResources() {
        return usedResources;
    }

    public TaskResources getOfferedResouces() {
        return offeredResouces;
    }

    public List<String> getCapabilities() {
        return capabilities;
    }

    public String getHostname() {
        return hostname;
    }

    public String getWebuiUrl() {
        return webuiUrl;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isConnected() {
        return connected;
    }

    public boolean isRecovered() {
        return recovered;
    }
}
