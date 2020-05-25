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

package com.stratio.qa.models.mesos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.stratio.qa.models.utils.UnixTimestampDeserializer;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MesosSlave {

    private String id;

    private String pid;

    private String hostname;

    private int port;

    public int getPort() {
        return port;
    }

    @JsonProperty("registered_time")
    @JsonDeserialize(using = UnixTimestampDeserializer.class)
    private Date registeredTime;

    @JsonProperty("reregistered_time")
    @JsonDeserialize(using = UnixTimestampDeserializer.class)
    private Date reregisteredTime;

    private TaskResources resources;

    @JsonProperty("used_resources")
    private TaskResources usedResources;

    @JsonProperty("offered_resources")
    private TaskResources offeredResources;

    @JsonProperty("reserved_resources")
    private Map<String, TaskResources> reservedResources;

    @JsonProperty("unreserved_resources")
    private TaskResources unreservedResources;

    private HashMap<String, Object> attributes;

    private boolean active;

    private String version;

    private List<String> capabilities;

    @JsonProperty("TASK_STAGING")
    private int taskStaging;

    @JsonProperty("TASK_STARTING")
    private int taskStarting;

    @JsonProperty("TASK_RUNNING")
    private int taskRunning;

    @JsonProperty("TASK_KILLING")
    private int taskKilling;

    @JsonProperty("TASK_FINISHED")
    private int taskFinished;

    @JsonProperty("TASK_KILLED")
    private int taskKilled;

    @JsonProperty("TASK_FAILED")
    private int taskFailed;

    @JsonProperty("TASK_LOST")
    private int taskLost;

    @JsonProperty("TASK_ERROR")
    private int taskError;

    @JsonProperty("TASK_UNREACHABLE")
    private int taskUnreachable;

    @JsonProperty("framework_ids")
    private List<String> frameworkIds;

    public String getVersion() {
        return version;
    }

    public boolean getActive() {
        return active;
    }

    public String getId() {
        return id;
    }

    public String getPid() {
        return pid;
    }

    public String getHostname() {
        return hostname;
    }

    public Date getRegisteredTime() {
        return registeredTime;
    }

    public void setRegisteredTime(Date registeredTime) {
        this.registeredTime = registeredTime;
    }

    public Date getReregisteredTime() {
        return reregisteredTime;
    }

    public List<String> getCapabilities() {
        return capabilities;
    }

    public TaskResources getResources() {
        return resources;
    }

    public TaskResources getUsedResources() {
        return usedResources;
    }

    public TaskResources getOfferedResources() {
        return offeredResources;
    }

    public HashMap<String, Object> getAttributes() {
        return attributes;
    }

    public Map<String, TaskResources> getReservedResources() {
        return reservedResources;
    }

    public TaskResources getUnreservedResources() {
        return unreservedResources;
    }


}
