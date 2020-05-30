package com.stratio.qa.models.mesos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Container {

    private String type;

    private Docker docker;

    private Collection<Volume> volumes;

    private String hostname;

    @JsonProperty("network_infos")
    private List<Map<String, String>> networkInfos;

    public String getType() {
        return type;
    }

    public Docker getDocker() {
        return docker;
    }

    public Collection<Volume> getVolumes() {
        return volumes;
    }

    public String getHostname() {
        return hostname;
    }

    public List<Map<String, String>> getNetworkInfos() {
        return networkInfos;
    }
}
