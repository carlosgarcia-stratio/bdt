package com.stratio.qa.models.cct.marathonServiceApi;

import java.util.List;

public class DeployedServiceTask {

    private String id;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public Healthiness getHealthiness() {
        return healthiness;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public Resources getResources() {
        return resources;
    }

    public String getHost() {
        return host;
    }

    public String getSecuredHost() {
        return securedHost;
    }

    public String getFrameworkId() {
        return frameworkId;
    }

    public List<TaskLog> getLogs() {
        return logs;
    }

    private String name;

    private TaskStatus status;

    private Healthiness healthiness;

    private Long timestamp;

    private Resources resources;

    private String host;

    private String securedHost;

    private String frameworkId;

    private List<TaskLog> logs;
}
