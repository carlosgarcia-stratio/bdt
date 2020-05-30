package com.stratio.qa.models.mesos;

import java.util.HashMap;
import java.util.Map;

public class MesosPort {

    private Integer number;

    private String protocol;

    private String name;

    private Map<String, Object> labels = new HashMap<>();

    public Integer getNumber() {
        return number;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getName() {
        return name;
    }

    public Map<String, Object> getLabels() {
        return labels;
    }
}
