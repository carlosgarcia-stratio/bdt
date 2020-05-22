package com.stratio.qa.models.cct.marathonServiceApi;

import java.util.List;

public class DeployedServiceExposition {

    private String pattern;

    private String port;

    private List<String> tags;

    public String getPattern() {
        return pattern;
    }

    public String getPort() {
        return port;
    }

    public List<String> getTags() {
        return tags;
    }
}
