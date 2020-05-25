package com.stratio.qa.models.marathon;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GetAppNamespaceResponse {

    @JsonProperty("*")
    private List<App> apps;

    public List<App> getApps() {
        return apps;
    }
}
