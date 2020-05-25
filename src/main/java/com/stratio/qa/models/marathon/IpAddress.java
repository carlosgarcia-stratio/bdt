package com.stratio.qa.models.marathon;

import java.util.List;
import java.util.Map;

public class IpAddress {

    private IpDiscovery discovery;

    private List<String> groups;

    private Map<String, Object> labels;

    private String networkName;

    private String ipAddress;

    private String protocol;

    public String getProtocol() {
        return protocol;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public IpDiscovery getDiscovery() {
        return discovery;
    }

    public List<String> getGroups() {
        return groups;
    }

    public Map<String, Object> getLabels() {
        return labels;
    }

    public String getNetworkName() {
        return networkName;
    }
}
