package com.stratio.qa.models.marathon;

import java.util.Collection;

public class Docker {

    private String image;

    private String network;

    private boolean forcePullImage;

    private Collection<Port> portMappings;

    private Collection<Parameter> parameters;

    private boolean privileged;

    private PullConfig pullConfig;

    public String getImage() {
        return image;
    }

    public String getNetwork() {
        return network;
    }

    public Collection<Port> getPortMappings() {
        return portMappings;
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

    public PullConfig getPullConfig() {
        return this.pullConfig;
    }
}
