package com.stratio.qa.models.marathon;

import java.util.Collection;

public class ContainerInfo {

    private String image;

    private Collection<String> options;

    public String getImage() {
        return image;
    }

    public Collection<String> getOptions() {
        return options;
    }
}
