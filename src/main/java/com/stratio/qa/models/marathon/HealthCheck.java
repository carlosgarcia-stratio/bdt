package com.stratio.qa.models.marathon;

public class HealthCheck {

    private Command command;

    private Integer gracePeriodSeconds;

    private Integer intervalSeconds;

    private Integer maxConsecutiveFailures;

    private Integer portIndex;

    private Integer port;

    private Integer timeoutSeconds;

    private boolean ignoreHttp1xx;

    private String path;

    private String delaySeconds;

    // protocol is http, https
    private String protocol;

    // ipProtocol is IPv4 or IPv6
    private String ipProtocol;

    public Integer getGracePeriodSeconds() {
        return gracePeriodSeconds;
    }

    public Integer getIntervalSeconds() {
        return intervalSeconds;
    }

    public Integer getMaxConsecutiveFailures() {
        return maxConsecutiveFailures;
    }

    public Integer getPortIndex() {
        return portIndex;
    }

    public Integer getPort() {
        return port;
    }

    public Integer getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public boolean isIgnoreHttp1xx() {
        return ignoreHttp1xx;
    }

    public String getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getIpProtocol() {
        return ipProtocol;
    }

    public Command getCommand() {
        return command;
    }

    public String getDelaySeconds() {
        return delaySeconds;
    }
}
