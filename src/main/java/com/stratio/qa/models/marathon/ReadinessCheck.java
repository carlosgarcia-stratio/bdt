package com.stratio.qa.models.marathon;

import java.util.Set;

public class ReadinessCheck {

    private String name;

    private String protocol;

    private String path;

    private String portName;

    private int intervalSeconds;

    private int timeoutSeconds;

    private Set<Integer> httpStatusCodesForReady;

    private boolean preserveLastResponse;

    public String getName() {
        return name;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getPath() {
        return path;
    }

    public String getPortName() {
        return portName;
    }

    public int getIntervalSeconds() {
        return intervalSeconds;
    }

    public int getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public Set<Integer> getHttpStatusCodesForReady() {
        return httpStatusCodesForReady;
    }

    public boolean isPreserveLastResponse() {
        return preserveLastResponse;
    }
}
