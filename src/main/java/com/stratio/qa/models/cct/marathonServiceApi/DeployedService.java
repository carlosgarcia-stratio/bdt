package com.stratio.qa.models.cct.marathonServiceApi;

import com.stratio.qa.models.BaseResponse;
import mesosphere.marathon.client.model.v2.Network;

import java.util.List;
import java.util.Map;

public class DeployedService extends BaseResponse {

    private String id;

    private String tenant;

    private String service;

    private String model;

    private String version;

    private String release;

    private String serviceLabel;

    private Resources resources;

    private ServiceStatus status;

    private Healthiness healthiness;

    private List<DeployedServiceTask> tasks;

    private DeployedServiceExposition exposition;

    private Integer instances;

    private Integer totalTasks;

    private Integer totalHealthyTasks;

    private List<Network> networks;

    private List<External> external;

    private Map<String, Object> env;

    public String getId() {
        return this.id;
    }

    public String getTenant() {
        return tenant;
    }

    public String getRelease() {
        return release;
    }

    public String getServiceLabel() {
        return serviceLabel;
    }

    public Resources getResources() {
        return resources;
    }

    public ServiceStatus getStatus() {
        return status;
    }

    public Healthiness getHealthiness() {
        return healthiness;
    }

    public List<DeployedServiceTask> getTasks() {
        return tasks;
    }

    public DeployedServiceExposition getExposition() {
        return exposition;
    }

    public Integer getInstances() {
        return instances;
    }

    public Integer getTotalTasks() {
        return totalTasks;
    }

    public Integer getTotalHealthyTasks() {
        return totalHealthyTasks;
    }

    public List<Network> getNetworks() {
        return networks;
    }

    public List<External> getExternal() {
        return external;
    }

    public Map<String, Object> getEnv() {
        return env;
    }

    public String getService() {
        return this.service;
    }

    public String getModel() {
        return this.model;
    }

    public String getVersion() {
        return this.version;
    }
}
