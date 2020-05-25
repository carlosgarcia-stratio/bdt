package com.stratio.qa.models.marathon;

import java.util.HashMap;
import java.util.Map;

public class Network {

    public String name;

    public String mode;

    private Map<String, String> labels = new HashMap<>();

    public String getName() {
        return name;
    }

    public String getMode() {
        return mode;
    }

    public Map<String, String> getLabels() {
        return labels;
    }
}
