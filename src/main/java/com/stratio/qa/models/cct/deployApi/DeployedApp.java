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

import com.stratio.qa.models.BaseResponse;

import java.util.List;

public class DeployedApp extends BaseResponse {

    public static final String ACTION_UPDATE = "update";

    public static final String ACTION_UPGRADE = "upgrade";

    public static final String ACTION_DELETE = "delete";

    public static final String ACTION_START = "start";

    public static final String ACTION_STOP = "stop";

    public static final String ACTION_OPEN_SERVICE = "open_service";

    private String serviceName;

    private String service;

    private String model;

    private String version;

    private TaskResources resources;

    private int status;

    private int healthy;

    private List<String> actions;

    private List<DeployedTask> tasks;

    private int totalTasks;

    private int totalHealthyTasks;

    private List<External> external;

    public String getServiceName() {
        return serviceName;
    }

    public String getService() {
        return service;
    }

    public String getModel() {
        return model;
    }

    public String getVersion() {
        return version;
    }

    public TaskResources getResources() {
        return resources;
    }

    public int getStatus() {
        return status;
    }

    public int getHealthy() {
        return healthy;
    }

    public List<String> getActions() {
        return actions;
    }

    public List<DeployedTask> getTasks() {
        return tasks;
    }

    public int getTotalTasks() {
        return totalTasks;
    }

    public int getTotalHealthyTasks() {
        return totalHealthyTasks;
    }

    public List<External> getExternal() {
        return external;
    }
}