package com.stratio.qa.models.mesos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stratio.qa.models.marathon.Parameter;

import java.util.Collection;

public class Docker {

    private String image;

    private String network;

    @JsonProperty("force_pull_image")
    private boolean forcePullImage;

    private Collection<Parameter> parameters;

    private boolean privileged;

    public String getImage() {
        return image;
    }

    public String getNetwork() {
        return network;
    }

    public boolean isPrivileged() {
        return privileged;
    }

    public Collection<Parameter> getParameters() {
        return parameters;
    }

    public boolean isForcePullImage() {
        return forcePullImage;
    }
}
