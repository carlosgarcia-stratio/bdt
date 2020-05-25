package com.stratio.qa.models.marathon;

import java.util.HashMap;
import java.util.Map;

public class PortDefinition {

    private Integer port;

    private String protocol;

    private String name;

    private Map<String, String> labels = new HashMap<>();

    public Integer getPort() {
        return port;
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
