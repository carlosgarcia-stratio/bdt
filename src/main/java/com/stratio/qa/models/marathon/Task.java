/*
 * Copyright (C) 2014 Stratio (http://stratio.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.stratio.qa.models.marathon;

import java.util.Collection;

public class Task {

    private String id;

    private String slaveId;

    private String host;

    private String state;

    private String startedAt;

    private String stagedAt;

    private Collection<Integer> ports;

    private String version;

    private Collection<IpAddress> ipAddresses;

    private String appId;

    private Collection<Integer> servicePorts;

    private Collection<HealthCheckResults> healthCheckResults;

    private Collection<LocalVolume> localVolumes;

    public Collection<LocalVolume> getLocalVolumes() {
        return localVolumes;
    }

    public String getId() {
        return id;
    }

    public String getSlaveId() {
        return slaveId;
    }

    public String getHost() {
        return host;
    }

    public String getState() {
        return state;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public String getStagedAt() {
        return stagedAt;
    }

    public Collection<Integer> getPorts() {
        return ports;
    }

    public String getVersion() {
        return version;
    }

    public Collection<IpAddress> getIpAddresses() {
        return ipAddresses;
    }

    public String getAppId() {
        return appId;
    }

    public Collection<Integer> getServicePorts() {
        return servicePorts;
    }

    public Collection<HealthCheckResults> getHealthCheckResults() {
        return healthCheckResults;
    }
}
