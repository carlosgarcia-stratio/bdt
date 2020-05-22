package com.stratio.qa.models.mesos;

import com.stratio.qa.models.BaseResponse;

import java.util.List;

public class MesosStateSummary extends BaseResponse {

    private String hostname;

    private String cluster;

    private List<MesosSlave> slaves;

    private List<MesosFramework> frameworks;

    public String getHostname() {
        return hostname;
    }

    public String getCluster() {
        return cluster;
    }

    public List<MesosSlave> getSlaves() {
        return slaves;
    }

    public List<MesosFramework> getFrameworks() {
        return frameworks;
    }
}
