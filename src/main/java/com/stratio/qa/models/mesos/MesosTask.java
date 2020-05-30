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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(value = { "health_check" })
public class MesosTask {

    public enum Status {
        TASK_STAGING,
        TASK_STARTING,
        TASK_RUNNING,
        TASK_KILLING,
        TASK_FINISHED,
        TASK_FAILED,
        TASK_KILLED,
        TASK_ERROR,
        TASK_LOST,
        TASK_DROPPED,
        TASK_UNREACHABLE,
        TASK_GONE,
        TASK_GONE_BY_OPERATOR,
        TASK_UNKNOWN
    }

    @JsonProperty("framework_id")
    private String frameworkId;

    @JsonProperty("id")
    private String taskId;

    private String name;

    @JsonProperty("slave_id")
    private String slaveId;

    @JsonProperty("executor_id")
    private String executorId;

    private String state;

    private String directory;

    private TaskResources resources;

    private List<TaskStatus> statuses;

    private String role;

    private List<Map<String, String>> labels;

    private Discovery discovery;

    private Container container;

    public String getRole() {
        return role;
    }

    public String getFrameworkId() {
        return frameworkId;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getName() {
        return name;
    }

    public String getSlaveId() {
        return slaveId;
    }

    public String getExecutorId() {
        return executorId;
    }

    public String getState() {
        return state;
    }

    public String getDirectory() {
        return directory;
    }

    public TaskResources getResources() {
        return resources;
    }

    public List<TaskStatus> getStatuses() {
        return statuses;
    }

    public List<Map<String, String>> getLabels() {
        return labels;
    }

    public Discovery getDiscovery() {
        return discovery;
    }

    public Container getContainer() {
        return container;
    }
}
