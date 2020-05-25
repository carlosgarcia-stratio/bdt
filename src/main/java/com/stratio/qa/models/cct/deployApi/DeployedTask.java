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

package com.stratio.qa.models.cct.deployApi;

import java.util.List;

public class DeployedTask {

    private String id;

    private String name;

    private String state;

    private int healthy;

    private long timestamp;

    private TaskResources resources;

    private String host;

    private String calicoIP;

    private String marathonServiceName;

    private List<SandboxItem> logs;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public int getHealthy() {
        return healthy;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public TaskResources getResources() {
        return resources;
    }

    public String getHost() {
        return host;
    }

    public String getCalicoIP() {
        return calicoIP;
    }

    public String getMarathonServiceName() {
        return marathonServiceName;
    }

    public List<SandboxItem> getLogs() {
        return logs;
    }
}
