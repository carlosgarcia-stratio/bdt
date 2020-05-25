package com.stratio.qa.models.marathon;

import java.util.Collection;

public class Container {

    private String type;

    private Docker docker;

    private Collection<Volume> volumes;

    private Collection<Port> portMappings;

    public String getType() {
        return type;
    }

    public Docker getDocker() {
        return docker;
    }

    public Collection<Volume> getVolumes() {
        return volumes;
    }

    public Collection<Port> getPortMappings() {
        return portMappings;
    }
}
