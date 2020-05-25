package com.stratio.qa.models.marathon;

import java.util.HashMap;
import java.util.Map;

public class Port {

    private Integer containerPort;

    private Integer hostPort;

    private Integer servicePort;

    private String protocol;

    private String name;

    private Map<String, String> labels = new HashMap<>();

    public Integer getContainerPort() {
        return containerPort;
    }

    public Integer getHostPort() {
        return hostPort;
    }

    public Integer getServicePort() {
        return servicePort;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getLabels() {
        return labels;
    }
}
