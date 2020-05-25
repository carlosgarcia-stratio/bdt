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

package com.stratio.qa.models.cct.marathonServiceApi;

import java.util.List;

public class DeployedServiceTask {

    private String id;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public Healthiness getHealthiness() {
        return healthiness;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public Resources getResources() {
        return resources;
    }

    public String getHost() {
        return host;
    }

    public String getSecuredHost() {
        return securedHost;
    }

    public String getFrameworkId() {
        return frameworkId;
    }

    public List<TaskLog> getLogs() {
        return logs;
    }

    private String name;

    private TaskStatus status;

    private Healthiness healthiness;

    private Long timestamp;

    private Resources resources;

    private String host;

    private String securedHost;

    private String frameworkId;

    private List<TaskLog> logs;
}
