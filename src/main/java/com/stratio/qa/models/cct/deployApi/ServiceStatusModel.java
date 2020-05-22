package com.stratio.qa.models.cct.deployApi;

import com.stratio.qa.models.BaseResponse;

import java.util.List;

public class ServiceStatusModel extends BaseResponse {

    private String serviceName;

    private String service;

    private String model;

    private int status;

    private int healthy;

    private List<String> actions;

    public String getServiceName() {
        return serviceName;
    }

    public String getService() {
        return service;
    }

    public String getModel() {
        return model;
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
}
